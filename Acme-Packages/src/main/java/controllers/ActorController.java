
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.ConfigurationService;
import forms.DisplayActorForm;
import forms.EditActorForm;
import forms.SignUpForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------
	public ActorController() {
		super();
	}

	//Create
	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final SignUpForm registerForm = this.actorService.getSignUpForm();

		final UserAccount ua = this.userAccountService.create();

		registerForm.setUserAccount(ua);

		result = this.createEditModelAndView(registerForm);

		return result;
	}

	//SAVE
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final @Valid SignUpForm actor, final BindingResult binding) {
		ModelAndView result;
		try {
			this.actorService.save(actor, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(actor);
			} else {
				result = new ModelAndView("redirect:/");
			}
		} catch (final Throwable oops) {

			final String mess = "actor.commit.error";

			result = this.createEditModelAndView(actor, mess);
		}
		return result;
	}

	//DISPLAY
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayActor(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			result = new ModelAndView("actor/display");
			final DisplayActorForm da = this.actorService.getDisplayActorForm(intId);
			result.addObject("da", da);
			result.addObject("id", id);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;

	}

	//EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editActor() {
		ModelAndView result;

		final EditActorForm eaf = this.actorService.getEditActorForm();

		result = this.createEditModelAndView(eaf);

		return result;
	}

	//SAVE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(final EditActorForm actor, final BindingResult binding) {
		ModelAndView result;
		try {
			this.actorService.save(actor, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(actor);
			} else {
				result = new ModelAndView("redirect:/actor/display.do");
			}
		} catch (final Throwable oops) {

			final String mess = "actor.commit.error";

			result = this.createEditModelAndView(actor, mess);
		}
		return result;
	}

	//DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteActor(final EditActorForm actor, final BindingResult binding) {
		ModelAndView result;

		try {
			this.actorService.delete();
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(actor, "actor.commit.error");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SignUpForm actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SignUpForm actor, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/create");
		result.addObject("signUpForm", actor);
		result.addObject("message", message);
		result.addObject("makes", this.configurationService.findMakes());

		return result;
	}

	private ModelAndView createEditModelAndView(final EditActorForm actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EditActorForm actor, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("editActorForm", actor);
		result.addObject("message", message);
		result.addObject("makes", this.configurationService.findMakes());

		final boolean company = this.actorService.findActorType().equals("Company");
		result.addObject("company", company);

		return result;
	}
}
