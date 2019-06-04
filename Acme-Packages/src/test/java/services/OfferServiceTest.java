
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Fare;
import domain.Offer;
import domain.Vehicle;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest extends AbstractTest {

	@Autowired
	private OfferService	offerService;

	@Autowired
	private FareService		fareService;

	@Autowired
	private VehicleService	vehicleService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Offer> offers;
		offers = this.offerService.findAll();
		Assert.notNull(offers);
	}

	/*
	 * Requirement tested: The actors of the system can see the offers of the system
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real offers id's and a non offer id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The offer exists in the database
				super.getEntityId("offer1"), null
			}, {
				// Correct: The offer exists in the database
				super.getEntityId("offer2"), null
			}, {
				// Correct: The offer exists in the database
				super.getEntityId("offer3"), null
			}, {
				// Incorrect: The id does not match any offer in the database
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
	 * Requirement tested: Offers can be filtered by town and by its carrier
	 * Sentence coverage of the methods findOpenOffers and findCarrierOffers: 100%
	 * Data coverage: 100% as we'v seen cases in which the offer is contained and not contained
	 */
	@Test
	public void driverList() {
		final Object testingData[][] = {
			{
				// Correct: The offer is owned by the specified carrier
				"carrier1", "offer1", "", true, null
			}, {
				// Incorrect: The offer is not owned by the specified carrier
				"carrier2", "offer1", "", true, IllegalArgumentException.class
			}, {
				// Incorrect: Customers don't have offers
				"customer1", "offer1", "", true, IllegalArgumentException.class
			}, {
				// Correct: The offer is found by the entered town
				"", "offer1", "Huesca", false, null
			}, {
				// Incorrect: The offer is not found by the entered town
				"", "offer1", "Murcia", false, IllegalArgumentException.class
			}, {
				// Incorrect: The offer is not in our system
				"", "offer1", "París", false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testList((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Boolean) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to create offers
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method with a carrier and with a non carrier user
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
				"customer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as a carrier must be able to create and save offers
	 * Sentence coverage: 92.5%
	 * Data coverage: 22.22%, as we've tested 8 out of 36 possible combinations
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "15/08/2019", "vehicle1", "fare1,fare2", null
			}, {
				// Correct: As long the offer is not in finalMode, it can have no fare selected
				"carrier1", "15/08/2019", "vehicle1", "", null
			}, {
				// Incorrect: The date cannot be in past nor the current day
				"carrier1", "25/05/2019", "vehicle1", "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"customer1", "25/09/2019", "vehicle1", "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"admin", "25/09/2019", "vehicle1", "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The vehicle is not owned by this carrier
				"carrier1", "25/09/2019", "vehicle4", "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The vehicle cannot be used
				"carrier2", "25/09/2019", "vehicle4", "fare5,fare6", IllegalArgumentException.class
			}, {
				// Incorrect: The vehicle cannot be null
				"carrier2", "25/09/2019", "", "fare5,fare6", IllegalArgumentException.class
			}, {
				// Incorrect: The fares does not belong to this carrier
				"carrier1", "25/09/2019", "vehicle1", "fare8,fare9", IllegalArgumentException.class
			}, {
				// Incorrect: The date cannot be null
				"carrier1", "", "vehicle1", "fare1,fare2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	/*
	 * Requirement tested: An authenticated carrier must be able to modify his offers
	 * Sentence coverage: 92.5%
	 * Data coverage: 5.55%, as we've tested 8 out of 144 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct: All the parameters are OK
				"carrier1", "offer4", "15/08/2019", "vehicle1", false, "fare1,fare2", null
			}, {
				// Correct: As long the offer is not in finalMode, it can have no fare selected
				"carrier1", "offer4", "15/08/2019", "vehicle1", false, "", null
			}, {
				// Incorrect: The date cannot be in past nor the current day
				"carrier1", "offer4", "25/05/2019", "vehicle1", false, "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not a carrier
				"customer1", "offer4", "25/09/2019", "vehicle1", false, "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The user is not the owner of the offer
				"carrier2", "offer4", "25/09/2019", "vehicle3", false, "fare5,fare6", IllegalArgumentException.class
			}, {
				// Incorrect: The vehicle is not owned by this carrier
				"carrier1", "offer4", "25/09/2019", "vehicle3", false, "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The vehicle cannot be null
				"carrier1", "offer4", "25/09/2019", "", false, "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: To be in final mode the offer needs at least one fare
				"carrier1", "offer4", "15/08/2019", "vehicle1", true, "", IllegalArgumentException.class
			}, {
				// Incorrect: The date cannot be null
				"carrier1", "offer4", "", "vehicle1", true, "fare1,fare2", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not the owner of the offer
				"carrier2", "offer4", "15/08/2019", "vehicle1", false, "fare1,fare2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	/*
	 * Requirement tested: Carriers can delete their offers as long as they are not in final mode
	 * Sentence coverage: 95.2%
	 * Data coverage: 100%, as we tried to delete offers in final and not final mode with its owners and with other users
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				// Correct: The carrier can delete his offer
				"carrier1", "offer4", null
			}, {
				// Incorrect: The carrier is not the owner of the offer
				"carrier2", "offer4", IllegalArgumentException.class
			}, {
				// Incorrect: The carrier is not a carrier
				"admin", "offer4", IllegalArgumentException.class
			}, {
				// Incorrect: An offer in final mode cannot be deleted
				"carrier2", "offer4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Offer offer;

		caught = null;
		try {
			offer = this.offerService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testList(final String userBean, final String offerBean, String townBean, Boolean ownedByCarrier, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			if (!userBean.equals("")) {
				super.authenticate(userBean);
			}

			Offer offer = this.offerService.findOne(super.getEntityId(offerBean));
			Collection<Offer> offers = new ArrayList<Offer>();

			if (ownedByCarrier) {
				offers = this.offerService.findCarrierOffers(super.getEntityId(userBean));
			} else {
				offers = this.offerService.findOpenOffers(townBean);
				Assert.isTrue(offers.contains(offer));
			}

			Assert.isTrue(offers.contains(offer));

			if (!userBean.equals("")) {
				super.unauthenticate();
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreate(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Offer offer;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(bean);

			offer = this.offerService.create();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testCreateAndSave(final String carrier, final String maxDateToRequest, String vehicleBean, String fareBeans, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Offer offer = this.offerService.create();

			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (maxDateToRequest != "") {
				offer.setMaxDateToRequest(sdf.parse(maxDateToRequest));
			} else {
				offer.setMaxDateToRequest(null);
			}

			if (fareBeans != "") {
				String[] fBeans = fareBeans.split(",");
				Collection<Fare> fares = new ArrayList<Fare>();
				for (int i = 0; i < fBeans.length; i++) {
					Fare f = this.fareService.findOne(super.getEntityId(fBeans[i]));
					fares.add(f);
				}

				offer.setFares(fares);
			}

			Vehicle vehicle;
			if (vehicleBean != "") {
				vehicle = this.vehicleService.findOne(super.getEntityId(vehicleBean));
			} else {
				vehicle = null;
			}

			offer.setVehicle(vehicle);

			this.offerService.save(offer);
			this.offerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testSave(final String carrier, String offerBean, final String maxDateToRequest, String vehicleBean, Boolean finalMode, String fareBeans, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			Offer offer = this.offerService.findOne(super.getEntityId(offerBean));

			Offer clon = (Offer) offer.clone();

			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (maxDateToRequest != "") {
				clon.setMaxDateToRequest(sdf.parse(maxDateToRequest));
			} else {
				clon.setMaxDateToRequest(null);
			}

			if (fareBeans != "") {
				String[] fBeans = fareBeans.split(",");
				Collection<Fare> fares = new ArrayList<Fare>();
				for (int i = 0; i < fBeans.length; i++) {
					Fare f = this.fareService.findOne(super.getEntityId(fBeans[i]));
					fares.add(f);
				}

				clon.setFares(fares);
			}

			Vehicle vehicle;
			if (vehicleBean != "") {
				vehicle = this.vehicleService.findOne(super.getEntityId(vehicleBean));
			} else {
				vehicle = null;
			}

			clon.setVehicle(vehicle);

			clon.setFinalMode(finalMode);

			offer = clon;

			this.offerService.save(offer);
			this.offerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String carrier, final String offerBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.startTransaction();
			super.authenticate(carrier);

			final int id = super.getEntityId(offerBean);
			final Offer offer = this.offerService.findOne(id);

			this.offerService.delete(offer);
			this.offerService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
