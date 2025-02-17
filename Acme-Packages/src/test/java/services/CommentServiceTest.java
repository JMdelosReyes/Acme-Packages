
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	private CommentService	commentService;

	@Autowired
	private IssueService	issueService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method receive only a parameter
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The comment exists in the database
				super.getEntityId("comment1"), null
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
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Comment> comments;
		comments = this.commentService.findAll();
		Assert.notNull(comments);
	}

	/*
	 * Requirement tested: An actor can be able to create a comments in his or her issues
	 * Sentence coverage: 87.7%
	 * Data coverage: 75% Are tested 3 of 4 possibles actors and if the actor is the propietary or not
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"customer2", "comment", "issue1", null
			}, {
				// Correct: All the parameters are OK
				"carrier1", "comment", "issue1", null
			}, {
				// Incorrect: The user is not a carrier, customer or auditor
				"admin", "comment", "issue1", IllegalArgumentException.class
			}, {
				// Incorrect: The customer is not the proprietary of the issue
				"auditor", "des", "issue1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Comment comment;

		caught = null;
		try {
			comment = this.commentService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String actor, final String com, final String issue, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Comment comment = this.commentService.create();

			comment.setUserComment(com);
			comment.setUsername("un");
			comment.setMoment(DateTime.now().minusMillis(1000).toDate());

			this.commentService.save(comment, super.getEntityId(issue));
			this.commentService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
