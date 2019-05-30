
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
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FinderService;
import domain.Finder;
import domain.Offer;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public FinderController() {
		super();
	}

	//SEARCH
	@RequestMapping(value = "/customer/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;

		try {
			finder = this.finderService.findFinderByLoggedCustomer();
			Assert.notNull(finder);
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//SAVE
	@RequestMapping(value = "/customer/search", method = RequestMethod.POST, params = "search")
	public ModelAndView saveFinder(final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder find;

		try {
			find = this.finderService.reconstruct(finder, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
		} else {
			try {
				find = this.finderService.save(find);
				result = this.createEditModelAndView(find);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		}
		return result;
	}

	//CLEAR
	@RequestMapping(value = "/customer/search", method = RequestMethod.POST, params = "clear")
	public ModelAndView cleanFinder() {
		ModelAndView result;
		Finder finder;

		try {
			this.finderService.clearFinder();
			finder = this.finderService.findFinderByLoggedCustomer();
			Assert.notNull(finder);
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		final Collection<Offer> offers = finder.getOffers();

		result = new ModelAndView("finder/search");

		result.addObject("finder", finder);
		result.addObject("offers", offers);
		result.addObject("requestURI", "finder/rookie/search.do");
		result.addObject("message", message);
		result.addObject("enCategories", this.categoryService.englishCategories());
		result.addObject("esCategories", this.categoryService.spanishCategories());

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		result.addObject("es", es);

		return result;
	}

}
