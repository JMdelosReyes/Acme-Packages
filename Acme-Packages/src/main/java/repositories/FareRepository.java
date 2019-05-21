
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Fare;

@Repository
public interface FareRepository extends JpaRepository<Fare, Integer> {

	@Query("select f from Carrier c join c.fares f where c.id=?1")
	Collection<Fare> findByCarrier(int id);

	@Query("select f from Offer o join o.fares f where o.id=?1")
	Collection<Fare> findByOffer(int id);

}
