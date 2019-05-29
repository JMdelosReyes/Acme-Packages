
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Carrier;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Town;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select s from Sponsor s join s.sponsorships ss where ss.valid=1")
	Collection<Sponsor> validSponsorshipsSponsor();

	@Query("select sum(ss.count) from Sponsor s join s.sponsorships ss where ss.valid=1 and s.id=?1")
	Integer numValidSponsorshipShowBySponsor(int id);

	@Query("select ss from Sponsor s join s.sponsorships ss where ss.valid=1 and s.id=?1")
	Collection<Sponsorship> sponsorSetCount0(int id);

	@Query("select c from Carrier c where c.offers.size>0")
	Collection<Carrier> findCarriersWithOffer();

	@Query("select s from Sponsorship s where s.expirationDate < CURRENT_DATE")
	Collection<Sponsorship> findInvalidSp();

	@Query("select avg(o.score) from Carrier c join c.offers o where c.id=?1")
	Double AvgScoreOffersFromCarrier(int id);

	//	@Modifying
	//	@Query("update Carrier a set a.score=(select avg(o.score) from Carrier ca join ca.offers o where ca.id=a.id)")
	//	void computeScoreCarrier();

	//SET SPAMMERS

	@Modifying
	@Query("update Carrier a set a.spammer=1 where ((select count(m) from MessBox mb join mb.messages m where m.sender.id=a.id and " + "mb.name='Spam Box')/(select count(m2) from Mess m2 where m2.sender.id=a.id)+0.0)>=0.10")
	void setCarrierSpammer();

	@Modifying
	@Query("update Auditor a set a.spammer=1 where ((select count(m) from MessBox mb join mb.messages m where m.sender.id=a.id and " + "mb.name='Spam Box')/(select count(m2) from Mess m2 where m2.sender.id=a.id)+0.0)>=0.10")
	void setAuditorSpammer();

	@Modifying
	@Query("update Sponsor a set a.spammer=1 where ((select count(m) from MessBox mb join mb.messages m where m.sender.id=a.id and " + "mb.name='Spam Box')/(select count(m2) from Mess m2 where m2.sender.id=a.id)+0.0)>=0.10")
	void setSponsorSpammer();

	@Modifying
	@Query("update Customer a set a.spammer=1 where ((select count(m) from MessBox mb join mb.messages m where m.sender.id=a.id and " + "mb.name='Spam Box')/(select count(m2) from Mess m2 where m2.sender.id=a.id)+0.0)>=0.10")
	void setCustomerSpammer();

	@Modifying
	@Query("update Carrier a set a.spammer=0")
	void setCarrierNotSpammer();

	@Modifying
	@Query("update Auditor a set a.spammer=0")
	void setAuditorNotSpammer();

	@Modifying
	@Query("update Sponsor a set a.spammer=0")
	void setSponsorNotSpammer();

	@Modifying
	@Query("update Customer a set a.spammer=0")
	void setCustomerNotSpammer();

	//Find spammers
	@Query("select a from Actor a where a.spammer=true")
	Collection<Actor> findPossibleBans();

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
	@Query("select avg(e.mark) from Customer c join c.evaluations e")
	Double avgEvaluationByCustomer();

	@Query("select min(e.mark) from Customer c join c.evaluations e")
	Double minEvaluationByCustomer();

	@Query("select max(e.mark) from Customer c join c.evaluations e")
	Double maxEvaluationByCustomer();

	@Query("select stddev(e.mark) from Customer c join c.evaluations e")
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
	@Query("select t from TraverseTown tt join tt.town t group by tt.town order by count(tt) desc")
	Collection<Town> top5MostVisitedTowns();

	//The ratio of empty versus non-empty finders.
	@Query("select (count(f2)/(select count(f1) from Finder f1)+0.0) from Finder f2 where f2.offers.size=0")
	Double RatioEmptyFinders();

	@Query("select (count(f2)/(select count(f1) from Finder f1)+0.0) from Finder f2 where f2.offers.size>0")
	Double RatioNonEmptyFinders();

	//TODO No la he podido probar
	//The listing of auditors who have got at least 10% of issues closed above the average. 
	@Query("select a from Auditor a join a.issues i where ((select count(i2) from Auditor a2 join a2.issues i2 where a2.id=a.id and i2.closed=1)/(a.issues.size)+0.0)>(select avg(1.0*(select count(i3) from Auditor a4 join a4.issues i3 where i3.closed=1)) from Auditor a3)*1.1")
	Collection<Auditor> AuditorsIwth10ClosesIssuesAboveAVG();

	//The ratio of closed versus non-closed issues.
	@Query("select count(i)/(select count(i2) from Issue i2)+0.0 from Issue i where i.closed=0")
	Double ratioNonClosedIssue();
	@Query("select count(i)/(select count(i2) from Issue i2)+0.0 from Issue i where i.closed=1")
	Double ratioClosedIssue();

}
