
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FareRepository;
import security.LoginService;
import domain.Carrier;
import domain.Fare;

@Service
@Transactional
public class FareService {

	@Autowired
	private FareRepository	fareRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarrierService	carrierService;

	@Autowired
	private OfferService	offerService;


	public FareService() {

	}

	public Collection<Fare> findAll() {
		Collection<Fare> fares;
		fares = this.fareRepository.findAll();
		Assert.notNull(fares);
		return fares;
	}

	public Fare findOne(final int id) {
		Assert.isTrue(id > 0);
		final Fare fare = this.fareRepository.findOne(id);
		Assert.notNull(fare);
		return fare;
	}

	public Collection<Fare> findByCarrier(final int id) {
		Assert.isTrue(id > 0);
		final Collection<Fare> fares = this.fareRepository.findByCarrier(id);
		Assert.notNull(fares);
		return fares;
	}

	public Collection<Fare> findByOffer(final int id) {
		Assert.isTrue(id > 0);
		final Collection<Fare> fares = this.fareRepository.findByOffer(id);
		Assert.notNull(fares);
		return fares;
	}

	public Fare create() {
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final Fare result = new Fare();
		result.setMaxWeight(0);
		result.setMaxVolume(0);
		result.setPrice(0);
		return result;
	}

	public Fare save(final Fare fare) {
		Assert.notNull(fare);
		final Fare result;
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		if (fare.getId() == 0) {
			result = this.fareRepository.save(fare);
			Assert.notNull(result);
			carrier.getFares().add(result);
			this.carrierService.save(carrier);
		} else {
			final Fare old = this.fareRepository.findOne(fare.getId());
			Assert.notNull(old);
			Assert.isTrue(carrier.getFares().contains(old));
			//Assert.isTrue(this.offerService.findByFare(fare.getId()).size() == 0);

			result = this.fareRepository.save(fare);
			Assert.notNull(result);
		}

		return result;
	}

	public void delete(final Fare fare) {
		Assert.notNull(fare);
		Assert.isTrue(fare.getId() > 0);

		final Fare old = this.fareRepository.findOne(fare.getId());
		Assert.notNull(old);

		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);
		Assert.isTrue(carrier.getFares().contains(old));

		Assert.isTrue(this.offerService.findByFare(fare.getId()).size() == 0);

		carrier.getFares().remove(old);
		this.fareRepository.delete(old.getId());
	}

	public void flush() {
		this.fareRepository.flush();
	}
}
