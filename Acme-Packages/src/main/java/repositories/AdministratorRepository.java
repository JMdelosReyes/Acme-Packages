
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Carrier;
import domain.Sponsorship;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//The average, the minimum, the maximum, and the standard deviation of times that a sponsorship has been shown.
	@Query("select avg(sp.totalCount) from Sponsorship sp")
	Double avgSPShow();

	@Query("select min(sp.totalCount) from Sponsorship sp")
	Double minSPShow();

	@Query("select max(sp.totalCount) from Sponsorship sp")
	Double maxSPShow();

	@Query("select stddev(sp.totalCount) from Sponsorship sp")
	Double stddevSPShow();

	//The average, the minimum, the maximum, and the standard deviation of scores from registered carriers.
	@Query("select avg(c.score) from Carrier c")
	Double avgScoreCarriers();

	@Query("select min(c.score) from Carrier c")
	Double minScoreCarriers();

	@Query("select max(c.score) from Carrier c")
	Double maxScoreCarriers();

	@Query("select stddev(c.score) from Carrier c")
	Double stddevScoreCarriers();

	//The average, the minimum, the maximum, and the standard deviation of evaluations made by customers.
	@Query("select avg(c.evaluations.size) from Customer c")
	Double avgEvaluationByCustomer();

	@Query("select min(c.evaluations.size) from Customer c")
	Double minEvaluationByCustomer();

	@Query("select max(c.evaluations.size) from Customer c")
	Double maxEvaluationByCustomer();

	@Query("select stddev(c.evaluations.size) from Customer c")
	Double stddevEvaluationByCustomer();

	//The average, the minimum, the maximum, and the standard deviation of comments per issues.
	@Query("select avg(i.comments.size) from Issue i")
	Double avgCommentsPerIssue();

	@Query("select min(i.comments.size) from Issue i")
	Double minCommentsPerIssue();

	@Query("select max(i.comments.size) from Issue i")
	Double maxCommentsPerIssue();

	@Query("select stddev(i.comments.size) from Issue i")
	Double stddevCommentsPerIssue();

	//Top-3 most shown sponsorships.
	@Query("select sp from Sponsorship sp order by sp.totalCount desc")
	Collection<Sponsorship> top3ShownSponsorships();

	//Top-3 carriers with the highest score.
	@Query("select c from Carrier c order by c.score desc")
	Collection<Carrier> top3CarriersWithHigherScore();

	//Top-5 most visited towns.
	//TODO

	//The ratio of empty versus non-empty finders.
	@Query("select (count(f2)/(select count(f1) from Finder f1)+0.0) from Finder f2 where f2.offers.size=0")
	Double RatioEmptyFinders();

	@Query("select (count(f2)/(select count(f1) from Finder f1)+0.0) from Finder f2 where f2.offers.size>0")
	Double RatioNonEmptyFinders();

	//The listing of auditors who have got at least 10% of issues closed above the average. 
	//TODO

	//The ratio of closed versus non-closed issues.

	@Query("select count(i)/(select count(i2) from Issue i2)+0.0 from Issue i where i.closed=0")
	Double ratioNonClosedIssue();
	@Query("select count(i)/(select count(i2) from Issue i2)+0.0 from Issue i where i.closed=1")
	Double ratioClosedIssue();

}
