
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
import domain.Evaluation;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EvaluationServiceTest extends AbstractTest {

	@Autowired
	private EvaluationService	evaluationService;

	@Autowired
	private OfferService		offerService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Evaluation> evaluations;
		evaluations = this.evaluationService.findAll();
		Assert.notNull(evaluations);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the only receive a single parameter
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The evaluation exists in the database
				super.getEntityId("evaluation1"), null
			}, {
				// Incorrect: The id does not match any evaluation in the database
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
	 * Data coverage: 100% is tested with a actor that is allowed to do the evaluation, an incorrect actor and and actor that doesn´t have a request in that offer
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"customer4", "comment", 4, "offer2", null
			}, {
				// Incorrect: The user must be a customer
				"auditor1", "comment", 4, "offer2", IllegalArgumentException.class
			}, {
				// Incorrect: the offer customer doesn´t have any request in that offer
				"customer1", "des", 5, "offer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Evaluation evaluation;

		caught = null;
		try {
			evaluation = this.evaluationService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String actor, final String comment, Integer mark, final String offer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(actor);

			Evaluation evaluation = this.evaluationService.create();

			evaluation.setOffer(this.offerService.findOne(super.getEntityId(offer)));
			evaluation.setComment(comment);
			evaluation.setMark(mark);

			this.evaluationService.save(evaluation);
			this.evaluationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
