
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
import services.AuditorService;
import services.CarrierService;
import services.CommentService;
import services.CustomerService;
import services.IssueService;
import services.RequestService;
import domain.Auditor;
import domain.Comment;
import domain.Customer;
import domain.Issue;
import domain.Request;

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

	@Autowired
	private RequestService	requestService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private CommentService	commentService;


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

			result = new ModelAndView("issue/list-assigned");
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
	@RequestMapping(value = "/carrier,customer,auditor/display", method = RequestMethod.GET)
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
			boolean involved = true;

			if (actorType.equals("Carrier")) {
				issues = this.issueService.findIssuesOfCarrier(actorId);
				Assert.isTrue(issues.contains(issue));
			} else if (actorType.equals("Customer")) {
				issues = this.issueService.findIssuesOfCustomer(actorId);
				Assert.isTrue(issues.contains(issue));
			} else {
				Auditor auditor = this.auditorService.findOne(actorId);
				issues = auditor.getIssues();
				if (!issues.contains(issue)) {
					involved = false;
				}
			}

			boolean assigned = true;
			if (this.issueService.findUnassigned().contains(issue)) {
				assigned = false;
			}

			result = new ModelAndView("issue/display");
			result.addObject("comment", this.commentService.create());
			result.addObject("assigned", assigned);
			result.addObject("involved", involved);
			result.addObject("issue", issue);
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

	// Create
	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final String vehId) {
		ModelAndView result;
		int intId;
		Issue issue;

		try {
			intId = Integer.valueOf(vehId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Request request = this.requestService.findOne(intId);
			int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Customer customer = this.customerService.findOne(actorId);
			Assert.isTrue(customer.getRequests().contains(request));
			Assert.isTrue(request.getOffer() != null);
			Assert.isTrue(request.getIssue() == null);

			issue = this.issueService.create();
			result = this.createEditModelAndView(issue, intId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Save
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Issue issue, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String reqId) {
		ModelAndView result;
		int intId;
		Issue iss;

		try {
			intId = Integer.valueOf(reqId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		Request request;

		try {
			request = this.requestService.findOne(intId);
			iss = this.issueService.reconstruct(issue, request, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(issue, intId);
		} else {
			try {
				Issue saved = this.issueService.save(iss, intId);
				result = new ModelAndView("redirect:/issue/carrier,customer,auditor/display.do?id=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(issue, intId, "iss.commit.error");
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
			final Issue issue = this.issueService.findOne(intId);
			this.issueService.delete(issue);
			result = new ModelAndView("redirect:/issue/customer/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Close
	@RequestMapping(value = "/auditor/close", method = RequestMethod.POST, params = "close")
	public ModelAndView close(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Issue issue = this.issueService.findOne(intId);
			this.issueService.close(issue);
			result = new ModelAndView("redirect:/issue/carrier,customer,auditor/display.do?id=" + issue.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Comment
	@RequestMapping(value = "/carrier,customer,auditor/add-comment", method = RequestMethod.POST, params = "save")
	public ModelAndView comment(final Comment comment, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String issId) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(issId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			String actorType = this.actorService.findActorType();
			final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

			Issue issue = this.issueService.findOne(intId);
			Assert.isTrue(!issue.isClosed());
			Assert.isTrue(!this.issueService.findUnassigned().contains(issue));

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

			this.commentService.save(comment, issue.getId());
			result = new ModelAndView("redirect:/issue/carrier,customer,auditor/display.do?id=" + issue.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/issue/carrier,customer,auditor/display.do?id=" + intId);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Issue issue, int reqId) {
		ModelAndView result;

		result = this.createEditModelAndView(issue, reqId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Issue issue, int reqId, final String message) {
		ModelAndView result;

		if (issue.getId() == 0) {
			result = new ModelAndView("issue/create");
		} else {
			result = new ModelAndView("issue/edit");
		}

		result.addObject("issue", issue);
		result.addObject("message", message);
		result.addObject("reqId", reqId);

		return result;
	}
}
