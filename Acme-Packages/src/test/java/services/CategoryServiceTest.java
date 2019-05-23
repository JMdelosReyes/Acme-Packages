
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService	categoryService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		final List<Category> sps = new ArrayList<Category>(this.categoryService.findAll());
		Assert.notEmpty(sps);
		super.unauthenticate();
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real category id's and a non category id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The category exists in the database
				super.getEntityId("category1"), null
			}, {
				// Correct: The category exists in the database
				super.getEntityId("category2"), null
			}, {
				// Incorrect: The id does not match any history in the database
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
	 * First part of the save method
	 * Requirement tested: An actor who is authenticated as admin can create categories
	 * Sentence coverage: 100%, as all Asserts of the method are tested
	 * Data coverage: 100%, as we test all the individual constraints
	 */

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Correct create
				"admin", "hello", "hola", "hello", "hola", null
			}, {
				//attributes must not be blank
				"admin", "hello", "", "hello", "hola", ConstraintViolationException.class
			}, {
				//not admin
				"carrier1", "hello", "hola", "hello", "hola", IllegalArgumentException.class
			}, {
				//Unlogged users cannot create socialProfiles
				null, "hello", "hola", "hello", "hola", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], ((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}
	/*
	 * Requirement tested: An actor who is authenticated as admin can update categories
	 * Sentence coverage: 100%
	 * Data coverage: 84.1% as we tested individual constraints and wrong actors
	 */
	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				//Correct update
				"admin", super.getEntityId("category1"), "holaeditado", null
			}, {
				//cant be blank update
				"admin", super.getEntityId("category1"), "", ConstraintViolationException.class
			}, {
				//wrong actor 
				"carrier1", super.getEntityId("category1"), "holaeditado", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testUpdate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}
	/*
	 * Requirement tested: An actor who is authenticated as admin can delete categories
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we test to delete using admin,delete assigned categories, wrong users and non authenticated users
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Correct
				"admin", "category7", null
			}, {
				//Category is assigned
				"admin", "category1", DataIntegrityViolationException.class
			}, {
				//Is not admin
				"carrier1", "category1", IllegalArgumentException.class
			}, {
				//Unlogged users cannot delete categories
				null, "category1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
		}
	}

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Category cat;

		caught = null;
		try {
			cat = this.categoryService.findOne(id);
			Assert.notNull(cat);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//TEMPLATES
	protected void testUpdate(final String username, int id, final String spanish1, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			if (username.equals(null)) {
				this.unauthenticate();

			} else {
				this.authenticate(username);
			}

			Category cat = this.categoryService.findOne(id);
			Category clon = (Category) cat.clone();

			clon.setSpanishName(spanish1);

			cat = clon;
			this.categoryService.save(cat);
			this.categoryService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

	protected void testCreateAndSave(final String username, final String spanishName, final String englishName, final String spanishDescription, final String englishDescription, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);
			final Category cat = this.categoryService.create();

			cat.setEnglishDescription(englishDescription);
			cat.setEnglishName(englishName);
			cat.setSpanishDescription(spanishDescription);
			cat.setSpanishName(spanishName);

			this.categoryService.save(cat);
			this.categoryService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

	protected void testDelete(final String username, final int Id, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);
			final Category old = this.categoryService.findOne(Id);
			this.categoryService.delete(old);
			this.categoryService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}
}
