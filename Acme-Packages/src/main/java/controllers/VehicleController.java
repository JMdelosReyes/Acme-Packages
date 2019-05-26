
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AuditorService;
import services.CarrierService;
import services.VehicleService;
import domain.Carrier;
import domain.Vehicle;

@Controller
@RequestMapping("/vehicle")
public class VehicleController extends AbstractController {

	@Autowired
	private VehicleService	vehicleService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarrierService	carrierService;

	@Autowired
	private AuditorService	auditorService;


	public VehicleController() {
		super();
	}

	// List
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Carrier carrier = this.carrierService.findOne(carrierId);
			Collection<Vehicle> vehicles = carrier.getVehicles();

			result = new ModelAndView("vehicle/list");
			result.addObject("vehicles", vehicles);
			result.addObject("requestURI", "vehicle/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Display
	@RequestMapping(value = "/carrier,auditor/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String id) {
		final ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			result = new ModelAndView("vehicle/display");

			Vehicle vehicle = this.vehicleService.findOne(intId);
			final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			boolean carrierView = false;
			boolean canBeEditedOrDeleted = this.vehicleService.canBeEditedOrDeleted(vehicle.getId());

			if (this.actorService.findActorType().equals("Carrier")) {
				Carrier carrier = this.carrierService.findOne(actorId);
				Assert.isTrue(carrier.getVehicles().contains(vehicle));
				carrierView = true;
			} else {
				Assert.isTrue(this.vehicleService.findVehiclesAuditedByAuditor(actorId).contains(vehicle));
			}

			final Locale locale = LocaleContextHolder.getLocale();
			boolean es = true;
			if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
				es = false;
			}

			result.addObject("vehicle", vehicle);
			result.addObject("solicitations", vehicle.getSolicitations());
			result.addObject("es", es);
			result.addObject("requestURI", "vehicle/display.do");
			result.addObject("carrierView", carrierView);
			result.addObject("canBeEditedOrDeleted", canBeEditedOrDeleted);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Create
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Vehicle vehicle;
		vehicle = this.vehicleService.create();
		result = this.createEditModelAndView(vehicle);
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Vehicle vehicle) {
		ModelAndView result;

		result = this.createEditModelAndView(vehicle, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Vehicle vehicle, final String message) {
		ModelAndView result;

		if (vehicle.getId() == 0) {
			result = new ModelAndView("vehicle/create");
		} else {
			result = new ModelAndView("vehicle/edit");
		}

		result.addObject("vehicle", vehicle);
		result.addObject("message", message);

		return result;
	}
}
