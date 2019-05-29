
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select o from Offer o join o.traverseTowns tt where tt.id=?1")
	Offer findByTraverseTown(int id);

	@Query("select o from Offer o join o.fares f where f.id=?1")
	Collection<Offer> findByFare(int id);

	@Query("select o from Offer o where o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE")
	Collection<Offer> findOpenOffers();

	@Query("select o from Carrier c join c.offers o where c.id=?1 AND o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE")
	Collection<Offer> findCarrierOpenOffers(int id);

	@Query("select o from Carrier c join c.offers o where c.id=?1")
	Collection<Offer> findCarrierOffers(int id);

}
