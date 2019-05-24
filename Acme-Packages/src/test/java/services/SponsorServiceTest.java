
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
import domain.CreditCard;
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Sponsor> sponsors;
		sponsors = this.sponsorService.findAll();
		Assert.notNull(sponsors);
	}
	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as we tested finding sponsor and non sponsor data in our database
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The sponsor exists in the database
				super.getEntityId("sponsor1"), null
			}, {
				// Correct: The sponsor exists in the database
				super.getEntityId("sponsor2"), null
			}, {
				// Correct: The sponsor exists in the database
				super.getEntityId("sponsor3"), null
			}, {
				// Incorrect: The id does not match any sponsor in the database
				super.getEntityId("curriculum1"), IllegalArgumentException.class
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
	 * Sentence coverage: 91,4%
	 * Data coverage: 0,2% as we testes 6 out of 256 possibilities
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// All the parameters are OK
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "sponsor69", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "10709279L", null
			}, {
				// NIF cannot be blank
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "sponsor69", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "", ConstraintViolationException.class
			}, {
				// NIF doen´t match pattern
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "sponsor69", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "wadawdawd", ConstraintViolationException.class
			}, {
				//String Parameters cannot be blank
				"", "", "middlename", "", "email@gmail.com", "123", "", "", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 90, "wadawdawd", ConstraintViolationException.class
			}, {
				// Credit card mut be valid /not expired
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "sponsor69", "password", 102, "Nombre", "", "VISA", 1, 2, "wadawdawd", IllegalArgumentException.class
			}, {
				// Credit card cannot be expired
				"name", "surname", "middlename", "https://www.photo.com", "email@gmail.com", "123456789", "address", "sponsor69", "password", 102, "Nombre", "4495840606545807", "VISA", 1, 2, "wadawdawd", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as an sponsor must be able to manage his personal data
	 * Sentence coverage: 84,2%
	 * Data coverage: 1,56%, as we've tested 4 out of 256 possible combinations
	 */

	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				// Normal update
				"sponsor1", "sponsor1", "nameupdate", "surnameupdate", "middleNameUpdate", "https://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", "46018584T", null
			}, {
				// NIF cannot be changed
				"sponsor2", "sponsor2", "nameupdate", "surnameupdate", "middleNameUpdate", "https://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", "46018584T", IllegalArgumentException.class
			}, {
				// Carrier cannot update other sponsor
				"carrier1", "sponsor2", "nameupdate", "surnameupdate", "middleNameUpdate", "https://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", "46018584T", IllegalArgumentException.class
			}, {
				// Name cannot be blank and photo mus be an url
				"sponsor2", "sponsor2", "", "", "", "ht://www.photo.com", "email@gmail.com", "123456789", "address", "usuario", "contraseñaaaa", "46038584T", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
		}
	}

	/*
	 * Requirement tested:Only the sponsor who owns the account can delete it
	 * Sentence coverage: 100%
	 * Data coverage: 100%,As we try to remove proprietary sponsors from their own account, and try to delete the sponsor account without being authenticated as sponsor
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{	//Correct: Auditor is deleted from database
				"sponsor1", null
			}, {//Correct: Auditor is deleted from database
				"sponsor2", null
			}, {//Correct: Auditor is deleted from database
				"sponsor3", null
			}, {//Incorrect: Bean must be a Auditor
				"customer1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Sponsor sponsor;

		caught = null;
		try {
			sponsor = this.sponsorService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String name, final String surname, final String middlename, final String photo, final String email, final String phone, final String address, final String user, final String password, final int cvv,
		final String holderName, final String number, final String make, final int expirationMonth, final int expirationYear, final String nif, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.unauthenticate();

			Sponsor sponsor = this.sponsorService.create();

			final CreditCard credit = new CreditCard();
			credit.setCvv(cvv);
			credit.setExpirationMonth(expirationMonth);
			credit.setExpirationYear(expirationYear);
			credit.setHolderName(holderName);
			credit.setMake(make);
			credit.setNumber(number);

			//Actor things
			sponsor.setName(name);
			sponsor.setSurname(surname);
			sponsor.setMiddleName(middlename);
			sponsor.setCreditCard(credit);
			sponsor.setEmail(email);
			sponsor.setPhoto(photo);
			sponsor.setPhoneNumber(phone);
			sponsor.setAddress(address);

			// Sponsor things
			sponsor.setNif(nif);

			//Account
			sponsor.getUserAccount().setUsername(user);
			sponsor.getUserAccount().setPassword(password);

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String sponsorBean, final String sponsorEditBean, final String name, final String surname, final String middleName, final String photo, final String email, final String phone, final String adress, final String user,
		final String password, final String nif, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(sponsorBean);

			Sponsor sponsor = this.sponsorService.findOne(super.getEntityId(sponsorEditBean));
			final Sponsor clon = (Sponsor) sponsor.clone();

			final List<String> surnames = new ArrayList<>();
			surnames.add(surname);
			//Actor things
			clon.setName(name);
			clon.setSurname(surname);
			clon.setNif(nif);
			clon.setEmail(email);
			clon.setPhoto(photo);
			clon.setPhoneNumber(phone);
			clon.setAddress(adress);

			//Account
			clon.getUserAccount().setUsername(user);
			clon.getUserAccount().setPassword(password);

			sponsor = clon;

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String bean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(bean);

			final Sponsor ad = this.sponsorService.findOne(super.getEntityId(bean));

			this.sponsorService.delete(ad);
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
