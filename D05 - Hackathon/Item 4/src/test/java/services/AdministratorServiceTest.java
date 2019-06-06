
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
import domain.Administrator;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	AdministratorService	adminservice;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Administrator> admins;
		admins = this.adminservice.findAll();
		Assert.notNull(admins);
	}
	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), one real admin id's and a non admin id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Incorrect: The id must be higher than zero
				0, IllegalArgumentException.class
			}, {
				// Correct: Administrator exists in the database
				super.getEntityId("administrator"), null
			}, {
				// Incorrect: The id does not match any auditor in the database
				super.getEntityId("carrier1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as an admin must be able to Create user accounts for new admin
	 * Sentence coverage: 98.8%
	 * Data coverage: 1,18%, as we've tested 3 out of 254 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct
				"AdminTest", "nombre", "https://www.photo.es/pin/73063873952015806/", "auditor@gmail.com", "ATU00000079", "6186169876", "direccion", "UsernameTest", "pass1234", "admin2", null
			}, {
				//Inorrect: Authenticate wrong pass 
				"AdminTest", "nombre", "https://www.photo.es/pin/73063873952015806/", "auditor@gmail.com", "ATU00000079", "6186169876", "direccion", "UsernameTest", "", "admin3", IllegalArgumentException.class
			}, {
				//Incorrect: Username is already  used
				"admin", "del valle", "https://www.javo13.com", "email@loko.es", "ATU00000079", "618616666", "calle los pajaritos 23", "admin", "passlargaa22a", "admin", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as an admin must be able to manage his personal data
	 * Sentence coverage: 98.8%
	 * Data coverage: 1,18%, as we've tested 3 out of 254 possible combinations
	 */

	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				// Normal update
				"admin", "administrator", "nameupdate", "surnameupdate", "CZ62321254", "https://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", null
			}, {
				//Incorrect: fields cannot be blank
				"admin", "administrator", "", "", "CZ62321254", "https://www.photo.com", "email@gmail.com", "1234569", "address", "usuario", "contraseñaaaa", ConstraintViolationException.class
			}, {
				//Incorrect: cannot update other auditor
				"admin", "auditor2", "nameUpdate", "surnameupdate", "CZ62321254", "https://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
		}
	}

	/*
	 * Requirement tested:Only the admin who owns the account can delete it
	 * Sentence coverage: 95.3%
	 * Data coverage: 20%,As we try to remove proprietary admin from their own account, and try to delete the admin account with other actor
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{	//Correct: Auditor is deleted from database
				"admin", "administrator", null
			}, {//Incorrect: Bean must be a admin
				"carrier1", "administrator", IllegalArgumentException.class
			}, {//Incorrect: Bean must be a admin
				"auditor1", "administrator", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	//TEST METHODS

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Administrator ad;

		caught = null;
		try {
			ad = this.adminservice.findOne(id);
			Assert.notNull(ad);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String name, final String surname, final String photo, final String email, final String vatNumber, final String phonenumber, final String address, final String username, final String password, final String bean,
		final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			super.authenticate("admin");
			this.adminservice.flush();

			Administrator ad = this.adminservice.create();

			final Administrator clon = (Administrator) ad.clone();

			final CreditCard credit = new CreditCard();
			credit.setCvv(100);
			credit.setExpirationMonth(9);
			credit.setExpirationYear(90);
			credit.setHolderName("Test");
			credit.setMake("VISA");
			credit.setNumber("4185204688002721");

			//Actor things
			//Actor things
			clon.setName(name);
			clon.setSurname(surname);
			clon.setCreditCard(credit);
			clon.setEmail(email);
			clon.setPhoto(photo);
			clon.setPhoneNumber(phonenumber);
			clon.setAddress(address);
			clon.setCreditCard(credit);

			//Account
			clon.getUserAccount().setUsername(username);
			clon.getUserAccount().setPassword(password);

			ad = clon;

			this.adminservice.save(ad);
			this.adminservice.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String adminBean, final String adminEditBean, final String name, final String surname, final String vat, final String photo, final String email, final String phone, final String adress, final String user,
		final String password, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(adminBean);

			Administrator admin = this.adminservice.findOne(super.getEntityId(adminEditBean));
			final Administrator clon = (Administrator) admin.clone();

			//Actor things
			clon.setName(name);
			clon.setSurname(surname);

			clon.setEmail(email);
			clon.setPhoto(photo);
			clon.setPhoneNumber(phone);
			clon.setAddress(adress);

			//Account
			clon.getUserAccount().setUsername(user);
			clon.getUserAccount().setPassword(password);

			admin = clon;

			this.adminservice.save(admin);
			this.adminservice.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String bean, final String adminBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			final int id = super.getEntityId(adminBean);
			final Administrator admin = this.adminservice.findOne(id);

			this.adminservice.delete(admin);
			this.adminservice.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}

//Para ver el validation que falla
//			final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//			final Validator validator = factory.getValidator();
//
//			final Set<ConstraintViolation<Auditor>> constraintViolations = validator.validate(bro);
//
//			if (constraintViolations.size() > 0) {
//				System.out.println("Constraint Violations occurred..");
//				for (final ConstraintViolation<Auditor> contraints : constraintViolations) {
//					System.out.println(contraints.getRootBeanClass().getSimpleName() + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
//				}
//			}
