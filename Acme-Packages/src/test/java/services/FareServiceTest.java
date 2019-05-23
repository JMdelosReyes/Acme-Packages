
package services;

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
import domain.Fare;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FareServiceTest extends AbstractTest {

	@Autowired
	private FareService	fareService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Fare> fares;
		fares = this.fareService.findAll();
		Assert.notNull(fares);
	}

	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The fare exists in the database
				super.getEntityId("fare1"), null
			}, {
				// Correct: The fare exists in the database
				super.getEntityId("fare2"), null
			}, {
				// Correct: The fare exists in the database
				super.getEntityId("fare3"), null
			}, {
				// Incorrect: The id does not match any fare in the database
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

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				// Correct: The user is a carrier
				"carrier1", null
			}, {
				// Incorrect: The user is not a carrier
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", 0., 5., 0., 3., 5.95, null
			}, {
				// Incorrect: The user must be a carrier
				"admin", 0., 5., 0., 3., 5.95, IllegalArgumentException.class
			}, {
				// Incorrect: The minimum weight cannot be lower than 0
				"carrier1", -1., 5., 0., 3., 5.95, ConstraintViolationException.class
			}, {
				// Incorrect: The minimum volume cannot be lower than 0
				"carrier1", 0., 5., -1., 3., 5.95, ConstraintViolationException.class
			}, {
				// Incorrect: The price cannot be lower than 1
				"carrier1", 0., 5., 0., 3., 0., ConstraintViolationException.class
			}, {
				// Incorrect: The maximum weight cannot be lower than the minimum weight
				"carrier1", 2., 1., 0., 3., 5.95, IllegalArgumentException.class
			}, {
				// Incorrect: The maximum volume cannot be lower than the minimum volume
				"carrier1", 2., 3., 2., 1., 5.95, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (Double) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "fare13", 0., 5., 0., 3., 5.95, null
			}, {
				// Incorrect: The fare is being used
				"carrier1", "fare11", 0., 5., 0., 3., 5.95, IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner of the fare
				"carrier2", "fare13", 0., 5., 0., 3., 5.95, IllegalArgumentException.class
			}, {
				// Incorrect: The minimum weight cannot be lower than 0
				"carrier1", "fare13", -1., 5., 0., 3., 5.95, ConstraintViolationException.class
			}, {
				// Incorrect: The minimum volume cannot be lower than 0
				"carrier1", "fare13", 0., 5., -1., 3., 5.95, ConstraintViolationException.class
			}, {
				// Incorrect: The price cannot be lower than 1
				"carrier1", "fare13", 0., 5., 0., 3., 0., ConstraintViolationException.class
			}, {
				// Incorrect: The maximum weight cannot be lower than the minimum weight
				"carrier1", "fare13", 2., 1., 0., 3., 5.95, IllegalArgumentException.class
			}, {
				// Incorrect: The maximum volume cannot be lower than the minimum volume
				"carrier1", "fare13", 2., 3., 2., 1., 5.95, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (Class<?>) testingData[i][7]);
		}
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: The carrier can delete his fare
				"carrier1", "fare13", null
			}, {
				// Incorrect: The fare is being used
				"carrier1", "fare1", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner of the fare
				"carrier2", "fare13", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Fare fare;

		caught = null;
		try {
			fare = this.fareService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Fare fare;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			fare = this.fareService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String carrier, final Double minWeight, final Double maxWeight, Double minVolume, Double maxVolume, Double price, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Fare fare = this.fareService.create();

			fare.setMinWeight(minWeight);
			fare.setMaxWeight(maxWeight);
			fare.setMinVolume(minVolume);
			fare.setMaxVolume(maxVolume);
			fare.setPrice(price);

			this.fareService.save(fare);
			this.fareService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String carrier, String fareBean, final Double minWeight, final Double maxWeight, Double minVolume, Double maxVolume, Double price, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Fare fare = this.fareService.findOne(super.getEntityId(fareBean));

			Fare clon = (Fare) fare.clone();

			clon.setMinWeight(minWeight);
			clon.setMaxWeight(maxWeight);
			clon.setMinVolume(minVolume);
			clon.setMaxVolume(maxVolume);
			clon.setPrice(price);

			fare = clon;

			this.fareService.save(fare);
			this.fareService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String fareBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			final int id = super.getEntityId(fareBean);
			final Fare fare = this.fareService.findOne(id);

			this.fareService.delete(fare);
			this.fareService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
