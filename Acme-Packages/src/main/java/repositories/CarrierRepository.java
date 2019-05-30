
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Integer> {

	@Query("select c from Carrier c join c.offers o where o.id=?1")
	public Carrier findCarrierFromOffer(int id);
}
