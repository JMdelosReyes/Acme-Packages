/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.SponsorshipService;
import domain.Sponsorship;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ActorService			actorService;


	// Panic handler ----------------------------------------------------------

	@ModelAttribute("banner")
	public String Banner() {
		return this.configurationService.findOne().getBanner();
	}
	@ModelAttribute("systemName")
	public String SystemName() {
		return this.configurationService.findOne().getSystemName();
	}

	@ModelAttribute("spon")
	public domain.Sponsorship Sponsorship() {
		domain.Sponsorship spon = null;
		try {
			spon = this.sponsorshipService.randomSponsorship();
		} catch (Throwable oops) {
			Collection<Sponsorship> spons = this.sponsorshipService.findValidSponsorships();
			final int size = spons.size();
			if (size >= 1) {
				final int random = (int) Math.round(Math.random() * (size - 1));
				spon = (Sponsorship) spons.toArray()[random];
			}
		}
		return spon;

	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}
