
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TraverseTown;

@Repository
public interface TraverseTownRepository extends JpaRepository<TraverseTown, Integer> {

}
