
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.Finder;
import domain.Offer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private OfferService	offerService;


	@Before
	public void setup() {
		super.authenticate("customer1");

		Finder finder = this.finderService.findFinderByLoggedCustomer();

		final Finder clon = (Finder) finder.clone();

		clon.setCategory("fragil");
		clon.setMaxPrice(10000.);
		clon.setTown("Sevilla");
		clon.setVolume(1.);
		clon.setWeight(1.);

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			clon.setMinDate(sdf.parse("2019/04/15"));
			clon.setMaxDate(sdf.parse("2020/04/19"));
		} catch (final ParseException e) {
		}

		finder = clon;
		this.finderService.save(finder);
		this.finderService.flush();

		super.unauthenticate();
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as the method does not receive any parameter
	 */
	@Test
	public void findAll() {
		Collection<Finder> finders;
		finders = this.finderService.findAll();
		Assert.notNull(finders);
	}

	/*
	 * Requirement tested: Customers have a finder
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real finders id's and a non finder id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The finder exists in the database
				super.getEntityId("finder1"), null
			}, {
				// Correct: The finder exists in the database
				super.getEntityId("finder2"), null
			}, {
				// Correct: The finder exists in the database
				super.getEntityId("finder3"), null
			}, {
				// Incorrect: The id does not match any finder in the database
				super.getEntityId("userAccount3"), IllegalArgumentException.class
			}, {
				// Incorrect: The id does not match any finder in the database
				0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: Customers have a finder that they can configure.
	 * Sentence coverage: 91.9%, as we use some Asserts just for extra security and avoid failure during search
	 * Data coverage: 100%, as the only way to get a failure is using a non owned finder
	 */
	@Test
	public void driverSearch() {
		final Object testingData[][] = {
			{
				// Correct: Normal search expecting no offer to match
				"customer1", "finder1", "category", "2019/04/15", "2019/04/15", "town", 1., 1., 1., "", null
			}, {
				// Correct: Normal search expecting to find a offer
				"customer1", "finder1", "Fragile", "2019/04/15", "2021/04/15", "Huesca", 4., 1., 1., "offer1", null
			}, {
				// Incorrect: The customer is not using his finder
				"customer1", "finder2", "category", "2019/04/15", "2019/04/15", "town", 1., 1., 1., "", IllegalArgumentException.class
			}, {
				// Incorrect: Normal search expecting to find a offer, the offer was not found
				"customer1", "finder1", "Fragile", "2019/04/15", "2021/04/15", "Huesca", 4., 1., 1., "offer3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSearch((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(Double) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
		}
	}

	/*
	 * Requirement tested: The results of a finder are cached for one hour by default. We use the setup method in top of the class
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as the only way to get a failure is not being a customer
	 */
	@Test
	public void driverFindByLoggedCustomer() {
		final Object testingData[][] = {
			{
				// Correct: The customer searched before the test and obtained no result
				"customer1", "empty", null
			}, {
				// Correct: The customer hasn't use his finder in a while, so when he ask for it, he obtains all the offers by default
				"customer2", "notEmpty", null
			}, {
				// Incorrect: The user is not a customer
				"admin", "notEmpty", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a customer
				"auditor1", "notEmpty", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindByLoggedCustomer((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	/*
	 * Requirement tested: Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as the only way to get a failure is not being a customer
	 */
	@Test
	public void driverClear() {
		final Object testingData[][] = {
			{
				// Correct: Normal clear
				"customer1", null
			}, {
				// Correct: Normal clear
				"customer2", null
			}, {
				// Incorrect: The user is not a customer
				"admin", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a customer
				"auditor1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testClear((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Finder finder;

		caught = null;
		try {
			finder = this.finderService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSearch(final String customerBean, final String finderBean, final String category, final String minDate, final String maxDate, final String town, final Double maxPrice, final Double weight, final Double volume,
		final String expectedOffer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(customerBean);

			final int customerId = super.getEntityId(customerBean);
			final Customer customer = this.customerService.findOne(customerId);
			Finder finder = this.finderService.findOne(super.getEntityId(finderBean));

			final Finder clon = (Finder) finder.clone();

			clon.setCategory(category);
			clon.setMaxPrice(maxPrice);
			clon.setTown(town);
			clon.setVolume(volume);
			clon.setWeight(weight);

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				clon.setMinDate(sdf.parse(minDate));
				clon.setMaxDate(sdf.parse(maxDate));
			} catch (final ParseException e) {
			}

			finder = clon;
			this.finderService.save(finder);
			this.finderService.flush();

			if (!expectedOffer.isEmpty()) {
				final int offerId = super.getEntityId(expectedOffer);
				final Offer offer = this.offerService.findOne(offerId);

				final Finder savedFinder = this.finderService.findCustomerFinder(customerId);
				Assert.isTrue(savedFinder.getOffers().contains(offer));
			}

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testFindByLoggedCustomer(final String customerBean, final String expectedContent, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(customerBean);

			final Finder finder = this.finderService.findFinderByLoggedCustomer();

			if (expectedContent.equals("empty")) {
				Assert.isTrue(finder.getOffers().isEmpty());
			} else {
				Assert.isTrue(!finder.getOffers().isEmpty());
			}

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testClear(final String customerBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(customerBean);

			this.finderService.clearFinder();
			this.customerService.flush();

			final Finder finder = this.finderService.findFinderByLoggedCustomer();
			Assert.isTrue(finder.getCategory().equals(""));
			Assert.isTrue(finder.getTown().equals(""));
			Assert.isNull(finder.getMinDate());
			Assert.isNull(finder.getMaxDate());
			Assert.isNull(finder.getMaxDate());

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
