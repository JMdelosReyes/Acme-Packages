
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Offer;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select distinct o from Offer o join o.traverseTowns t join o.fares f join o.vehicle.solicitations s where o.canceled=0 AND o.maxDateToRequest>=CURRENT_DATE AND" + " (?1='' OR (t.town.name=?1)) AND"
		+ "(?2=0. OR (f.minWeight<=?2 AND ?2<=f.maxWeight)) AND( ?3=0. OR ( f.minVolume<=?3 AND ?3<=f.maxVolume)) AND ( ?4=0. OR ?4<=f.price) AND" + "(?5=null OR (o.maxDateToRequest>=?5)) AND (?6=null OR (o.maxDateToRequest<=?6)) AND"
		+ "(?7='' OR (s.status='ACCEPTED' AND s.endDate>=CURRENT_DATE AND (s.category.spanishName=?7 OR s.category.englishName=?7)))")
	Page<Offer> findOffers(String town, Double weight, Double volume, Double maxPrice, Date minimumDate, Date maxDate, String category, Pageable pageable);

	@Query("select c.finder from Customer c where c.id=?1")
	Finder findCustomerFinder(int id);

	@Query("select f from Finder f join f.offers o where o.id=?1")
	Collection<Finder> findersWithOffers(int id);
}
