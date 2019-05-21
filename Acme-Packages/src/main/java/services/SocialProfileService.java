
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	//Managed Repository
	@Autowired
	SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService	actorService;


	//Constructor
	public SocialProfileService() {
		super();
	}
	//CRUD METHODS
	public SocialProfile create() {
		final Actor a = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(a.getId() != 0);
		final SocialProfile result = new SocialProfile();
		result.setProfileLink("");
		result.setNick("");
		result.setSocialNetwork("");
		return result;
	}

	public SocialProfile findOne(final int id) {
		Assert.isTrue(id > 0);
		final SocialProfile result;
		result = this.socialProfileRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<SocialProfile> findOwnSocialProfiles() {
		final UserAccount ua = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(ua.getId());
		return this.socialProfileRepository.findAllByActorId(a.getId());
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		SocialProfile result;
		final UserAccount principal = LoginService.getPrincipal();
		final Actor logged = this.actorService.findByUserAccountId(principal.getId());
		if (socialProfile.getId() == 0) {
			result = this.socialProfileRepository.save(socialProfile);
			logged.getSocialProfiles().add(result);
			this.actorService.save(logged);
		} else {
			Assert.isTrue(logged.getSocialProfiles().contains(socialProfile));
			result = this.socialProfileRepository.save(socialProfile);

		}
		return result;
	}
	public void delete(final SocialProfile sp) {
		Assert.notNull(sp);
		Assert.isTrue(sp.getId() != 0);
		final UserAccount principal = LoginService.getPrincipal();
		final Collection<SocialProfile> spowned = this.findOwnSocialProfiles();
		Assert.isTrue(spowned.contains(sp));

		final Actor logged = this.actorService.findByUserAccountId(principal.getId());

		logged.getSocialProfiles().remove(sp);
		this.actorService.save(logged);
		this.socialProfileRepository.delete(sp);

	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;
		result = this.socialProfileRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.socialProfileRepository.flush();
	}

}
