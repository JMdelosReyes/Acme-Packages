
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;


	// Constructor

	public ConfigurationService() {
		super();
	}

	// CRUD methods

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;
		result = this.configurationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Configuration findOne() {
		Configuration result;

		result = this.configurationRepository.findAll().get(0);
		Assert.notNull(result);
		return result;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		// Only administrators can modify the configuration
		Assert.isTrue(this.actorService.findActorType().equals("Administrator"));

		return this.configurationRepository.save(configuration);
	}

	public Collection<String> findSpamWords() {
		final Collection<String> result = this.configurationRepository.findSpamWords();
		Assert.notNull(result);
		return result;
	}

	public int findFinderTime() {
		final int result = this.configurationRepository.findFinderTime();
		Assert.isTrue(result > 0);
		return result;
	}

	public Collection<String> findMakes() {
		final Collection<String> result = this.configurationRepository.findMakes();
		Assert.notNull(result);
		return result;
	}

	public double findVat() {
		final double result = this.configurationRepository.findVatTax();
		Assert.isTrue(result > 0);
		return result;
	}

	public void flush() {
		this.configurationRepository.flush();
	}

}
