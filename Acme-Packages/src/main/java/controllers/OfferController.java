
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
import services.CarrierService;
import services.EvaluationService;
import services.FareService;
import services.OfferService;
import services.VehicleService;
import domain.Carrier;
import domain.Offer;
import forms.OfferForm;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	@Autowired
	private OfferService		offerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CarrierService		carrierService;

	@Autowired
	private FareService			fareService;

	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private EvaluationService	evaluationService;


	// Constructor
	public OfferController() {
		super();
	}

	// CARRIER LIST
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView listCarrier() {
		ModelAndView result;

		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Collection<Offer> offers = this.offerService.findCarrierOffers(carrierId);

			result = new ModelAndView("offer/list");
			result.addObject("offers", offers);
			result.addObject("showFilter", false);
			result.addObject("requestURI", "offer/carrier/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//GENERAL LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "") final String filter, @RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Collection<Offer> offers;
			result = new ModelAndView("offer/list");

			if (intId != 0) {
				result.addObject("showFilter", false);
				offers = this.offerService.findCarrierOpenOffers(intId);
			} else {
				result.addObject("showFilter", true);
				offers = this.offerService.findOpenOffers(filter);
			}

			result.addObject("offers", offers);
			result.addObject("requestURI", "offer/list.do");

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
			final Offer offer = this.offerService.findOne(intId);
			boolean owner = false;
			boolean canEvaluate = false;

			String type = this.actorService.findActorType();
			final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

			if (type.equals("Carrier")) {
				final Carrier carrier = this.carrierService.findOne(actorId);
				owner = carrier.getOffers().contains(offer);
			} else if (type.equals("Customer")) {
				if (this.evaluationService.findEvaluableOffersByCustomer(actorId).contains(offer)) {
					canEvaluate = true;
				}
			}

			result = new ModelAndView("offer/display");
			result.addObject("offer", offer);
			result.addObject("traverseTowns", offer.getTraverseTowns());
			result.addObject("owner", owner);
			result.addObject("canEvaluate", canEvaluate);
			result.addObject("requestURI", "offer/display.do");

		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}
	// CREATE
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		OfferForm of = new OfferForm();
		of.setId(0);
		result = this.createEditModelAndView(of);
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
			OfferForm of = this.offerService.getOfferForm(intId);
			Assert.isTrue(!(of.isCanceled() && of.isFinalMode()));
			result = this.createEditModelAndView(of);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}
	// SAVE
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final OfferForm of, final BindingResult binding) {
		ModelAndView result;
		Offer o;

		try {
			o = this.offerService.reconstruct(of, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(of);
		} else {
			try {
				final Offer saved = this.offerService.save(o);
				result = new ModelAndView("redirect:/offer/display.do?id=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(of, "curr.commit.error");
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
			final Offer offer = this.offerService.findOne(intId);
			Assert.isTrue(!offer.isFinalMode());

			this.offerService.delete(offer);
			result = new ModelAndView("redirect:/offer/carrier/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final OfferForm of) {
		ModelAndView result;

		result = this.createEditModelAndView(of, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final OfferForm of, final String message) {
		ModelAndView result;

		if (of.getId() == 0) {
			result = new ModelAndView("offer/create");
		} else {
			result = new ModelAndView("offer/edit");
		}

		final int carrierId;
		try {
			carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		result.addObject("offerForm", of);
		result.addObject("message", message);
		result.addObject("fares", this.fareService.findByCarrier(carrierId));
		result.addObject("vehicles", this.vehicleService.findCarrierVehicles(carrierId));

		return result;
	}
}
