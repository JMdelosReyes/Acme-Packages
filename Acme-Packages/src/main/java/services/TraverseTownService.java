
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

		offer.getTraverseTowns().remove(old);
		this.traverseTownRepository.delete(old.getId());

		//TODO Reordenar traverse towns
	}

	public void flush() {
		this.traverseTownRepository.flush();
	}

	public void reOrder(Offer offer) {

	}
}
