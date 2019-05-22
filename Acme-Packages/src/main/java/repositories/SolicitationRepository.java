
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Solicitation;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Integer> {

}
