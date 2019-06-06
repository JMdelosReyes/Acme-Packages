
package controllers.administrator;

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

import repositories.CategoryRepository;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import domain.Package;
import domain.Solicitation;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private CategoryRepository	categoryRepository;


	//Constructors----------------------------------------------------------------------

	public CategoryController() {
		super();
	}

	//LIST
	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView listCategories() {
		ModelAndView result;

		try {
			final Collection<Category> categories;
			categories = this.categoryService.findAll();

			final Locale locale = LocaleContextHolder.getLocale();
			boolean es = true;
			if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
				es = false;
			}

			result = new ModelAndView("category/list");
			result.addObject("categories", categories);
			result.addObject("es", es);
			result.addObject("requestURI", "category/administrator/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//CREATE
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView createCategory() {
		ModelAndView result;
		Category cat;

		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}

		cat = this.categoryService.create();

		result = new ModelAndView("category/create");
		result.addObject("category", cat);

		result.addObject("es", es);
		result.addObject("message", null);
		return result;
	}

	//EDIT
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam final int category) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(category);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Category cat;

			final Locale locale = LocaleContextHolder.getLocale();
			boolean es = true;
			if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
				es = false;
			}

			cat = this.categoryService.findOne(category);
			Assert.notNull(cat);

			result = this.createEditModelAndView(cat, es);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	//SAVE
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCategory(final Category category, final BindingResult binding) {
		ModelAndView result;
		Category cat;
		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		//
		try {
			cat = this.categoryService.reconstructCategory(category, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		//		} else {
		//			cat = category;
		//		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(category, es);
		} else {
			try {
				this.categoryService.save(cat);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(cat, "category.commit.error", es);
			}
		}
		return result;
	}

	//DELETE
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteCategory(final Category category, final BindingResult binding) {
		ModelAndView result;
		Collection<Package> lista = this.categoryRepository.CategoryInUsePackage(category.getId());
		Collection<Solicitation> lista2 = this.categoryRepository.CategoryInUseSolicitation(category.getId());
		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		String StringError = "category.commit.error";
		if (!lista.isEmpty() || !lista2.isEmpty()) {
			StringError = "category.error.enUso";
		}

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(category, StringError, es);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Category category, final boolean es) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null, es);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message, final boolean es) {
		ModelAndView result;
		if (category.getId() == 0) {
			result = new ModelAndView("category/create");
		} else {
			result = new ModelAndView("category/edit");
		}
		result.addObject("category", category);
		result.addObject("es", es);
		result.addObject("message", message);

		return result;
	}

}
