
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Solicitation;
import domain.Vehicle;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Integer> {

	@Query("select s from Carrier c join c.vehicles v join v.solicitations s where c.id=?1")
	Collection<Solicitation> solicitationsOfCarrier(int idCarrier);

	@Query("select v from Vehicle v join v.solicitations s where s.id=?1")
	Vehicle vehicleOfSolicitation(int idVehicle);

	@Query("select s from Solicitation s where (select count(a) from Auditor a join a.solicitations s2 where s2.id=s.id)=0")
	Collection<Solicitation> findUnassigned();
}
