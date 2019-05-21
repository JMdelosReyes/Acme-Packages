
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.HashPasswordParameter;
import utilities.Validators;
import domain.Administrator;
import domain.MessBox;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessBoxService			messBoxService;


	// Constructor

	public AdministratorService() {
		super();
	}
	// CRUD methods
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Administrator create() {
		//Only admins creates admins
		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		final Administrator result = new Administrator();

		final Collection<SocialProfile> soc = new ArrayList<SocialProfile>();
		//COSAS COMUNES DE ACTOR
		result.setName("");
		result.setMiddleName("");
		result.setSurname("");
		result.setPhoto("");
		result.setEmail("");
		result.setPhoneNumber("");
		result.setAddress("");
		result.setBanned(false);
		result.setSpammer(false);
		result.setSocialProfiles(soc);
		result.setCreditCard(null);
		final List<MessBox> systemBoxes = new ArrayList<MessBox>();
		result.setMessageBoxes(systemBoxes);

		//CUENTA
		final UserAccount ua = new UserAccount();
		final Authority auth2 = new Authority();
		auth2.setAuthority(Authority.ADMIN);
		ua.addAuthority(auth);
		result.setUserAccount(ua);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);
		//Only admins save admins
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Administrator result;
		if (administrator.getId() == 0) {
			//Username must be unique
			UserAccount uc = administrator.getUserAccount();

			uc.setPassword(HashPasswordParameter.generateHashPassword(uc.getPassword()));

			uc = this.userAccountService.save(uc);

			administrator.setUserAccount(uc);

			Collection<MessBox> sb = administrator.getMessageBoxes();

			sb.addAll(this.messBoxService.createSystemMessageBoxes());

			sb = this.messBoxService.saveSystemBoxes(sb);

			administrator.setMessageBoxes(sb);

			//Comprobacion de phonenumber
			if (administrator.getPhoneNumber() != null) {
				final String phone = administrator.getPhoneNumber();
				if (Validators.validPhone(phone))
					administrator.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
			}
			//COMPROBAR CREDITCARD
			Assert.isTrue(Validators.checkCreditCard(administrator.getCreditCard()));
			Assert.isTrue(this.configurationService.findOne().getMakes().contains(administrator.getCreditCard().getMake()));

			result = this.administratorRepository.save(administrator);
		} else {

			final Administrator admin = (Administrator) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(admin.getId() == administrator.getId());
			Assert.isTrue(admin.getUserAccount().getAuthorities() == administrator.getUserAccount().getAuthorities());

			//Comprobacion de phonenumber
			if (administrator.getPhoneNumber() != null) {
				final String phone = administrator.getPhoneNumber();
				if (Validators.validPhone(phone))
					administrator.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
			}
			//COMPROBAR CREDITCARD
			Assert.isTrue(Validators.checkCreditCard(administrator.getCreditCard()));
			Assert.isTrue(this.configurationService.findOne().getMakes().contains(administrator.getCreditCard().getMake()));

			result = this.administratorRepository.save(administrator);
		}

		return result;
	}
	public void flush() {
		this.administratorRepository.flush();
	}

	public void delete(final Administrator admin) {
		Assert.notNull(admin);
		Assert.isTrue(admin.getId() > 0);
		//Only an admin can delete his own profile
		final UserAccount principal = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Assert.isTrue(this.actorService.findByUserAccountId(principal.getId()).getId() == admin.getId());

		this.messBoxService.deleteMessBoxes();

		this.administratorRepository.delete(admin.getId());
	}

}
