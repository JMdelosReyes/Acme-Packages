
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
}
