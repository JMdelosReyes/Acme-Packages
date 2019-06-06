
package services;

import java.util.ArrayList;
import java.util.List;

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
import domain.Mess;
import domain.MessBox;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessServiceTest extends AbstractTest {

	@Autowired
	private MessService				messService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private MessBoxService			messBoxService;
	@Autowired
	private CarrierService			carrierService;
	@Autowired
	private ConfigurationService	confService;


	/*
	 * Requirement tested: An actor can send mess to any actor in the system.
	 * Sentence coverage: 99.1%
	 * Data coverage: 1,66%, we have tested 9 from 540 posibilities
	 */
	@Test
	public void createSaveDriver() {
		Object testingData[][] = {
			{	//Correct: It is saved successfully
				"admin", null, "subject1", "Body1", "HIGH", "carrier1", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"carrier1", null, "subject2", "Body2", "LOW", "administrator,carrier2", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"customer1", null, "subject3", "Body3", "NEUTRAL", "customer2,auditor1,administrator", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"auditor1", null, "subject4", "Body4", "LOW", "auditor2,administrator,customer2", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"sponsor1", null, "subject5", "Body5", "HIGH", "sponsor2,administrator", "tag1,tag2"
			}, {
				//Correct: It is flagged as spam mess
				"sponsor1", null, "viagra", "sex", "HIGH", "administrator", "tag1,tag2"
			}, {
				//Incorrect: Must have a logged user to send a mess
				"", IllegalArgumentException.class, "subject6", "body6", "HIGH", "customer1", ""
			}, {
				//Incorrect: Must have a subject
				"admin", IllegalArgumentException.class, "", "body7", "NEUTRAL", "customer1", ""
			}, {
				//Incorrect: Must have a body
				"admin", IllegalArgumentException.class, "subject8", "", "NEUTRAL", "customer1", ""
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testCreateSendMess((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6]);
		}
	}

	protected void testCreateSendMess(String username, Class<?> expected, String subject, String body, String priority, String recipients, String tags) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);

			Mess res = this.messService.create();
			res.setSubject(subject);
			res.setBody(body);
			res.setPriority(priority);
			List<Actor> acs = new ArrayList<>();
			String[] recs = recipients.split(",");
			for (int i = 0; i < recs.length; i++) {
				Actor a = this.actorService.findOne(super.getEntityId(recs[i]));
				acs.add(a);
			}
			res.setRecipients(acs);
			List<String> tagss = new ArrayList<>();
			String[] tgs = tags.split(",");
			for (int i = 0; i < tgs.length; i++) {
				tagss.add(tgs[i]);
			}
			res.setTags(tagss);
			this.messService.send(res, false);
			this.messService.flush();
			this.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	/*
	 * Requirement tested: An actor can delete any mess owned and move it to the trash box or delete it
	 * from database
	 * Sentence coverage: 53.2%
	 * Data coverage: 60% we have tested 3 of 5 posibilities, one per each actor in the system
	 */
	@Test
	public void createDeleteDriver() {
		Object testingData[][] = {
			{	//Correct: It is deleted successfully
				"customer1", null, "messageAd1InCus1", "In box"
			}, {
				//Correct: It is deleted successfully
				"carrier1", null, "messageAd1InCar1", "In box"
			}, {
				//Correct: It is deleted successfully
				"admin", null, "messageAd1Out", "Out box"
			}, {
				//Incorrect: Try to delete a message whose owner is not logged
				"admin", IllegalArgumentException.class, "messageAd1InCus1", "In box"
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testDeleteMess((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void testDeleteMess(String username, Class<?> expected, String mess, String source) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			UserAccount principal = LoginService.getPrincipal();
			Actor a = this.actorService.findByUserAccountId(principal.getId());
			Mess bbdd = this.messService.findOne(this.getEntityId(mess));
			MessBox src = this.messBoxService.findMessageBoxByNameAndActorId(source, a.getId());
			this.messService.delete(bbdd, src);

			this.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	/*
	 * Requirement tested: An actor can copy mess to any mess box that are not system boxes
	 * Sentence coverage: 95.7%
	 * Data coverage: 40% as we've tested 3 out of 5 possible combinations, one per each actor
	 */
	@Test
	public void copyDriver() {
		Object testingData[][] = {
			{	//Correct: It is copied successfully
				"admin", null, "messageAd1Out", "Custom 10"
			}, {
				//Correct: It is copied successfully
				"carrier1", null, "messageAd1InCar1", "Custom 10"
			}, {
				//Correct: It is copied successfully
				"customer1", null, "messageAd1InCus1", "Custom 10"
			}, {
				//Incorrect: Try to copied a message whose owner is not logged
				"admin", IllegalArgumentException.class, "messageAd1InCus1", "Custom 10"
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testCopyMess((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void testCopyMess(String username, Class<?> expected, String mess, String endMessBox) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			UserAccount principal = LoginService.getPrincipal();
			Actor a = this.actorService.findByUserAccountId(principal.getId());
			Mess bbdd = this.messService.findOne(this.getEntityId(mess));
			MessBox src;
			src = this.messBoxService.create();
			src.setName(endMessBox);
			src = this.messBoxService.save(src);
			this.messService.copy(bbdd, src);

			this.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	/*
	 * Requirement tested: Send a notification message to all actors in database, only available to admins
	 * Sentence coverage: 98.4%
	 * Data coverage: 6.48 as we have tested 7 from 108 possibilities
	 */
	@Test
	public void sendNotiDriver() {
		Object testingData[][] = {
			{	//Correct: It is saved successfully
				"admin", null, "subject1", "Body1", "HIGH", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"admin", null, "subject3", "Body3", "NEUTRAL", "tag1,tag2"
			}, {
				//Correct: It is saved successfully
				"admin", null, "subject4", "Body4", "LOW", "tag1,tag2"
			}, {
				//Incorrect: Only admins can send notifications
				"carrier1", IllegalArgumentException.class, "subject2", "Body2", "LOW", "tag1,tag2"
			}, {
				//Incorrect: Must have a logged admin to send a notification
				"", IllegalArgumentException.class, "subject6", "body6", "HIGH", ""
			}, {
				//Incorrect: Must have a subject
				"admin", IllegalArgumentException.class, "", "body7", "NEUTRAL", ""
			}, {
				//Incorrect: Must have a body
				"admin", IllegalArgumentException.class, "subject8", "", "NEUTRAL", ""
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.sendNotiDriver((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5]);
		}
	}

	protected void sendNotiDriver(String username, Class<?> expected, String subject, String body, String priority, String tags) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);

			Mess res = this.messService.create();
			res.setSubject(subject);
			res.setBody(body);
			res.setPriority(priority);
			List<String> tagss = new ArrayList<>();
			String[] tgs = tags.split(",");
			for (int i = 0; i < tgs.length; i++) {
				tagss.add(tgs[i]);
			}
			res.setTags(tagss);
			this.messService.sendNotification(res);
			this.messService.flush();
			this.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
