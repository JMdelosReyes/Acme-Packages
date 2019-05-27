
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query("select case when ((count(s)+count(o))>0) then false else true end from Offer o join o.vehicle v join v.solicitations s where v.id=?1 and s.status!='REJECTED'")
	Boolean canBeEditedOrDeleted(int id);

	@Query("select case when (count(s)>0) then true else false end from Vehicle v join v.solicitations s where v.id=?1 and s.endDate>=current_date")
	Boolean canBeUsed(int id);

	@Query("select v from Vehicle v join v.solicitations s where (select count(a) from Auditor a join a.solicitations s2 where a.id=?1 and s.id=s2.id)>0")
	Collection<Vehicle> findVehiclesAuditedByAuditor(int id);

	@Query("select v from Vehicle v join v.solicitations s where s.id=?1")
	Vehicle findBySolicitation(int id);

	@Query("select c from Category c where c not in (select c2 from Vehicle v join v.solicitations s join s.category c2 where v.id=?1 and (s.endDate>=current_date or s.status!='REJECTED'))")
	Collection<Category> findApplicableCategories(int id);

}
