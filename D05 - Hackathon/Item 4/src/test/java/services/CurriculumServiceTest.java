
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
import domain.Carrier;
import domain.Curriculum;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private CarrierService		carrierService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Curriculum> curricula;
		curricula = this.curriculumService.findAll();
		Assert.notNull(curricula);
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to
	 * manage his or her curriculums, which includes listing and showing them
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real curriculums id's and a non curriculum id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The curriculum exists in the database
				super.getEntityId("curriculum1"), null
			}, {
				// Correct: The curriculum exists in the database
				super.getEntityId("curriculum2"), null
			}, {
				// Correct: The curriculum exists in the database
				super.getEntityId("curriculum3"), null
			}, {
				// Incorrect: The id does not match any curriculum in the database
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
	 * Requirement tested: An actor who is authenticated as a carrier must be able to
	 * manage his or her curriculums, which includes creating them
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier user and with non carrier users
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
				"company1", IllegalArgumentException.class
			}
		};
	}

	/*
	 * First part of the save method
	 * Requirement tested: An actor who is authenticated as a carrier must be able to
	 * manage his or her curriculums, which includes creating and saving them
	 * Sentence coverage: 100%
	 * Data coverage: 4%, as we've tested 7 out of 216 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Incorrect: The fullName must not be blank
				"carrier1", "", "666666666", "emil@gmail.com", "http://www.linkedin.com/photo", ConstraintViolationException.class
			}, {
				// Incorrect: The statement must not be blank
				"carrier1", "nombre", "666666666", "", "http://www.linkedin.com/photo", ConstraintViolationException.class
			}, {
				// Incorrect: The phoneNumber must not be blank
				"carrier1", "nombre", "", "emil@gmail.com", "http://www.linkedin.com/photo", ConstraintViolationException.class
			}, {
				// Incorrect: The GitHub profile must not be blank
				"carrier1", "", "666666666", "emil@gmail.com", "", ConstraintViolationException.class
			}, {
				// Incorrect: The LinkedIn profile must not be blank
				"carrier1", "", "666666666", "emil@gmail.com", "://www.linkedin.com/photo", ConstraintViolationException.class
			}, {
				// Incorrect: The user is not a carrier
				"sponsor1", "", "666666666", "emil@gmail.com", "http://www.linkedin.com/photo", IllegalArgumentException.class
			}, {
				// Correct: Normal create and save
				"carrier1", "fullname", "666666666", "emil@gmail.com", "http://www.linkedin.com/photo", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	/*
	 * Second part of the save method
	 * Requirement tested: An actor who is authenticated as a carrier must be able to
	 * manage his or her curriculums, which includes updating them
	 * Sentence coverage: 100%
	 * Data coverage: 2'47%, as we've tested 8 out of 324 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Incorrect: The fullName must not be blank
				"carrier1", "curriculum1", "", "http://www.github.com/photo", "666666666", "email@gmail.com", ConstraintViolationException.class
			}, {
				// Incorrect: The photo must not be blank
				"carrier1", "curriculum1", "fullName", "", "666666666", "email@gmail.com", ConstraintViolationException.class
			}, {
				// Incorrect: The phoneNumber must not be blank
				"carrier1", "curriculum1", "fullName", "http://www.github.com", "", "email@gmail.com", ConstraintViolationException.class
			}, {
				// Incorrect: The email must not be blank
				"carrier1", "curriculum1", "fullName", "http://www.github.com", "666666666", "", ConstraintViolationException.class
			}, {
				// Incorrect: The carrier is not the owner of the curriculum
				"carrier1", "curriculum2", "fullName", "http://www.github.com", "666666666", "email@gmail.com", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"sponsor1", "curriculum1", "", "http://www.github.com", "666666666", "email@gmail.com", IllegalArgumentException.class
			}, {
				// Correct: Normal update
				"carrier1", "curriculum1", "fullName", "http://www.github.com", "666666666", "email@gmail.com", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to
	 * manage his or her curriculums, which includes deleting them
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we test the method using a carrier with more than one curriculum,
	 * one with just one curriculum and with a non carrier user. We've tried also to delete a curriculum
	 * owned by another carrier
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: This carrier owns more than one curriculum
				"carrier1", "curriculum1", null
			}, {
				// Incorrect: The carrier is not the owner of the curriculum
				"carrier1", "curriculum2", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner of the curriculum
				"sponsor1", "curriculum2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Curriculum curriculum;

		caught = null;
		try {
			curriculum = this.curriculumService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Curriculum curriculum;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);
			curriculum = this.curriculumService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String bean, final String fullName, final String phoneNumber, final String email, final String photo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);
			final Curriculum curriculum = this.curriculumService.create();

			curriculum.setFullName(fullName);
			curriculum.setPhoneNumber(phoneNumber);
			curriculum.setPhoto(photo);
			curriculum.setEmail(email);

			this.curriculumService.save(curriculum);
			this.curriculumService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String bean, final String curr, final String fullName, final String photo, final String phoneNumber, final String email, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			Curriculum curriculum = this.curriculumService.findOne(super.getEntityId(curr));
			final Curriculum clon = (Curriculum) curriculum.clone();

			clon.setFullName(fullName);
			clon.setPhoneNumber(phoneNumber);
			clon.setEmail(email);
			clon.setPhoto(photo);

			curriculum = clon;

			this.curriculumService.save(curriculum);
			this.curriculumService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String curr, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(carrier);
			final Curriculum curriculum = this.curriculumService.findOne(super.getEntityId(curr));
			this.curriculumService.delete(curriculum);
			this.curriculumService.flush();
			final Carrier carr = this.carrierService.findOne(super.getEntityId(carrier));
			Assert.isTrue(!carr.getCurricula().contains(curriculum));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
