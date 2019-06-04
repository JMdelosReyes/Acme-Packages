
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Town;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TownServiceTest extends AbstractTest {

	@Autowired
	private TownService	townService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Town> towns;
		towns = this.townService.findAll();
		Assert.notNull(towns);
	}

	/*
	 * Requirement tested: The actors of the system can see the available towns
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real town id's and a non town id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The town exists in the database
				super.getEntityId("town1"), null
			}, {
				// Correct: The town exists in the database
				super.getEntityId("town2"), null
			}, {
				// Correct: The town exists in the database
				super.getEntityId("town3"), null
			}, {
				// Incorrect: The id does not match any town in the database
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
	 * Requirement tested: Administrators can create towns
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with an administrator and a a non administrator user
	 */
	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The user is an administrator
				"admin", null
			}, {
				// Incorrect: The user is not an administrator
				"carrier1", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not an administrator
				"customer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: Administrators can create and save towns
	 * Sentence coverage: 100%
	 * Data coverage: 25%, as we've tested 6 out of 24 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"admin", "Sevilla", "41001", "Sevilla", null
			}, {
				// Incorrect: The user is not an administrator
				"carrier1", "Sevilla", "41001", "Sevilla", IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"admin", "", "41001", "Sevilla", ConstraintViolationException.class
			}, {
				// Incorrect: The ZIP code cannot be blank
				"admin", "Sevilla", "", "Sevilla", ConstraintViolationException.class
			}, {
				// Incorrect: The ZIP code is already being used
				"admin", "Sevilla", "22001", "Sevilla", DataIntegrityViolationException.class
			}, {
				// Incorrect: The county cannot be blank
				"admin", "Sevilla", "41001", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	/*
	 * Requirement tested: Administrators can modify towns
	 * Sentence coverage: 100%
	 * Data coverage: 25%, as we've tested 6 out of 24 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"admin", "town1", "Sevilla", "41001", "Sevilla", null
			}, {
				// Incorrect: The user is not an administrator
				"carrier1", "town1", "Sevilla", "41001", "Sevilla", IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"admin", "town1", "", "41001", "Sevilla", ConstraintViolationException.class
			}, {
				// Incorrect: The ZIP code cannot be blank
				"admin", "town1", "Sevilla", "", "Sevilla", ConstraintViolationException.class
			}, {
				// Incorrect: The ZIP code is already being used
				"admin", "town1", "Sevilla", "45678", "Sevilla", DataIntegrityViolationException.class
			}, {
				// Incorrect: The county cannot be blank
				"admin", "town1", "Sevilla", "41001", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	/*
	 * Requirement tested: Administrators can delete towns
	 * Sentence coverage: 96.3%
	 * Data coverage: 100%, as we tried to delete a town that's being used and a non administrator user
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Incorrect: The town has been used
				"admin", "town1", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not an administrator
				"carrier1", "town1", IllegalArgumentException.class
			}, {
				// Correct: The administrator can delete towns
				"admin", "town10", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Town town;

		caught = null;
		try {
			town = this.townService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Town town;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			town = this.townService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(String admin, String name, String zipCode, String county, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(admin);

			Town town = this.townService.create();

			town.setName(name);
			town.setZipCode(zipCode);
			town.setCounty(county);

			this.townService.save(town);
			this.townService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(String admin, String townBean, String name, String zipCode, String county, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(admin);

			Town town = this.townService.findOne(super.getEntityId(townBean));

			Town clon = (Town) town.clone();

			clon.setName(name);
			clon.setZipCode(zipCode);
			clon.setCounty(county);

			town = clon;

			this.townService.save(town);
			this.townService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String townBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			final int id = super.getEntityId(townBean);
			final Town town = this.townService.findOne(id);

			this.townService.delete(town);
			this.townService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
