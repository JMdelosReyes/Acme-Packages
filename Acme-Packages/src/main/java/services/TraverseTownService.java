
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TraverseTownRepository;
import security.LoginService;
import domain.Carrier;
import domain.Offer;
import domain.TraverseTown;

@Service
@Transactional
public class TraverseTownService {

	@Autowired
	private TraverseTownRepository	traverseTownRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CarrierService			carrierService;

	@Autowired
	private OfferService			offerService;

	@Autowired
	private Validator				validator;


	public TraverseTownService() {

	}

	public Collection<TraverseTown> findAll() {
		final Collection<TraverseTown> result = this.traverseTownRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public TraverseTown findOne(final int id) {
		Assert.isTrue(id > 0);
		TraverseTown result;
		result = this.traverseTownRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public TraverseTown create() {
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final TraverseTown result = new TraverseTown();

		result.setCurrentTown(false);
		result.setEstimatedDate(null);
		result.setNumber(0);
		result.setTown(null);

		return result;
	}

	public TraverseTown save(final TraverseTown traverseTown) {
		Assert.notNull(traverseTown);
		final TraverseTown result;

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		if (traverseTown.getId() == 0) {
			traverseTown.setCurrentTown(false);
			result = this.traverseTownRepository.save(traverseTown);
			Assert.notNull(result);
		} else {
			// La numeración y el cambio de los booleanos se hace por fuera
			final TraverseTown old = this.traverseTownRepository.findOne(traverseTown.getId());
			Assert.notNull(old);
			final Offer offer = this.offerService.findByTraverseTown(old.getId());
			Assert.notNull(offer);
			Assert.isTrue(carrier.getOffers().contains(offer));

			result = this.traverseTownRepository.save(traverseTown);
			Assert.notNull(result);
		}

		return result;
	}

	public void delete(final TraverseTown traverseTown) {
		Assert.notNull(traverseTown);
		Assert.isTrue(traverseTown.getId() > 0);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final TraverseTown old = this.traverseTownRepository.findOne(traverseTown.getId());
		Assert.notNull(old);
		final Offer offer = this.offerService.findByTraverseTown(old.getId());
		Assert.notNull(offer);
		Assert.isTrue(carrier.getOffers().contains(offer));

		this.offerService.removeTraverseTown(old);

		offer.getTraverseTowns().remove(old);
		this.traverseTownRepository.delete(old.getId());

		//TODO Reordenar traverse towns
	}

	public void flush() {
		this.traverseTownRepository.flush();
	}

	public void reOrder(Offer offer) {

	}

	public Collection<TraverseTown> findCarrierTraverseTowns(int id) {
		final Collection<TraverseTown> result = this.traverseTownRepository.findCarrierTraverseTowns(id);
		Assert.notNull(result);
		return result;
	}

	public TraverseTown findOfferTraverseTownCurrent(int id) {
		final Collection<TraverseTown> result = this.traverseTownRepository.findOfferTraverseTownCurrent(id);
		return result.size() == 0 ? null : new ArrayList<>(result).get(0);
	}

	public TraverseTown reconstruct(TraverseTown tt, Integer offerId, BindingResult binding) {
		TraverseTown result;
		TraverseTown old = null;
		TraverseTown clon2 = (null);

		if (tt.getId() == 0) {
			result = this.create();
			result.setCurrentTown(tt.isCurrentTown());
			result.setEstimatedDate(tt.getEstimatedDate());
			result.setTown(tt.getTown());

		} else {
			result = this.traverseTownRepository.findOne(tt.getId());
			old = result;
			Assert.notNull(result);
			final TraverseTown clon = (TraverseTown) result.clone();

			clon.setCurrentTown(tt.isCurrentTown());
			clon.setEstimatedDate(tt.getEstimatedDate());
			clon.setTown(tt.getTown());

			if (!old.isCurrentTown() && clon.isCurrentTown()) {
				TraverseTown ttToChange = this.findOfferTraverseTownCurrent(offerId);
				if (ttToChange != null) {
					clon2 = (TraverseTown) ttToChange.clone();
					clon2.setCurrentTown(false);
				}
			}

			result = clon;
		}

		this.validator.validate(result, binding);

		Offer o = this.offerService.findOne(offerId);

		if ((old != null)
			&& o.isFinalMode()
			&& (!old.getTown().equals(result.getTown()) || (old.getEstimatedDate().getDay() != (result.getEstimatedDate().getDay())) || (old.getEstimatedDate().getMonth() != (result.getEstimatedDate().getMonth())) || (old.getEstimatedDate().getYear() != (result
				.getEstimatedDate().getYear())))) {
			binding.rejectValue("town", "of.error.finalMode");
			binding.rejectValue("estimatedDate", "of.error.finalMode");
		}
		if ((o != null) && !o.isFinalMode()) {

			if ((result.getEstimatedDate() != null) && result.getEstimatedDate().before(o.getMaxDateToRequest())) {
				binding.rejectValue("estimatedDate", "tt.error.estimatedDateBeforeOfferDate");
			}

			Date maxEstimated = this.offerService.maxEstimatedDateofAnOffer(offerId);

			if (((result.getEstimatedDate() != null) && (maxEstimated != null)) && result.getEstimatedDate().before(maxEstimated)) {
				binding.rejectValue("estimatedDate", "tt.error.estimatedDateBeforeMaxDate");
			}
		}

		if (!binding.hasErrors()) {
			result = this.save(result);
			if (clon2 != null) {
				this.save(clon2);
			}
			this.flush();
			Assert.notNull(result);
			if (tt.getId() == 0) {
				this.offerService.addTraverseTown(result, offerId);
			}
		}

		return result;
	}
}
