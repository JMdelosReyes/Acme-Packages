
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

	@Query("select r.issue from Customer c join c.requests r where c.id=?1")
	Collection<Issue> issuesOfCustomer(int idCustomer);
}
