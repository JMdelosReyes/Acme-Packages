
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	//por testear con populate
	@Query("select sum(p.weight) from Request r join r.packages p where r.id=?1")
	Double calculateWeight(int reqId);
	@Query("select sum(p.volume) from Request r join r.packages p where r.id=?1")
	Double calculateVolume(int reqId);

}
