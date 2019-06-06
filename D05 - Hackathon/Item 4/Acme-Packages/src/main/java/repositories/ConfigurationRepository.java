
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	@Query("select sw from Configuration c join c.spamWords sw")
	Collection<String> findSpamWords();

	@Query("select c.finderTime from Configuration c")
	int findFinderTime();

	@Query("select pw from Configuration c join c.makes pw")
	Collection<String> findMakes();

	@Query("select c.vat from Configuration c")
	double findVatTax();
}
