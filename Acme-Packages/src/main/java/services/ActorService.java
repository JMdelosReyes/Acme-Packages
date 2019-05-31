
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import pojos.ActorPojo;
import pojos.AuditorPojo;
import pojos.CarrierPojo;
import pojos.CurriculumPojo;
import pojos.CategoryPojo;
import pojos.CustomerPojo;
import pojos.EvaluationPojo;
import pojos.FarePojo;
import pojos.MessBoxPojo;
import pojos.MessPojo;
import pojos.MiscellaneousRecordPojo;
import pojos.OfferPojo;
import pojos.ProfessionalRecordPojo;
import pojos.PackagePojo;
import pojos.RequestPojo;
import pojos.SocialProfilePojo;
import pojos.SolicitationPojo;
import pojos.SponsorPojo;
import pojos.SponsorshipPojo;
import pojos.VehiclePojo;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Carrier;
import domain.Category;
import domain.Curriculum;
import domain.Customer;
import domain.Evaluation;
import domain.Fare;
import domain.Mess;
import domain.MessBox;
import domain.MiscellaneousRecord;
import domain.Offer;
import domain.ProfessionalRecord;
import domain.Request;
import domain.SocialProfile;
import domain.Solicitation;
import domain.Sponsor;
import domain.Sponsorship;
import domain.TraverseTown;
import domain.Vehicle;
import forms.DisplayActorForm;
import forms.EditActorForm;
import forms.SignUpForm;

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

	@Autowired
	private Validator				validator;


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
			if (userAccount.getAuthorities().contains(auth)) {
				type = "Administrator";
			}

			auth.setAuthority(Authority.AUDITOR);
			if (userAccount.getAuthorities().contains(auth)) {
				type = "Auditor";
			}

			auth.setAuthority(Authority.CARRIER);
			if (userAccount.getAuthorities().contains(auth)) {
				type = "Carrier";
			}

			auth.setAuthority(Authority.CUSTOMER);
			if (userAccount.getAuthorities().contains(auth)) {
				type = "Customer";
			}

			auth.setAuthority(Authority.SPONSOR);
			if (userAccount.getAuthorities().contains(auth)) {
				type = "Sponsor";
			}

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
		if (userAccount.getAuthorities().contains(auth)) {
			type = "Administrator";
		}

		auth.setAuthority(Authority.AUDITOR);
		if (userAccount.getAuthorities().contains(auth)) {
			type = "Auditor";
		}

		auth.setAuthority(Authority.CARRIER);
		if (userAccount.getAuthorities().contains(auth)) {
			type = "Carrier";
		}

		auth.setAuthority(Authority.CUSTOMER);
		if (userAccount.getAuthorities().contains(auth)) {
			type = "Customer";
		}

		auth.setAuthority(Authority.SPONSOR);
		if (userAccount.getAuthorities().contains(auth)) {
			type = "Sponsor";
		}

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

	public DisplayActorForm getDisplayActorForm(final int id) {
		final DisplayActorForm da = new DisplayActorForm();

		Authentication authentication;
		SecurityContext context;
		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();

		if (authentication.getPrincipal().hashCode() != 1105384920) {
			//Actor logged searching another one
			if (id != 0) {
				final Actor a = this.findOne(id);
				da.setName(a.getName());
				da.setMiddleName(a.getMiddleName());
				da.setSurname(a.getSurname());
				da.setPhoto(a.getPhoto());
				da.setEmail(a.getEmail());
				da.setSpammer(a.getSpammer());

				if (this.findActorType(a.getUserAccount()).equals("Carrier")) {
					final Carrier c = this.carrierService.findOne(id);
					da.setVat(c.getVat());
					da.setScore(c.getScore());
				}
				if (this.findActorType(a.getUserAccount()).equals("Sponsor")) {
					final Sponsor s = this.sponsorService.findOne(id);
					da.setNif(s.getNif());
				}
				//Actor logged looking at itself
			} else {
				final UserAccount userAccount = LoginService.getPrincipal();
				final Actor a = this.findByUserAccountId(userAccount.getId());
				da.setName(a.getName());
				da.setMiddleName(a.getMiddleName());
				da.setSurname(a.getSurname());
				da.setCreditCard(a.getCreditCard());
				da.setPhoto(a.getPhoto());
				da.setEmail(a.getEmail());
				da.setPhoneNumber(a.getPhoneNumber());
				da.setAddress(a.getAddress());
				da.setSpammer(a.getSpammer());

				if (this.findActorType(a.getUserAccount()).equals("Carrier")) {
					final Carrier c = this.carrierService.findOne(a.getId());
					da.setVat(c.getVat());
					da.setScore(c.getScore());
				}
				if (this.findActorType(a.getUserAccount()).equals("Sponsor")) {
					final Sponsor s = this.sponsorService.findOne(a.getId());
					da.setNif(s.getNif());
				}
			}
			//Not logged actor looking someone
		} else if (id != 0) {
			final Actor a = this.findOne(id);
			da.setName(a.getName());
			da.setMiddleName(a.getMiddleName());
			da.setSurname(a.getSurname());
			da.setPhoto(a.getPhoto());
			da.setEmail(a.getEmail());

			if (this.findActorType(a.getUserAccount()).equals("Carrier")) {
				final Carrier c = this.carrierService.findOne(id);
				da.setVat(c.getVat());
				da.setScore(c.getScore());
			}
			if (this.findActorType(a.getUserAccount()).equals("Sponsor")) {
				final Sponsor s = this.sponsorService.findOne(id);
				da.setNif(s.getNif());
			}
		} else {
			throw new IllegalAccessError();
		}
		return da;
	}
	public SignUpForm getSignUpForm() {
		final SignUpForm res = new SignUpForm();
		final String actorType = this.findActorType();
		if (actorType.equals("None")) {
			res.setActorType("carrier");
		} else {
			res.setActorType("administrator");
		}
		return res;

	}

	public void save(final SignUpForm actor, final BindingResult binding) {
		Assert.notNull(actor);

		final String actorType = actor.getActorType();

		if (actorType.equals("administrator")) {

			final Administrator administrator = this.administratorService.create();

			administrator.setAddress(actor.getAddress());
			administrator.setCreditCard(actor.getCreditCard());
			administrator.setEmail(actor.getEmail());
			administrator.setName(actor.getName());
			administrator.setMiddleName(actor.getMiddleName());
			administrator.setSurname(actor.getSurname());
			administrator.setPhoneNumber(actor.getPhoneNumber());
			administrator.setPhoto(actor.getPhoto());
			administrator.getUserAccount().setPassword((actor.getUserAccount().getPassword()));
			administrator.getUserAccount().setUsername(actor.getUserAccount().getUsername());

			this.validator.validate(administrator, binding);
			if (!actor.getPassConfirmation().equals(actor.getUserAccount().getPassword())) {
				binding.rejectValue("passConfirmation", "act.pass.error");
			}
			if (!actor.isTermsAccepted()) {
				binding.rejectValue("termsAccepted", "act.terms.error");
			}

			if (!binding.hasErrors()) {
				this.administratorService.save(administrator);
			}

		} else if (actorType.equals("customer")) {

			Customer customer = this.customerService.create();

			customer.setAddress(actor.getAddress());
			customer.setCreditCard(actor.getCreditCard());
			customer.setEmail(actor.getEmail());
			customer.setName(actor.getName());
			customer.setMiddleName(actor.getMiddleName());
			customer.setSurname(actor.getSurname());
			customer.setPhoneNumber(actor.getPhoneNumber());
			customer.setPhoto(actor.getPhoto());
			customer.getUserAccount().setPassword((actor.getUserAccount().getPassword()));
			customer.getUserAccount().setUsername(actor.getUserAccount().getUsername());

			this.validator.validate(customer, binding);

			if (!actor.getPassConfirmation().equals(actor.getUserAccount().getPassword())) {
				binding.rejectValue("passConfirmation", "act.pass.error");
			}
			if (!actor.isTermsAccepted()) {
				binding.rejectValue("termsAccepted", "act.terms.error");
			}

			if (!binding.hasErrors()) {
				this.customerService.save(customer);
			}

		} else if (actorType.equals("carrier")) {

			Carrier carrier = this.carrierService.create();

			carrier.setAddress(actor.getAddress());
			carrier.setCreditCard(actor.getCreditCard());
			carrier.setEmail(actor.getEmail());
			carrier.setName(actor.getName());
			carrier.setMiddleName(actor.getMiddleName());
			carrier.setSurname(actor.getSurname());
			carrier.setPhoneNumber(actor.getPhoneNumber());
			carrier.setPhoto(actor.getPhoto());
			carrier.getUserAccount().setPassword((actor.getUserAccount().getPassword()));
			carrier.getUserAccount().setUsername(actor.getUserAccount().getUsername());

			carrier.setVat(actor.getVat());

			this.validator.validate(carrier, binding);

			if (!actor.getPassConfirmation().equals(actor.getUserAccount().getPassword())) {
				binding.rejectValue("passConfirmation", "act.pass.error");
			}
			if (!actor.isTermsAccepted()) {
				binding.rejectValue("termsAccepted", "act.terms.error");
			}

			if (!binding.hasErrors()) {
				this.carrierService.save(carrier);
			}

		} else if (actorType.equals("auditor")) {

			Auditor auditor = this.auditorService.create();

			auditor.setAddress(actor.getAddress());
			auditor.setCreditCard(actor.getCreditCard());
			auditor.setEmail(actor.getEmail());
			auditor.setName(actor.getName());
			auditor.setMiddleName(actor.getMiddleName());
			auditor.setSurname(actor.getSurname());
			auditor.setPhoneNumber(actor.getPhoneNumber());
			auditor.setPhoto(actor.getPhoto());
			auditor.getUserAccount().setPassword((actor.getUserAccount().getPassword()));
			auditor.getUserAccount().setUsername(actor.getUserAccount().getUsername());

			this.validator.validate(auditor, binding);

			if (!actor.getPassConfirmation().equals(actor.getUserAccount().getPassword())) {
				binding.rejectValue("passConfirmation", "act.pass.error");
			}
			if (!actor.isTermsAccepted()) {
				binding.rejectValue("termsAccepted", "act.terms.error");
			}

			if (!binding.hasErrors()) {
				this.auditorService.save(auditor);
			}

		} else if (actorType.equals("sponsor")) {

			Sponsor sponsor = this.sponsorService.create();

			sponsor.setAddress(actor.getAddress());
			sponsor.setCreditCard(actor.getCreditCard());
			sponsor.setEmail(actor.getEmail());
			sponsor.setName(actor.getName());
			sponsor.setMiddleName(actor.getMiddleName());
			sponsor.setSurname(actor.getSurname());
			sponsor.setPhoneNumber(actor.getPhoneNumber());
			sponsor.setPhoto(actor.getPhoto());
			sponsor.getUserAccount().setPassword((actor.getUserAccount().getPassword()));
			sponsor.getUserAccount().setUsername(actor.getUserAccount().getUsername());

			sponsor.setNif(actor.getNif());

			this.validator.validate(sponsor, binding);

			if (!actor.getPassConfirmation().equals(actor.getUserAccount().getPassword())) {
				binding.rejectValue("passConfirmation", "act.pass.error");
			}
			if (!actor.isTermsAccepted()) {
				binding.rejectValue("termsAccepted", "act.terms.error");
			}

			if (!binding.hasErrors()) {
				this.sponsorService.save(sponsor);
			}

		}

	}

	public EditActorForm getEditActorForm() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.findByUserAccountId(principal.getId());
		final String actorType = this.findActorType();

		final EditActorForm eaf = new EditActorForm();

		eaf.setName(a.getName());
		eaf.setMiddleName(a.getMiddleName());
		eaf.setSurname(a.getSurname());
		eaf.setCreditCard(a.getCreditCard());
		eaf.setPhoto(a.getPhoto());
		eaf.setEmail(a.getEmail());
		eaf.setPhoneNumber(a.getPhoneNumber());
		eaf.setAddress(a.getAddress());

		if (actorType.equals("Carrier")) {
			final Carrier c = (Carrier) this.findByUserAccountId(principal.getId());
			eaf.setVat(c.getVat());

		}

		if (actorType.equals("Sponsor")) {
			final Sponsor p = (Sponsor) this.findByUserAccountId(principal.getId());
			eaf.setNif(p.getNif());

		}

		return eaf;

	}

	public void save(final EditActorForm actor, final BindingResult binding) {
		Assert.notNull(actor);

		final String actorType = this.findActorType();
		final UserAccount principal = LoginService.getPrincipal();

		if (actorType.equals("Administrator")) {

			final Administrator administrator = (Administrator) this.findByUserAccountId(principal.getId());

			final Administrator clon = (Administrator) administrator.clone();

			clon.setName(actor.getName());
			clon.setSurname(actor.getSurname());
			clon.setMiddleName(actor.getMiddleName());
			clon.setCreditCard(actor.getCreditCard());
			clon.setPhoto(actor.getPhoto());
			clon.setEmail(actor.getEmail());
			clon.setPhoneNumber(actor.getPhoneNumber());
			clon.setAddress(actor.getAddress());

			this.validator.validate(clon, binding);
			if (!binding.hasErrors()) {
				this.administratorService.save(clon);
			}

		} else if (actorType.equals("Carrier")) {
			final Carrier c = (Carrier) this.findByUserAccountId(principal.getId());

			final Carrier clon = (Carrier) c.clone();

			clon.setName(actor.getName());
			clon.setSurname(actor.getSurname());
			clon.setMiddleName(actor.getMiddleName());
			clon.setCreditCard(actor.getCreditCard());
			clon.setPhoto(actor.getPhoto());
			clon.setEmail(actor.getEmail());
			clon.setPhoneNumber(actor.getPhoneNumber());
			clon.setAddress(actor.getAddress());
			clon.setVat(actor.getVat());

			this.validator.validate(clon, binding);
			if (!binding.hasErrors()) {
				this.carrierService.save(clon);
			}

		} else if (actorType.equals("Customer")) {

			final Customer c = (Customer) this.findByUserAccountId(principal.getId());

			final Customer clon = (Customer) c.clone();

			clon.setName(actor.getName());
			clon.setSurname(actor.getSurname());
			clon.setMiddleName(actor.getMiddleName());
			clon.setCreditCard(actor.getCreditCard());
			clon.setPhoto(actor.getPhoto());
			clon.setEmail(actor.getEmail());
			clon.setPhoneNumber(actor.getPhoneNumber());
			clon.setAddress(actor.getAddress());

			this.validator.validate(clon, binding);
			if (!binding.hasErrors()) {
				this.customerService.save(clon);
			}

		} else if (actorType.equals("Auditor")) {

			final Auditor aud = (Auditor) this.findByUserAccountId(principal.getId());

			final Auditor clon = (Auditor) aud.clone();

			clon.setName(actor.getName());
			clon.setSurname(actor.getSurname());
			clon.setMiddleName(actor.getMiddleName());
			clon.setCreditCard(actor.getCreditCard());
			clon.setPhoto(actor.getPhoto());
			clon.setEmail(actor.getEmail());
			clon.setPhoneNumber(actor.getPhoneNumber());
			clon.setAddress(actor.getAddress());

			this.validator.validate(clon, binding);
			if (!binding.hasErrors()) {
				this.auditorService.save(clon);
			}

		} else if (actorType.equals("Sponsor")) {

			final Sponsor pro = (Sponsor) this.findByUserAccountId(principal.getId());

			final Sponsor clon = (Sponsor) pro.clone();

			clon.setName(actor.getName());
			clon.setSurname(actor.getSurname());
			clon.setMiddleName(actor.getMiddleName());
			clon.setCreditCard(actor.getCreditCard());
			clon.setPhoto(actor.getPhoto());
			clon.setEmail(actor.getEmail());
			clon.setPhoneNumber(actor.getPhoneNumber());
			clon.setAddress(actor.getAddress());
			clon.setNif(actor.getNif());

			this.validator.validate(clon, binding);
			if (!binding.hasErrors()) {
				this.sponsorService.save(clon);
			}

		}

	}
	public SponsorPojo getSponsorPojo() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.findByUserAccountId(principal.getId());

		final SponsorPojo res = new SponsorPojo();

		final Sponsor s = (Sponsor) a;

		res.setAddress(a.getAddress());
		res.setCreditCard(a.getCreditCard());
		res.setEmail(a.getEmail());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhoneNumber(a.getPhoneNumber());
		res.setPhoto(a.getPhoto());
		res.setSurname(a.getSurname());

		//Social profiles
		final Collection<SocialProfilePojo> socialProfiles = new ArrayList<>();
		for (final SocialProfile sp : a.getSocialProfiles()) {
			final SocialProfilePojo spp = new SocialProfilePojo();
			spp.setNick(sp.getNick());
			spp.setSocialNetwork(sp.getSocialNetwork());
			spp.setProfileLink(sp.getProfileLink());
			socialProfiles.add(spp);
		}
		res.setSocialProfiles(socialProfiles);

		//MessBoxes
		final Collection<MessBoxPojo> messBoxPojos = new ArrayList<>();
		for (final MessBox mb : a.getMessageBoxes()) {
			MessBoxPojo mbp = new MessBoxPojo();
			mbp.setName(mb.getName());
			final Collection<MessPojo> messPojo = new ArrayList<>();
			for (Mess m : mb.getMessages()) {
				MessPojo mp = new MessPojo();
				mp.setBody(m.getBody());
				mp.setPriority(m.getPriority());
				mp.setSendDate(m.getSendDate());
				mp.setSender(m.getSender().getName() + " " + m.getSender().getSurname());
				mp.setSubject(m.getSubject());
				mp.setRecipients(new ArrayList<String>());
				for (Actor actor : m.getRecipients()) {
					mp.getRecipients().add(actor.getName() + " " + actor.getSurname());
				}
				messPojo.add(mp);
			}
			mbp.setMessages(messPojo);
			messBoxPojos.add(mbp);

		}
		res.setMessageBoxes(messBoxPojos);

		//Sponsor

		res.setNif(s.getNif());

		final Collection<SponsorshipPojo> sponsorshipPojos = new ArrayList<>();
		for (final Sponsorship sponsorship : s.getSponsorships()) {
			final SponsorshipPojo sp = new SponsorshipPojo();
			sp.setBanner(sponsorship.getBanner());
			sp.setExpirationDate(sponsorship.getExpirationDate());
			sp.setTarget(sponsorship.getTarget());
			sp.setValid(sponsorship.isValid());

			sponsorshipPojos.add(sp);
		}
		res.setSponsorships(sponsorshipPojos);

		return res;
	}

	public AuditorPojo getAuditorPojo() {
	public CustomerPojo getCustomerPojo() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.findByUserAccountId(principal.getId());

		final AuditorPojo res = new AuditorPojo();
		final CustomerPojo res = new CustomerPojo();

		final Auditor ad = (Auditor) a;
		final Customer c = (Customer) a;

		res.setAddress(a.getAddress());
		res.setCreditCard(a.getCreditCard());
		res.setEmail(a.getEmail());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhoneNumber(a.getPhoneNumber());
		res.setPhoto(a.getPhoto());
		res.setSurname(a.getSurname());

		//Social profiles
		final Collection<SocialProfilePojo> socialProfiles = new ArrayList<>();
		for (final SocialProfile sp : a.getSocialProfiles()) {
			final SocialProfilePojo spp = new SocialProfilePojo();
			spp.setNick(sp.getNick());
			spp.setSocialNetwork(sp.getSocialNetwork());
			spp.setProfileLink(sp.getProfileLink());
			socialProfiles.add(spp);
		}
		res.setSocialProfiles(socialProfiles);

		//MessBoxes
		final Collection<MessBoxPojo> messBoxPojos = new ArrayList<>();
		for (final MessBox mb : a.getMessageBoxes()) {
			MessBoxPojo mbp = new MessBoxPojo();
			mbp.setName(mb.getName());
			final Collection<MessPojo> messPojo = new ArrayList<>();
			for (Mess m : mb.getMessages()) {
				MessPojo mp = new MessPojo();
				mp.setBody(m.getBody());
				mp.setPriority(m.getPriority());
				mp.setSendDate(m.getSendDate());
				mp.setSender(m.getSender().getName() + " " + m.getSender().getSurname());
				mp.setSubject(m.getSubject());
				mp.setRecipients(new ArrayList<String>());
				for (Actor actor : m.getRecipients()) {
					mp.getRecipients().add(actor.getName() + " " + actor.getSurname());
				}
				messPojo.add(mp);
			}
			mbp.setMessages(messPojo);
			messBoxPojos.add(mbp);

		}
		res.setMessageBoxes(messBoxPojos);

		//Auditor

		return res;
	}

	public CarrierPojo getCarrierPojo() {
		final UserAccount principal = LoginService.getPrincipal();
		final Actor a = this.findByUserAccountId(principal.getId());

		final CarrierPojo res = new CarrierPojo();

		final Carrier c = (Carrier) a;

		res.setAddress(a.getAddress());
		res.setCreditCard(a.getCreditCard());
		res.setEmail(a.getEmail());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhoneNumber(a.getPhoneNumber());
		res.setPhoto(a.getPhoto());
		res.setSurname(a.getSurname());

		//Social profiles
		final Collection<SocialProfilePojo> socialProfiles = new ArrayList<>();
		for (final SocialProfile sp : a.getSocialProfiles()) {
			final SocialProfilePojo spp = new SocialProfilePojo();
			spp.setNick(sp.getNick());
			spp.setSocialNetwork(sp.getSocialNetwork());
			spp.setProfileLink(sp.getProfileLink());
			socialProfiles.add(spp);
		}
		res.setSocialProfiles(socialProfiles);
		//Sponsor

		//MessBoxes
		final Collection<MessBoxPojo> messBoxPojos = new ArrayList<>();
		for (final MessBox mb : a.getMessageBoxes()) {
			MessBoxPojo mbp = new MessBoxPojo();
			mbp.setName(mb.getName());
			final Collection<MessPojo> messPojo = new ArrayList<>();
			for (Mess m : mb.getMessages()) {
				MessPojo mp = new MessPojo();
				mp.setBody(m.getBody());
				mp.setPriority(m.getPriority());
				mp.setSendDate(m.getSendDate());
				mp.setSender(m.getSender().getName() + " " + m.getSender().getSurname());
				mp.setSubject(m.getSubject());
				mp.setRecipients(new ArrayList<String>());
				for (Actor actor : m.getRecipients()) {
					mp.getRecipients().add(actor.getName() + " " + actor.getSurname());
		final Collection<RequestPojo> requestPojos = new ArrayList<>();
		for (final Request request : c.getRequests()) {
			final RequestPojo rp = new RequestPojo();
			rp.setComment(request.getComment());
			rp.setDeadline(request.getDeadline());
			rp.setDescription(request.getDescription());
			rp.setFinalMode(request.isFinalMode());
			rp.setIssue(request.getIssue() == null ? null : request.getIssue().getDescription());
			rp.setMaxPrice(request.getMaxPrice());
			rp.setMoment(request.getMoment());
			rp.setOffer(request.getOffer() == null ? null : request.getOffer().getTicker());
			rp.setStatus(request.getStatus());
			rp.setStreetAddress(request.getStreetAddress());
			rp.setTicker(request.getTicker());
			rp.setTown(request.getTown().getName());
			rp.setVolume(request.getVolume());
			rp.setWeight(request.getWeight());
			Collection<PackagePojo> packagePojos = new ArrayList<PackagePojo>();
			for (domain.Package pack : request.getPackages()) {
				PackagePojo packPojo = new PackagePojo();
				packPojo.setDetails(pack.getDetails());
				packPojo.setHeight(pack.getHeight());
				packPojo.setLength(pack.getLength());
				packPojo.setPrice(pack.getPrice());
				packPojo.setWeight(pack.getWeight());
				packPojo.setWidth(pack.getWidth());

				Collection<CategoryPojo> categoryPojos = new ArrayList<CategoryPojo>();
				for (Category cat : pack.getCategories()) {
					CategoryPojo cp = new CategoryPojo();
					cp.setEnglishDescription(cat.getEnglishDescription());
					cp.setEnglishName(cat.getEnglishName());
					cp.setSpanishDescription(cat.getSpanishDescription());
					cp.setSpanishName(cat.getSpanishName());

					categoryPojos.add(cp);
				}
				messPojo.add(mp);
			}
			mbp.setMessages(messPojo);
			messBoxPojos.add(mbp);

		}
		res.setMessageBoxes(messBoxPojos);

		//Carrier

		res.setAddress(c.getAddress());
		res.setScore(c.getScore());

		Collection<CurriculumPojo> curriculumPojos = new ArrayList<>();
		for (Curriculum cur : c.getCurricula()) {
			CurriculumPojo cp = new CurriculumPojo();
			cp.setEmail(cur.getEmail());
			cp.setFullName(cur.getFullName());
			cp.setPhoneNumber(cur.getPhoneNumber());
			cp.setPhoto(cur.getPhoto());
				packPojo.setCategories(categoryPojos);

			Collection<MiscellaneousRecordPojo> misRecordPojos = new ArrayList<>();
			for (MiscellaneousRecord mr : cur.getMiscellaneousRecords()) {
				MiscellaneousRecordPojo mrp = new MiscellaneousRecordPojo();
				mrp.setAttachments(mr.getAttachment());
				mrp.setComments(mr.getComments());
				mrp.setTitle(mr.getTitle());
				packagePojos.add(packPojo);

				misRecordPojos.add(mrp);
			}
			requestPojos.add(rp);

			cp.setMiscellaneousRecord(misRecordPojos);

			Collection<ProfessionalRecordPojo> proRecordPojos = new ArrayList<>();
			for (ProfessionalRecord pr : cur.getProfessionalRecords()) {
				ProfessionalRecordPojo prp = new ProfessionalRecordPojo();
				prp.setAttachment(pr.getAttachment());
				prp.setComments(pr.getComments());
				prp.setCompanyName(pr.getCompanyName());
				prp.setStartTime(pr.getStartTime());
				prp.setEndTime(pr.getEndTime());

				proRecordPojos.add(prp);
			}

			cp.setProfessionalRecord(proRecordPojos);

			curriculumPojos.add(cp);
		}

		res.setCurricula(curriculumPojos);

		Collection<FarePojo> farePojos = new ArrayList<>();
		for (Fare f : c.getFares()) {
			FarePojo fp = new FarePojo();
			fp.setMaxVolume(f.getMaxVolume());
			fp.setMaxWeight(f.getMaxWeight());
			fp.setPrice(f.getPrice());

			farePojos.add(fp);
		res.setRequests(requestPojos);

		final Collection<EvaluationPojo> evaluationPojos = new ArrayList<EvaluationPojo>();
		for (Evaluation ev : c.getEvaluations()) {
			EvaluationPojo evp = new EvaluationPojo();
			evp.setComment(ev.getComment());
			evp.setCustomer(ev.getCustomer().getName());
			evp.setMark(ev.getMark());
			evp.setMoment(ev.getMoment());
			evp.setOffer(ev.getOffer().getTicker());
			evaluationPojos.add(evp);
		}

		res.setFares(farePojos);

		Collection<VehiclePojo> vehiclePojos = new ArrayList<>();
		for (Vehicle v : c.getVehicles()) {
			VehiclePojo vp = new VehiclePojo();
			vp.setComment(v.getComment());
			vp.setMaxVolume(v.getMaxVolume());
			vp.setMaxWeight(v.getMaxWeight());
			vp.setPictures(v.getPictures());
			vp.setPlate(v.getPlate());
			vp.setType(v.getType());

			Collection<SolicitationPojo> solicitationPojos = new ArrayList<>();
			for (Solicitation s : v.getSolicitations()) {
				SolicitationPojo sp = new SolicitationPojo();
				sp.setComments(s.getComments());
				sp.setEndDate(s.getEndDate());
				sp.setMoment(s.getMoment());
				sp.setStartDate(s.getStartDate());
				sp.setStatus(s.getStatus());

				Category cat = s.getCategory();
				sp.setCategory(cat.getEnglishName() + " " + cat.getSpanishName());

				solicitationPojos.add(sp);
			}

			vp.setSolicitations(solicitationPojos);
			vehiclePojos.add(vp);
		}

		res.setVehicles(vehiclePojos);

		Collection<OfferPojo> offerPojos = new ArrayList<>();
		for (Offer o : c.getOffers()) {
			OfferPojo op = new OfferPojo();
			op.setCanceled(o.isCanceled());
			op.setFinalMode(o.isFinalMode());
			op.setMaxDateToRequest(o.getMaxDateToRequest());
			op.setScore(o.getScore());
			op.setTicker(o.getTicker());
			op.setTotalPrice(o.getTotalPrice());
			op.setVehicle(o.getVehicle().getPlate());

			Collection<String> traverseTowns = new ArrayList<>();
			for (TraverseTown tt : o.getTraverseTowns()) {
				traverseTowns.add(tt.getTown().getName());
			}

			op.setTraverseTowns(traverseTowns);

			Collection<EvaluationPojo> evaluationPojos = new ArrayList<>();
			for (Evaluation e : o.getEvaluations()) {
				EvaluationPojo ep = new EvaluationPojo();
				ep.setComment(e.getComment());
				ep.setCustomer(e.getCustomer().getName());
				ep.setMark(e.getMark());
				ep.setMoment(e.getMoment());
				ep.setOffer(e.getOffer().getTicker());

				evaluationPojos.add(ep);
			}

			op.setEvaluations(evaluationPojos);
		}

		res.setOffers(offerPojos);
		res.setEvaluations(evaluationPojos);

		return res;
	}

	public ActorPojo findActorPojo() {
		ActorPojo ap = new ActorPojo();
		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);

			final Authority auth = new Authority();

			auth.setAuthority(Authority.SPONSOR);
			if (userAccount.getAuthorities().contains(auth)) {
				ap = this.getSponsorPojo();
			}
			auth.setAuthority(Authority.AUDITOR);
			if (userAccount.getAuthorities().contains(auth)) {
				ap = this.getAuditorPojo();
			}
			auth.setAuthority(Authority.CARRIER);

			auth.setAuthority(Authority.CUSTOMER);
			if (userAccount.getAuthorities().contains(auth)) {
				ap = this.getCarrierPojo();
				ap = this.getCustomerPojo();
			}

		} catch (final Exception e) {
		}

		return ap;
	}
}
