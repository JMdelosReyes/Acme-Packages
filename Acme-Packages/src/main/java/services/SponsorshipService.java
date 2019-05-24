
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private Validator				validator;


	public SponsorshipService() {
		super();
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		return result;
	}

	public Sponsorship findOne(final int id) {
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}
	public Sponsorship create() {
		final Sponsorship result = new Sponsorship();
		//Must be a sponsor
		Assert.isTrue(this.actorService.findActorType().equals("Sponsor"));

		result.setBanner("");
		result.setTarget("");
		result.setCount(0);
		result.setExpirationDate(null);
		result.setValid(false);
		result.setTotalCount(0);
		return result;
	}

	public Sponsorship save(final Sponsorship spon) {
		Assert.notNull(spon);
		Assert.isTrue(this.actorService.findActorType().equals("Sponsor") || (this.actorService.findActorType().equals("Administrator")));
		Sponsorship res;
		if (spon.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Sponsor"));
			final UserAccount principal = LoginService.getPrincipal();
			final Sponsor sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Assert.isTrue(spon.isValid() == false);
			Assert.isNull(spon.getExpirationDate());
			Assert.isTrue((spon.getCount() == 0) && (spon.getTotalCount() == 0));
			res = this.sponsorshipRepository.save(spon);
			Assert.notNull(res);
			sponsor.getSponsorships().add(res);
			this.sponsorService.save(sponsor);
		} else {
			final Sponsorship old = this.sponsorshipRepository.findOne(spon.getId());
			Assert.notNull(old);
			if (this.actorService.findActorType().equals("Administrator")) {
				//TODO: QUITAR EL COMPROBAR EL EXPIRATION DATE
				if (old.getExpirationDate() == null) {
					final Date date = new Date(System.currentTimeMillis() - 1000);
					final Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					final int anyoActual = cal.get(Calendar.YEAR);

					final Calendar cal2 = Calendar.getInstance();
					cal2.setTime(spon.getExpirationDate());
					final int anyoSpon = cal2.get(Calendar.YEAR);

					Assert.isTrue(spon.isValid());
					Assert.isTrue(anyoSpon == (anyoActual + 1));
				} else {
					Assert.isTrue(old.getBanner().equals(spon.getBanner()));
					Assert.isTrue(old.getTarget().equals(spon.getTarget()));
					Assert.isTrue(old.isValid() == spon.isValid());
					Assert.isTrue(old.getExpirationDate() == spon.getExpirationDate());
				}
			} else if (this.actorService.findActorType().equals("Sponsor")) {
				final UserAccount principal = LoginService.getPrincipal();
				final Sponsor sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
				Assert.isTrue(sponsor.getSponsorships().contains(old));
				Assert.isTrue(old.isValid());
				Assert.isTrue(old.getExpirationDate().after(new Date(System.currentTimeMillis() - 1000)));
			}

			res = this.sponsorshipRepository.save(spon);
		}
		return res;
	}
	public void delete(final int id) {
		Assert.notNull(id);
		Assert.isTrue(id != 0);
		final Sponsorship res = this.sponsorshipRepository.findOne(id);
		Assert.isTrue(this.actorService.findActorType().equals("Sponsor") || (this.actorService.findActorType().equals("Administrator")));
		if (this.actorService.findActorType().equals("Sponsor")) {
			final UserAccount principal = LoginService.getPrincipal();
			final Sponsor sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Assert.isTrue(sponsor.getSponsorships().contains(res));
			sponsor.getSponsorships().remove(res);
			this.sponsorService.save(sponsor);
			//Borrar el sponsorship
			this.sponsorshipRepository.delete(id);
		} else if (this.actorService.findActorType().equals("Administrator")) {
			//Quitar del sponsor
			Assert.isTrue(!res.isValid());
			final Sponsor sponsor = this.sponsorService.findSponsorBySponsorship(id);
			sponsor.getSponsorships().remove(res);
			this.sponsorService.adminUpdate(sponsor);
			//Borrar el sponsorship
			this.sponsorshipRepository.delete(id);
		}
	}

	public void updateCount(Sponsorship sponsorship) {
		Sponsorship clon = (Sponsorship) sponsorship.clone();
		clon.setCount(sponsorship.getCount() + 1);
		clon.setTotalCount(sponsorship.getTotalCount() + 1);
		sponsorship = clon;
		this.sponsorshipRepository.save(sponsorship);
	}

	//	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
	//		Sponsorship result;
	//
	//		final UserAccount principal = LoginService.getPrincipal();
	//		Assert.notNull(principal);
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.PROVIDER);
	//		Assert.isTrue(principal.getAuthorities().contains(auth));
	//
	//		if (sponsorship.getId() == 0) {
	//			Assert.isTrue(this.positionService.findAllFinalNotCancelled().contains(sponsorship.getPosition()));
	//			result = sponsorship;
	//		} else {
	//			final Provider prov = this.provService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
	//			result = this.sponsorshipRepository.findOne(sponsorship.getId());
	//			Assert.notNull(result);
	//			Assert.isTrue(prov.getSponsorships().contains(sponsorship));
	//			Assert.isTrue(this.positionService.findAllFinalNotCancelled().contains(result.getPosition()));
	//			final Sponsorship clon = (Sponsorship) result.clone();
	//			clon.setBanner(sponsorship.getBanner());
	//			clon.setCreditCard(sponsorship.getCreditCard());
	//			clon.setTargetUrl(sponsorship.getTargetUrl());
	//			result = clon;
	//		}
	//
	//		this.validator.validate(result, binding);
	//
	//		//Check if the credit card has expired
	//		final Date date = new Date();
	//		final Calendar cal = Calendar.getInstance();
	//		cal.setTime(date);
	//		final int mesActual = cal.get(Calendar.MONTH) + 1;
	//		final int anyoActual = cal.get(Calendar.YEAR) % 100;
	//
	//		if ((binding.getFieldError("creditCard.expirationMonth") == null) && (binding.getFieldError("creditCard.expirationYear") == null)) {
	//			if (((result.getCreditCard().getExpirationYear() < anyoActual) || ((result.getCreditCard().getExpirationYear() == anyoActual) && (result.getCreditCard().getExpirationMonth() < mesActual)))) {
	//				binding.rejectValue("creditCard.expirationMonth", "spon.commit.error.caduca");
	//				binding.rejectValue("creditCard.expirationYear", "spon.commit.error.caduca");
	//			}
	//		}
	//
	//		return result;
	//	}
	//	public Collection<Sponsorship> findActiveSponsorshipsByPositionId(final int id) {
	//		Assert.notNull(id);
	//		Collection<Sponsorship> result;
	//		result = this.sponsorshipRepository.findActiveSponsorshipsByParadeId(id);
	//		Assert.notNull(result);
	//		return result;
	//
	//	}
	//
	//	public void deleteSponsorshipsOfPosition(final Collection<Sponsorship> spons) {
	//		for (final Sponsorship s : spons) {
	//			final Provider provider = this.providerService.findProviderOfSponsorship(s.getId());
	//			provider.getSponsorships().remove(s);
	//			this.providerService.updateSponsorships(provider);
	//		}
	//		this.sponsorshipRepository.delete(spons);
	//	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	public Collection<Sponsorship> findSponsorshipNotValid() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findSponsorshipsNotValid();
		return result;
	}
}
