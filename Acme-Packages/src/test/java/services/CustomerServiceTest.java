
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
import domain.CreditCard;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService	cusService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Customer> customers;
		customers = this.cusService.findAll();
		Assert.notNull(customers);
	}
	/*
	 * Requirement tested: An actor who is not authenticated must be able to create a customer account
	 * Sentence coverage: 100%
	 * Data coverage: 100% as we have tested valid and invalid ids
	 */
	@Test
	public void driverFindOne() {
		Object testingData[][] = {
			{
				// Correct: The customer exists in the database
				super.getEntityId("customer1"), null
			}, {
				// Correct: The customer exists in the database
				super.getEntityId("customer2"), null
			}, {
				// Correct: The customer exists in the database
				super.getEntityId("customer3"), null
			}, {
				// Incorrect: The id does not match any customer in the database
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
	protected void testFindOne(int id, Class<?> expected) {
		Class<?> caught;
		Customer customer;

		caught = null;
		try {
			customer = this.cusService.findOne(id);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Requirement tested: An actor who is not authenticated must be able to register to the system as a customer
	 * Sentence coverage: 73.1%
	 * Data coverage: 0.045%, as we have tested 8 of 17496 possibilities
	 */

	@Test
	public void driverCreateAndSave() {
		Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer2000", "customer2000", 102, "Nombre", "4495840606545807", "VISA", 1, 90, null
			}, {
				// Incorrect: The user name exists already
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer2001", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The surname cannot be blank
				"name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The photo must be an URL
				"name", "surname", "middlename", "photo", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The email is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The user account is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "asd", "asd", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The credit card is not valid
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "carrier19", "password", 102, "Nombre", "4495840606545807", "MAAAAL", 1, 90, IllegalArgumentException.class

			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(Class<?>) testingData[i][15]);
		}
	}

	protected void testCreateAndSave(String name, String surname, String middlename, String photo, String email, String phone, String address, String user, String password, int cvv, String holderName, String number, String make, int expirationMonth,
		int expirationYear, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.unauthenticate();

			Customer customer = this.cusService.create();

			CreditCard credit = new CreditCard();
			credit.setCvv(cvv);
			credit.setExpirationMonth(expirationMonth);
			credit.setExpirationYear(expirationYear);
			credit.setHolderName(holderName);
			credit.setMake(make);
			credit.setNumber(number);

			//Actor things
			customer.setName(name);
			customer.setSurname(surname);
			customer.setMiddleName(middlename);
			customer.setCreditCard(credit);
			customer.setEmail(email);
			customer.setPhoto(photo);
			customer.setPhoneNumber(phone);
			customer.setAddress(address);

			//Account
			customer.getUserAccount().setUsername(user);
			customer.getUserAccount().setPassword(password);

			this.cusService.save(customer);
			this.cusService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Requirement tested: An authenticated customer must be able to modify his or her profile
	 * Sentence coverage: 73.1%
	 * Data coverage: 0.051%, as we have tested 9 of 17496 possibilities
	 */
	@Test
	public void driverSave() {
		Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"customer1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, null
			}, {
				// Incorrect: The user name cannot be changed
				"customer1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer3", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The name cannot be blank
				"customer1", "", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The surname cannot be blank
				"customer1", "name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The photo must be an URL
				"customer1", "name", "surname", "middlename", "photo", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}, {
				// Incorrect: The email is not valid
				"customer1", "name", "surname", "middlename", "https://www.photo.com", "email", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The user account is not valid
				"customer1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "asd", "asd", 102, "Nombre", "4495840606545807", "VISA", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The credit card is not valid
				"customer1", "name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "MAAAAL", 1, 90, IllegalArgumentException.class
			}, {
				// Incorrect: The vat number is not valid
				"customer1", "name", "", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "customer1", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (int) testingData[i][14], (int) testingData[i][15],
				(Class<?>) testingData[i][16]);
		}
	}

	protected void testSave(String bean, String name, String surname, String middlename, String photo, String email, String phone, String address, String user, String password, int cvv, String holderName, String number, String make, int expirationMonth,
		int expirationYear, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			Customer customer = this.cusService.findOne(super.getEntityId(bean));

			Customer clon = (Customer) customer.clone();

			CreditCard credit = new CreditCard();
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

			if (!customer.getUserAccount().getUsername().equals(user)) {
				UserAccount ua = customer.getUserAccount();
				Authority auth = new Authority();
				auth.setAuthority(Authority.CUSTOMER);
				ua.addAuthority(auth);
				ua.setUsername(user);
				ua.setPassword(password);

				//Account
				clon.setUserAccount(ua);
			}

			customer = clon;

			this.cusService.save(customer);
			this.cusService.flush();

			super.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Requirement tested: A customer must be able to delete
	 * Sentence coverage: 97.6%
	 * Data coverage: 100%, as we test the method using a customer and a non customer user
	 */
	@Test
	public void driverDelete() {
		Object testingData[][] = {
			{
				// Correct: A customer can delete his profile
				"customer1", "customer1", null
			}, {
				// Correct: A customer can delete his profile
				"customer2", "customer2", null
			}, {
				// Correct: A customer can delete his profile
				"customer3", "customer3", null
			}, {
				// Incorrect: No actor can delete other's actor profile
				"customer1", "customer2", IllegalArgumentException.class
			}, {
				// Incorrect: No actor can delete other's actor profile
				"carrier1", "customer2", IllegalArgumentException.class
			}, {
				// Incorrect: No actor can delete other's actor profile
				"sponsor1", "customer2", IllegalArgumentException.class
			}, {
				// Incorrect: No actor can delete other's actor profile
				"admin", "customer2", IllegalArgumentException.class
			}, {
				// Incorrect: No actor can delete other's actor profile
				"auditor1", "customer2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void testDelete(String bean, String cusBean, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			int id = super.getEntityId(cusBean);
			Customer comp = this.cusService.findOne(id);

			this.cusService.delete(comp);
			this.cusService.flush();
			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
