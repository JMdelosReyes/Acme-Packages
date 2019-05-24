
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;
import domain.Customer;
import domain.Finder;
import domain.Offer;

@Service
@Transactional
public class FinderService {

	// Managed repository

	@Autowired
	private FinderRepository		finderRepository;

	// Supporting services
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	//Validator

	@Autowired
	private Validator				validator;


	// Constructor

	public FinderService() {
		super();
	}

	// CRUD methods
	public Finder findOne(final int id) {
		Assert.isTrue(id > 0);
		final Finder result;
		result = this.finderRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Finder create() {
		final Finder result = new Finder();

		result.setCategory("");
		result.setMaxDate(null);
		result.setMaxPrice(0.);
		result.setMinDate(null);
		result.setWeight(0.);
		result.setVolume(0.);
		result.setTown("");
		result.setLastUpdate(DateTime.now().minusMillis(1000).toDate());
		result.setOffers(new ArrayList<Offer>());

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Finder result;

		if (finder.getId() == 0) {
			result = this.finderRepository.save(finder);
		} else {
			final Finder old = this.finderRepository.findOne(finder.getId());
			Assert.notNull(old);

			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));

			final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
			final Customer customer = this.customerService.findOne(id);
			Assert.notNull(customer);

			Assert.isTrue(customer.getFinder().getId() == finder.getId());

			if (finder.getCategory() == null) {
				finder.setCategory("");
			}

			if (finder.getTown() == null) {
				finder.setTown("");
			}

			if ((finder.getVolume() == null) || (finder.getVolume() < 0)) {
				finder.setVolume(0.);
			}

			if ((finder.getWeight() == null) || (finder.getWeight() < 0)) {
				finder.setWeight(0.);
			}

			if ((finder.getMaxPrice() == null) || (finder.getMaxPrice() < 0)) {
				finder.setMaxPrice(0.);
			}

			final Configuration conf = this.configurationService.findOne();

			final PageRequest pr = new PageRequest(0, conf.getFinderResults());

			final Page<Offer> offers = this.finderRepository.findOffers(finder.getTown(), finder.getWeight(), finder.getVolume(), finder.getMaxPrice(), finder.getMinDate(), finder.getMaxDate(), finder.getCategory(), pr);
			Assert.notNull(offers);

			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
			finder.setOffers(offers.getContent());

			result = this.finderRepository.save(finder);
		}
		return result;
	}
	public void flush() {
		this.finderRepository.flush();
	}

	public Collection<Finder> findFindersWithOffers(final int id) {
		Assert.isTrue(id > 0);
		Collection<Finder> result;
		result = this.finderRepository.findersWithOffers(id);
		Assert.notNull(result);
		return result;
	}

	public void updateFinders(final List<Finder> finders) {
		Assert.notNull(finders);
		this.finderRepository.save(finders);
	}

	//Other methods
	public Finder findCustomerFinder(final int id) {
		Assert.isTrue(id > 0);
		Finder result;
		result = this.finderRepository.findCustomerFinder(id);
		Assert.notNull(result);
		return result;
	}

	public Finder findFinderByLoggedCustomer() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(userAccount.getAuthorities().contains(auth));

		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Customer customer = this.customerService.findOne(id);
		Assert.notNull(customer);

		Finder result;
		result = this.finderRepository.findCustomerFinder(customer.getId());
		Assert.notNull(result);

		final Configuration conf = this.configurationService.findOne();
		final int hours = conf.getFinderTime();

		DateTime lastPlusHours = new DateTime(result.getLastUpdate());
		lastPlusHours = lastPlusHours.plusHours(hours);
		final DateTime now = DateTime.now();

		if (!lastPlusHours.isAfter(now)
			|| (result.getMaxPrice().equals(0.) && result.getWeight().equals(0.) && result.getVolume().equals(0.) && result.getCategory().equals("") && result.getTown().equals("") && (result.getMaxDate() == null) && (result.getMinDate() == null))) {

			final Finder clon = (Finder) result.clone();

			clon.setCategory("");
			clon.setMaxDate(null);
			clon.setMaxPrice(0.);
			clon.setMinDate(null);
			clon.setWeight(0.);
			clon.setVolume(0.);
			clon.setTown("");
			clon.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

			final PageRequest pr = new PageRequest(0, conf.getFinderResults());
			final Page<Offer> offers = this.finderRepository.findOffers("", 0., 0., 0., null, null, "", pr);
			Assert.notNull(offers);
			clon.setOffers(offers.getContent());

			result = clon;

			result = this.finderRepository.save(result);
			Assert.notNull(result);
		}

		return result;
	}

	public void clearFinder() {
		UserAccount userAccount;
		//User must be a customer
		userAccount = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);

		Assert.isTrue(userAccount.getAuthorities().contains(auth));

		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Customer customer = this.customerService.findOne(id);
		Assert.notNull(customer);

		final Finder finder = customer.getFinder();
		finder.setCategory("");
		finder.setMaxDate(null);
		finder.setMaxPrice(0.);
		finder.setMinDate(null);
		finder.setWeight(0.);
		finder.setVolume(0.);
		finder.setTown("");
		finder.setLastUpdate(DateTime.now().minusMillis(1000).toDate());
		this.finderRepository.save(finder);
	}

	//Reconstruct

	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result;

		result = this.finderRepository.findOne(finder.getId());
		Assert.notNull(result);

		final Finder clon = (Finder) result.clone();

		clon.setCategory(finder.getCategory());
		clon.setMaxDate(finder.getMaxDate());
		clon.setMaxPrice(finder.getMaxPrice());
		clon.setMinDate(finder.getMinDate());
		clon.setWeight(finder.getWeight());
		clon.setVolume(finder.getVolume());
		clon.setTown(finder.getTown());

		result = clon;

		this.validator.validate(result, binding);

		return result;
	}

}
