
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import services.ActorService;
import services.ConfigurationService;
import services.MessBoxService;
import services.MessService;
import domain.Actor;
import domain.Mess;
import domain.MessBox;
import forms.CopyForm;
import forms.DeleteForm;
import forms.MoveForm;

@Controller
//Esto para mirar entre todos los RequestMapping, cual es el controllador a llamar
@RequestMapping("/mess")
public class MessageController extends AbstractController {

	@Autowired
	private MessService				messService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private MessBoxService			messBoxService;
	@Autowired
	private ConfigurationService	confService;


	public MessageController() {

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Mess message;

		message = this.messService.create();
		Assert.notNull(message);
		result = this.createEditModelAndView(message);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	public ModelAndView send(Mess mess, BindingResult binding) {
		ModelAndView result;
		Mess me;
		try {
			me = this.messService.reconstruct(mess, binding);
		} catch (Exception e) {
			me = null;
			result = new ModelAndView("redirect:/");
			return result;
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(mess);
		} else {
			try {
				Mess m = this.messService.send(me, false);
				result = new ModelAndView("redirect:display.do?messageId=" + m.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(mess, "mess.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView broadcast(Mess mess, BindingResult binding) {
		ModelAndView result;
		Mess recons;
		UserAccount principal;
		try {
			principal = LoginService.getPrincipal();
		} catch (Exception e) {
			principal = null;
		}
		try {
			recons = this.messService.reconstructNotification(mess, binding);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(mess);
		} else {
			try {
				Mess m = this.messService.sendNotification(recons);
				result = new ModelAndView("redirect:display.do?messageId=" + m.getId());
			} catch (Throwable oops) {
				mess.setSender(this.actorService.findByUserAccountId(principal.getId()));
				result = this.createEditModelAndView(mess, "mess.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") String messageId) {
		ModelAndView result;
		Mess mensaje;
		Integer intId;
		UserAccount principal;
		try {
			intId = Integer.valueOf(messageId);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			principal = LoginService.getPrincipal();
			mensaje = this.messService.findOne(intId);
			if ((principal == null) || !this.messService.findOwnMessages().contains(mensaje)) {
				result = new ModelAndView("redirect:/");
			} else {
				int container = new ArrayList<>(this.messBoxService.findByMessageId(intId)).size();

				result = new ModelAndView("mess/display");
				result.addObject("mess", mensaje);
				result.addObject("containers", container);
				result.addObject("requestURI", "mess/display.do");
			}
		} catch (Exception e) {
			principal = null;
			mensaje = null;
			result = new ModelAndView("redirect:/");
		}

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") String messId) {
		ModelAndView result;
		UserAccount principal;
		Mess mensaje;
		Integer intId;

		try {
			intId = Integer.valueOf(messId);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			mensaje = this.messService.findOne(intId);
			principal = LoginService.getPrincipal();
		} catch (Exception e) {
			principal = null;
			mensaje = null;
			return new ModelAndView("redirect:/");
		}

		if ((principal == null) || !this.messService.findOwnMessages().contains(mensaje)) {
			result = new ModelAndView("redirect:/");
			return result;
		}

		try {
			DeleteForm deleteForm = new DeleteForm();

			List<MessBox> messBoxes = new ArrayList<>(this.messBoxService.findByMessageId(intId));

			result = new ModelAndView("mess/delete");
			result.addObject("requestURI", "mess/delete.do");
			result.addObject("containers", messBoxes);
			deleteForm.setIdMessage(intId);
			result.addObject("deleteForm", deleteForm);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDelete(@Valid DeleteForm deleteForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			List<MessBox> containers = new ArrayList<>(this.messBoxService.findByMessageId(deleteForm.getIdMessage()));

			result = new ModelAndView("mess/delete");
			result.addObject("requestURI", "mess/delete.do");
			result.addObject("containers", containers);
			result.addObject("deleteForm", deleteForm);
		} else {
			UserAccount principal;
			try {
				principal = LoginService.getPrincipal();
			} catch (Exception e) {
				principal = null;
			}
			try {
				Mess m = this.messService.findOne(deleteForm.getIdMessage());
				if ((principal != null) && this.messBoxService.findOwnMessageBoxes().contains(deleteForm.getContainer()) && this.messService.findOwnMessages().contains(m)) {
					this.messService.delete(this.messService.findOne(deleteForm.getIdMessage()), deleteForm.getContainer());
					result = new ModelAndView("redirect:/messageBox/display.do?messageBoxId=" + deleteForm.getContainer().getId());
				} else {
					result = new ModelAndView("redirect:/");
				}
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/mess/display.do?messageId=" + deleteForm.getIdMessage());
			}
		}
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "copy")
	public ModelAndView copy(@RequestParam(required = false, defaultValue = "0") String messId) {
		ModelAndView result;
		UserAccount principal;
		Mess mensaje;
		Integer intId;

		try {
			intId = Integer.valueOf(messId);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		try {
			mensaje = this.messService.findOne(intId);
			principal = LoginService.getPrincipal();
		} catch (Exception e) {
			principal = null;
			result = new ModelAndView("redirect:/");
			mensaje = null;
			return result;
		}

		if ((principal == null) || !this.messService.findOwnMessages().contains(mensaje)) {
			result = new ModelAndView("redirect:/");
			return result;
		}

		CopyForm form = new CopyForm();

		List<MessBox> messBoxes = new ArrayList<>(this.messBoxService.findPosibleDestinations(intId));

		result = new ModelAndView("mess/copy");
		form.setMessageId(intId);
		result.addObject("destinations", messBoxes);
		result.addObject("copyForm", form);

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "move")
	public ModelAndView move(@RequestParam(required = false, defaultValue = "0") String messId) {
		ModelAndView result;
		UserAccount principal;
		Integer intId;

		try {
			intId = Integer.valueOf(messId);
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		MoveForm form = new MoveForm();

		Mess mensaje;

		try {
			mensaje = this.messService.findOne(intId);
			principal = LoginService.getPrincipal();
		} catch (Exception e) {
			principal = null;
			mensaje = null;
			return new ModelAndView("redirect:/");
		}

		if ((principal == null) || !this.messService.findOwnMessages().contains(mensaje)) {
			result = new ModelAndView("redirect:/");
			return result;
		}

		List<MessBox> messBoxes = new ArrayList<>(this.messBoxService.findPosibleDestinations(intId));
		List<MessBox> source = new ArrayList<>(this.messBoxService.findByMessageId(intId));

		result = new ModelAndView("mess/move");
		form.setMessageId(intId);
		result.addObject("moveForm", form);
		result.addObject("destinations", messBoxes);
		result.addObject("source", source);

		return result;
	}

	@RequestMapping(value = "/copy", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCopy(@Valid CopyForm copyForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			List<MessBox> messBoxes = new ArrayList<>(this.messBoxService.findPosibleDestinations(copyForm.getMessageId()));

			result = new ModelAndView("mess/copy");
			result.addObject("destinations", messBoxes);
			result.addObject("copyForm", copyForm);
		} else {
			Mess m = this.messService.findOne(copyForm.getMessageId());
			if (this.messBoxService.findOwnMessageBoxes().contains(copyForm.getDestination()) && this.messService.findOwnMessages().contains(m)) {
				try {
					this.messService.copy(this.messService.findOne(copyForm.getMessageId()), copyForm.getDestination());
					result = new ModelAndView("redirect:/mess/display.do?messageId=" + copyForm.getMessageId());
				} catch (Throwable oops) {
					result = new ModelAndView("redirect:/messageBox/display.do?messageBoxId=" + copyForm.getDestination());
				}
			} else {
				result = new ModelAndView("redirect:/");
			}
		}
		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView moveSave(@Valid MoveForm moveForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			List<MessBox> messBoxes = new ArrayList<>(this.messBoxService.findPosibleDestinations(moveForm.getMessageId()));
			List<MessBox> source = new ArrayList<>(this.messBoxService.findByMessageId(moveForm.getMessageId()));

			result = new ModelAndView("mess/move");

			result.addObject("moveForm", moveForm);
			result.addObject("destinations", messBoxes);
			result.addObject("source", source);
		} else {
			try {
				this.messService.move(this.messService.findOne(moveForm.getMessageId()), moveForm.getSource(), moveForm.getDestination());
				result = new ModelAndView("redirect:/mess/display.do?messageId=" + moveForm.getMessageId());
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/messBox/display.do?messageBoxId=" + moveForm.getDestination().getId());
			}
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(Mess message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Mess mess, String messageCode) {
		ModelAndView result;
		List<Actor> recipients;
		List<String> priorities;

		recipients = new ArrayList<>(this.actorService.findAll());
		recipients.remove(mess.getSender());

		priorities = new ArrayList<>(this.confService.findOne().getMessPriorities());

		result = new ModelAndView("mess/edit");

		result.addObject("mess", mess);
		result.addObject("recipients", recipients);
		result.addObject("message", messageCode);
		result.addObject("priorities", priorities);
		return result;
	}

}
