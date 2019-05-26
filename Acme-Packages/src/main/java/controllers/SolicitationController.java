
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AuditorService;
import services.SolicitationService;
import domain.Auditor;
import domain.Solicitation;

@Controller
@RequestMapping("/solicitation")
public class SolicitationController extends AbstractController {

	@Autowired
	private SolicitationService	solicitationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AuditorService		auditorService;


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
			result.addObject("requestURI", "solicitation/list.do");
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
			result.addObject("requestURI", "solicitation/list.do");
			result.addObject("es", es);
			result.addObject("assignedView", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Assign
	@RequestMapping(value = "/auditor/assign", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String id) {
		final ModelAndView result;
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
}
