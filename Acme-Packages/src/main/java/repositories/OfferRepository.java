
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Customer;
import domain.Offer;
import domain.Request;
import domain.Town;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select o from Offer o join o.traverseTowns tt where tt.id=?1")
	Offer findByTraverseTown(int id);

	@Query("select distinct o from Offer o join o.fares f where f.id=?1")
	Collection<Offer> findByFare(int id);

	@Query("select distinct o from Offer o join o.traverseTowns tt where o.finalMode=1 AND o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE AND (?1='' OR (tt.town.name=?1))")
	Collection<Offer> findOpenOffers(String filter);

	@Query("select distinct o from Carrier c join c.offers o where c.id=?1 AND o.finalMode=1 AND o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE")
	Collection<Offer> findCarrierOpenOffers(int id);

	@Query("select distinct o from Carrier c join c.offers o where c.id=?1")
	Collection<Offer> findCarrierOffers(int id);

	@Query("select max(tt.number) from Offer o join o.traverseTowns tt where o.id=?1")
	Integer findMaxNumberTTByOffer(int id);

	@Query("select max(tt.estimatedDate) from Offer o join o.traverseTowns tt where o.id=?1")
	Date findMaxDateTTByOffer(int id);

	@Query("select distinct o from Carrier c join c.offers o where c.id=?1 AND o.finalMode=1 AND o.canceled=0 AND o.maxDateToRequest<CURRENT_DATE")
	Collection<Offer> findCarrierPastOffers(int id);

	@Query("select distinct r from Request r where r.finalMode=1 AND r.offer=null AND r.volume<=?1 AND r.weight<=?2 AND (select count(tt) from Offer o join o.traverseTowns tt where o.id=?3 and tt.town.id=r.town.id and tt.estimatedDate<=r.deadline)>0")
	Collection<Request> findRequestsToNotify(Double volume, Double Weight, int offerId);

	@Query("select distinct c from Request r join r.packages p join p.categories c where r.id=?1")
	Collection<Category> findRequestCategories(int id);

	@Query("select distinct s.category from Offer o join o.vehicle v join v.solicitations s where o.id=?1")
	Collection<Category> findOfferCategories(int id);

	@Query("select distinct t.town from Offer o join o.traverseTowns t where o.id=?1")
	Collection<Town> findOfferTowns(int id);

	@Query("select distinct c from Customer c join c.requests r where r.id=?1")
	Customer findCustomerOfRequest(int id);

	@Query("select min(f.price) from Offer o join o.fares f where o.id=?1 and f.maxWeight>=?2 and f.maxVolume>=?3")
	Double findFareForPackage(int id, double weight, double volume);

	@Query("select sum(p.price) from Offer o join o.requests r join r.packages p where o.id=?1 and r.status='ACCEPTED'")
	Double calculaPriceTotal(int id);
}
