
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import security.LoginService;
import utilities.Tickers;
import domain.Carrier;
import domain.Evaluation;
import domain.Fare;
import domain.Offer;
import domain.Request;
import domain.TraverseTown;

@Service
@Transactional
public class OfferService {

	@Autowired
	private OfferRepository	offerRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarrierService	carrierService;

	@Autowired
	private VehicleService	vehicleService;


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
		result.setTicker("");
		result.setFinalMode(false);
		result.setMaxDateToRequest(DateTime.now().minusMillis(1000).toDate());
		result.setCanceled(false);
		result.setScore(0);
		result.setFares(new ArrayList<Fare>());
		result.setVehicle(null);
		result.setEvaluations(new ArrayList<Evaluation>());
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
			offer.setFares(new ArrayList<Fare>());
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

		//TODO Quitar de m�s sitios

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

	}

	public void addTraverseTown(TraverseTown traverseTown, int offerId) {

	}

	public void addRequest(Request request, int offerId) {

	}

	public void addEvaluation(Evaluation evaluation, int offerId) {

	}

}
