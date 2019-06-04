
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

import security.Authority;
import security.UserAccount;
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

	/*
	 * Requirement tested: The actors of the system can see the profile of any carrier
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real carriers id's and a non carrier id
	 */
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

	/*
	 * Requirement tested: An actor who is not authenticated must be able to create a carrier account
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a logged and a a non logged user
	 */
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

	/*
	 * Requirement tested: An actor who is not authenticated must be able to register to the system as a carrier
	 * Sentence coverage: 96%
	 * Data coverage: 3.5%, as we've tested 9 out of 256 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier89", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", null
			}, {
				// Incorrect: The user name exists already
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The surname cannot be blank
				"name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The photo must be an URL
				"name", "surname", "middlename", "photo", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The email is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The user account is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "asd", "asd", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The credit card is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "MAAAAL", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The vat number is not valid
				"name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "asdsa89789", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
		}
	}

	/*
	 * Requirement tested: An authenticated carrier must be able to modify his or her profile
	 * Sentence coverage: 96%
	 * Data coverage: 1.75%, as we've tested 9 out of 512 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", null
			}, {
				// Incorrect: The user name cannot be changed
				"carrier1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier8", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"carrier1", "", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The surname cannot be blank
				"carrier1", "name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The photo must be an URL
				"carrier1", "name", "surname", "middlename", "photo", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", ConstraintViolationException.class
			}, {
				// Incorrect: The email is not valid
				"carrier1", "name", "surname", "middlename", "https://www.photo.com", "email", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The user account is not valid
				"carrier1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "asd", "asd", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The credit card is not valid
				"carrier1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "MAAAAL", 1, 90, "CZ62321254", IllegalArgumentException.class
			}, {
				// Incorrect: The vat number is not valid
				"carrier1", "name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "asdsa89789", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (int) testingData[i][14], (int) testingData[i][15],
				(String) testingData[i][16], (Class<?>) testingData[i][17]);
		}
	}

	/*
	 * Requirement tested: Every actor of the system must be able to delete his or her account
	 * Sentence coverage: 96.6%
	 * Data coverage: 100%, as we test the method using a carrier and a non carrier user
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: A carrier can delete his profile
				"carrier1", "carrier1", null
			}, {
				// Incorrect: No actor can delete other's actor profile
				"customer1", "carrier2", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"customer1", "customer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
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

	protected void testSave(String bean, final String name, final String surname, final String middlename, final String photo, final String email, final String phone, final String address, final String user, final String password, final int cvv,
		final String holderName, final String number, final String make, final int expirationMonth, final int expirationYear, final String vat, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			Carrier carrier = this.carrierService.findOne(super.getEntityId(bean));

			Carrier clon = (Carrier) carrier.clone();

			final CreditCard credit = new CreditCard();
			credit.setCvv(cvv);
			credit.setExpirationMonth(expirationMonth);
			credit.setExpirationYear(expirationYear);
			credit.setHolderName(holderName);
			credit.setMake(make);
			credit.setNumber(number);

			//Actor things
			clon.setName(name);
			clon.setSurname(surname);
			clon.setMiddleName(middlename);
			clon.setCreditCard(credit);
			clon.setEmail(email);
			clon.setPhoto(photo);
			clon.setPhoneNumber(phone);
			clon.setAddress(address);

			// Carrier things
			clon.setVat(vat);

			if (!carrier.getUserAccount().getUsername().equals(user)) {
				UserAccount ua = carrier.getUserAccount();
				Authority auth = new Authority();
				auth.setAuthority(Authority.CARRIER);
				ua.addAuthority(auth);
				ua.setUsername(user);
				ua.setPassword(password);

				//Account
				clon.setUserAccount(ua);
			}

			carrier = clon;

			this.carrierService.save(carrier);
			this.carrierService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String bean, final String carrierBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			final int id = super.getEntityId(carrierBean);
			final Carrier comp = this.carrierService.findOne(id);

			this.carrierService.delete(comp);
			this.carrierService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
