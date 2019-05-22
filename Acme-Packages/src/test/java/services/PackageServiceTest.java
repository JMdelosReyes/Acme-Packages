
package services;

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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PackageServiceTest extends AbstractTest {

	@Autowired
	private PackageService	pacService;
	@Autowired
	private CategoryService	catService;
	@Autowired
	private RequestService	reqService;


	//@Test
	public void testFindAll() {
		Collection<Package> packages;
		packages = this.pacService.findAll();
		Assert.notNull(packages);
	}

	//@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The package exists in the database
				super.getEntityId("package1"), null
			}, {
				// Correct: The package exists in the database
				super.getEntityId("package2"), null
			}, {
				// Correct: The package exists in the database
				super.getEntityId("package3"), null
			}, {
				// Incorrect: The id does not match any package in the database
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
		Package pac;

		caught = null;
		try {
			pac = this.pacService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSave() {
		Object testingData[][] = {
			{
				// Correct: The package exists in the database
				"customer1", null, 1000.0, 2.0, 2.0, 3.0, 0.0, "cuidaito con la play", "category1"
			}, {
				// Correct: The package exists in the database
				"customer1", null, 1000.0, 2.0, 2.0, 3.0, 0.0, "cuidaito con la play2", "category2,category3"
			}, {
				// Correct: The package exists in the database
				"customer1", null, 2000.0, 4.0, 8.0, 10.0, 6.0, "cuidaito con la play3", "category1"
			}, {
				// Incorrect: The weight cannot be under 100 grams
				"customer1", ConstraintViolationException.class, 0.0, 2.0, 2.0, 3.0, 0.0, "cuidaito con la play", "category1"
			}, {
				// Incorrect: The height cannot be under 1 cm
				"customer1", ConstraintViolationException.class, 2000.0, 0.0, 2.0, 3.0, 0.0, "cuidaito con la play", "category1"
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateSave((String) testingData[i][0], (Class<?>) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (double) testingData[i][5], (double) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8]);
		}
	}

	protected void testCreateSave(String username, Class<?> expected, double weight, double height, double width, double length, double price, String desc, String categories) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			this.authenticate(username);
			Package res = this.pacService.create();
			res.setWeight(weight);
			res.setHeight(height);
			res.setWidth(width);
			res.setLength(length);
			if (price > 0.0) {
				res.setPrice(price);
			}
			res.setDescription(desc);
			List<Category> acs = new ArrayList<>();
			String[] cats = categories.split(",");
			for (int i = 0; i < cats.length; i++) {
				Category a = this.catService.findOne(super.getEntityId(cats[i]));
				acs.add(a);
			}
			res.setCategories(acs);

			this.pacService.save(res, this.reqService.findOne(super.getEntityId("request1")));
			this.pacService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
