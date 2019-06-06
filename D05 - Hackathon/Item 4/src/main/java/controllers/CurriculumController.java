
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
import services.CarrierService;
import services.CurriculumService;
import domain.Carrier;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CarrierService		carrierService;


	// Constructor
	public CurriculumController() {
		super();
	}

	// CARRIER LIST
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView listCarrier() {
		ModelAndView result;

		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);

			result = new ModelAndView("curriculum/list");
			result.addObject("curricula", carrier.getCurricula());
			result.addObject("requestURI", "curriculum/carrier/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//LIST BY CARRIER ID
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listByCarrierId(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Carrier carrier = this.carrierService.findOne(intId);

			result = new ModelAndView("curriculum/list");
			result.addObject("curricula", carrier.getCurricula());
			result.addObject("requestURI", "curriculum/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	// DISPLAY
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String id) {
		final ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Curriculum curriculum = this.curriculumService.findOne(intId);
			boolean owner = false;

			if (this.actorService.findActorType().equals("Carrier")) {
				final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				final Carrier carrier = this.carrierService.findOne(carrierId);
				owner = carrier.getCurricula().contains(curriculum);
			}

			result = new ModelAndView("curriculum/display");
			result.addObject("curriculum", curriculum);
			result.addObject("owner", owner);

		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// CREATE
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum curriculum;
		curriculum = this.curriculumService.create();
		result = this.createEditModelAndView(curriculum);
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
			final Curriculum curriculum = this.curriculumService.findOne(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getCurricula().contains(curriculum));
			result = this.createEditModelAndView(curriculum);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// SAVE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		Curriculum curr;

		try {
			curr = this.curriculumService.reconstruct(curriculum, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(curriculum);
		} else {
			try {
				final Curriculum saved = this.curriculumService.save(curr);
				result = new ModelAndView("redirect:/curriculum/display.do?id=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curr.commit.error");
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
			final Curriculum curriculum = this.curriculumService.findOne(intId);
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/curriculum/carrier/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String message) {
		ModelAndView result;

		if (curriculum.getId() == 0) {
			result = new ModelAndView("curriculum/create");
		} else {
			result = new ModelAndView("curriculum/edit");
		}

		result.addObject("curriculum", curriculum);
		result.addObject("message", message);

		return result;
	}
}
