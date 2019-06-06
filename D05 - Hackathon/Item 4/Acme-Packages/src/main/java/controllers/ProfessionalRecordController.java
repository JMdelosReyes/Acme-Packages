
package controllers;

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
import services.ActorService;
import services.CarrierService;
import services.CurriculumService;
import services.ProfessionalRecordService;
import domain.Carrier;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord")
public class ProfessionalRecordController extends AbstractController {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CarrierService				carrierService;


	public ProfessionalRecordController() {
		super();
	}

	// Create
	@RequestMapping(value = "/carrier/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final String curId) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(curId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Curriculum curr = this.curriculumService.findOne(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getCurricula().contains(curr));

			final ProfessionalRecord pRecord = this.professionalRecordService.create();
			result = this.createEditModelAndView(pRecord);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Edit
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final ProfessionalRecord pRecord = this.professionalRecordService.findOne(intId);
			final Curriculum curriculum = this.curriculumService.findCurriculumByProRecord(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getCurricula().contains(curriculum));
			result = this.createEditModelAndView(pRecord);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord proRecord, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String curId) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(curId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);

			Curriculum curriculum;

			if (proRecord.getId() != 0) {
				curriculum = this.curriculumService.findCurriculumByProRecord(proRecord.getId());
				intId = curriculum.getId();
			} else {
				curriculum = this.curriculumService.findOne(intId);
			}
			Assert.isTrue(carrier.getCurricula().contains(curriculum));

			if ((binding.getFieldError("startTime") == null) && (binding.getFieldError("endTime") == null) && (proRecord.getEndTime() != null)) {
				if (!proRecord.getEndTime().after(proRecord.getStartTime())) {
					binding.rejectValue("endTime", "pRecord.wrongTime.error");
				}
			}
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(proRecord);
		} else {
			try {
				this.professionalRecordService.save(proRecord, intId);
				result = new ModelAndView("redirect:/curriculum/display.do?id=" + intId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(proRecord, "pRecord.commit.error");
			}
		}
		return result;
	}
	// Delete
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "0") final String id) {
		ModelAndView result;
		int intId;

		try {
			intId = Integer.valueOf(id);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		try {
			final Curriculum curriculum = this.curriculumService.findCurriculumByProRecord(intId);
			final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(intId);
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/curriculum/display.do?id=" + curriculum.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord pRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(pRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord pRecord, final String message) {
		ModelAndView result;

		if (pRecord.getId() == 0) {
			result = new ModelAndView("professionalRecord/create");
		} else {
			result = new ModelAndView("professionalRecord/edit");
			final int curId = this.curriculumService.findCurriculumByProRecord(pRecord.getId()).getId();
			result.addObject("curId", curId);
		}

		result.addObject("professionalRecord", pRecord);
		result.addObject("message", message);

		return result;
	}

}
