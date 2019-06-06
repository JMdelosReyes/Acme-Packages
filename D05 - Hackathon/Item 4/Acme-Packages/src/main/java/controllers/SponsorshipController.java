
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	//Services
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorService		sponsorService;


	//Constructor

	public SponsorshipController() {
		super();
	}

	//LIST SPONSOR
	@RequestMapping(value = "/sponsor/list", method = RequestMethod.GET)
	public ModelAndView listSponsorshipsSponsor() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsor sponsor;
		//Sponsor logged
		try {
			UserAccount principal = LoginService.getPrincipal();
			sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			sponsorships = sponsor.getSponsorships();
			result = new ModelAndView("sponsorship/list");
			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	//LIST ADMIN
	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView listSponsorshipsAdmin() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Assert.isTrue(this.actorService.findActorType().equals("Administrator"));
		//Admin logged
		try {
			sponsorships = this.sponsorshipService.findSponsorshipNotValid();
			result = new ModelAndView("sponsorship/list");
			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/administrator/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	//CREATE
	@RequestMapping(value = "/sponsor/create", method = RequestMethod.GET)
	public ModelAndView createSponsorship() {
		ModelAndView result;
		Sponsorship spon;

		spon = this.sponsorshipService.create();
		result = this.createEditModelAndView(spon);

		return result;
	}

	//EDIT SPONSOR
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
	public ModelAndView editSponsorship(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		Sponsorship spo;
		Integer intId;
		Sponsor sponsor;
		try {
			intId = Integer.valueOf(id);
			UserAccount principal = LoginService.getPrincipal();
			sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			spo = this.sponsorshipService.findOne(intId);
			Assert.notNull(spo);
			final Collection<Sponsorship> sponsorships = sponsor.getSponsorships();
			Assert.isTrue(sponsorships.contains(spo));
			result = this.createEditModelAndView(spo);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//EDIT ADMIN
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editSponsorshipAdmin(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		Sponsorship spo;
		Integer intId;
		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			spo = this.sponsorshipService.findOne(intId);
			Assert.notNull(spo);
			Assert.isNull(spo.getExpirationDate());
			Assert.isTrue(spo.isValid() == false);
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipNotValid();
			Assert.isTrue(sponsorships.contains(spo));
			result = this.createEditModelAndView(spo);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//SAVE
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorship(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;
		Sponsorship spo;

		try {
			spo = this.sponsorshipService.reconstruct(sponsorship, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsorship);
		} else {
			try {
				this.sponsorshipService.save(spo);
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "spon.commit.error");
			}
		}
		return result;

	}

	//SAVE
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorshipAdmin(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;
		Sponsorship spo;

		try {
			spo = this.sponsorshipService.validate(sponsorship.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsorship);
		} else {
			try {
				this.sponsorshipService.save(spo);
				result = new ModelAndView("redirect:/sponsorship/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "spon.commit.error");
			}
		}
		return result;

	}

	//DELETE
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteSponsorship(@RequestParam(required = false, defaultValue = "0") String id) {
		ModelAndView result;
		Sponsor sponsor;
		int intId;
		Sponsorship sponsorship;
		try {
			intId = Integer.valueOf(id);
			sponsorship = this.sponsorshipService.findOne(intId);
			UserAccount principal = LoginService.getPrincipal();
			sponsor = this.sponsorService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Assert.isTrue(sponsor.getSponsorships().contains(sponsorship));
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			this.sponsorshipService.delete(intId);
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "spon.commit.error");
		}
		return result;
	}

	//DELETE ADMIN
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteSponsorshipAdmin(@RequestParam(required = false, defaultValue = "0") String id) {
		ModelAndView result;
		int intId;
		Sponsorship sponsorship;
		try {
			Assert.isTrue(this.actorService.findActorType().equals("Administrator"));
			intId = Integer.valueOf(id);
			sponsorship = this.sponsorshipService.findOne(intId);
			Assert.isTrue(sponsorship.isValid() == false);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			this.sponsorshipService.delete(intId);
			result = new ModelAndView("redirect:/sponsorship/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "spon.commit.error");
		}
		return result;
	}

	//CreateEditModelAndView

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		if (sponsorship.getId() == 0) {
			result = new ModelAndView("sponsorship/create");
		} else {
			result = new ModelAndView("sponsorship/edit");
		}

		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);

		return result;
	}
}
