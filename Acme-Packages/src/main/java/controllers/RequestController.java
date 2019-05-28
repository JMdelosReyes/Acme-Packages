
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CategoryService;
import services.CustomerService;
import services.PackageService;
import services.RequestService;
import services.TownService;
import domain.Category;
import domain.Customer;
import domain.Package;
import domain.Request;
import domain.Town;
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
	private CategoryService	catService;
	@Autowired
	private PackageService	pacService;
	@Autowired
	private TownService		townService;


	// My requests list
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			UserAccount principal = LoginService.getPrincipal();
			Customer cus = this.cusService.findOne(this.actorService.findByUserAccountId(principal.getId()).getId());
			Collection<Request> requests = cus.getRequests();

			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("requestURI", "request/customer/list.do");
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
		Package pack = this.pacService.create();
		Request req = this.reqService.create();
		crf.setPac(pack);
		crf.setRequest(req);
		result = this.createEditModelAndView(crf);

		return result;
	}

	//CREATE A NEW REQUEST
	protected ModelAndView createEditModelAndView(CreateRequestForm crq) {
		ModelAndView result;

		result = this.createEditModelAndView(crq, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(CreateRequestForm crq, String message) {
		ModelAndView result;

		result = new ModelAndView("request/create");

		List<Town> towns = new ArrayList<>(this.townService.findAll());
		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		result.addObject("createRequestForm", crq);
		result.addObject("towns", towns);
		result.addObject("categories", categories);
		result.addObject("es", es);
		result.addObject("message", message);

		return result;
	}

	//Create a new package 
	protected ModelAndView createEditModelAndView(Package pack) {
		ModelAndView result;

		result = this.createEditModelAndView(pack, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Package pack, String message) {
		ModelAndView result;

		if (pack.getId() == 0) {
			result = new ModelAndView("package/create");
		} else {
			result = new ModelAndView("package/edit");
		}

		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		result.addObject("package", pack);
		result.addObject("message", message);
		result.addObject("es", es);
		result.addObject("categories", categories);

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
		result.addObject("towns", towns);
		result.addObject("request", req);
		result.addObject("message", message);
		return result;
	}
}
