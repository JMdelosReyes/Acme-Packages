
package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

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
import services.CategoryService;
import services.CustomerService;
import services.IssueService;
import services.OfferService;
import services.PackageService;
import services.RequestService;
import services.TownService;
import domain.Carrier;
import domain.Category;
import domain.Customer;
import domain.Issue;
import domain.Offer;
import domain.Package;
import domain.Request;
import domain.Town;
import forms.ChooseOfferForm;
import forms.CreateRequestForm;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService	reqService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private CustomerService	cusService;
	@Autowired
	private CarrierService	carService;
	@Autowired
	private AuditorService	audService;
	@Autowired
	private CategoryService	catService;
	@Autowired
	private PackageService	pacService;
	@Autowired
	private TownService		townService;
	@Autowired
	private IssueService	issService;
	@Autowired
	private OfferService	offService;


	//Requests list
	@RequestMapping(value = "/carrier,customer/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;
		List<Request> requests;
		boolean carrierView = false;
		boolean customerView = false;
		final int actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		if (this.actorService.findActorType().equals("Carrier")) {
			Offer offer;
			try {
				intId = Integer.valueOf(id);
				offer = this.offService.findOne(intId);
				Assert.isTrue(this.carService.findOne(actorId).getOffers().contains(offer));
			} catch (final Throwable oops) {
				return new ModelAndView("redirect:/");
			}
			requests = new ArrayList<>(offer.getRequests());
			carrierView = true;
		} else {
			Customer cus = this.cusService.findOne(actorId);
			requests = new ArrayList<>(cus.getRequests());
			customerView = true;
		}
		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("customerView", customerView);
		result.addObject("carrierView", carrierView);
		result.addObject("requestURI", "request/carrier,customer/list.do");
		return result;
	}

	//Display de request
	@RequestMapping(value = "/carrier,customer,auditor/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;
		Request request;
		boolean owner = false;
		int actorId;
		boolean issueId;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			Issue issue = this.issService.findOne(intId);
			issueId = true;
		} catch (Throwable oops) {
			issueId = false;
		}
		if (issueId) {
			if (this.actorService.findActorType().equals("Auditor")) {
				request = this.reqService.findRequestByIssueId(intId);
				Assert.isTrue(request.isFinalMode());
			} else {
				return new ModelAndView("redirect:/");
			}
		} else {
			if (this.actorService.findActorType().equals("Customer")) {
				request = this.reqService.findOne(intId);
				Customer cus = this.cusService.findOne(actorId);
				Assert.isTrue(cus.getRequests().contains(request));
				owner = true;
			} else {
				request = this.reqService.findOne(intId);
				Assert.isTrue(request.isFinalMode());
				Carrier car = this.carService.findOne(actorId);
				Assert.isTrue(this.reqService.findRequestsByCarrierId(car.getId()).contains(request));
			}
			//De momento, los auditor solo entran a partir de ids de issues
		}
		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		result = new ModelAndView("request/display");
		result.addObject("request", request);
		result.addObject("packages", new ArrayList<>(request.getPackages()));
		result.addObject("requestURI", "request/carrier,customer,auditor/display.do");
		result.addObject("owner", owner);
		result.addObject("es", es);

		List<Offer> offersAvailables;
		try {
			Assert.isTrue(owner);
			Assert.isTrue(request.getOffer() == null);
			offersAvailables = new ArrayList<>(this.reqService.findOffersByRequest(request));
			ChooseOfferForm cof = new ChooseOfferForm();
			cof.setId(request.getId());
			result.addObject("chooseOfferForm", cof);
		} catch (Throwable oops) {
			offersAvailables = new ArrayList<>();
		}
		result.addObject("offers", offersAvailables);

		return result;
	}
	@RequestMapping(value = "/carrier,customer,auditor/display", method = RequestMethod.POST, params = "apply")
	public ModelAndView apply(ChooseOfferForm cof, BindingResult binding) {
		ModelAndView result;
		int requestId;
		int offerId;
		int actorId;
		Offer off;
		Request req;

		try {
			Assert.notNull(cof);
			Assert.isTrue((cof.getId() != 0) || (cof.getIdOffer() != 0));
			Assert.isTrue(this.actorService.findActorType().equals("Customer"));
			requestId = Integer.valueOf(cof.getId());
			req = this.reqService.findOne(requestId);
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(req));
			offerId = Integer.valueOf(cof.getIdOffer());
			off = this.offService.findOne(offerId);

		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			this.reqService.applyOffer(req, off);
			result = new ModelAndView("redirect:/request/carrier,customer/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	//ACCEPT OR REJECT REQUEST
	@RequestMapping(value = "/carrier,customer,auditor/display", method = RequestMethod.POST, params = "save")
	public ModelAndView acceptRejectSave(String id, String status) {
		ModelAndView result;
		int intId;
		int actorId;
		Offer off;
		Request req;

		try {
			Assert.notNull(status);
			Assert.isTrue(status.equals(Request.ACCEPTED) || status.equals(Request.REJECTED));
			Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
			intId = Integer.valueOf(id);
			req = this.reqService.findOne(intId);
			Assert.isTrue(req.getStatus().equals(Request.SUBMITTED));
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			Carrier car = this.carService.findOne(actorId);
			Assert.isTrue(car.getOffers().contains(req.getOffer()));
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			this.reqService.changeStatus(intId, status);
			result = new ModelAndView("redirect:/request/carrier,customer/list.do?id=" + req.getOffer().getId());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
	//Create a request
	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateRequestForm crf = new CreateRequestForm();
		result = this.createEditModelAndView(crf);

		return result;
	}

	//TODO: Save a new Request
	@RequestMapping(value = "/customer/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNewRequest(@Valid CreateRequestForm crf, BindingResult binding) {
		ModelAndView result;
		Package pac = this.pacService.setearCampos(crf);
		Request req = this.reqService.setearCampos(crf);
		try {

			pac = this.pacService.reconstruct(pac, binding);
			if (binding.hasErrors()) {
				req = this.reqService.reconstruct(req, binding);
			} else {
				pac = this.pacService.save(pac);
				req.getPackages().add(pac);
				req = this.reqService.reconstruct(req, binding);
			}
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(crf);
		} else {
			try {
				req = this.reqService.save(req);
				result = new ModelAndView("redirect:/request/carrier,customer,auditor/display.do?id=" + req.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(crf, "req.commit.error");
			}
		}
		return result;
	}
	//Edit a request
	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") String id) {
		ModelAndView result;
		int intId;
		int actorId;
		Request request;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(id);
			request = this.reqService.findOne(intId);
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(request));
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		result = this.createEditModelAndView(request);

		return result;
	}
	//Edit a request
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(Request req, BindingResult binding) {
		ModelAndView result;
		int intId;
		int actorId;
		Request request;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(req.getId());
			request = this.reqService.findOne(intId);
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(request));
			request = this.reqService.reconstruct(req, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(req);
		} else {
			try {
				request = this.reqService.save(request);
				result = new ModelAndView("redirect:/request/carrier,customer,auditor/display.do?id=" + request.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(req, "req.commit.error");
			}
		}

		return result;
	}
	//Delete a request
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Request req, BindingResult binding) {
		ModelAndView result;
		int intId;
		int actorId;
		Request request;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(req.getId());
			request = this.reqService.findOne(intId);
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(request));
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			this.reqService.delete(req);
			result = new ModelAndView("redirect:/request/carrier,customer/list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(req, "req.commit.error");
		}
		return result;
	}

	//APPLY FOR AN OFFER

	//CREATE A NEW REQUEST
	protected ModelAndView createEditModelAndView(CreateRequestForm crf) {
		ModelAndView result;

		result = this.createEditModelAndView(crf, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(CreateRequestForm crf, String message) {
		ModelAndView result;

		result = new ModelAndView("request/create");

		List<Town> towns = new ArrayList<>(this.townService.findAll());
		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		result.addObject("createRequestForm", crf);
		result.addObject("towns", towns);
		result.addObject("categories", categories);
		result.addObject("es", es);
		result.addObject("message", message);

		return result;
	}

	//EDIT A REQUEST
	protected ModelAndView createEditModelAndView(Request req) {
		ModelAndView result;

		result = this.createEditModelAndView(req, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Request req, String message) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		List<Town> towns = new ArrayList<>(this.townService.findAll());
		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		if (!req.isFinalMode()) {
			//List<Offer> offers = this.findOffersByRequestId();
		}
		result.addObject("towns", towns);
		result.addObject("es", es);
		result.addObject("categories", categories);
		result.addObject("request", req);
		result.addObject("message", message);
		return result;
	}
}
