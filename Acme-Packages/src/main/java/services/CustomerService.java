
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.HashPasswordParameter;
import utilities.Validators;
import domain.Actor;
import domain.Customer;
import domain.MessBox;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	//Repository
	@Autowired
	private CustomerRepository		custRepository;

	//Services
	@Autowired
	private MessBoxService			messBoxService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	confService;


	//Constructor
	public CustomerService() {

	}
	//CRUD
	public Customer findOne(final int id) {
		Assert.isTrue(id != 0);
		Customer res;
		res = this.custRepository.findOne(id);
		Assert.notNull(id);
		return res;
	}
	public Collection<Customer> findAll() {
		Collection<Customer> res;
		res = this.custRepository.findAll();
		return res;
	}
	public Customer create() {
		Authentication authentication;
		SecurityContext context;
		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();

		boolean check = false;
		if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920)) {
			check = true;
		}
		Assert.isTrue(check);

		final Customer res = new Customer();
		//Actor
		res.setName("");
		res.setSurname("");
		res.setEmail("");
		res.setSpammer(false);
		res.setBanned(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setMessageBoxes(this.messBoxService.createSystemMessageBoxes());

		//User Account
		final UserAccount userCus = new UserAccount();
		final Authority cusAuth = new Authority();
		cusAuth.setAuthority(Authority.CUSTOMER);
		userCus.addAuthority(cusAuth);
		res.setUserAccount(userCus);

		//Customer
		//		res.setFinder(this.finderService.create());
		//		res.setRequests(new ArrayList<Request>());
		//		res.setEvaluations(new ArrayList<Evaluation>());

		return res;
	}
	public Customer save(final Customer cus) {
		Assert.notNull(cus);
		Customer res;
		if (cus.getId() == 0) {
			//User must not be logged
			Authentication authentication;
			SecurityContext context;
			context = SecurityContextHolder.getContext();
			Assert.notNull(context);
			authentication = context.getAuthentication();

			boolean check = false;
			if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920)) {
				check = true;
			}
			Assert.isTrue(check);

			//Hash password
			UserAccount uc = cus.getUserAccount();
			uc.setPassword(HashPasswordParameter.generateHashPassword(uc.getPassword()));
			uc = this.userAccountService.save(uc);
			cus.setUserAccount(uc);

			//Save finder
			//			Assert.notNull(cus.getFinder());
			//			final Finder finderSaved = this.finderService.save(cus.getFinder());
			//			cus.setFinder(finderSaved);

			//The email must be a valid one
			final String email = cus.getEmail();
			final String pattern = "^[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}|^[a-zA-Z0-9\\s]{1,}<[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}>";
			final Pattern r = Pattern.compile(pattern);
			final Matcher m = r.matcher(email);
			Assert.isTrue(m.find());

			//Add ZipCode phoneNumber
			if (cus.getPhoneNumber() != null) {
				final String phone = cus.getPhoneNumber();
				final String pattern2 = "^[0-9]{4,9}";
				final Pattern r2 = Pattern.compile(pattern2);
				final Matcher m2 = r2.matcher(phone);
				if (m2.find()) {
					cus.setPhoneNumber(this.confService.findOne().getCountryCode() + phone);
				}
			}
			//Check credit card
			Assert.isTrue(Validators.checkCreditCard(cus.getCreditCard()));

			//Save messBoxes
			final Collection<MessBox> sysBoxes = this.messBoxService.saveSystemBoxes(cus.getMessageBoxes());
			cus.setMessageBoxes(sysBoxes);

			//Save customer
			res = this.custRepository.save(cus);

		} else {
			Assert.isTrue(!this.actorService.findActorType().equals("None"));
			final Customer old = this.custRepository.findOne(cus.getId());
			//Administrator modifies
			if (this.actorService.findActorType().equals("Administrator")) {
				Assert.isTrue(old.getName().equals(cus.getName()));
				Assert.isTrue(old.getMiddleName().equals(cus.getMiddleName()));
				Assert.isTrue(old.getSurname().equals(cus.getSurname()));
				Assert.isTrue(old.getPhoto().equals(cus.getPhoto()));
				Assert.isTrue(old.getEmail().equals(cus.getEmail()));
				Assert.isTrue(old.getPhoneNumber().equals(cus.getPhoneNumber()));
				Assert.isTrue(old.getAddress().equals(cus.getAddress()));
				Assert.isTrue(old.getCreditCard().equals(cus.getCreditCard()));
				Assert.isTrue(old.getSocialProfiles().equals(cus.getSocialProfiles()));
				Assert.isTrue(old.getUserAccount().equals(cus.getUserAccount()));

				Assert.isTrue(old.getMessageBoxes().equals(cus.getMessageBoxes()));
				Assert.isTrue(old.getFinder().equals(cus.getFinder()));
				Assert.isTrue(old.getRequests().equals(cus.getRequests()));
				Assert.isTrue(old.getEvaluations().equals(cus.getEvaluations()));

				res = this.custRepository.save(cus);
			} else {
				//Same logged customer
				final UserAccount principal = LoginService.getPrincipal();
				Assert.isTrue(this.actorService.findByUserAccountId(principal.getId()).getId() == cus.getId());

				Assert.isTrue(old.isBanned() != cus.isBanned());
				Assert.isTrue(old.isSpammer() != cus.isSpammer());
				//The email must be a valid one
				final String email = cus.getEmail();
				final String pattern = "^[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}|^[a-zA-Z0-9\\s]{1,}<[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}>";
				final Pattern r = Pattern.compile(pattern);
				final Matcher m = r.matcher(email);
				Assert.isTrue(m.find());

				//Add ZipCode phoneNumber
				if (cus.getPhoneNumber() != null) {
					final String phone = cus.getPhoneNumber();
					final String pattern2 = "^[0-9]{4,9}";
					final Pattern r2 = Pattern.compile(pattern2);
					final Matcher m2 = r2.matcher(phone);
					if (m2.find()) {
						cus.setPhoneNumber(this.confService.findOne().getCountryCode() + phone);
					}
				}
				//Check credit card
				Assert.isTrue(Validators.checkCreditCard(cus.getCreditCard()));

				res = this.custRepository.save(cus);
			}

		}
		return res;

	}
	public void delete(final Customer cus) {
		Assert.notNull(cus);
		Assert.isTrue(cus.getId() != 0);

		final UserAccount principal = LoginService.getPrincipal();
		final Actor aLogged = this.actorService.findByUserAccountId(principal.getId());
		final Customer oldCus = this.custRepository.findOne(cus.getId());
		Assert.isTrue(oldCus.getId() == aLogged.getId());

		this.messBoxService.deleteMessBoxes();

		this.custRepository.delete(cus);
	}
	//	public Customer reconstruct(Customer cus, BindingResult){
	//		
	//	}
	//Business methods

}
