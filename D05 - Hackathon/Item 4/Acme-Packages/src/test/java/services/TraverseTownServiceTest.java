
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Town;
import domain.TraverseTown;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TraverseTownServiceTest extends AbstractTest {

	@Autowired
	private TraverseTownService	traverseTownService;

	@Autowired
	private TownService			townService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<TraverseTown> traverseTowns;
		traverseTowns = this.traverseTownService.findAll();
		Assert.notNull(traverseTowns);
	}

	/*
	 * Requirement tested: The actors of the system can see the traverse towns of an offer
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real traverse towns id's and a non traverse town id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The traverseTown exists in the database
				super.getEntityId("traverseTown1"), null
			}, {
				// Correct: The traverseTown exists in the database
				super.getEntityId("traverseTown2"), null
			}, {
				// Correct: The traverseTown exists in the database
				super.getEntityId("traverseTown3"), null
			}, {
				// Incorrect: The id does not match any traverseTown in the database
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
	 * Requirement tested: An actor who is authenticated as a carrier must be able to create traverse towns
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier and a a non carrier user
	 */
	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The user is a carrier
				"carrier1", null
			}, {
				// Incorrect: The user is not a carrier
				"customer1", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"sponsor1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to create and save traverse towns
	 * Sentence coverage: 100%
	 * Data coverage: 25%, as we've tested 4 out of 16 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", 1, "2019/07/15", "town1", null
			}, {
				// Incorrect: The user is not a carrier
				"customer1", 1, "2019/07/15", "town1", IllegalArgumentException.class
			}, {
				// Incorrect: The estimated date cannot be null
				"carrier1", 1, null, "town1", ConstraintViolationException.class
			}, {
				// Incorrect: The town is not valid
				"carrier1", 1, "2019/07/15", "fare3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	/*
	 * Requirement tested: An authenticated carrier must be able to modify his or her traverse towns
	 * Sentence coverage: 100%
	 * Data coverage: 15.6%, as we've tested 5 out of 32 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "traverseTown1", 1, "2019/07/15", "town1", null
			}, {
				// Incorrect: The user is not a carrier
				"customer1", "traverseTown1", 1, "2019/07/15", "town1", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not the owner
				"carrier2", "traverseTown1", 1, "2019/07/15", "town1", IllegalArgumentException.class
			}, {
				// Incorrect: The estimated date cannot be null
				"carrier1", "traverseTown1", 1, null, "town1", ConstraintViolationException.class
			}, {
				// Incorrect: The town is not valid
				"carrier1", "traverseTown1", 1, "2019/07/15", "fare3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	/*
	 * Requirement tested: Carriers must be able to delete their traverse towns
	 * Sentence coverage: 98.4%
	 * Data coverage: 100%, as we test the method using the carrier owner and other users
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Incorrect: The user is not a carrier
				"admin", "town1", IllegalArgumentException.class
			}, {
				// Correct: The carrier can delete his traverse town
				"carrier1", "traverseTown1", null
			}, {
				// Incorrect: The user is not the owner
				"carrier", "traverseTown1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		TraverseTown traverseTown;

		caught = null;
		try {
			traverseTown = this.traverseTownService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final TraverseTown traverseTown;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			traverseTown = this.traverseTownService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(String carrier, Integer number, String estimatedDate, String townBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Town town = this.townService.findOne(super.getEntityId(townBean));

			TraverseTown traverseTown = this.traverseTownService.create();

			traverseTown.setNumber(number);
			traverseTown.setTown(town);

			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (estimatedDate != null) {
				traverseTown.setEstimatedDate(sdf.parse(estimatedDate));
			}

			this.traverseTownService.save(traverseTown);
			this.traverseTownService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(String carrier, String traverseTownBean, Integer number, String estimatedDate, String townBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			TraverseTown traverseTown = this.traverseTownService.findOne(super.getEntityId(traverseTownBean));

			TraverseTown clon = (TraverseTown) traverseTown.clone();

			Town town = this.townService.findOne(super.getEntityId(townBean));

			clon.setNumber(number);
			clon.setTown(town);

			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (estimatedDate != null) {
				clon.setEstimatedDate(sdf.parse(estimatedDate));
			} else {
				clon.setEstimatedDate(null);
			}

			traverseTown = clon;

			this.traverseTownService.save(traverseTown);
			this.traverseTownService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String traverseTownBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			final int id = super.getEntityId(traverseTownBean);
			final TraverseTown traverseTown = this.traverseTownService.findOne(id);

			this.traverseTownService.delete(traverseTown);
			this.traverseTownService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
