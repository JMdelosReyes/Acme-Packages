
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
import domain.Carrier;
import domain.MessBox;
import domain.SocialProfile;
import domain.Sponsorship;

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
		systemBoxes.addAll(this.messBoxService.createSystemMessageBoxes());

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

			sb = this.messBoxService.saveSystemBoxes(sb);

			administrator.setMessageBoxes(sb);

			//Comprobacion de phonenumber
			if (administrator.getPhoneNumber() != null) {
				final String phone = administrator.getPhoneNumber();
				if (Validators.validPhone(phone)) {
					administrator.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
				}
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
				if (Validators.validPhone(phone)) {
					administrator.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
				}
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

		this.administratorRepository.delete(admin.getId());
	}

	//Dashboard
	//The average, the minimum, the maximum, and the standard deviation of times that a sponsorship has been shown.

	public Double avgSPShow() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.avgSPShow();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double minSPShow() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.minSPShow();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double maxSPShow() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.maxSPShow();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double stddevSPShow() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.stddevSPShow();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}

	//The average, the minimum, the maximum, and the standard deviation of scores from registered carriers.

	public Double avgScoreCarriers() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.avgScoreCarriers();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double minScoreCarriers() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.minScoreCarriers();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double maxScoreCarriers() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.maxScoreCarriers();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double stddevScoreCarriers() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.stddevScoreCarriers();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}

	//The average, the minimum, the maximum, and the standard deviation of evaluations made by customers.

	public Double avgEvaluationByCustomer() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.avgEvaluationByCustomer();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double minEvaluationByCustomer() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.minEvaluationByCustomer();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double maxEvaluationByCustomer() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.maxEvaluationByCustomer();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double stddevEvaluationByCustomer() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.stddevEvaluationByCustomer();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}

	//The average, the minimum, the maximum, and the standard deviation of comments per issues.

	public Double avgCommentsPerIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.avgCommentsPerIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double minCommentsPerIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.minCommentsPerIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double maxCommentsPerIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.maxCommentsPerIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double stddevCommentsPerIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.stddevCommentsPerIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	//Top-3 most shown sponsorships.

	public Collection<Sponsorship> top3ShownSponsorships() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		List<Sponsorship> result = new ArrayList<>(this.administratorRepository.top3ShownSponsorships());
		final Integer last = result.size();
		if (last == 1) {
			result = result.subList(0, 1);
		} else if (last == 2) {
			result = result.subList(0, 2);
		} else if (last >= 3) {
			result = result.subList(0, 3);
		} else {
			result = new ArrayList<Sponsorship>();
		}
		return result;
	}

	//Top-3 carriers with the highest score.

	public Collection<Carrier> top3CarriersWithHigherScore() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		List<Carrier> result = new ArrayList<>(this.administratorRepository.top3CarriersWithHigherScore());
		final Integer last = result.size();
		if (last == 1) {
			result = result.subList(0, 1);
		} else if (last == 2) {
			result = result.subList(0, 2);
		} else if (last >= 3) {
			result = result.subList(0, 3);
		} else {
			result = new ArrayList<Carrier>();
		}
		return result;
	}

	//Top-5 most visited towns.
	//	//TODO
	//	public Collection<Town> top5MostVisitedTowns() {
	//
	//		final UserAccount principal = LoginService.getPrincipal();
	//		Assert.notNull(principal);
	//
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.ADMIN);
	//		Assert.isTrue(principal.getAuthorities().contains(auth));
	//		List<Town> result = new ArrayList<>(this.administratorRepository.top5MostVisitedTowns());
	//		final Integer last = result.size();
	//		if (last == 1) {
	//			result = result.subList(0, 1);
	//		} else if (last == 2) {
	//			result = result.subList(0, 2);
	//		} else if (last == 3) {
	//			result = result.subList(0, 2);
	//		} else if (last == 4) {
	//			result = result.subList(0, 2);
	//		} else if (last >= 5) {
	//			result = result.subList(0, 3);
	//		} else {
	//			result = new ArrayList<Town>();
	//		}
	//		return result;
	//	}

	//The ratio of empty versus non-empty finders.
	public Double RatioNonEmptyFinders() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.RatioNonEmptyFinders();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double RatioEmptyFinders() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.RatioEmptyFinders();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	//The ratio of closed versus non-closed issues.
	public Double ratioClosedIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.ratioClosedIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}
	public Double ratioNonClosedIssue() {

		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));
		Double result;
		result = this.administratorRepository.ratioNonClosedIssue();
		if (result == null) {
			result = 0.0;
		}
		return result;

	}

	//TODO
	//The listing of auditors who have got at least 10% of issues closed above the average. 
	//	public Collection<Auditor> AudiorsWith10percentmoreIsuseClosedThanAVG() {
	//
	//		final UserAccount principal = LoginService.getPrincipal();
	//		Assert.notNull(principal);
	//
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.ADMIN);
	//		Assert.isTrue(principal.getAuthorities().contains(auth));
	//		List<Auditor> result = new ArrayList<>(this.administratorRepository.AudiorsWith10percentmoreIsuseClosedThanAVG());
	//		final int size = result.size();
	//
	//		if (size == 0) {
	//			result = new ArrayList<>();
	//		}
	//		return result;
	//
	//	}
}
