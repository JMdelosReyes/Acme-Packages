/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ConfigurationService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		boolean logged;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		UserAccount principal;
		result = new ModelAndView("welcome/index");
		final Locale locale = LocaleContextHolder.getLocale();
		boolean es = true;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			es = false;
		}
		try {
			principal = LoginService.getPrincipal();
			logged = true;
			final Actor a = this.actorService.findByUserAccountId(principal.getId());
			String name = a.getName() + " " + a.getSurname();

			final String welcomeMessageES = this.configurationService.findOne().getSpanishMessage();
			final String welcomeMessageEN = this.configurationService.findOne().getEnglishMessage();
			final String banner = this.configurationService.findOne().getBanner();
			result.addObject("name", name);
			result.addObject("welcomeES", welcomeMessageES);
			result.addObject("welcomeEN", welcomeMessageEN);
			result.addObject("banner", banner);
			result.addObject("es", es);
		} catch (final Throwable oops) {
			logged = false;
		}

		result.addObject("moment", moment);
		result.addObject("logged", logged);

		return result;
	}

	@RequestMapping(value = "/terms")
	public ModelAndView terms() {
		ModelAndView result;
		result = new ModelAndView("misc/terms");
		return result;
	}
}
