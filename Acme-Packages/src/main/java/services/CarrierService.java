
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

import repositories.CarrierRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.HashPasswordParameter;
import utilities.Validators;
import domain.Carrier;
import domain.Curriculum;
import domain.Fare;
import domain.MessBox;
import domain.Offer;
import domain.SocialProfile;
import domain.Vehicle;

@Transactional
@Service
public class CarrierService {

	@Autowired
	private CarrierRepository	carrierRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService actorService;

	@Autowired
	@private ConfigurationService configurationService;


	public CarrierService() {

	}

	public Carrier findOne(final int id) {
		Assert.isTrue(id > 0);
		Carrier result;
		result = this.carrierRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Carrier> findAll() {
		Collection<Carrier> result;
		result = this.carrierRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Carrier create() {
		Authentication authentication;
		SecurityContext context;
		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();

		boolean check = false;
		if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920))
			check = true;
		Assert.isTrue(check);

		final Carrier result = new Carrier();

		// Actor
		result.setName("");
		result.setSurname("");
		result.setMiddleName("");
		result.setPhoto("");
		result.setEmail("");
		result.setPhoneNumber("");
		result.setAddress("");
		result.setSpammer(false);
		result.setCreditCard(null);
		result.setBanned(false);
		result.setMessageBoxes(new ArrayList<MessBox>());
		result.setSocialProfiles(new ArrayList<SocialProfile>());

		// User Account
		final UserAccount userAc = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CARRIER);
		userAc.addAuthority(auth);
		result.setUserAccount(userAc);

		// Carrier
		result.setVat("");
		result.setScore(0);
		result.setCurricula(new ArrayList<Curriculum>());
		result.setFares(new ArrayList<Fare>());
		result.setOffers(new ArrayList<Offer>());
		result.setVehicles(new ArrayList<Vehicle>());

		return result;
	}

	public Carrier save(final Carrier carrier) {
		Assert.notNull(carrier);
		final Carrier result;

		if (carrier.getId() == 0) {
			Authentication authentication;
			SecurityContext context;
			context = SecurityContextHolder.getContext();
			Assert.notNull(context);
			authentication = context.getAuthentication();

			boolean check = false;
			if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920))
				check = true;
			Assert.isTrue(check);

			UserAccount uc = carrier.getUserAccount();
			uc.setPassword(HashPasswordParameter.generateHashPassword(uc.getPassword()));
			uc = this.userAccountService.save(uc);
			carrier.setUserAccount(uc);

			Assert.isTrue(Validators.validEmail(carrier.getEmail()));

			if (carrier.getPhoneNumber() != null) {
				final String phone = carrier.getPhoneNumber();
				if (Validators.validPhone(phone))
					carrier.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
			}

			Assert.isTrue(Validators.checkCreditCard(carrier.getCreditCard()));
			Assert.isTrue(this.configurationService.findOne().getMakes().contains(carrier.getCreditCard().getMake()));
		} else {
			final UserAccount principal = LoginService.getPrincipal();
			Assert.isTrue(!this.actorService.findActorType().equals("Carrier"));
			final Carrier old = this.carrierRepository.findOne(carrier.getId());
			Assert.notNull(old);

			Assert.isTrue(this.actorService.findByUserAccountId(principal.getId()).getId() == carrier.getId());

			Assert.isTrue(Validators.validEmail(carrier.getEmail()));

			if (carrier.getPhoneNumber() != null) {
				final String phone = carrier.getPhoneNumber();
				if (Validators.validPhone(phone))
					carrier.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
			}

			Assert.isTrue(Validators.checkCreditCard(carrier.getCreditCard()));
			Assert.isTrue(this.configurationService.findOne().getMakes().contains(carrier.getCreditCard().getMake()));

			Assert.isTrue(carrier.isBanned()==old.isBanned());
			Assert.isTrue(carrier.isSpammer()==old.isSpammer());
			Assert.isTrue(carrier.getScore()==old.getScore());
		}

		result = this.carrierRepository.save(carrier);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Carrier carrier) {
		Assert.notNull(carrier);
		Assert.isTrue(carrier.getId() > 0);
		Assert.isTrue(this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId() == carrier.getId());
		// BORRAR MIERDA
		this.carrierRepository.delete(carrier.getId());
	}

	public void flush() {
		this.carrierRepository.flush();
	}

	public void adminUpdate(final Carrier carrier){
		Assert.notNull(carrier);
		Assert.isTrue(carrier.getId()>0);
		Assert.isTrue(this.actorService.findActorType().equals("Administrador"));

		final Carrier old = this.carrierRepository.findOne(carrier.getId());
		Assert.notNull(old);

		final Carrier clon = (Carrier) old.clone();
		clon.setBanned(carrier.isBanned());
		clon.setSpammer(carrier.isSpammer());
		clon.setScore(carrier.getScore());
		this.carrierRepository.save(clon);
	}

}
