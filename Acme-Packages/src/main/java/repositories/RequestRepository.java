
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Carrier;
import domain.Category;
import domain.Customer;
import domain.Fare;
import domain.Offer;
import domain.Request;
import domain.Town;
import domain.TraverseTown;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	//por testear con populate
	@Query("select sum(p.weight)*1.0 from Request r join r.packages p where r.id=?1")
	Double calculateWeight(int reqId);
	@Query("select sum((p.width * p.height * p.length)*1.0) from Request r join r.packages p where r.id=?1")
	Double calculateVolume(int reqId);

	//tiene peso disponible
	@Query("select case when (veh.maxWeight-sum(r.weight)+?1 >=0) then true else false end from Offer o join o.vehicle veh join o.requests r where o.id=?2")
	Boolean weightCanBeAddedToOfferByWeightOfRequestAndOfferId(double weight, int offerId);

	//tiene volumen disponible
	@Query("select case when (veh.maxVolume-sum(r.volume)+?1 >=0) then true else false end from Offer o join o.vehicle veh join o.requests r where o.id=?2")
	Boolean volumeCanBeAddedToOfferByVolumeOfRequestAndOfferId(double volume, int offerId);

	//Categories de la request
	@Query("select distinct p.categories from Request r join r.packages p where r.id=?1")
	Collection<Category> findCategoriesPackagesByRequestId(int id);

	//Categories disponibles del vehiculo de esa offer
	@Query("select cat from Offer o join o.vehicle veh join veh.solicitations sols join sols.category cat where o.id=?1 and sols.status='ACCEPTED'")
	Collection<Category> findOfferCategoriesByOfferId(int id);

	//Todas las towns de esa offer
	@Query("select tt.town from Offer o join o.traverseTowns tt where o.id=?1")
	Collection<Town> findTownsByOfferId(int id);

	//Traverse town del destino para la request (con esa offer)
	@Query("select tt from Request r join r.offer o join o.traverseTowns tt where tt.town=r.town and r.id=?1")
	TraverseTown findTraverseTownOfDestinationTownByRequestId(int id);

	//Fares ordenados por precio para la offer de esa request
	@Query("select f from Request r join r.offer o join o.fares f join r.packages p where f.maxWeight>p.weight and f.maxVolume>(p.length * p.width * p.height) and p.id=?1 order by f.price asc")
	Collection<Fare> findFaresOrderedByPriceByPackageId(int id);

	//Carrier by offer	
	@Query("select car from Carrier car join car.offers ofs where ofs.id=?1")
	Carrier findCarrierByOfferId(int id);
	//Customer by request
	@Query("select cus from Customer cus join cus.requests req where req.id=?1")
	Customer findCustomerByRequestId(int id);
	//Busca request by issue id
	@Query("select r from Request r join r.issue i where i.id=?1")
	Request findRequestByIssueId(int id);

	@Query("select o.requests from Carrier car join car.offers o where car.id=?1")
	Collection<Request> findRequestsByCarrierId(int id);

	@Query("select sum(p.weight) from Request r join r.packages p where r.id=?1")
	Double calculaTotalWeightByRequestId(int id);

	@Query("select sum(p.length * p.width * p.height) from Request r join r.packages p where r.id=?1")
	Double calculaTotalVolumeByRequestId(int id);

	//*******************FILTER OFFER
	//TODO y hacer el calculo con la resta de los volumenes y pesos de los paquetes
	@Query("select o from Offer o join o.vehicle veh where o.finalMode = 1 and veh.maxVolume => ?1 and veh.maxWeight >= ?2")
	Collection<Offer> findOffersByWeightAndVolumeMax(double maxVolume, double maxWeight);

}
