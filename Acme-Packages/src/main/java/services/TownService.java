
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TownRepository;
import domain.Town;

@Service
@Transactional
public class TownService {

	@Autowired
	private TownRepository	townRepository;

	@Autowired
	private ActorService	actorService;


	public TownService() {

	}

	public Collection<Town> findAll() {
		final Collection<Town> result = this.townRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Town findOne(final int id) {
		Assert.isTrue(id > 0);
		Town result;
		result = this.townRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Town create() {
		//		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		//		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		//		final Carrier carrier = this.carrierService.findOne(id);
		//		Assert.notNull(carrier);

		final Town result = new Town();
		result.setCountry("");
		result.setName("");
		result.setZipCode("");
		return result;
	}

	public Town save(final Town town) {
		Assert.notNull(town);
		final Town result;

		//		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		//		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		//		final Carrier carrier = this.carrierService.findOne(id);
		//		Assert.notNull(carrier);

		if (town.getId() == 0) {
			result = this.townRepository.save(town);
			Assert.notNull(town);
		} else {
			final Town old = this.townRepository.findOne(town.getId());
			Assert.notNull(old);

			result = this.townRepository.save(town);
			Assert.notNull(town);
		}

		return result;
	}

	public void delete(final Town town) {
		Assert.notNull(town);
		Assert.isTrue(town.getId() > 0);

		//		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		//		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		//		final Carrier carrier = this.carrierService.findOne(id);
		//		Assert.notNull(carrier);

		final Town old = this.townRepository.findOne(town.getId());
		Assert.notNull(old);

		// TODO Alguna comprobación

		this.townRepository.delete(old.getId());
	}

	public void flush() {
		this.townRepository.flush();
	}

}
