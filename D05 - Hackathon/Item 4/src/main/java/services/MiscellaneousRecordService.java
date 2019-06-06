
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Carrier;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Transactional
@Service
public class MiscellaneousRecordService {

	// Managed repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services

	@Autowired
	private ActorService					actorService;

	@Autowired
	private CarrierService					carrierService;

	@Autowired
	private CurriculumService				curriculumService;


	// Constructor
	public MiscellaneousRecordService() {
		super();
	}

	// CRUD methods

	public MiscellaneousRecord findOne(final int id) {
		Assert.isTrue(id > 0);
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public MiscellaneousRecord create() {
		//The logged user must be a carrier
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		//The logged rookie must at least have one curriculum
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.isTrue(carrier.getCurricula().size() >= 1);

		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setAttachment("");
		miscellaneousRecord.setComments(new ArrayList<String>());

		return miscellaneousRecord;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord, final Integer curriculumId) {
		Assert.notNull(miscellaneousRecord);
		final MiscellaneousRecord result;

		//The logged user must be a carrier
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);

		Assert.isTrue(carrier.getCurricula().contains(curriculum));

		if (miscellaneousRecord.getId() == 0) {

			result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
			this.miscellaneousRecordRepository.flush();
			Assert.notNull(result);

			curriculum.getMiscellaneousRecords().add(result);
			this.curriculumService.save(curriculum);
		} else {
			final MiscellaneousRecord old = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
			Assert.notNull(old);

			//The miscellaneous data must be owned by the logged carrier
			Assert.isTrue(curriculum.getMiscellaneousRecords().contains(old));

			result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
			Assert.notNull(result);
		}
		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		//The logged user must be a carrier
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(this.actorService.findActorType().equals("Carrier"));

		final MiscellaneousRecord old = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
		Assert.notNull(old);

		//The data must belong to the logged carrier
		final int id = this.actorService.findByUserAccountId(userAccount.getId()).getId();
		final Carrier carrier = this.carrierService.findOne(id);
		Assert.notNull(carrier);

		final Curriculum curriculum = this.curriculumService.findCurriculumByMisRecord(old.getId());
		Assert.isTrue(carrier.getCurricula().contains(curriculum));
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(old));

		curriculum.getMiscellaneousRecords().remove(old);
		this.curriculumService.save(curriculum);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord.getId());
	}

	// Other methods

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}
}
