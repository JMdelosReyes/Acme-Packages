
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
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
import services.SolicitationService;
import services.VehicleService;
import domain.Auditor;
import domain.Carrier;
import domain.Category;
import domain.Solicitation;
import domain.Vehicle;

@Controller
@RequestMapping("/solicitation")
public class SolicitationController extends AbstractController {

	@Autowired
	private SolicitationService	solicitationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private CarrierService		carrierService;


	public SolicitationController() {
		super();
	}

	// List
	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Solicitation> solicitations;
			solicitations = this.solicitationService.findUnassigned();

			final Locale locale = LocaleContextHolder.getLocale();
			boolean es = true;
			if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
				es = false;
			}

			result = new ModelAndView("solicitation/list");
			result.addObject("solicitations", solicitations);
			result.addObject("requestURI", "solicitation/auditor/list.do");
			result.addObject("es", es);
			result.addObject("assignedView", false);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/auditor/list-assigned", method = RequestMethod.GET)
	public ModelAndView listAssigned() {
		ModelAndView result;
		try {
			final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Auditor auditor = this.auditorService.findOne(actorId);

			Collection<Solicitation> solicitations;
			solicitations = auditor.getSolicitations();

			final Locale locale = LocaleContextHolder.getLocale();
			boolean es = true;
			if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
				es = false;
			}

			result = new ModelAndView("solicitation/list");
			result.addObject("solicitations", solicitations);
			result.addObject("requestURI", "solicitation/auditor/list-assigned.do");
			result.addObject("es", es);
			result.addObject("assignedView", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Assign
	@RequestMapping(value = "/auditor/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam(required = false, defaultValue = "0") final String id) {
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Solicitation solicitation = this.solicitationService.findOne(intId);
			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			this.solicitationService.assign(solicitation, actorId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/solicitation/auditor/list-assigned.do");
	}

	// Create
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final String vehId) {
		ModelAndView result;
		int intId;
		Solicitation solicitation;

		try {
			intId = Integer.valueOf(vehId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Assert.isTrue(this.vehicleService.findApplicableCategories(intId).size() > 0);

			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Carrier carrier = this.carrierService.findOne(actorId);
			Vehicle vehicle = this.vehicleService.findOne(intId);
			Assert.isTrue(carrier.getVehicles().contains(vehicle));
			solicitation = this.solicitationService.create();
			result = this.createEditModelAndView(solicitation, vehicle.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Edit
	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		Integer intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Solicitation solicitation = this.solicitationService.findOne(intId);
			Vehicle vehicle = this.vehicleService.findBySolicitation(solicitation.getId());

			final int auditorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Auditor auditor = this.auditorService.findOne(auditorId);
			Assert.isTrue(auditor.getSolicitations().contains(solicitation));
			Assert.isTrue(solicitation.getStatus().equals("PENDING"));

			Solicitation clon = (Solicitation) solicitation.clone();
			clon.setStartDate(DateUtils.truncate(DateTime.now().toDate(), java.util.Calendar.DAY_OF_MONTH));
			clon.setEndDate(DateUtils.truncate(DateTime.now().plusYears(1).toDate(), java.util.Calendar.DAY_OF_MONTH));

			result = this.createEditModelAndView(clon, vehicle.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Solicitation solicitation, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String vehId) {
		ModelAndView result;
		int intId;
		Solicitation sol;

		try {
			intId = Integer.valueOf(vehId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Carrier carrier = this.carrierService.findOne(actorId);
			Vehicle vehicle = this.vehicleService.findOne(intId);
			Assert.isTrue(carrier.getVehicles().contains(vehicle));
			sol = this.solicitationService.reconstruct(solicitation, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(solicitation, intId);
		} else {
			try {
				Assert.isTrue(this.vehicleService.findApplicableCategories(intId).contains(solicitation.getCategory()));
			} catch (final Throwable oops) {
				return new ModelAndView("redirect:/");
			}
			try {
				this.solicitationService.save(sol, intId);
				result = new ModelAndView("redirect:/vehicle/carrier,auditor/display.do?id=" + intId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(solicitation, intId, "veh.commit.error");
			}
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(final Solicitation solicitation, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String vehId) {
		ModelAndView result;
		int intId;
		Solicitation sol;

		try {
			intId = Integer.valueOf(vehId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		Vehicle vehicle;

		try {
			vehicle = this.vehicleService.findOne(intId);
			sol = this.solicitationService.reconstruct(solicitation, binding);
			Assert.isTrue(vehicle.getSolicitations().contains(sol));
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(solicitation, intId);
		} else {
			try {
				this.solicitationService.save(sol, intId);
				result = new ModelAndView("redirect:/solicitation/auditor/list-assigned.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(solicitation, intId, "sol.commit.error");
			}
		}
		return result;
	}

	// Delete
	@RequestMapping(value = "/carrier/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Solicitation solicitation = this.solicitationService.findOne(intId);
			Vehicle vehicle = this.vehicleService.findBySolicitation(intId);

			this.solicitationService.delete(solicitation);
			result = new ModelAndView("redirect:/vehicle/carrier,auditor/display.do?id=" + vehicle.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Solicitation solicitation, int vehId) {
		ModelAndView result;

		result = this.createEditModelAndView(solicitation, vehId, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Solicitation solicitation, int vehId, final String message) {
		ModelAndView result;

		if (solicitation.getId() == 0) {
			result = new ModelAndView("solicitation/create");
		} else {
			result = new ModelAndView("solicitation/edit");
		}

		Collection<Category> categories = this.vehicleService.findApplicableCategories(vehId);

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		Collection<String> decision = this.solicitationService.findStatus();

		result.addObject("solicitation", solicitation);
		result.addObject("categories", categories);
		result.addObject("decision", decision);
		result.addObject("message", message);
		result.addObject("es", es);
		result.addObject("vehId", vehId);

		return result;
	}
}
