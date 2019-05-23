
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		Collection<Sponsorship> sponsorships;
		sponsorships = this.sponsorshipService.findAll();
		Assert.notNull(sponsorships);
	}

	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), one real member id's and a non sponsorship id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct:The Sponsor exists in the database
				super.getEntityId("sponsorship1"), null
			}, {
				// Coorect : The sponsor exists in the database
				super.getEntityId("sponsorship2"), null
			}, {
				// Incorrect : The id does not match any Sponsor in the database
				super.getEntityId("town1"), IllegalArgumentException.class
			}, {
				//Incorrect :  The id does not match any Sponsor in the database
				0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as sponsor must be able to Create sponsorship
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we've tested the method been authenticated as sponsor and with a non authenticated actor of the system.
	 */
	@Test
	public void drivercreate() {
		final Object testingData[][] = {
			{// Correct: Actor is   been   authenticated as a sponsor
				"sponsor1", null
			}, {
				// Incorrect: Actor is not  been   authenticated
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.create((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as sponsor must be able to Create sponsorship and manage his sponsorship
	 * Sentence coverage: 81,1%
	 * Data coverage: 9,3%, as we've tested 6 out of 64 possible combinations
	 */
	@Test
	public void drivercreateAndSave() {
		final Object testingData[][] = {
			{   //Correct 
				"sponsor1", "http://www.banner1.com", "http://www.hhh.com", null, false, null
			}, {//Incorrect : The authenticated actor is a company.
				"sponsor2", "http://www.banner1.com", "http://www.hhh.com", LocalDateTime.parse("2020-01-01").toDate(), false, IllegalArgumentException.class
			}, {//Incorrect : Not valid position 
				"sponsor1", "http://www.banner1.com", "http://www.hhh.com", LocalDateTime.parse("2020-01-01").toDate(), true, IllegalArgumentException.class
			}, {//Incorrect : target must not be blank
				"sponsor1", "", "http://www.hhh.com", null, false, ConstraintViolationException.class
			}, {//Incorrect : wrong  credit card
				"sponsor1", "http://www.banner1.com", "", null, false, ConstraintViolationException.class
			}, {//Incorrect : target must not be blank
				"carrier1", "http://link.com", "http://www.hhh.com", null, false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.createAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as sponsor must be able to Create sponsorship and manage his sponsorship
	 * Sentence coverage: 100%
	 * Data coverage: Data coverage: 12.5%, as we've tested 4 out of 32 possible combinations
	 */
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{
				// Correct 
				"sponsor1", "sponsorship1", "http://www.bannertest.com", "http://www.hhhawdaw.com", LocalDateTime.parse("2020-05-05").toDate(), true, null
			}, {
				// Incorrect : banner cant be null 
				"sponsor1", "sponsorship2", "http://www.banner1.com", "http://www.hhh.com", null, true, IllegalArgumentException.class
			}, {
				// Incorrect : banner must not be blank
				"sponsor1", "sponsorship1", "", "", LocalDateTime.parse("2020-05-05").toDate(), true, ConstraintViolationException.class
			}, {
				// Incorrect : banner must not be blank
				"sponsor1", "sponsorship3", "http://www.banner1.com", "http://www.hhh.com", null, false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.testSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	/*
	 * Requirement tested: An actor who is authenticated as sponsor can delete his sponsorships.
	 * Sentence coverage: 95,2%
	 * Data coverage: 95,2%, as we test to delete using sponsors with or without sponsorships and not authenticated user.
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Correct delete
				"sponsor1", "sponsorship1", null
			}, {
				//Doesnt own that sponsorship
				"sponsor3", "sponsorship2", IllegalArgumentException.class
			}, {
				//Can delete as is not valid that sponsorship
				"admin", "sponsorship2", null
			}, {
				//Unlogged users cannot delete sponsorships
				null, "sponsorship2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
		}
	}
	//	Ancillary Methods

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		Sponsorship sponsorship;

		caught = null;
		try {
			sponsorship = this.sponsorshipService.findOne(id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void create(final String bean, final Class<?> expected) {
		Class<?> caught;
		final Sponsorship sponsorship;

		caught = null;
		try {
			super.authenticate(bean);

			sponsorship = this.sponsorshipService.create();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void createAndSave(final String bean, final String banner, final String target, final Date expirationDate, final boolean valid, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(bean);
			Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setBanner(banner);
			sponsorship.setTarget(target);
			sponsorship.setExpirationDate(expirationDate);
			sponsorship.setValid(valid);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void testSave(final String bean, final String beansp, final String banner, final String target, final Date expirationDate, final boolean valid, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			super.authenticate(bean);

			Sponsorship spo = this.sponsorshipService.findOne(super.getEntityId(beansp));
			final Sponsorship clon = (Sponsorship) spo.clone();
			if (bean.equals("admin")) {
				clon.setExpirationDate(expirationDate);
				clon.setValid(valid);
			} else {
				clon.setBanner(banner);
				clon.setTarget(target);
				clon.setValid(valid);
				clon.setExpirationDate(expirationDate);
			}

			spo = clon;

			this.sponsorshipService.save(spo);
			this.sponsorshipService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void testDelete(final String username, final int spId, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);
			final Sponsorship old = this.sponsorshipService.findOne(spId);
			this.sponsorshipService.delete(old.getId());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

}
