
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//Sponsorship thar aren´t validated by the admin
	@Query("select s from Sponsorship s where s.expirationDate=null")
	Collection<Sponsorship> findSponsorshipsNotValid();

	//Sponsorships that are valid
	@Query("select s from Sponsorship s where s.valid=1")
	Collection<Sponsorship> findValidSponsorships();

	//Sponsorships that are expired
	@Query("select s from Sponsorship s where s.expirationDate < CURRENT_DATE")
	Collection<Sponsorship> findExpiredSponsorships();
}
