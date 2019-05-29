
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TraverseTown;

@Repository
public interface TraverseTownRepository extends JpaRepository<TraverseTown, Integer> {

	@Query("select t from Carrier c join c.offers o join o.traverseTowns t where c.id=?1")
	Collection<TraverseTown> findCarrierTraverseTowns(int id);

}
