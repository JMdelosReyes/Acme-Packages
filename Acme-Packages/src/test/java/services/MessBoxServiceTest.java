
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.MessBox;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessBoxServiceTest extends AbstractTest {

	@Autowired
	private MessBoxService	messageBoxService;

	@Autowired
	private ActorService	actorService;


	/*
	 * Requirement tested: An actor can be able to create a new mess box in database
	 * Sentence coverage: 86.3%
	 * Data coverage: 36.666% as we've tested 11 out of 30 possible combinations
	 */
	@Test
	public void createSaveDriver() {
		final Object testingData[][] = {
			{	//Correct:It is saved successfully(customer) without parent
				"customer1", null, "messbox sin padre", null
			}, {//Correct:It is saved successfully(customer) with parent
				"customer1", null, "messbox con padre", "In Box"
			}, {//Correct:It is saved successfully (carrier) without parent
				"carrier1", null, "messbox sin padre", null
			}, {//Correct:It is saved successfully(carrier) with parent
				"carrier1", null, "messbox con padre", "In Box"
			}, {//Correct: It is saved successfully (Admin) without parent
				"admin", null, "messbox sin padre", null
			}, {//Correct: It is saved successfully(Admin) with parent
				"admin", null, "messbox con padre", "In Box"
			}, {//Correct: It is saved successfully (Sponsor) without parent
				"sponsor1", null, "messbox sin padre", null
			}, {//Correct: It is saved successfully(Sponsor) with parent
				"sponsor1", null, "messbox con padre", "In Box"
			}, {//Correct: It is saved successfully (auditor1) without parent
				"auditor1", null, "messbox sin padre", null
			}, {//Correct: It is saved successfully(auditor1) with parent
				"auditor1", null, "messbox con padre", "In Box"
			}, {//Incorrect: Needs somebody authenticated to save a messBox
				"", IllegalArgumentException.class, "messbox con padre", "In Box"
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateSaveMessBox((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
		}
	}
	protected void testCreateSaveMessBox(final String username, final Class<?> expected, final String name, final String parent) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			final UserAccount principal = LoginService.getPrincipal();
			final Actor a = this.actorService.findByUserAccountId(principal.getId());
			final MessBox result = this.messageBoxService.create();
			result.setName(name);
			if (parent != null) {
				final MessBox parentBox = this.messageBoxService.findMessageBoxByNameAndActorId(parent, a.getId());
				result.setParent(parentBox);
			}
			this.messageBoxService.save(result);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);

	}
	/*
	 * Requirement tested: An actor can create and update a mess box
	 * Sentence coverage: 86.3%
	 * Data coverage: 36.666% as we've tested 11 out of 30 possible combinations
	 */
	@Test
	public void createUpdateDriver() {
		final Object testingData[][] = {
			{	//Correct: It is saved successfully(customer)
				"customer1", null, "messbox sin padre", null, "Custom 1"
			}, {//Correct: It is saved successfully with parent folder(customer)
				"customer1", null, "messbox con padre", "In Box", "Custom 1"
			}, {//Correct: It is saved successfully (carrier)
				"carrier1", null, "messbox sin padre", null, "Custom 1"
			}, {//Correct: It is saved successfully with parent folder(carrier)
				"carrier1", null, "messbox con padre", "In Box", "Custom 1"
			}, {//Correct: It is saved successfully (Admin)
				"admin", null, "messbox sin padre", null, "Custom 1"
			}, {//Correct: It is saved successfully with parent folder(Admin)
				"admin", null, "messbox con padre", "In Box", "Custom 1"
			}, {//Correct: It is saved successfully (auditor)
				"auditor1", null, "messbox sin padre", null, "Custom 1"
			}, {//Correct: It is saved successfully with parent folder(auditor)
				"auditor1", null, "messbox con padre", "In Box", "Custom 1"
			}, {//Correct: It is saved successfully (Sponsor)
				"sponsor1", null, "messbox sin padre", null, "Custom 1"
			}, {//Correct: It is saved successfully with parent folder(Sponsor)
				"sponsor1", null, "messbox con padre", "In Box", "Custom 1"
			}, {//Incorrect: Needs somebody authenticated to save a messBox
				"", IllegalArgumentException.class, "messbox con padre", "In Box", "Custom 1"
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateUpdateMessBox((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4]);
		}
	}
	protected void testCreateUpdateMessBox(final String username, final Class<?> expected, final String name, final String parent, final String newName) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			final UserAccount principal = LoginService.getPrincipal();
			final Actor a = this.actorService.findByUserAccountId(principal.getId());
			MessBox result = this.messageBoxService.create();
			result.setName(name);
			if (parent != null) {
				final MessBox parentBox = this.messageBoxService.findMessageBoxByNameAndActorId(parent, a.getId());
				result.setParent(parentBox);
			}
			result = this.messageBoxService.save(result);

			final MessBox updated = (MessBox) result.clone();
			updated.setName(newName);
			this.messageBoxService.save(updated);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);

	}
	/*
	 * Requirement tested: Delete a mess box in database
	 * Sentence coverage: 72.5%
	 * Data coverage: 36.666% as we've tested 11 out of 30 possible combinations
	 */
	@Test
	public void createDeleteDriver() {
		final Object testingData[][] = {
			{	//Correct: It is deleted successfully(customer1)
				"customer1", null, "messbox sin padre", null
			}, {//Correct: It is deleted successfully with parent folder(customer1)
				"customer1", null, "messbox con padre", "In Box"
			}, {//Correct: It is deleted successfully (carrier1)
				"carrier1", null, "messbox sin padre", null
			}, {//Correct: It is deleted successfully with parent folder(carrier1)
				"carrier1", null, "messbox con padre", "In Box"
			}, {//Correct: It is deleted successfully (Admin)
				"admin", null, "messbox sin padre", null
			}, {//Correct: It is deleted successfully with parent folder(Admin)
				"admin", null, "messbox con padre", "In Box"
			}, {//Correct: It is deleted successfully (Sponsor)
				"sponsor1", null, "messbox sin padre", null
			}, {//Correct: It is deleted successfully with parent folder(Sponsor)
				"sponsor1", null, "messbox con padre", "In Box"
			}, {//Correct: It is deleted successfully (auditor1)
				"auditor1", null, "messbox sin padre", null
			}, {//Correct: It is deleted successfully with parent folder(auditor1)
				"auditor1", null, "messbox con padre", "In Box"
			}, {//Incorrect: Needs somebody authenticated to save a messBox
				"", IllegalArgumentException.class, "messbox con padre", "In Box"
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateDeleteMessBox((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
		}
	}
	protected void testCreateDeleteMessBox(final String username, final Class<?> expected, final String name, final String parent) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			final UserAccount principal = LoginService.getPrincipal();
			final Actor a = this.actorService.findByUserAccountId(principal.getId());
			MessBox result = this.messageBoxService.create();
			result.setName(name);
			if (parent != null) {
				final MessBox parentBox = this.messageBoxService.findMessageBoxByNameAndActorId(parent, a.getId());
				result.setParent(parentBox);
			}
			result = this.messageBoxService.save(result);

			this.messageBoxService.flush();
			this.messageBoxService.delete(result);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);

	}

}
