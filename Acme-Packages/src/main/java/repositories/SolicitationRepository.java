
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Solicitation;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Integer> {

	@Query("select s from Carrier c join c.vehicles v join v.solicitations s where c.id=?1")
	Collection<Solicitation> solicitationsOfCarrier(int idCarrier);

}
