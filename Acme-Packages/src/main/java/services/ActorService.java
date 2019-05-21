
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Carrier;
import domain.Customer;
import domain.MessBox;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	// Managed repository

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CarrierService			carrierService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private UserAccountService		userAccountService;


	public ActorService() {

	}

	public Actor findByUserAccountId(final int userAccountId) {
		return this.actorRepository.findActorByUserAccountId(userAccountId);

	}

	public String findActorType() {
		String type = "None";
		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);

			final Authority auth = new Authority();

			auth.setAuthority(Authority.ADMIN);
			if (userAccount.getAuthorities().contains(auth))
				type = "Administrator";

			auth.setAuthority(Authority.AUDITOR);
			if (userAccount.getAuthorities().contains(auth))
				type = "Auditor";

			auth.setAuthority(Authority.CARRIER);
			if (userAccount.getAuthorities().contains(auth))
				type = "Carrier";

			auth.setAuthority(Authority.CUSTOMER);
			if (userAccount.getAuthorities().contains(auth))
				type = "Customer";

			auth.setAuthority(Authority.SPONSOR);
			if (userAccount.getAuthorities().contains(auth))
				type = "Sponsor";

		} catch (final Exception e) {
		}

		return type;
	}

	public String findActorType(final UserAccount a) {
		String type = "None";
		final UserAccount userAccount = a;
		Assert.notNull(userAccount);

		final Authority auth = new Authority();

		auth.setAuthority(Authority.ADMIN);
		if (userAccount.getAuthorities().contains(auth))
			type = "Administrator";

		auth.setAuthority(Authority.AUDITOR);
		if (userAccount.getAuthorities().contains(auth))
			type = "Auditor";

		auth.setAuthority(Authority.CARRIER);
		if (userAccount.getAuthorities().contains(auth))
			type = "Carrier";

		auth.setAuthority(Authority.CUSTOMER);
		if (userAccount.getAuthorities().contains(auth))
			type = "Customer";

		auth.setAuthority(Authority.SPONSOR);
		if (userAccount.getAuthorities().contains(auth))
			type = "Sponsor";

		return type;
	}

	public Actor create() {
		final Actor a = new Actor();
		a.setName("");
		a.setMiddleName("");
		a.setSurname("");
		a.setPhoto("");
		a.setEmail("");
		a.setPhoneNumber("");
		a.setAddress("");
		a.setBanned(false);
		a.setSocialProfiles(new ArrayList<SocialProfile>());
		final UserAccount ua = this.userAccountService.create();
		a.setUserAccount(ua);
		a.setMessageBoxes(new ArrayList<MessBox>());
		a.setSpammer(false);
		a.setCreditCard(null);

		return a;
	}
	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;

		result = this.actorRepository.findOneOwn(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	public void delete() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.findByUserAccountId(principal.getId());

		final String actorType = this.findActorType(principal);

		if (actorType.equals("Administrator")) {
			final Administrator ad = (Administrator) a;
			this.administratorService.delete(ad);
		} else if (actorType.equals("Auditor")) {
			final Auditor h = (Auditor) a;
			this.auditorService.delete(h);
		} else if (actorType.equals("Carrier")) {
			final Carrier p = (Carrier) a;
			this.carrierService.delete(p);
		} else if (actorType.equals("Customer")) {
			final Customer c = (Customer) a;
			this.customerService.delete(c);
		} else {
			final Sponsor c = (Sponsor) a;
			this.sponsorService.delete(c);
		}
	}
}
