
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Package;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	@Autowired
	private RequestService	reqService;
	@Autowired
	private TownService		townService;
	@Autowired
	private PackageService	pacService;
	@Autowired
	private CategoryService	catService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Request> requests;
		requests = this.reqService.findAll();
		Assert.notNull(requests);
	}
	/*
	 * Requirement tested: Actors can search a request
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real carriers id's and a non carrier id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The issue exists in the database
				super.getEntityId("request1"), null
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

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Request req;

		caught = null;
		try {
			req = this.reqService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Requirement tested: A customer can create a request and save it.
	 * Sentence coverage: 97.4%
	 * Data coverage: 1.66%, we have tested 4 from 540 possibilities
	 */
	@Test
	public void driverCreateSave() {
		final Object testingData[][] = {
			{
				// Correct: The request is saved successfully
				"customer1", null, "description1", 1000.0, "25/09/2019", false, "Calle pinta", "No comments", super.getEntityId("town1"), "190925-POLKI"
			}, {
				// Incorrect: Deadline cannot be in the past
				"customer1", ConstraintViolationException.class, "description1", 1000.0, "", false, "Calle pinta", "No comments", super.getEntityId("town1"), "190925-POLKI"
			}, {
				// Incorrect: Description cannot be empty
				"customer1", ConstraintViolationException.class, "", 1000.0, "25/09/2019", false, "Calle pinta", "No comments", super.getEntityId("town1"), "190925-POLKI"
			}, {
				// Incorrect: Street comment cannot be empty
				"customer1", ConstraintViolationException.class, "description1", 1000.0, "25/09/2019", false, "", "No comments", super.getEntityId("town1"), "190925-POLKI"
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateSave((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (double) testingData[i][3], (String) testingData[i][4], (boolean) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (String) testingData[i][9]);
		}
	}
	protected void testCreateSave(String username, Class<?> expected, String desc, double maxPrice, String deadline, boolean finalMode, String streetAddress, String comment, int townId, String ticker) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(username);
			Request res = this.reqService.create();
			res.setDescription(desc);
			res.setMaxPrice(maxPrice);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (deadline != "") {
				res.setDeadline(sdf.parse(deadline));
			} else {
				res.setDeadline(null);
			}
			res.setTicker(ticker);
			res.setFinalMode(finalMode);
			res.setStreetAddress(streetAddress);
			res.setComment(comment);
			res.setTown(this.townService.findOne(townId));

			Package pac = this.pacService.create();
			pac.setWeight(1000.0);
			pac.setHeight(10.0);
			pac.setWidth(10.0);
			pac.setLength(10.0);
			List<Category> cats = new ArrayList<Category>();
			Category cat = this.catService.findOne(super.getEntityId("category1"));
			cats.add(cat);
			pac.setCategories(cats);
			pac.setDetails("No desc");
			this.reqService.anyadePackage(pac, res);
			this.reqService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * Requirement tested: A customer can update a request and save it.
	 * Sentence coverage: 97.4%
	 * Data coverage: 5.55%, we have tested 4 from 72 possibilities
	 */
	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				// Correct: The request is saved successfully
				"customer4", null, "description1", 1000.0, "25/09/2019", false, "Calle pinta", "No comments", super.getEntityId("town1"), super.getEntityId("request4")
			}, {
				// Incorrect: Deadline cannot be in the past
				"customer4", ConstraintViolationException.class, "description1", 1000.0, "", false, "Calle pinta", "No comments", super.getEntityId("town1"), super.getEntityId("request4")
			}, {
				// Incorrect: Description cannot be empty
				"customer4", ConstraintViolationException.class, "", 1000.0, "25/09/2019", false, "Calle pinta", "No comments", super.getEntityId("town1"), super.getEntityId("request4")
			}, {
				// Incorrect: Street comment cannot be empty
				"customer4", ConstraintViolationException.class, "description1", 1000.0, "25/09/2019", false, "", "No comments", super.getEntityId("town1"), super.getEntityId("request4")
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testUpdateSave((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (double) testingData[i][3], (String) testingData[i][4], (boolean) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (int) testingData[i][8], (int) testingData[i][9]);
		}
	}
	protected void testUpdateSave(String username, Class<?> expected, String desc, double maxPrice, String deadline, boolean finalMode, String streetAddress, String comment, int townId, int reqId) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(username);
			Request old = this.reqService.findOne(reqId);
			Request res = (Request) old.clone();
			res.setDescription(desc);
			res.setMaxPrice(maxPrice);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (deadline != "") {
				res.setDeadline(sdf.parse(deadline));
			} else {
				res.setDeadline(null);
			}
			res.setFinalMode(finalMode);
			res.setStreetAddress(streetAddress);
			res.setComment(comment);
			res.setTown(this.townService.findOne(townId));

			Package pac = this.pacService.create();
			pac.setWeight(1000.0);
			pac.setHeight(10.0);
			pac.setWidth(10.0);
			pac.setLength(10.0);
			List<Category> cats = new ArrayList<Category>();
			Category cat = this.catService.findOne(super.getEntityId("category1"));
			cats.add(cat);
			pac.setCategories(cats);
			pac.setDetails("No desc");
			old = res;
			this.reqService.anyadePackage(pac, old);
			this.reqService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * Requirement tested: A customer can delete a request if the request is not in final mode.
	 * Sentence coverage: 100%
	 * Data coverage: 100%, we have tested 3 from 3 possibilities
	 */
	@Test
	public void delete() {
		Object testingData[][] = {

			{
				// Correct: The request is deleted from the database
				"customer4", null, "request4"
			}, {
				// Incorrect: The request is in final mode
				"customer1", IllegalArgumentException.class, "request2"
			}, {
				// Incorrect: The package is owned by other customer
				"customer1", IllegalArgumentException.class, "request4"
			}
		};
		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
		}
	}

	protected void testDelete(String username, Class<?> expected, String request) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			Request r = this.reqService.findOne(super.getEntityId(request));
			this.reqService.delete(r);
			this.reqService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
