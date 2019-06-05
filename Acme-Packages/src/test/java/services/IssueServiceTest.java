
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
import domain.Issue;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class IssueServiceTest extends AbstractTest {

	@Autowired
	private IssueService	issueService;

	@Autowired
	private OfferService	offerService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Issue> issues;
		issues = this.issueService.findAll();
		Assert.notNull(issues);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method receive only a parameter
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The issue exists in the database
				super.getEntityId("issue1"), null
			}, {
				// Incorrect: The id does not match any issue in the database
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
	 * Requirement tested: An customer can be able to create an issue of an offer that he or she is involved
	 * Sentence coverage: 100%
	 * Data coverage: 80%, all actors checked, but not other request
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"customer1", "des", "request6", null
			}, {
				// Incorrect: The user must be a customer
				"auditor1", "des", "request3", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not able to change the description
				"carrier1", "des", "request3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	/*
	 * Requirement tested: An customer can be able to edit the description of an issue
	 * Sentence coverage: 50.1%
	 * Data coverage: 40% only checked 2 of 3 actors and the same issue
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"auditor1", "issue1", "Ha llegado roto al destinatario", false, "offer1", null
			}, {
				// Incorrect: The issue is being used
				"customer1", "issue1", "des2", true, "offer1", IllegalArgumentException.class
			}, {
				// Incorrect: The actor is not the owner of the issue
				"customer1", "issue1", "des2", false, "offer2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (boolean) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Issue issue;

		caught = null;
		try {
			issue = this.issueService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String actor, final String description, final String request, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Issue issue = this.issueService.create();

			issue.setDescription(description);

			this.issueService.save(issue, super.getEntityId(request));
			this.issueService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
	protected void testSave(final String actor, String issueBean, String description, boolean closed, String offer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Issue issue = this.issueService.findOne(super.getEntityId(issueBean));

			Issue clon = (Issue) issue.clone();

			clon.setClosed(closed);
			clon.setDescription(description);
			clon.setOffer(this.offerService.findOne(super.getEntityId(offer)));

			this.issueService.save(clon, null);
			this.issueService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
