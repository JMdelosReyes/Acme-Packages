
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.MessBoxService;
import domain.MessBox;

@Controller
//Esto para mirar entre todos los RequestMapping, cual es el controllador a llamar
@RequestMapping("/messageBox")
public class MessBoxController extends AbstractController {

	@Autowired
	private MessBoxService	messageBoxService;


	//@Autowired
	//private ActorService	actorService;

	public MessBoxController() {

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MessBox> messBoxes;
		try {
			messBoxes = this.messageBoxService.findMessBoxesNoChildren();

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/");
			return result;
		}

		result = new ModelAndView("messageBox/list");
		result.addObject("requestURI", "messageBox/list.do");
		result.addObject("messBox", messBoxes);

		return result;

	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final String messageBoxId) {
		ModelAndView result;
		UserAccount principal;
		List<MessBox> children;
		int intId;

		try {
			intId = Integer.valueOf(messageBoxId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final MessBox messBox = this.messageBoxService.findOne(intId);
			principal = LoginService.getPrincipal();
			result = new ModelAndView("messageBox/display");
			if (this.messageBoxService.findChildrenMessBoxes(messBox) != null) {
				children = new ArrayList<>(this.messageBoxService.findChildrenMessBoxes(messBox));
				result.addObject("children", children);
			}

			result.addObject("messageBox", messBox);
			result.addObject("requestURI", "messageBox/display.do");
		} catch (final Exception e) {
			principal = null;
			result = new ModelAndView("redirect:/");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessBox messBox;

		messBox = this.messageBoxService.create();
		Assert.notNull(messBox);
		result = this.createEditModelAndView(messBox);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String messageBoxId) {
		ModelAndView result;
		MessBox messBox;

		UserAccount principal;
		int intId;

		try {
			intId = Integer.valueOf(messageBoxId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			principal = LoginService.getPrincipal();
			messBox = this.messageBoxService.findOne(intId);
			if (principal == null || !this.messageBoxService.findOwnMessageBoxes().contains(messBox))
				result = new ModelAndView("redirect:/");
			else if (messBox == null || messBox.getIsSystem())
				result = new ModelAndView("redirect:list.do");
			else
				result = this.createEditModelAndView(messBox);
		} catch (final Exception e) {
			principal = null;
			messBox = null;
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MessBox messBox, final BindingResult binding) {
		ModelAndView result;
		MessBox messageBox;

		try {
			messBox = this.messageBoxService.reconstruct(messBox, binding);
		} catch (final Exception e) {
			messBox = null;
		}
		if (messBox == null) {
			result = new ModelAndView("redirect:/");
			return result;
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(messBox);
		else {
			UserAccount principal;
			try {
				principal = LoginService.getPrincipal();
			} catch (final Exception e) {
				principal = null;
			}

			if (principal == null)
				result = new ModelAndView("redirect:/");
			try {
				if (messBox.getId() != 0) {
					final Collection<MessBox> owned = this.messageBoxService.findOwnMessageBoxes();

					try {
						messageBox = this.messageBoxService.findOne(messBox.getId());
					} catch (final Exception e) {
						messageBox = null;
					}

					if (messageBox == null || !owned.contains(messageBox) || messageBox.getIsSystem())
						result = new ModelAndView("redirect:/");
					else {
						this.messageBoxService.save(messBox);
						result = new ModelAndView("redirect:list.do");
					}
				} else {
					this.messageBoxService.save(messBox);
					result = new ModelAndView("redirect:list.do");
				}

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messBox, "messBox.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MessBox messBox, final BindingResult binding) {
		ModelAndView result;

		UserAccount principal;
		try {
			principal = LoginService.getPrincipal();
			messBox = this.messageBoxService.reconstruct(messBox, binding);
		} catch (final Exception e) {
			principal = null;
			return new ModelAndView("redirect:/");
		}

		if (principal == null || !this.messageBoxService.findOwnMessageBoxes().contains(messBox) || !this.messageBoxService.findChildrenMessBoxes(messBox).isEmpty())
			result = new ModelAndView("redirect:/");
		try {
			this.messageBoxService.delete(messBox);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messBox, "messBox.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAll")
	public ModelAndView deleteAll(@RequestParam(required = false, defaultValue = "0") final String messageBoxId) {
		ModelAndView result;
		MessBox messBox;
		UserAccount principal;
		int intId;

		try {
			intId = Integer.valueOf(messageBoxId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			messBox = this.messageBoxService.findOne(intId);
			principal = LoginService.getPrincipal();
		} catch (final Exception e) {
			principal = null;
			return new ModelAndView("redirect:/");
		}

		if (principal == null || !this.messageBoxService.findOwnMessageBoxes().contains(messBox))
			result = new ModelAndView("redirect:/");
		else
			try {
				this.messageBoxService.clean(messBox);
				final MessBox clean = this.messageBoxService.findOne(intId);
				result = new ModelAndView("redirect:display.do?messageBoxId=" + clean.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messBox, "messBox.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final MessBox messBox) {
		ModelAndView result;
		result = this.createEditModelAndView(messBox, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final MessBox messBox, final String messageCode) {
		ModelAndView result;
		List<MessBox> parents;
		result = new ModelAndView("messageBox/edit");
		parents = new ArrayList<>(this.messageBoxService.findOwnMessageBoxes());

		result.addObject("messBox", messBox);
		result.addObject("message", messageCode);
		if (messBox.getId() == 0)
			result.addObject("parents", parents);
		if (messBox.getId() != 0)
			if (!this.messageBoxService.findChildrenMessBoxes(messBox).isEmpty())
				result.addObject("children", this.messageBoxService.findChildrenMessBoxes(messBox));
		return result;
	}
}
