
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Actor;
import forms.BanForm;

@Controller
@RequestMapping("/ban/administrator")
public class BanController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ActorService			actorService;


	public BanController() {

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final String auth = this.actorService.findActorType();
		if (auth == "Administrator") {
			try {
				final BanForm form = new BanForm();

				final Collection<Actor> suspicious = this.adminService.findSpammers();

				result = new ModelAndView("ban/list");
				result.addObject("suspicious", suspicious);
				result.addObject("banForm", form);
				result.addObject("requestURI", "ban/administrator/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/");
			}
		} else {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@Valid final BanForm banForm, final BindingResult binding) {
		ModelAndView result;
		final String auth = this.actorService.findActorType();
		Actor a;
		if (auth == "Administrator") {
			if (binding.hasErrors()) {
				result = new ModelAndView("redirect:");
			} else {
				try {
					a = this.actorService.findOne(banForm.getIdActor());
				} catch (final Exception e) {
					return new ModelAndView("redirect:/");
				}
				if (a.getSpammer() == true) {
					try {
						this.adminService.changeStatus(banForm.getIdActor());
						result = new ModelAndView("redirect:list.do");
					} catch (final Throwable oops) {
						result = new ModelAndView("redirect:/");
					}
				} else {
					result = new ModelAndView("redirect:/");
					return result;
				}
			}
		} else {
			result = new ModelAndView("redirect:/");
			return result;
		}

		return result;
	}
}
