
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.HashPasswordParameter;
import utilities.Validators;
import domain.Actor;
import domain.Auditor;
import domain.Issue;
import domain.MessBox;
import domain.SocialProfile;
import domain.Solicitation;

@Transactional
@Service
public class AuditorService {

	//Managed repository
	@Autowired
	AuditorRepository		auditorRepository;

	//Supporting services
	@Autowired
	ActorService			actorService;

	@Autowired
	UserAccountService		userAccountService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	MessService				messService;

	@Autowired
	MessBoxService			messBoxService;


	public AuditorService() {
		super();
	}

	public Auditor findOne(final int audId) {
		Assert.isTrue(audId != 0);
		Auditor res;

		res = this.auditorRepository.findOne(audId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> res;

		res = this.auditorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Auditor create() {
		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));

		final Auditor result = new Auditor();

		//Actor
		result.setName("");
		result.setMiddleName("");
		result.setSurname("");
		result.setPhoto("");
		result.setEmail("");
		result.setPhoneNumber("");
		result.setAddress("");
		result.setBanned(false);
		result.setSpammer(false);
		final Collection<SocialProfile> soc = new ArrayList<SocialProfile>();
		result.setSocialProfiles(soc);
		result.setCreditCard(null);
		final List<MessBox> systemBoxes = new ArrayList<MessBox>();
		result.setMessageBoxes(systemBoxes);

		//User Account
		final UserAccount userAud = new UserAccount();
		final Authority hacAuth = new Authority();

		hacAuth.setAuthority(Authority.AUDITOR);
		userAud.addAuthority(hacAuth);
		result.setUserAccount(userAud);

		//
		result.setSolicitations(new ArrayList<Solicitation>());
		result.setIssues(new ArrayList<Issue>());

		return result;
	}
	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);
		Auditor res;

		if (auditor.getId() == 0) {
			//User must be admin
			final UserAccount principal = LoginService.getPrincipal();
			Assert.notNull(principal);

			Assert.isTrue(this.actorService.findActorType().equals("Administrator"));

			//Hash password	
			UserAccount uc = auditor.getUserAccount();
			uc.setPassword(HashPasswordParameter.generateHashPassword(uc.getPassword()));
			uc = this.userAccountService.save(uc);
			auditor.setUserAccount(uc);

			Collection<MessBox> sb = auditor.getMessageBoxes();
			sb.addAll(this.messBoxService.createSystemMessageBoxes());
			sb = this.messBoxService.saveSystemBoxes(sb);
			auditor.setMessageBoxes(sb);

			//The email must be a valid one
			Assert.isTrue(Validators.validEmail(auditor.getEmail()));

			//Add ZipCode phoneNumber
			if (auditor.getPhoneNumber() != null) {
				final String phone = auditor.getPhoneNumber();
				if (Validators.validPhone(phone)) {
					auditor.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
				}
			}

			//COMPROBAR CREDITCARD
			Assert.isTrue(Validators.checkCreditCard(auditor.getCreditCard()));
			Assert.isTrue(this.configurationService.findOne().getMakes().contains(auditor.getCreditCard().getMake()));

			//Save in database
			res = this.auditorRepository.save(auditor);

		} else {
			//Only the auditor and the admin can update the profile
			final UserAccount principal = LoginService.getPrincipal();

			final Authority auth = new Authority();
			auth.setAuthority(Authority.AUDITOR);
			final Authority auth2 = new Authority();
			auth2.setAuthority(Authority.ADMIN);

			Assert.isTrue((principal.getAuthorities().contains(auth)) || (principal.getAuthorities().contains(auth2)));

			//The user must exists
			final Auditor old = this.findOne(auditor.getId());
			Assert.notNull(old);

			//If the auditor wants to edit his or her profile
			if (principal.getAuthorities().contains(auth)) {
				Assert.isTrue(this.actorService.findByUserAccountId(principal.getId()).getId() == auditor.getId());

				//The auditor can't change his or her privileges nor his or her boolean attributes
				Assert.isTrue(auditor.getSpammer() == old.getSpammer());
				Assert.isTrue(auditor.getBanned() == old.getBanned());
				Assert.isTrue(auditor.getUserAccount().getAuthorities().equals(old.getUserAccount().getAuthorities()));

				//The email must be a valid one
				Assert.isTrue(Validators.validEmail(auditor.getEmail()));

				if (auditor.getPhoneNumber() != null) {
					final String phone = auditor.getPhoneNumber();
					if (Validators.validPhone(phone)) {
						auditor.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
					}
				}

				Assert.isTrue(Validators.checkCreditCard(auditor.getCreditCard()));
				Assert.isTrue(this.configurationService.findOne().getMakes().contains(auditor.getCreditCard().getMake()));

			} else if (principal.getAuthorities().contains(auth2)) {
				//If the admin is updating
				//Admins are able to change boolean attributes 

				Assert.isTrue((auditor.getName() != null) && (auditor.getName().equals(old.getName())));
				Assert.isTrue((auditor.getMiddleName() != null) && (auditor.getMiddleName().equals(old.getMiddleName())));
				Assert.isTrue((auditor.getSurname() != null) && (auditor.getSurname().equals(old.getSurname())));
				Assert.isTrue((auditor.getPhoto() != null) && (auditor.getPhoto().equals(old.getPhoto())));
				Assert.isTrue((auditor.getEmail() != null) && (auditor.getEmail().equals(old.getEmail())));
				Assert.isTrue((auditor.getPhoneNumber() != null) && (auditor.getPhoneNumber().equals(old.getPhoneNumber())));
				Assert.isTrue((auditor.getAddress() != null) && (auditor.getAddress().equals(old.getAddress())));
				Assert.isTrue((auditor.getSocialProfiles() != null) && (auditor.getSocialProfiles().equals(old.getSocialProfiles())));
				Assert.isTrue((auditor.getMessageBoxes() != null) && (auditor.getMessageBoxes().equals(old.getMessageBoxes())));

				Assert.isTrue((auditor.getCreditCard() != null) && (auditor.getCreditCard().equals(old.getCreditCard())));
				Assert.isTrue((auditor.getIssues() != null) && (auditor.getIssues().equals(old.getIssues())));
				Assert.isTrue((auditor.getSolicitations() != null) && (auditor.getSolicitations().equals(old.getSolicitations())));

			}
			res = this.auditorRepository.save(auditor);
		}
		return res;

	}
	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);

		//Same auditor logged
		final UserAccount principal = LoginService.getPrincipal();
		Assert.isTrue(this.actorService.findActorType().equals("Auditor"));
		final Actor hLogged = this.actorService.findByUserAccountId(principal.getId());
		final Auditor old = this.auditorRepository.findOne(auditor.getId());
		Assert.isTrue(hLogged.getId() == old.getId());

		this.auditorRepository.delete(auditor);

	}

	public void flush() {
		this.auditorRepository.flush();
	}

}
