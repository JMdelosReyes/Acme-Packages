
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;
import domain.Issue;
import domain.Request;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

	@Query("select r.issue from Customer c join c.requests r where c.id=?1")
	Collection<Issue> issuesOfCustomer(int idCustomer);

	@Query("select i from Issue i join i.offer o where o.id=?1")
	Collection<Issue> findIssuesOfOffer(int id);

	@Query("select a from Auditor a join a.issues i where i.id=?1")
	Auditor findAuditorOfIssue(int id);

	@Query("select i from Issue i join i.offer o where (select count(o2) from Carrier c join c.offers o2 where c.id=?1 and o.id=o2.id)>0")
	Collection<Issue> findIssuesOfCarrier(int id);

	@Query("select i from Issue i where i not in (select i2 from Auditor a join a.issues i2)")
	Collection<Issue> findUnassigned();

	@Query("select r from Request r join r.issue i where i.id=?1")
	Request findByIssue(int id);
}
