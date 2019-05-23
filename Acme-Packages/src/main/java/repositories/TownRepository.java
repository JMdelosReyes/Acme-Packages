
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {

	@Query("select count(r) from Request r join r.town t where t.id=?1")
	Integer findNumberOfRequests(int id);

	@Query("select count(tt) from TraverseTown tt join tt.town t where t.id=?1")
	Integer findNumberOfTraverseTowns(int id);

}
