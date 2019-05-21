
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Integer> {

}
