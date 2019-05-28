
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
import services.IssueService;
import domain.Auditor;
import domain.Issue;

@Controller
@RequestMapping("/issue")
public class IssueController extends AbstractController {

	@Autowired
	private IssueService	issueService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarrierService	carrierService;

	@Autowired
	private AuditorService	auditorService;


	public IssueController() {
		super();
	}

	// List carrier
	@RequestMapping(value = "/carrier/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Collection<Issue> issues = this.issueService.findIssuesOfCarrier(carrierId);

			result = new ModelAndView("issue/list");
			result.addObject("issues", issues);
			result.addObject("requestURI", "issue/carrier/list.do");
			result.addObject("assignable", false);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List customer
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView listCustomer() {
		ModelAndView result;

		try {
			final int customerId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Collection<Issue> issues = this.issueService.findIssuesOfCustomer(customerId);

			result = new ModelAndView("issue/list");
			result.addObject("issues", issues);
			result.addObject("requestURI", "issue/customer/list.do");
			result.addObject("assignable", false);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List assigned auditor
	@RequestMapping(value = "/auditor/list-assigned", method = RequestMethod.GET)
	public ModelAndView listAuditor() {
		ModelAndView result;
		try {
			final int auditorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Collection<Issue> issues = this.auditorService.findOne(auditorId).getIssues();

			result = new ModelAndView("issue/list");
			result.addObject("issues", issues);
			result.addObject("requestURI", "issue/auditor/list-assigned.do");
			result.addObject("assignable", false);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// List unassigned auditor
	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView listAuditorUnassigned() {
		ModelAndView result;
		try {
			Collection<Issue> issues = this.issueService.findUnassigned();

			result = new ModelAndView("issue/list");
			result.addObject("issues", issues);
			result.addObject("requestURI", "issue/auditor/list.do");
			result.addObject("assignable", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Display
	@RequestMapping(value = "/carrier,customer,auditor/list", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			String actorType = this.actorService.findActorType();
			final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

			Issue issue = this.issueService.findOne(intId);
			Collection<Issue> issues;

			if (actorType.equals("Carrier")) {
				issues = this.issueService.findIssuesOfCarrier(actorId);
			} else if (actorType.equals("Customer")) {
				issues = this.issueService.findIssuesOfCustomer(actorId);
			} else {
				Auditor auditor = this.auditorService.findOne(actorId);
				issues = auditor.getIssues();
			}
			Assert.isTrue(issues.contains(issue));

			result = new ModelAndView("issue/display");
			result.addObject("issues", issues);
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
			Issue issue = this.issueService.findOne(intId);
			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			this.issueService.assign(issue, actorId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/issue/auditor/list-assigned.do");
	}
}
