
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;


	// Supporting services

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
		final UserAccount principal = LoginService.getPrincipal();
		Assert.notNull(principal);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(principal.getAuthorities().contains(auth));

		Assert.isTrue((configuration.getSystemName() != null) && !configuration.getSystemName().equals(""));
		Assert.isTrue((configuration.getBanner() != null) && !configuration.getBanner().equals(""));
		Assert.isTrue((configuration.getCountryCode() != null) && !configuration.getCountryCode().equals(""));
		Assert.isTrue((configuration.getEnglishMessage() != null) && !configuration.getEnglishMessage().equals(""));
		Assert.isTrue((configuration.getSpanishMessage() != null) && !configuration.getSpanishMessage().equals(""));
		Assert.isTrue(configuration.getMakes() != null);
		Assert.isTrue(configuration.getSpamWords() != null);
		Assert.isTrue(configuration.getMessPriorities() != null);

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

}
