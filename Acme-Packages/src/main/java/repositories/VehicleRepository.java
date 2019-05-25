
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query("select case when ((count(s)+count(o))>0) then false else true end from Offer o join o.vehicle v join v.solicitations s where v.id=?1 and s.status!='REJECTED'")
	Boolean canBeEditedOrDeleted(int id);

	@Query("select case when (count(s)>0) then true else false end from Vehicle v join v.solicitations s where v.id=?1 and s.endDate>=current_date")
	Boolean canBeUsed(int id);

}
