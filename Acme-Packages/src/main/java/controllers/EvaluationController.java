
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
import services.EvaluationService;
import services.OfferService;
import domain.Evaluation;
import domain.Offer;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController extends AbstractController {

	@Autowired
	private EvaluationService	evaluationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private OfferService		offerService;


	public EvaluationController() {
		super();
	}

	// List
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		try {
			final int customerId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Collection<Evaluation> evaluations = this.evaluationService.findEvaluationsByCustomer(customerId);

			result = new ModelAndView("evaluation/list");
			result.addObject("evaluations", evaluations);
			result.addObject("requestURI", "evaluation/customer/list.do");
			result.addObject("owner", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView listCarrier() {
		ModelAndView result;

		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Collection<Evaluation> evaluations = this.evaluationService.findEvaluationsByCarrier(carrierId);

			result = new ModelAndView("evaluation/list");
			result.addObject("evaluations", evaluations);
			result.addObject("requestURI", "evaluation/carrier/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listByOffer(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Offer offer = this.offerService.findOne(intId);
			Collection<Evaluation> evaluations = offer.getEvaluations();

			result = new ModelAndView("evaluation/list");
			result.addObject("evaluations", evaluations);
			result.addObject("requestURI", "evaluation/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Create
	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final String offId) {
		ModelAndView result;
		int intId;
		Evaluation evaluation;

		try {
			intId = Integer.valueOf(offId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Offer offer = this.offerService.findOne(intId);
			Collection<Offer> offers = this.evaluationService.findEvaluableOffersByCustomer(intId);
			Assert.isTrue(offers.contains(offer));

			evaluation = this.evaluationService.create();
			result = this.createEditModelAndView(evaluation, intId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Save
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Evaluation evaluation, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String offId) {
		ModelAndView result;
		int intId;
		Evaluation eva;

		try {
			intId = Integer.valueOf(offId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Offer offer = this.offerService.findOne(intId);
			eva = this.evaluationService.reconstruct(evaluation, offer, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(evaluation, intId);
		} else {
			try {
				this.evaluationService.save(eva);
				result = new ModelAndView("redirect:/offer/display.do?id=" + intId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(evaluation, intId, "eva.commit.error");
			}
		}
		return result;
	}

	// Delete
	@RequestMapping(value = "/customer/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Evaluation evaluation = this.evaluationService.findOne(intId);
			this.evaluationService.delete(evaluation);
			result = new ModelAndView("redirect:/evaluation/customer/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Evaluation evaluation, int offId) {
		ModelAndView result;

		result = this.createEditModelAndView(evaluation, offId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Evaluation evaluation, int offId, final String message) {
		ModelAndView result;

		if (evaluation.getId() == 0) {
			result = new ModelAndView("evaluation/create");
		} else {
			result = new ModelAndView("evaluation/edit");
		}

		result.addObject("evaluation", evaluation);
		result.addObject("message", message);
		result.addObject("offId", offId);

		return result;
	}
}
