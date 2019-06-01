
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OfferRepository;
import security.LoginService;
import utilities.Tickers;
import domain.Carrier;
import domain.Evaluation;
import domain.Fare;
import domain.Offer;
import domain.Request;
import domain.TraverseTown;
import forms.OfferForm;

@Service
@Transactional
public class OfferService {

	@Autowired
	private OfferRepository		offerRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CarrierService		carrierService;

	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private TraverseTownService	traverseTownService;

	@Autowired
	private Validator			validator;


	public OfferService() {

	}

	public Collection<Offer> findAll() {
		final Collection<Offer> offers = this.offerRepository.findAll();
		Assert.notNull(offers);
		return offers;
	}

	public Offer findOne(final int id) {
		Assert.isTrue(id > 0);
		Offer result;
		result = this.offerRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Offer create() {
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final Offer result = new Offer();
		result.setCanceled(false);
		result.setFinalMode(false);
		result.setScore(0);
		result.setTicker(Tickers.generateTicker());
		result.setTotalPrice(0);
		result.setEvaluations(new ArrayList<Evaluation>());
		result.setFares(new ArrayList<Fare>());
		result.setRequests(new ArrayList<Request>());
		result.setTraverseTowns(new ArrayList<TraverseTown>());

		return result;
	}

	public Offer save(final Offer offer) {
		Assert.notNull(offer);
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		Assert.isTrue(offer.getVehicle() != null);
		Assert.isTrue(carrier.getVehicles().contains(offer.getVehicle()));
		Assert.isTrue(this.vehicleService.canBeUsed(offer.getVehicle().getId()));

		Assert.isTrue(carrier.getFares().equals(offer.getFares()) || carrier.getFares().containsAll(offer.getFares()));

		Offer result;

		if (offer.getId() == 0) {
			offer.setCanceled(false);
			offer.setFinalMode(false);
			offer.setScore(0);
			offer.setTicker(Tickers.generateTicker());
			offer.setTotalPrice(0);
			offer.setEvaluations(new ArrayList<Evaluation>());
			offer.setRequests(new ArrayList<Request>());
			offer.setTraverseTowns(new ArrayList<TraverseTown>());

			Assert.isTrue(offer.getMaxDateToRequest().after(new Date(System.currentTimeMillis() - 1000)));

			result = this.offerRepository.save(offer);
			Assert.notNull(result);
			carrier.getOffers().add(result);
			this.carrierService.save(carrier);
		} else {
			final Offer old = this.offerRepository.findOne(offer.getId());
			Assert.notNull(old);

			Assert.isTrue(carrier.getOffers().contains(old));
			Assert.isTrue(!old.isCanceled());
			Assert.isTrue(!old.isFinalMode());

			if ((offer.isFinalMode() && !old.isFinalMode()) || (!offer.isFinalMode() && !old.isFinalMode())) {
				Assert.isTrue(offer.getMaxDateToRequest().after(new Date(System.currentTimeMillis() - 1000)));
				Assert.isTrue(offer.getTotalPrice() == 0);
				Assert.isTrue(offer.getScore() == 0);
			}

			if (offer.isFinalMode() && !old.isFinalMode()) {
				Assert.isTrue(offer.getFares().size() > 0);
				Assert.isTrue(offer.getTraverseTowns().size() > 0);
			}

			result = this.offerRepository.save(offer);
			Assert.notNull(result);
		}

		return result;
	}
	public void delete(final Offer offer) {
		Assert.notNull(offer);
		Assert.isTrue(offer.getId() > 0);

		final Offer old = this.offerRepository.findOne(offer.getId());
		Assert.notNull(old);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);
		Assert.isTrue(carrier.getOffers().contains(old));
		Assert.isTrue(!old.isFinalMode());

		carrier.getOffers().remove(old);
		this.offerRepository.delete(old.getId());
	}

	public void flush() {
		this.offerRepository.flush();
	}

	public Offer findByTraverseTown(final int id) {
		Assert.isTrue(id > 0);
		final Offer result;
		result = this.offerRepository.findByTraverseTown(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Offer> findByFare(int id) {
		Assert.isTrue(id > 0);
		final Collection<Offer> result;
		result = this.offerRepository.findByFare(id);
		Assert.notNull(result);
		return result;
	}

	public void cancelOffer(int id) {
		Assert.isTrue(id > 0);
		Offer offer = this.offerRepository.findOne(id);
		Assert.notNull(offer);

		Assert.isTrue(offer.isFinalMode());
		Assert.isTrue(!offer.isCanceled());

		final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(actorId);
		Assert.notNull(carrier);

		Assert.isTrue(carrier.getOffers().contains(offer));

		Offer clon = (Offer) offer.clone();
		clon.setCanceled(true);
		offer = clon;

		this.offerRepository.save(offer);
	}

	public void addTraverseTown(TraverseTown traverseTown, int offerId) {
		Assert.isTrue(offerId > 0);
		Offer offer = this.offerRepository.findOne(offerId);
		Assert.isTrue(!offer.isFinalMode());

		int maxTt = 0;

		if (offer.getTraverseTowns().size() > 0) {
			maxTt = this.offerRepository.findMaxNumberTTByOffer(offerId);
		}

		TraverseTown clon = (TraverseTown) traverseTown.clone();
		clon.setNumber(maxTt + 1);
		traverseTown = clon;

		offer.getTraverseTowns().add(traverseTown);

		this.traverseTownService.save(traverseTown);

	}

	public void removeTraverseTown(TraverseTown traverseTown) {
		Offer offer = this.offerRepository.findByTraverseTown(traverseTown.getId());

		int number = traverseTown.getNumber();

		for (TraverseTown tt : offer.getTraverseTowns()) {
			if (tt.getNumber() > number) {
				TraverseTown clon = (TraverseTown) tt.clone();
				clon.setNumber(tt.getNumber() - 1);
				tt = clon;
				this.traverseTownService.save(tt);
			}
		}
	}

	public OfferForm getOfferForm(Integer offerId) {
		OfferForm result = new OfferForm();
		Offer f = this.findOne(offerId);

		final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(carrierId);
		Assert.isTrue(carrier.getOffers().contains(f));

		Assert.notNull(result);
		result.setId(offerId);
		result.setCanceled(f.isCanceled());
		result.setFares(f.getFares());
		result.setFinalMode(f.isFinalMode());
		result.setMaxDateToRequest(f.getMaxDateToRequest());
		result.setVehicle(f.getVehicle());

		return result;
	}

	public Offer reconstruct(final OfferForm of, final BindingResult binding) {
		Offer result;
		Offer old = null;

		if (of.getId() == 0) {
			result = this.create();
			result.setFares(of.getFares());
			result.setMaxDateToRequest(of.getMaxDateToRequest());
			result.setVehicle(of.getVehicle());

		} else {

			result = this.offerRepository.findOne(of.getId());
			old = result;

			Assert.notNull(result);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getOffers().contains(result));

			final Offer clon = (Offer) result.clone();
			if (result.isFinalMode()) {
				clon.setCanceled(of.isCanceled());
			}
			clon.setFares(of.getFares());
			clon.setFinalMode(of.isFinalMode());
			clon.setMaxDateToRequest(of.getMaxDateToRequest());
			clon.setVehicle(of.getVehicle());

			result = clon;
		}

		this.validator.validate(result, binding);

		if ((of.getId() == 0) || !old.isFinalMode()) {
			if ((result.getMaxDateToRequest() != null) && result.getMaxDateToRequest().before(LocalDateTime.now().toDate())) {
				binding.rejectValue("maxDateToRequest", "of.error.date");
			}
		}

		if ((old != null) && old.isFinalMode()
			&& (!old.getFares().equals(result.getFares()) || (old.isFinalMode() != result.isFinalMode()) || !old.getMaxDateToRequest().equals(result.getMaxDateToRequest()) || !old.getVehicle().equals(result.getVehicle()))) {
			binding.rejectValue("finalMode", "of.error.finalMode");
		}

		return result;
	}

	public Collection<Offer> findOpenOffers(String filter) {
		final Collection<Offer> offers = this.offerRepository.findOpenOffers(filter);
		Assert.notNull(offers);
		return offers;
	}
	public Collection<Offer> findCarrierOpenOffers(int carrierId) {
		final Collection<Offer> offers = this.offerRepository.findCarrierOpenOffers(carrierId);
		Assert.notNull(offers);
		return offers;
	}
	public Collection<Offer> findCarrierOffers(int carrierId) {
		final Collection<Offer> offers = this.offerRepository.findCarrierOffers(carrierId);
		Assert.notNull(offers);
		return offers;
	}

	public Date maxEstimatedDateofAnOffer(int offerId) {
		return this.offerRepository.findMaxDateTTByOffer(offerId);
	}

}
