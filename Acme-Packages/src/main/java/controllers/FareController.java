
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
import services.ActorService;
import services.FareService;
import domain.Fare;

@Controller
@RequestMapping("/fare")
public class FareController extends AbstractController {

	@Autowired
	private FareService		fareService;

	@Autowired
	private ActorService	actorService;


	// Constructor
	public FareController() {
		super();
	}

	// CARRIER LIST
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView listCarrier() {
		ModelAndView result;

		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Collection<Fare> fares = this.fareService.findByCarrier(carrierId);

			result = new ModelAndView("fare/list");
			result.addObject("fares", fares);
			result.addObject("requestURI", "fare/carrier/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//GENERAL LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {

			final Collection<Fare> fares = this.fareService.findByOffer(intId);

			result = new ModelAndView("fares/list");
			result.addObject("fares", fares);
			result.addObject("requestURI", "fares/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	// CREATE
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Fare fare;
		fare = this.fareService.create();
		result = this.createEditModelAndView(fare);
		return result;
	}

	// EDIT
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		Integer intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Fare fare = this.fareService.findOne(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Assert.isTrue(this.fareService.findByCarrier(carrierId).contains(fare));

			result = this.createEditModelAndView(fare);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// SAVE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Fare fare, final BindingResult binding) {
		ModelAndView result;
		Fare reco;

		try {
			reco = this.fareService.reconstruct(fare, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(fare);
		} else {
			try {
				final Fare saved = this.fareService.save(reco);
				result = new ModelAndView("redirect:/fare/carrier/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fare, "fa.commit.error");
			}
		}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Fare fare = this.fareService.findOne(intId);
			this.fareService.delete(fare);
			result = new ModelAndView("redirect:/fare/carrier/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Fare fare) {
		ModelAndView result;

		result = this.createEditModelAndView(fare, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Fare fare, final String message) {
		ModelAndView result;

		if (fare.getId() == 0) {
			result = new ModelAndView("fare/create");
		} else {
			result = new ModelAndView("fare/edit");
		}

		result.addObject("fare", fare);
		result.addObject("message", message);

		return result;
	}
}
