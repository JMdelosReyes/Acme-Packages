
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String id, @RequestParam(required = false, defaultValue = "no") final String sol) {
		final ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (sol.equals("yes")) {
			try {
				intId = this.vehicleService.findBySolicitation(intId).getId();
			} catch (final Throwable oops) {
				return new ModelAndView("redirect:/");
			}
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

	// Edit
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
			final Vehicle vehicle = this.vehicleService.findOne(intId);
			Assert.isTrue(this.vehicleService.canBeEditedOrDeleted(vehicle.getId()));
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getVehicles().contains(vehicle));
			result = this.createEditModelAndView(vehicle);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Vehicle vehicle, final BindingResult binding) {
		ModelAndView result;
		Vehicle veh;

		try {
			veh = this.vehicleService.reconstruct(vehicle, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(vehicle);
		} else {
			try {
				final Vehicle saved = this.vehicleService.save(veh);
				result = new ModelAndView("redirect:/vehicle/carrier,auditor/display.do?id=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(vehicle, "veh.commit.error");
			}
		}
		return result;
	}

	// Delete
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
			final Vehicle vehicle = this.vehicleService.findOne(intId);
			this.vehicleService.delete(vehicle);
			result = new ModelAndView("redirect:/vehicle/carrier/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
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

		Collection<String> types = this.vehicleService.findVehicleTypes();

		result.addObject("vehicle", vehicle);
		result.addObject("types", types);
		result.addObject("message", message);

		return result;
	}
}
