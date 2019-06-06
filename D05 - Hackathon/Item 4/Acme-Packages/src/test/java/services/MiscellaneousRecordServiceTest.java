
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CarrierService				carrierService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> miscellaneousRecord;
		miscellaneousRecord = this.miscellaneousRecordService.findAll();
		Assert.notNull(miscellaneousRecord);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real miscellaneous record id's and a non miscellaneous record id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The miscellaneous data exists in the database
				super.getEntityId("miscellaneousRecord1"), null
			}, {
				// Incorrect: The id does not match any miscellaneous Data in the database
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
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes creating miscellanous record
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier, a non carrier actor of the system
	 */
	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The carrier have curriculum, so he or she can create a miscellaneous data
				"carrier1", null
			}, {
				// Incorrect: A sponsor cannot create a miscellaneous data
				"sponsor1", IllegalArgumentException.class
			}, {
				// Correct: The carrier has curriculum, so he or she can create a miscellaneous data
				"carrier2", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes creating and saving miscellanous record
	 * Sentence coverage: 100%
	 * Data coverage: 15,6%, as we've tested 5 of 32 possibilities
	 */
	@Test
	public void driverCreateAndSave() {
		final List<String> aux = new ArrayList<>();
		aux.add("comment");
		final Object testingData[][] = {
			{
				// Correct: Normal update
				"carrier1", "text", aux, "http:/link.com", "curriculum1", null
			}, {
				// Incorrect: The title cannot be blank
				"carrier1", "", aux, "http:/link.com", "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The comments cannot be null
				"carrier1", "text", null, "http:/link.com", "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The selected carrier is not the owner of the curriculum
				"carrier2", "text", aux, "http:/link.com", "curriculum1", IllegalArgumentException.class
			}, {
				// Incorrect: the actor has a curriculum
				"sponsor1", "text", aux, "http:/link.com", "curriculum1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (List<String>) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	/*
	 * Second part of the save method
	 * Requirement tested: An actor who is authenticated as a carrier must be able to manage his or her curriculum, which includes updating datas
	 * Sentence coverage: 100%
	 * Data coverage: 6,25%, as we've tested 4 out of 64 possible combinations
	 */
	@Test
	public void driverSave() {
		final List<String> aux = new ArrayList<>();
		aux.add("comment");
		final Object testingData[][] = {
			{
				// Incorrect: The text cannot be blank
				"carrier1", "miscellaneousRecord1", "", "https://clockify.me/tracker", aux, "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The attachments cannot be null
				"carrier1", "miscellaneousRecord1", "text", "https://clockify.me/tracker", null, "curriculum1", ConstraintViolationException.class
			}, {
				// Incorrect: The selected carrier is not the owner of the miscellaneousRecord
				"carrier2", "miscellaneousRecord1", "text", "https://clockify.me/tracker", aux, "curriculum1", IllegalArgumentException.class
			}, {
				// Correct: Normal update
				"carrier1", "miscellaneousRecord1", "text", "https://clockify.me/tracker", aux, "curriculum1", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (List<String>) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
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
				// correct: Only carriers can delete miscellaneous record
				"carrier1", "miscellaneousRecord1", null
			}, {
				// Incorrect: The carrier is not the owner
				"carrier3", "miscellaneousRecord1", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner
				"sponsor1", "miscellaneousRecord3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		MiscellaneousRecord miscellaneousRecord;

		caught = null;
		try {
			miscellaneousRecord = this.miscellaneousRecordService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final MiscellaneousRecord miscellaneousRecord;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);
			miscellaneousRecord = this.miscellaneousRecordService.create();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String bean, final String title, final Collection<String> comments, final String attachment, final String curriculumBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();

			super.authenticate(bean);

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();

			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setComments(comments);
			miscellaneousRecord.setAttachment(attachment);

			this.miscellaneousRecordService.save(miscellaneousRecord, super.getEntityId(curriculumBean));

			this.miscellaneousRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();

		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String bean, final String miscellaneousBean, final String title, final String attachment, final Collection<String> comments, final String curriculumBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		MiscellaneousRecord miscellaneousRecord;
		try {
			super.startTransaction();

			super.authenticate(bean);
			int id = super.getEntityId(miscellaneousBean);
			miscellaneousRecord = this.miscellaneousRecordService.findOne(id);
			final MiscellaneousRecord clon = (MiscellaneousRecord) miscellaneousRecord.clone();

			clon.setTitle(title);
			clon.setComments(comments);
			clon.setAttachment(attachment);

			miscellaneousRecord = clon;

			this.miscellaneousRecordService.save(miscellaneousRecord, super.getEntityId(curriculumBean));
			this.miscellaneousRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();

			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String misc, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(carrier);
			final MiscellaneousRecord miscellaneous = this.miscellaneousRecordService.findOne(super.getEntityId(misc));
			this.miscellaneousRecordService.delete(miscellaneous);
			this.miscellaneousRecordService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
