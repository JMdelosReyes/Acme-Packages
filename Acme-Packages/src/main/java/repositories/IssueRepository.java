
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;
import domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

	@Query("select r.issue from Customer c join c.requests r where c.id=?1")
	Collection<Issue> issuesOfCustomer(int idCustomer);

	@Query("select i from Issue i join i.offer o where o.id=?1")
	Collection<Issue> findIssuesOfOffer(int id);

	@Query("select a from Auditor a join a.issues i where i.id=?1")
	Auditor findAuditorOfIssue(int id);
}
