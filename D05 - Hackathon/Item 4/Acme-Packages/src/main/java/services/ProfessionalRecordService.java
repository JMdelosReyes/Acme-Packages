
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Carrier;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Transactional
@Service
public class ProfessionalRecordService {

	// Managed repository

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private ActorService					actorService;

	@Autowired
	private CarrierService					carrierService;

	@Autowired
	private CurriculumService				curriculumService;


	// Constructor
	public ProfessionalRecordService() {
		super();
	}

	// CRUD methods

	public ProfessionalRecord findOne(final int id) {
		Assert.isTrue(id > 0);
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public ProfessionalRecord create() {
		//The logged user must be a carrier
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		//The logged carrier must at least have one curriculum
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.isTrue(carrier.getCurricula().size() >= 1);

		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setCompanyName("");
		professionalRecord.setAttachment("");
		professionalRecord.setStartTime(new Date(System.currentTimeMillis() - 1000));
		professionalRecord.setEndTime(null);
		professionalRecord.setComments(new ArrayList<String>());

		return professionalRecord;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord, final Integer curriculumId) {
		Assert.notNull(professionalRecord);
		final ProfessionalRecord result;

		//The logged user must be a carrier
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);

		Assert.isTrue(carrier.getCurricula().contains(curriculum));

		if (professionalRecord.getId() == 0) {

			if (professionalRecord.getEndTime() != null) {
				Assert.isTrue(professionalRecord.getStartTime().before(professionalRecord.getEndTime()));
			}

			result = this.professionalRecordRepository.save(professionalRecord);
			this.professionalRecordRepository.flush();
			Assert.notNull(result);

			curriculum.getProfessionalRecords().add(result);
			this.curriculumService.save(curriculum);
		} else {
			final ProfessionalRecord old = this.professionalRecordRepository.findOne(professionalRecord.getId());
			Assert.notNull(old);

			//The professional record must be owned by the logged carrier
			Assert.isTrue(curriculum.getProfessionalRecords().contains(old));
			if (professionalRecord.getEndTime() != null) {
				Assert.isTrue(professionalRecord.getStartTime().before(professionalRecord.getEndTime()));
			}

			result = this.professionalRecordRepository.save(professionalRecord);
			Assert.notNull(result);
		}
		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		//The logged user must be a carrier
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final ProfessionalRecord old = this.professionalRecordRepository.findOne(professionalRecord.getId());
		Assert.notNull(old);

		//The data must belong to the logged carrier
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final Curriculum curriculum = this.curriculumService.findCurriculumByProRecord(old.getId());
		Assert.isTrue(carrier.getCurricula().contains(curriculum));
		Assert.isTrue(curriculum.getProfessionalRecords().contains(old));

		curriculum.getProfessionalRecords().remove(old);
		this.curriculumService.save(curriculum);
		this.professionalRecordRepository.delete(professionalRecord.getId());
	}

	// Other methods

	public void flush() {
		this.professionalRecordRepository.flush();
	}
}
