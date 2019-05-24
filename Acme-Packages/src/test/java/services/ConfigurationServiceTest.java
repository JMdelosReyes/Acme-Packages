
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	public ConfigurationService		configurationService;
	@Rule
	public final ExpectedException	exception	= ExpectedException.none();


	/*
	 * Sentence coverage: 100%
	 * Data coverage: 100% as the method does not receive any parameter
	 */

	@Test
	public void testFindAll() {
		Assert.isTrue(this.configurationService.findAll().size() == 1);

	}
	/*
	 * Requirement tested: An actor who is authenticated as a admin must be able to manage the configuration.
	 * Sentence coverage: 93,3%
	 * Data coverage: 70%, as we've tested the method with various authorities and some wrong attributes
	 */
	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			{
				//Correct update
				"admin", "test", "hola", null
			}, {
				//Fields must not be blank	
				"admin", "", "", ConstraintViolationException.class
			}, {
				//carrier cannot update configuration
				"carrier1", "test", "hola", IllegalArgumentException.class
			}, {
				//auditor cannot update configuration
				"auditor1", "test", "hola", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testUpdate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	//TEMPLATES
	protected void testUpdate(final String username, final String text, final String spanishMessage, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			super.startTransaction();
			this.authenticate(username);
			Configuration conf = this.configurationService.findOne();
			final Configuration clon = (Configuration) conf.clone();
			clon.setSystemName(text);
			clon.setSpanishMessage(spanishMessage);
			conf = clon;
			this.configurationService.save(conf);
			this.configurationService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
