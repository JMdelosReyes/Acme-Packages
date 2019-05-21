
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//Sponsorship que no han sido validados por el administrador
	@Query("select s from Sponsorship s where s.expirationDate=null")
	Collection<Sponsorship> findSponsorshipsNotValid();
}
