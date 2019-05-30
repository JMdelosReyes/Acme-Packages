
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select o from Offer o join o.traverseTowns tt where tt.id=?1")
	Offer findByTraverseTown(int id);

	@Query("select distinct o from Offer o join o.fares f where f.id=?1")
	Collection<Offer> findByFare(int id);

	@Query("select distinct o from Offer o join o.traverseTowns tt where o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE AND (?1='' OR (tt.town.name=?1))")
	Collection<Offer> findOpenOffers(String filter);

	@Query("select distinct o from Carrier c join c.offers o where c.id=?1 AND o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE")
	Collection<Offer> findCarrierOpenOffers(int id);

	@Query("select distinct o from Carrier c join c.offers o where c.id=?1")
	Collection<Offer> findCarrierOffers(int id);

	@Query("select max(tt.number) from Offer o join o.traverseTowns tt where o.id=?1")
	Integer findMaxNumberTTByOffer(int id);

	@Query("select max(tt.estimatedDate) from Offer o join o.traverseTowns tt where o.id=?1")
	Date findMaxDateTTByOffer(int id);

}
