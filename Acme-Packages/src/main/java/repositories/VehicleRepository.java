
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query("select case when (count(o)>0) then false else true end from Offer o join o.vehicle v join o.traverseTowns tt where v.id=?1 and tt.estimatedDate>current_date")
	Boolean canBeDeleted(int id);

}
