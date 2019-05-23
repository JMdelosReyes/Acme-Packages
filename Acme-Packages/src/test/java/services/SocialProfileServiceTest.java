
package services;

import java.util.ArrayList;
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
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	private SocialProfileService	socialProfileService;


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAll() {
		final List<SocialProfile> sps = new ArrayList<SocialProfile>(this.socialProfileService.findAll());
		Assert.notEmpty(sps);
		super.unauthenticate();
	}
	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */
	@Test
	public void testFindAllOwnSocialProfiles() {
		super.authenticate("auditor1");
		final List<SocialProfile> sps = new ArrayList<SocialProfile>(this.socialProfileService.findOwnSocialProfiles());
		Assert.notEmpty(sps);
		super.unauthenticate();
	}
	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100%, as we tested the limit id (0), some real proclaims id's and a non proclaim id
	 */
	@Test
	public void driverFindOne() {
		final Object testingData[][] = {
			{
				// Correct: The history exists in the database
				super.getEntityId("socialProfile1"), null
			}, {
				// Correct: The history exists in the database
				super.getEntityId("socialProfile2"), null
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
	 * Requirement tested: An actor who is authenticated can create socialProfiles
	 * Sentence coverage: 96.4%
	 * Data coverage: 96.4%, as we test all the individual constraints
	 */

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Correct create
				"carrier1", "Manuel", "FaceBook", "https://www.facebook.com/", null
			}, {
				//Link must not be blank
				"carrier1", "Manuel", "FaceBook", "", ConstraintViolationException.class
			}, {
				//SocialNetworkName must not be blank
				"carrier1", "Manuel", "", "https://www.facebook.com/", ConstraintViolationException.class
			}, {
				//Nick must not be blank
				"carrier1", "", "Facebook", "https://www.facebook.com/", ConstraintViolationException.class
			}, {
				//Unlogged users cannot create socialProfiles
				null, "", "Facebook", "https://www.facebook.com/", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreateAndSave((String) testingData[i][0], ((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}
	/*
	 * Requirement tested: An actor who is authenticated can update his social profiles
	 * Sentence coverage: 100%
	 * Data coverage: 84.1% as we tested all individual constraints and wrong actors
	 */
	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				//Correct update
				"auditor1", super.getEntityId("socialProfile2"), "Manuel", "FaceBook", "https://www.facebook.com/", null
			}, {
				//Link must not be blank
				"auditor1", super.getEntityId("socialProfile2"), "Manuel", "FaceBook", "", ConstraintViolationException.class
			}, {
				//SocialNetworkName must not be blank
				"auditor1", super.getEntityId("socialProfile2"), "Manuel", "", "https://www.facebook.com/", ConstraintViolationException.class
			}, {
				//Nick must not be blank
				"auditor1", super.getEntityId("socialProfile2"), "", "Facebook", "https://www.facebook.com/", ConstraintViolationException.class
			}, {
				//auditor doesnt own that socialProfile
				"auditor1", super.getEntityId("socialProfile5"), "Hola", "Facebook", "https://www.facebook.com/", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testUpdate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}
	/*
	 * Requirement tested: An actor who is authenticated can delete his social profiles.
	 * Sentence coverage: 97,4%
	 * Data coverage: 97,4%, as we test to delete using user with or without owning social profiles and not authenticated.
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Correct delete
				"auditor1", "socialProfile2", null
			}, {
				//Doesnt own that socialProfile
				"auditor1", "socialProfile7", IllegalArgumentException.class
			}, {
				//Unlogged users cannot delete social profiles
				null, "socialProfile5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
		}
	}

	protected void testFindOne(final int id, final Class<?> expected) {
		Class<?> caught;
		SocialProfile social;

		caught = null;
		try {
			social = this.socialProfileService.findOne(id);
			Assert.notNull(social);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//TEMPLATES
	protected void testUpdate(final String username, final int spId, final String nick, final String social, final String link, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);

			SocialProfile socialProfile = this.socialProfileService.findOne(spId);
			final SocialProfile clon = (SocialProfile) socialProfile.clone();

			clon.setNick(nick);
			clon.setProfileLink(link);
			clon.setSocialNetwork(social);

			socialProfile = clon;
			this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

	protected void testCreateAndSave(final String username, final String nick, final String social, final String link, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);
			final SocialProfile socialProfile = this.socialProfileService.create();

			socialProfile.setNick(nick);
			socialProfile.setProfileLink(link);
			socialProfile.setSocialNetwork(social);

			this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

	protected void testDelete(final String username, final int spId, final Class<?> expected) {
		Class<?> caugth;
		caugth = null;

		try {
			this.authenticate(username);
			final SocialProfile old = this.socialProfileService.findOne(spId);
			this.socialProfileService.delete(old);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caugth = oops.getClass();
		}
		super.checkExceptions(expected, caugth);
	}

}
