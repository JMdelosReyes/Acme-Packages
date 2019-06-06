
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
import services.MiscellaneousRecordService;
import domain.Carrier;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CarrierService				carrierService;


	public MiscellaneousRecordController() {
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

			final MiscellaneousRecord mRecord = this.miscellaneousRecordService.create();
			result = this.createEditModelAndView(mRecord);
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
			final MiscellaneousRecord mRecord = this.miscellaneousRecordService.findOne(intId);
			final Curriculum curriculum = this.curriculumService.findCurriculumByMisRecord(intId);
			final int carrierId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			final Carrier carrier = this.carrierService.findOne(carrierId);
			Assert.isTrue(carrier.getCurricula().contains(curriculum));
			result = this.createEditModelAndView(mRecord);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/carrier/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord misRecord, final BindingResult binding, @RequestParam(required = false, defaultValue = "0") final String curId) {
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

			if (misRecord.getId() != 0) {
				curriculum = this.curriculumService.findCurriculumByMisRecord(misRecord.getId());
				intId = curriculum.getId();
			} else {
				curriculum = this.curriculumService.findOne(intId);
			}
			Assert.isTrue(carrier.getCurricula().contains(curriculum));
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(misRecord);
		} else {
			try {
				this.miscellaneousRecordService.save(misRecord, intId);
				result = new ModelAndView("redirect:/curriculum/display.do?id=" + intId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(misRecord, "mRecord.commit.error");
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
			final Curriculum curriculum = this.curriculumService.findCurriculumByMisRecord(intId);
			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(intId);
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/curriculum/display.do?id=" + curriculum.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(mRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mRecord, final String message) {
		ModelAndView result;

		if (mRecord.getId() == 0) {
			result = new ModelAndView("miscellaneousRecord/create");
		} else {
			result = new ModelAndView("miscellaneousRecord/edit");
			final int curId = this.curriculumService.findCurriculumByMisRecord(mRecord.getId()).getId();
			result.addObject("curId", curId);
		}

		result.addObject("miscellaneousRecord", mRecord);
		result.addObject("message", message);

		return result;
	}

}
