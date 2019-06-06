
package controllers.administrator;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationController extends AbstractController {

	@Autowired
	private ConfigurationService	confService;
	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		final String auth = this.actorService.findActorType();
		if (auth == "Administrator") {
			Configuration configuration;

			configuration = new ArrayList<Configuration>(this.confService.findAll()).get(0);

			result = new ModelAndView("configuration/administrator/list");

			result.addObject("requestURI", "configuration/administrator/list.do");
			result.addObject("configuration", configuration);
		} else {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final String auth = this.actorService.findActorType();
		if (auth == "Administrator") {
			Configuration configuration;
			configuration = this.confService.findOne();
			Assert.notNull(configuration);
			result = this.createEditModelAndView(configuration);
		} else {
			result = new ModelAndView("redirect:/");
		}

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;
		final String auth = this.actorService.findActorType();
		if (auth == "Administrator") {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(configuration);
			} else {
				try {

					this.confService.save(configuration);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(configuration, "configuration.commit.error");
				}
			}
		} else {
			result = new ModelAndView("redirect:/");
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;
		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configuration/administrator/edit");

		result.addObject("configuration", configuration);
		result.addObject("message", messageCode);
		return result;
	}

}
