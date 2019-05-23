
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ProfessionalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CarrierService				carrierService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<ProfessionalRecord> professionalRecord;
		professionalRecord = this.professionalRecordService.findAll();
		Assert.notNull(professionalRecord);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real professional record id's and a non professional data id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The professional data exists in the database
				super.getEntityId("professionalRecord1"), null
			}, {
				// Incorrect: The id does not match any professional Data in the database
				super.getEntityId("userAccount3"), IllegalArgumentException.class
			}, {
				// Incorrect: The id must be higher than zero
				0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes creating miscellanous data
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier, a non carrier actor of the system
	 */
	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The carrier have curriculum, so he or she can create a professional record
				"carrier1", null
			}, {
				// Incorrect: A sponsor cannot create a professional record
				"sponsor1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes creating and saving professional data
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier, a non carrier actor of the system
	 */
	@Test
	public void driverCreateAndSave() {
		final Collection<String> aux = new ArrayList<>();
		aux.add("comment");
		final Object testingData[][] = {
			{
				// Correct: Normal update
				"carrier1", "company", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", aux, "curriculum1", null
			}, {
				// Incorrect: The title cannot be blank
				"carrier1", "", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", aux, "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The comments cannot be null
				"carrier1", "company", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "htt", aux, "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The selected carrier is not the owner of the curriculum
				"carrier1", "company", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2001-03-03").toDate(), "http:/link.com", aux, "curriculum1", IllegalArgumentException.class
			}, {
				// Incorrect: the actor has a curriculum
				"carrier1", "company", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", aux, "curriculum2", IllegalArgumentException.class
			}, {
				// Incorrect: the actor has a curriculum
				"carrier2", "company", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", null, "curriculum2", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (Collection<String>) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
		}
	}

	/*
	 * Second part of the save method
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes updating his records
	 * Sentence coverage: 100%
	 * Data coverage: 12.5%, as we've tested 9 out of 72 possible combinations
	 */
	@Test
	public void driverSave() {
		final List<String> aux = new ArrayList<>();
		aux.add("comment");
		final Object testingData[][] = {
			{
				// Incorrect: The text cannot be blank
				"carrier1", "professionalRecord1", "companyupdate", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", aux, "curriculum1", null
			}, {
				// Incorrect: The attachments cannot be null
				"carrier2", "professionalRecord1", "text", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "htt", aux, "curriculum1", IllegalArgumentException.class
			}, {
				// Incorrect: The attachments cannot be null
				"carrier1", "professionalRecord1", "text", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "htt", aux, "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The selected carrier is not the owner of the professionalRecord
				"carrier1", "professionalRecord1", "", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2007-03-03").toDate(), "http:/link.com", aux, "curriculum1", ConstraintViolationException.class
			}, {
				// Correct: Normal update
				"carrier1", "professionalRecord1", "text", LocalDateTime.parse("2003-03-03").toDate(), LocalDateTime.parse("2002-03-03").toDate(), "http:/link.com", aux, "curriculum1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], (Collection<String>) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes deleting its records
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we test to delete using a carrier, other kind of actors and a carrier that doesn't own the record
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Incorrect: Only carriers can delete professional record
				"carrier1", "professionalRecord1", null
			}, {
				// Incorrect: The carrier is not the owner
				"carrier3", "professionalRecord1", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner
				"sponsor1", "professionalRecord3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		ProfessionalRecord professionalRecord;

		caught = null;
		try {
			professionalRecord = this.professionalRecordService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final ProfessionalRecord professionalRecord;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);
			professionalRecord = this.professionalRecordService.create();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String bean, final String company, final Date startTime, final Date endTime, final String attachment, final Collection<String> comments, final String curriculumBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();

			super.authenticate(bean);

			final ProfessionalRecord professionalRecord = this.professionalRecordService.create();

			professionalRecord.setCompanyName(company);
			professionalRecord.setStartTime(startTime);
			professionalRecord.setEndTime(endTime);
			professionalRecord.setAttachment(attachment);
			professionalRecord.setComments(comments);

			this.professionalRecordService.save(professionalRecord, super.getEntityId(curriculumBean));

			this.professionalRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();

		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String bean, final String professionalBean, final String company, final Date startTime, final Date endTime, final String attachment, final Collection<String> comments, final String curriculumBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		ProfessionalRecord professionalRecord;
		try {
			super.startTransaction();

			super.authenticate(bean);
			int id = super.getEntityId(professionalBean);
			professionalRecord = this.professionalRecordService.findOne(id);
			final ProfessionalRecord clon = (ProfessionalRecord) professionalRecord.clone();

			clon.setCompanyName(company);
			clon.setStartTime(startTime);
			clon.setEndTime(endTime);
			clon.setAttachment(attachment);
			clon.setComments(comments);

			professionalRecord = clon;

			this.professionalRecordService.save(professionalRecord, super.getEntityId(curriculumBean));
			this.professionalRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String pro, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(carrier);
			final ProfessionalRecord professional = this.professionalRecordService.findOne(super.getEntityId(pro));
			this.professionalRecordService.delete(professional);
			this.professionalRecordService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
