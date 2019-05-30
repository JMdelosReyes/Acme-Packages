
package controllers;

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
import services.TownService;
import services.TraverseTownService;
import domain.TraverseTown;

@Controller
@RequestMapping("/traverseTown")
public class TraverseTownController extends AbstractController {

	@Autowired
	private TraverseTownService	traverseTownService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TownService			townService;


	// Constructor
	public TraverseTownController() {
		super();
	}

	// CREATE
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) String offerId) {
		ModelAndView result;
		TraverseTown tw;
		tw = this.traverseTownService.create();
		result = this.createEditModelAndView(tw, offerId);
		return result;
	}

	// EDIT
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String id, @RequestParam(required = true) String offerId) {
		ModelAndView result;
		Integer intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final TraverseTown tw = this.traverseTownService.findOne(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Assert.isTrue(this.traverseTownService.findCarrierTraverseTowns(carrierId).contains(tw));

			result = this.createEditModelAndView(tw, offerId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// SAVE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final TraverseTown tt, @RequestParam(required = true) String offerId, final BindingResult binding) {
		ModelAndView result;

		try {
			Integer intId = Integer.valueOf(offerId);

			this.traverseTownService.reconstruct(tt, intId, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(tt, offerId);
			} else {
				result = new ModelAndView("redirect:/offer/display.do?id=" + offerId);
			}
		} catch (final Throwable oops) {

			final String mess = "traverseTown.commit.error";

			result = this.createEditModelAndView(tt, offerId, mess);
		}
		return result;

	}

	// DELETE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id, @RequestParam(required = true) String offerId) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final TraverseTown traverseTown = this.traverseTownService.findOne(intId);
			this.traverseTownService.delete(traverseTown);
			result = new ModelAndView("redirect:/offer/display.do?id=" + offerId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final TraverseTown tt, final String offerId) {
		ModelAndView result;

		result = this.createEditModelAndView(tt, offerId, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final TraverseTown tt, final String offerId, final String message) {
		ModelAndView result;

		if (tt.getId() == 0) {
			result = new ModelAndView("traverseTown/create");
		} else {
			result = new ModelAndView("traverseTown/edit");
		}

		result.addObject("traverseTown", tt);
		result.addObject("message", message);
		result.addObject("towns", this.townService.findAll());
		result.addObject("offerId", offerId);

		return result;
	}
}
