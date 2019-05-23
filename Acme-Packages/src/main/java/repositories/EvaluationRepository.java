
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Evaluation;
import domain.Offer;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

	@Query("select r.offer from Customer c join c.requests r where c.id=?1")
	Collection<Offer> offerOfCustomer(int idCustomer);

	@Query("select r.issue.offer from Customer c join c.requests r where c.id=?1")
	Collection<Offer> offerOfCustomerEvaluateds(int idCustomer);

}
