
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TownService;
import domain.Town;

@Controller
@RequestMapping("/town")
public class TownController extends AbstractController {

	@Autowired
	private TownService	townService;


	public TownController() {
		super();
	}

	// List
	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Town> towns = this.townService.findAll();

			result = new ModelAndView("town/list");
			result.addObject("towns", towns);
			result.addObject("requestURI", "town/administrator/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Create
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			Town town = this.townService.create();
			result = this.createEditModelAndView(town);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		return result;
	}

	// Edit
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		Integer intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Town town = this.townService.findOne(intId);
			result = this.createEditModelAndView(town);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Town town, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(town);
		} else {
			try {
				if (town.getId() == 0) {
					boolean zipUsed = this.townService.townWithSameZip(town.getZipCode());
					if (zipUsed) {
						return this.createEditModelAndView(town, "town.commit.error.zipUsed");
					}
				} else {
					Town old = this.townService.findOne(town.getId());
					if (!old.getZipCode().equals(town.getZipCode())) {
						boolean zipUsed = this.townService.townWithSameZip(town.getZipCode());
						if (zipUsed) {
							return this.createEditModelAndView(town, "town.commit.error.zipUsed");
						}
					}
				}

				this.townService.save(town);
				result = new ModelAndView("redirect:/town/administrator/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(town, "town.commit.error");
			}
		}

		return result;
	}
	// Delete
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			Town town = this.townService.findOne(intId);
			Assert.isTrue(this.townService.findNumberOfRequests(town.getId()) == 0);
			Assert.isTrue(this.townService.findNumberOfTraverseTowns(town.getId()) == 0);
			this.townService.delete(town);
			result = new ModelAndView("redirect:/town/administrator/list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Town town) {
		ModelAndView result;

		result = this.createEditModelAndView(town, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Town town, final String message) {
		ModelAndView result;

		boolean canBeDeleted = false;

		if (town.getId() == 0) {
			result = new ModelAndView("town/create");
		} else {
			result = new ModelAndView("town/edit");
			if ((this.townService.findNumberOfRequests(town.getId()) == 0) && (this.townService.findNumberOfTraverseTowns(town.getId()) == 0)) {
				canBeDeleted = true;
			}
		}

		result.addObject("town", town);
		result.addObject("message", message);
		result.addObject("canBeDeleted", canBeDeleted);
		return result;
	}

}
