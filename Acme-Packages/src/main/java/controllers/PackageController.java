
package controllers;

import java.util.ArrayList;
import java.util.List;
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
import services.CategoryService;
import services.CustomerService;
import services.PackageService;
import services.RequestService;
import domain.Category;
import domain.Customer;
import domain.Package;
import domain.Request;
import forms.AddPackageForm;

@Controller
@RequestMapping("/package/customer")
public class PackageController {

	@Autowired
	private PackageService	pacService;
	@Autowired
	private CategoryService	catService;
	@Autowired
	private RequestService	reqService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private CustomerService	cusService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "add")
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") String reqId) {
		ModelAndView result;
		int intId;
		int actorId;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(reqId);
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(this.reqService.findOne(intId)));
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		AddPackageForm apf = new AddPackageForm();
		apf.setRequestId(reqId);
		result = this.createModelAndView(apf);
		return result;
	}
	//Create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createSave(AddPackageForm apf, BindingResult binding) {
		ModelAndView result;
		int intId;
		int actorId;
		Package pac;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(apf.getRequestId());
			Customer cus = this.cusService.findOne(actorId);
			Assert.isTrue(cus.getRequests().contains(this.reqService.findOne(intId)));
			pac = this.pacService.reconstruct(apf, binding);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		if (binding.hasErrors()) {
			result = this.createModelAndView(apf);
		} else {
			try {
				this.reqService.anyadePackage(pac, this.reqService.findOne(intId));
				result = new ModelAndView("redirect:/request/carrier,customer,auditor/display.do?id=" + intId);
			} catch (Throwable oops) {
				result = this.createModelAndView(apf, "pac.error.commit");
			}
		}
		return result;
	}
	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") String id) {
		ModelAndView result;
		int intId;
		int actorId;
		Package pac;
		try {
			actorId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			intId = Integer.valueOf(id);
			pac = this.pacService.findOne(intId);
			Request req = this.pacService.findRequestByPackageId(intId);
			Assert.isTrue(!req.isFinalMode());
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		result = this.editModelAndView(pac);
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(Package packa, BindingResult binding) {
		ModelAndView result;
		int intId;
		Package pac;
		try {
			Assert.isTrue(packa.getId() != 0);
			intId = this.pacService.findRequestByPackageId(packa.getId()).getId();
			pac = this.pacService.reconstruct(packa, binding);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		if (binding.hasErrors()) {
			result = this.editModelAndView(packa);
		} else {
			try {
				this.reqService.anyadePackage(pac, this.reqService.findOne(intId));
				result = new ModelAndView("redirect:/request/carrier,customer,auditor/display.do?id=" + intId);
			} catch (Throwable oops) {
				result = this.editModelAndView(packa, "pac.error.commit");
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Package packa, BindingResult binding) {
		ModelAndView result;
		int intId;
		Package pac;
		try {
			Assert.isTrue(packa.getId() != 0);
			intId = this.pacService.findRequestByPackageId(packa.getId()).getId();
			pac = this.pacService.reconstruct(packa, binding);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		if (binding.hasErrors()) {
			result = this.editModelAndView(packa);
		} else {
			try {
				this.pacService.delete(pac);
				result = new ModelAndView("redirect:/request/carrier,customer,auditor/display.do?id=" + intId);
			} catch (Throwable oops) {
				result = this.editModelAndView(packa, "pac.error.commit");
			}
		}
		return result;
	}
	//Create a package 
	protected ModelAndView createModelAndView(AddPackageForm apf) {
		ModelAndView result;

		result = this.createModelAndView(apf, null);

		return result;
	}
	protected ModelAndView createModelAndView(AddPackageForm apf, String message) {
		ModelAndView result;
		result = new ModelAndView("package/create");

		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		result.addObject("addPackageForm", apf);
		result.addObject("message", message);
		result.addObject("es", es);
		result.addObject("categories", categories);

		return result;
	}

	//Create a package 
	protected ModelAndView editModelAndView(Package pac) {
		ModelAndView result;

		result = this.editModelAndView(pac, null);

		return result;
	}
	protected ModelAndView editModelAndView(Package pac, String message) {
		ModelAndView result;
		result = new ModelAndView("package/edit");

		List<Category> categories = new ArrayList<>(this.catService.findAll());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		result.addObject("package", pac);
		result.addObject("request", this.pacService.findRequestByPackageId(pac.getId()));
		result.addObject("message", message);
		result.addObject("es", es);
		result.addObject("categories", categories);

		return result;
	}
}
