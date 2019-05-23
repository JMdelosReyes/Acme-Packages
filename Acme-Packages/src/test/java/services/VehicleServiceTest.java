
package services;

import java.util.Arrays;
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
import domain.Vehicle;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VehicleServiceTest extends AbstractTest {

	@Autowired
	private VehicleService	vehicleService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Vehicle> vehicles;
		vehicles = this.vehicleService.findAll();
		Assert.notNull(vehicles);
	}

	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The vehicle exists in the database
				super.getEntityId("vehicle1"), null
			}, {
				// Correct: The vehicle exists in the database
				super.getEntityId("vehicle2"), null
			}, {
				// Correct: The vehicle exists in the database
				super.getEntityId("vehicle3"), null
			}, {
				// Incorrect: The id does not match any vehicle in the database
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
				"carrier1", "VAN", "4567ASD", 500., 3000., "http://asd.com,http://asd2.com", "comment", null
			}, {
				// Incorrect: The user is not a carrier
				"admin", "VAN", "4567ASD", 500., 3000., "http://asd.com,http://asd2.com", "comment", IllegalArgumentException.class
			}, {
				// Incorrect: The type is not correct
				"carrier1", "furgo", "4567ASD", 500., 3000., "http://asd.com,http://asd2.com", "comment", ConstraintViolationException.class
			}, {
				// Incorrect: The plate cannot be blank
				"carrier1", "VAN", "", 500., 3000., "http://asd.com,http://asd2.com", "comment", ConstraintViolationException.class
			}, {
				// Incorrect: The maximum volume cannot be lower than 1
				"carrier1", "VAN", "4567ASD", 0., 3000., "http://asd.com,http://asd2.com", "comment", ConstraintViolationException.class
			}, {
				// Incorrect: The maximum weight cannot be lower than 1
				"carrier1", "VAN", "4567ASD", 500., 0., "http://asd.com,http://asd2.com", "comment", ConstraintViolationException.class
			}, {
				// Incorrect: The pictures must be URLs
				"carrier1", "VAN", "4567ASD", 500., 3000., "pic1,pic2", "comment", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
		}
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: The carrier can delete his vehicle
				"carrier1", "vehicle2", null
			}, {
				// Incorrect: The vehicle is being used or is going to be
				"carrier1", "vehicle1", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner of the vehicle
				"carrier2", "vehicle2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Vehicle vehicle;

		caught = null;
		try {
			vehicle = this.vehicleService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Vehicle vehicle;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			vehicle = this.vehicleService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(String carrier, String type, String plate, Double maxVolume, Double maxWeight, String pictures, String comment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Vehicle vehicle = this.vehicleService.create();

			String[] pics = pictures.split(",");
			List<String> photos = Arrays.asList(pics);

			vehicle.setComment(comment);
			vehicle.setMaxVolume(maxVolume);
			vehicle.setMaxWeight(maxWeight);
			vehicle.setPictures(photos);
			vehicle.setPlate(plate);
			vehicle.setType(type);

			this.vehicleService.save(vehicle);
			this.vehicleService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String vehicleBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			final int id = super.getEntityId(vehicleBean);
			final Vehicle vehicle = this.vehicleService.findOne(id);

			this.vehicleService.delete(vehicle);
			this.vehicleService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
