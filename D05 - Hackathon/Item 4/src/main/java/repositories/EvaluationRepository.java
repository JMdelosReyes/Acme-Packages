
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Query("select e from Evaluation e join e.customer c where c.id=?1")
	Collection<Evaluation> findEvaluationsByCustomer(int id);

	@Query("select e from Carrier c join c.offers o join o.evaluations e where c.id=?1")
	Collection<Evaluation> findEvaluationsByCarrier(int id);

	@Query("select o from Customer c join c.requests r join r.offer o where r.status!='SUBMITTED' and c.id=?1 and (select count(e) from Customer c2 join c2.evaluations e join e.offer o2 where o2.id=o.id and c2.id=?1)=0")
	Collection<Offer> findEvaluableOffersByCustomer(int id);

	@Modifying
	@Query("update Offer o set o.score=?2 where o.id=?1")
	public void updateOfferScore(int id, double score);

}
