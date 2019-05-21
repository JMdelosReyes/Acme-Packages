
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

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.HashPasswordParameter;
import utilities.Validators;
import domain.MessBox;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository		sponsorRepository;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	public SponsorService() {
		super();
	}

	public Sponsor findOne(final int id) {
		Assert.isTrue(id > 0);
		Sponsor result;
		result = this.sponsorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor create() {
		Authentication authentication;
		SecurityContext context;
		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();

		boolean check = false;
		if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920))
			check = true;
		Assert.isTrue(check);

		final Sponsor result = new Sponsor();

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
		auth.setAuthority(Authority.SPONSOR);
		userAc.addAuthority(auth);
		result.setUserAccount(userAc);

		// Sponsor
		result.setNif("");
		result.setSponsorships(new ArrayList<Sponsorship>());

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		final Sponsor result;

		if (sponsor.getId() == 0) {
			Authentication authentication;
			SecurityContext context;
			context = SecurityContextHolder.getContext();
			Assert.notNull(context);
			authentication = context.getAuthentication();

			boolean check = false;
			if ((authentication == null) || (authentication.getPrincipal().hashCode() == 1105384920))
				check = true;
			Assert.isTrue(check);

			UserAccount uc = sponsor.getUserAccount();
			uc.setPassword(HashPasswordParameter.generateHashPassword(uc.getPassword()));
			uc = this.userAccountService.save(uc);
			sponsor.setUserAccount(uc);

			Assert.isTrue(Validators.validEmail(sponsor.getEmail()));

			if (sponsor.getPhoneNumber() != null) {
				final String phone = sponsor.getPhoneNumber();
				if (Validators.validPhone(phone))
					sponsor.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
			}

		} else {
			final UserAccount principal = LoginService.getPrincipal();
			if (this.actorService.findActorType().equals("Sponsor")) {

				final Sponsor old = this.sponsorRepository.findOne(sponsor.getId());
				Assert.notNull(old);

				Assert.isTrue(this.actorService.findByUserAccountId(principal.getId()).getId() == sponsor.getId());

				Assert.isTrue(Validators.validEmail(sponsor.getEmail()));

				if (sponsor.getPhoneNumber() != null) {
					final String phone = sponsor.getPhoneNumber();
					if (Validators.validPhone(phone))
						sponsor.setPhoneNumber(this.configurationService.findOne().getCountryCode() + phone);
				}

				Assert.isTrue(sponsor.isBanned() == old.isBanned());
				Assert.isTrue(sponsor.isSpammer() == old.isSpammer());
				Assert.isTrue(sponsor.getNif() == old.getNif());
			} else if (this.actorService.findActorType().equals("Administrator")) {
				final Sponsor old = this.sponsorRepository.findOne(sponsor.getId());
				Assert.notNull(old);
				Assert.isTrue(old.getSponsorships() != sponsor.getSponsorships());
			}

		}

		result = this.sponsorRepository.save(sponsor);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		Assert.isTrue(this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId() == sponsor.getId());
		this.sponsorRepository.delete(sponsor.getId());
	}

	public void flush() {
		this.sponsorRepository.flush();
	}

	public void adminUpdate(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() > 0);
		Assert.isTrue(this.actorService.findActorType().equals("Administrator"));

		final Sponsor old = this.sponsorRepository.findOne(sponsor.getId());
		Assert.notNull(old);

		final Sponsor clon = (Sponsor) old.clone();
		clon.setBanned(sponsor.isBanned());
		clon.setSpammer(sponsor.isSpammer());
		clon.setSponsorships(sponsor.getSponsorships());
		this.sponsorRepository.save(clon);
	}

	public Sponsor findSponsorBySponsorship(final int id) {
		Assert.isTrue(id > 0);
		final Sponsor result = this.sponsorRepository.findSponsorBySponsorship(id);
		return result;
	}
}
