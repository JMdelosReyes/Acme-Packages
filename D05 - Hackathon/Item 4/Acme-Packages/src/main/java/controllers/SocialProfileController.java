
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructors -----------------------------------------------------------

	public SocialProfileController() {
		super();
	}

	// LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listSocialProfile() {
		ModelAndView result;

		try {
			Collection<SocialProfile> socialProfiles;
			socialProfiles = this.socialProfileService.findOwnSocialProfiles();

			result = new ModelAndView("socialProfile/list");
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("requestURI", "socialProfile/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		SocialProfile sp;
		sp = this.socialProfileService.create();
		result = this.createEditModelAndView(sp);
		return result;
	}

	//EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView Edit(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			SocialProfile sp;
			sp = this.socialProfileService.findOne(intId);
			Assert.notNull(sp);
			Assert.isTrue(this.socialProfileService.findOwnSocialProfiles().contains(sp));
			result = this.createEditModelAndView(sp);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// SAVE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile sp, final BindingResult binding) {
		ModelAndView result;

		if (sp.getId() != 0) {
			try {
				this.socialProfileService.findOne(sp.getId());
				final Collection<SocialProfile> ownedProfiles = this.socialProfileService.findOwnSocialProfiles();
				Assert.isTrue(ownedProfiles.contains(sp));
			} catch (final Throwable oops) {
				return new ModelAndView("redirect:/");
			}
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sp);
		} else {
			try {
				this.socialProfileService.save(sp);
				result = new ModelAndView("redirect:/socialProfile/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sp, "sp.commit.error");
			}
		}

		return result;
	}

	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile sp, final BindingResult binding) {
		ModelAndView result;

		try {
			final SocialProfile sociP = this.socialProfileService.findOne(sp.getId());
			Assert.notNull(sociP);
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findOwnSocialProfiles();
			Assert.notNull(socialProfiles);
			Assert.isTrue(socialProfiles.contains(sociP));
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			this.socialProfileService.delete(sp);
			result = new ModelAndView("redirect:/socialProfile/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sp, "sp.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final SocialProfile sp) {
		ModelAndView result;
		result = this.createEditModelAndView(sp, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile sp, final String messageCode) {
		ModelAndView result;
		if (sp.getId() == 0) {
			result = new ModelAndView("socialProfile/create");
		} else {
			result = new ModelAndView("socialProfile/edit");
		}

		result.addObject("socialProfile", sp);
		result.addObject("message", messageCode);
		return result;
	}
}
