
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Carrier;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CarrierServiceTest extends AbstractTest {

	@Autowired
	private CarrierService	carrierService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Carrier> carriers;
		carriers = this.carrierService.findAll();
		Assert.notNull(carriers);
	}

	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The carrier exists in the database
				super.getEntityId("carrier1"), null
			}, {
				// Correct: The carrier exists in the database
				super.getEntityId("carrier2"), null
			}, {
				// Correct: The carrier exists in the database
				super.getEntityId("carrier3"), null
			}, {
				// Incorrect: The id does not match any carrier in the database
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
				// Correct: The user cannot be logged
				"", null
			}, {
				// Incorrect: The user is logged
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
				// All the parameters are OK
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier89", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Carrier carrier;

		caught = null;
		try {
			carrier = this.carrierService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Carrier carrier;

		caught = null;
		try {
			super.startTransaction();
			if (!bean.equals("")) {
				super.authenticate(bean);
			}
			carrier = this.carrierService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String name, final String surname, final String middlename, final String photo, final String email, final String phone, final String address, final String user, final String password, final int cvv,
		final String holderName, final String number, final String make, final int expirationMonth, final int expirationYear, final String vat, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.unauthenticate();

			Carrier carrier = this.carrierService.create();

			final CreditCard credit = new CreditCard();
			credit.setCvv(cvv);
			credit.setExpirationMonth(expirationMonth);
			credit.setExpirationYear(expirationYear);
			credit.setHolderName(holderName);
			credit.setMake(make);
			credit.setNumber(number);

			//Actor things
			carrier.setName(name);
			carrier.setSurname(surname);
			carrier.setMiddleName(middlename);
			carrier.setCreditCard(credit);
			carrier.setEmail(email);
			carrier.setPhoto(photo);
			carrier.setPhoneNumber(phone);
			carrier.setAddress(address);

			// Carrier things
			carrier.setVat(vat);

			//Account
			carrier.getUserAccount().setUsername(user);
			carrier.getUserAccount().setPassword(password);

			this.carrierService.save(carrier);
			this.carrierService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
