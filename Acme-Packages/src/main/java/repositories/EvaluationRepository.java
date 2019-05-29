
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

	@Query("select e from Customer c join c.requests r join r.offer o join o.evaluations e where c.id=?1")
	Collection<Evaluation> findEvaluationsByCustomer(int id);

	@Query("select e from Carrier c join c.offers o join o.evaluations e where c.id=?1")
	Collection<Evaluation> findEvaluationsByCarrier(int id);

	@Query("select o from Offer o where (select count(o2) from Evaluation e join e.customer c join e.offer o2 where c.id=?1 and o.id=o2.id)=0")
	Collection<Offer> findEvaluableOffersByCustomer(int id);

}
