
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.CurriculumRepository;
import security.LoginService;
import domain.Carrier;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CarrierService			carrierService;

	@Autowired
	Validator						validator;


	// Constructor

	public CurriculumService() {
		super();
	}

	// CRUD methods

	public Collection<Curriculum> findAll() {
		final Collection<Curriculum> result;
		result = this.curriculumRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Curriculum findOne(final int id) {
		Assert.isTrue(id > 0);
		Curriculum result;
		result = this.curriculumRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Curriculum create() {
		final String actorType = this.actorService.findActorType();
		Assert.isTrue(actorType.equals("Carrier"));

		final Curriculum curriculum = new Curriculum();
		curriculum.setFullName("");
		curriculum.setEmail("");
		curriculum.setPhoneNumber("");
		curriculum.setPhoto("");
		curriculum.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		curriculum.setProfessionalRecords(new ArrayList<ProfessionalRecord>());

		return curriculum;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		final String actorType = this.actorService.findActorType();
		Assert.isTrue(actorType.equals("Carrier"));

		final Curriculum result;
		Carrier carrier;
		if (curriculum.getId() == 0) {
			Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
			result = this.curriculumRepository.save(curriculum);
			Assert.notNull(result);
			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			carrier = this.carrierService.findOne(id);
			carrier.getCurricula().add(result);
			this.carrierService.save(carrier);

		} else {
			final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			carrier = this.carrierService.findOne(id);
			Assert.notNull(carrier);
			Assert.isTrue(actorType.equals("Carrier"));
			final Curriculum old = this.curriculumRepository.findOne(curriculum.getId());
			Assert.notNull(old);
			Assert.isTrue(carrier.getCurricula().contains(old));
			result = this.curriculumRepository.save(curriculum);
			Assert.notNull(result);
		}
		return result;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));
		final Curriculum old = this.curriculumRepository.findOne(curriculum.getId());
		Assert.notNull(old);

		Carrier carrier;
		final int id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);
		Assert.isTrue(carrier.getCurricula().contains(old));
		carrier.getCurricula().remove(old);
		this.curriculumRepository.delete(old.getId());
	}

	// Other methods

	public void flush() {
		this.curriculumRepository.flush();
	}

	public Curriculum findCurriculumByProRecord(final int id) {
		Assert.isTrue(id > 0);
		Curriculum result;
		result = this.curriculumRepository.findCurriculumByProRecord(id);
		Assert.notNull(result);
		return result;
	}

	public Curriculum findCurriculumByMisRecord(final int id) {
		Assert.isTrue(id > 0);
		Curriculum result;
		result = this.curriculumRepository.findCurriculumByMisRecord(id);
		Assert.notNull(result);
		return result;
	}

	//	//Reconstruct
	//
	//	public Curriculum reconstruct(final Curriculum curriculum, final BindingResult binding) {
	//		Curriculum result;
	//
	//		if (curriculum.getId() == 0) {
	//			result = this.create();
	//			result.setFullName(curriculum.getFullName());
	//			result.setStatement(curriculum.getStatement());
	//			result.setPhoneNumber(curriculum.getPhoneNumber());
	//			result.setGitHubProfile(curriculum.getGitHubProfile());
	//			result.setLinkedInProfile(curriculum.getLinkedInProfile());
	//		} else {
	//			result = this.curriculumRepository.findOne(curriculum.getId());
	//			Assert.notNull(result);
	//			final int rookieId = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
	//			final Rookie rookie = this.rookieService.findOne(rookieId);
	//			Assert.isTrue(rookie.getCurricula().contains(result));
	//
	//			final Curriculum clon = (Curriculum) result.clone();
	//
	//			clon.setFullName(curriculum.getFullName());
	//			clon.setStatement(curriculum.getStatement());
	//			clon.setPhoneNumber(curriculum.getPhoneNumber());
	//			clon.setGitHubProfile(curriculum.getGitHubProfile());
	//			clon.setLinkedInProfile(curriculum.getLinkedInProfile());
	//
	//			result = clon;
	//		}
	//
	//		this.validator.validate(result, binding);
	//
	//		return result;
	//	}
}
