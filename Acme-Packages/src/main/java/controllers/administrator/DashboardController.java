
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Carrier;
import domain.Sponsorship;
import domain.Town;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public DashboardController() {

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		if (this.actorService.findActorType() != "Administrator") {
			result = new ModelAndView("redirect:/");
		} else {

			result = new ModelAndView("dashboard/administrator");

			//The average, the minimum, the maximum, and the standard deviation of times that a sponsorship has been shown.

			final Double avgSPShow = this.adminService.avgSPShow();
			final Double minSPShow = this.adminService.minSPShow();
			final Double maxSPShow = this.adminService.maxSPShow();
			final Double stddevSPShow = this.adminService.stddevSPShow();

			result.addObject("avgSPShow", avgSPShow);
			result.addObject("minSPShow", minSPShow);
			result.addObject("maxSPShow", maxSPShow);
			result.addObject("stddevSPShow", stddevSPShow);
			//The average, the minimum, the maximum, and the standard deviation of scores from registered carriers.
			final Double avgScoreCarriers = this.adminService.avgScoreCarriers();
			final Double minScoreCarriers = this.adminService.minScoreCarriers();
			final Double maxScoreCarriers = this.adminService.maxScoreCarriers();
			final Double stddevScoreCarriers = this.adminService.stddevScoreCarriers();

			result.addObject("avgScoreCarriers", avgScoreCarriers);
			result.addObject("minScoreCarriers", minScoreCarriers);
			result.addObject("maxScoreCarriers", maxScoreCarriers);
			result.addObject("stddevScoreCarriers", stddevScoreCarriers);
			//The average, the minimum, the maximum, and the standard deviation of evaluations made by customers.
			final Double avgEvaluationByCustomer = this.adminService.avgEvaluationByCustomer();
			final Double minEvaluationByCustomer = this.adminService.minEvaluationByCustomer();
			final Double maxEvaluationByCustomer = this.adminService.maxEvaluationByCustomer();
			final Double stddevEvaluationByCustomer = this.adminService.stddevEvaluationByCustomer();

			result.addObject("avgEvaluationByCustomer", avgEvaluationByCustomer);
			result.addObject("minEvaluationByCustomer", minEvaluationByCustomer);
			result.addObject("maxEvaluationByCustomer", maxEvaluationByCustomer);
			result.addObject("stddevEvaluationByCustomer", stddevEvaluationByCustomer);
			//The average, the minimum, the maximum, and the standard deviation of comments per issues.
			final Double avgCommentsPerIssue = this.adminService.avgCommentsPerIssue();
			final Double minCommentsPerIssue = this.adminService.minCommentsPerIssue();
			final Double maxCommentsPerIssue = this.adminService.maxCommentsPerIssue();
			final Double stddevCommentsPerIssue = this.adminService.stddevCommentsPerIssue();

			result.addObject("avgCommentsPerIssue", avgCommentsPerIssue);
			result.addObject("minCommentsPerIssue", minCommentsPerIssue);
			result.addObject("maxCommentsPerIssue", maxCommentsPerIssue);
			result.addObject("stddevCommentsPerIssue", stddevCommentsPerIssue);

			//Top-3 most shown sponsorships.	
			final Collection<Sponsorship> top3ShownSponsorships = this.adminService.top3ShownSponsorships();

			result.addObject("top3ShownSponsorships", top3ShownSponsorships);
			//Top-3 carriers with the highest score.
			final Collection<Carrier> top3CarriersWithHigherScore = this.adminService.top3CarriersWithHigherScore();

			result.addObject("top3CarriersWithHigherScore", top3CarriersWithHigherScore);

			//TODO Top-5 most visited towns.
			final Collection<Town> top5MostVisitedTowns = this.adminService.top5MostVisitedTowns();

			result.addObject("top5MostVisitedTowns", top5MostVisitedTowns);
			//The ratio of empty versus non-empty finders.
			final Double RatioNonEmptyFinders = this.adminService.RatioNonEmptyFinders();
			final Double RatioEmptyFinders = this.adminService.RatioEmptyFinders();
			result.addObject("RatioNonEmptyFinders", RatioNonEmptyFinders);
			result.addObject("RatioEmptyFinders", RatioEmptyFinders);

			//The ratio of closed versus non-closed issues.
			final Double ratioNonClosedIssue = this.adminService.ratioNonClosedIssue();
			final Double ratioClosedIssue = this.adminService.ratioClosedIssue();
			result.addObject("ratioNonClosedIssue", ratioNonClosedIssue);
			result.addObject("ratioClosedIssue", ratioClosedIssue);

			//The listing of auditors who have got at least 10% of issues closed above the average. 
			final Collection<Auditor> AuditorsIwth10ClosesIssuesAboveAVG = this.adminService.AuditorsIwth10ClosesIssuesAboveAVG();

			result.addObject("AuditorsIwth10ClosesIssuesAboveAVG", AuditorsIwth10ClosesIssuesAboveAVG);

		}
		return result;

	}
	//	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "spammers")
	//	public ModelAndView setSpammers() {
	//		ModelAndView result;
	//		UserAccount principal;
	//		try {
	//			principal = LoginService.getPrincipal();
	//		} catch (final Exception e) {
	//			return new ModelAndView("redirect:/");
	//		}
	//		if (this.actorService.findActorType() != "Administrator") {
	//			result = new ModelAndView("redirect:/");
	//		} else {
	//			try {
	//				this.adminService.setSpammers();
	//				result = new ModelAndView("redirect:list.do");
	//			} catch (final Exception e) {
	//				result = new ModelAndView("redirect:/");
	//			}
	//		}
	//		return result;
	//
	//	}

	//	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "mensajee")
	//	public ModelAndView messRebranding() {
	//		ModelAndView result;
	//		UserAccount principal;
	//		try {
	//			principal = LoginService.getPrincipal();
	//		} catch (final Exception e) {
	//			return new ModelAndView("redirect:/");
	//		}
	//		if (this.actorService.findActorType() != "Administrator") {
	//			result = new ModelAndView("redirect:/");
	//		} else {
	//			try {
	//				this.adminService.messRebranding();
	//				result = new ModelAndView("redirect:list.do");
	//			} catch (final Exception e) {
	//				result = new ModelAndView("redirect:/");
	//			}
	//		}
	//		return result;
	//
	//	}

}
