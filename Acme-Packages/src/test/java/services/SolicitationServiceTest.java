
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Solicitation;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SolicitationServiceTest extends AbstractTest {

	@Autowired
	private SolicitationService	solicitationService;

	@Autowired
	private CategoryService		categoryService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Solicitation> solicitations;
		solicitations = this.solicitationService.findAll();
		Assert.notNull(solicitations);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method receive only a parameter
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The solicitation exists in the database
				super.getEntityId("solicitation1"), null
			}, {
				// Correct: The solicitation exists in the database
				super.getEntityId("solicitation2"), null
			}, {
				// Correct: The solicitation exists in the database
				super.getEntityId("solicitation3"), null
			}, {
				// Incorrect: The id does not match any solicitation in the database
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
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method only receive an actor
	 */
	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The user is a carrier
				"carrier1", null
			}, {
				// Incorrect: The user is not a carrier
				"admin", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"customer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 60% the method receive different actor, but the same category and vehicle
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "category3", "comment", "vehicle2", null
			}, {
				// Incorrect: The user must be a carrier
				"admin", "category3", "comment", "vehicle2", IllegalArgumentException.class
			}, {
				// Incorrect: the user must be a carrier
				"auditor1", "category3", "comment", "vehicle2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}
	/*
	 * Sentence coverage: 70.5%
	 * Data coverage: 25%, this save receive many parameters, only 1/4 of the possibilities are tested
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "solicitation3", "category2", "comment", null, null, null, null
			}, {
				// Incorrect: The solicitation is being used
				"carrier1", "solicitation3", "category2", "comment", null, null, "ACCEPTED", IllegalArgumentException.class
			}, {
				// Incorrect: The actor is not the owner of the solicitation
				"carrier2", "solicitation3", "category2", "comment", null, null, null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
		}
	}
	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method receive only a parameter and tried all the possibilities
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: The actor can delete his solicitation
				"carrier1", "solicitation3", null
			}, {
				// Incorrect: The solicitation is being used
				"auditor1", "solicitation2", IllegalArgumentException.class
			}, {
				// Incorrect: The actor is not the owner of the solicitation
				"carrier2", "solicitation2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}
	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Solicitation solicitation;

		caught = null;
		try {
			solicitation = this.solicitationService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Solicitation solicitation;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			solicitation = this.solicitationService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String actor, final String category, final String comment, final String vehicle, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Solicitation solicitation = this.solicitationService.create();

			solicitation.setCategory(this.categoryService.findOne(super.getEntityId(category)));
			solicitation.getComments().add(comment);

			this.solicitationService.save(solicitation, this.getEntityId(vehicle));
			this.solicitationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
	protected void testSave(final String actor, String solicitationBean, String category, String comment, String startDate, String endDate, String status, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Solicitation solicitation = this.solicitationService.findOne(super.getEntityId(solicitationBean));

			Solicitation clon = (Solicitation) solicitation.clone();

			if (category != null) {
				clon.setCategory(this.categoryService.findOne(super.getEntityId(category)));
			}
			List<String> comm = new ArrayList<>(clon.getComments());
			comm.add(comment);
			clon.setComments(comm);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				if (startDate != null) {
					clon.setStartDate(sdf.parse(startDate));
				}
				if (endDate != null) {
					clon.setEndDate(sdf.parse(endDate));
				}
			} catch (final ParseException e) {
			}
			if (status != null) {
				clon.setStatus(status);
			}

			this.solicitationService.save(clon, null);
			this.solicitationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String actor, final String solicitationBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			final int id = super.getEntityId(solicitationBean);
			final Solicitation solicitation = this.solicitationService.findOne(id);

			this.solicitationService.delete(solicitation);
			this.solicitationService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
