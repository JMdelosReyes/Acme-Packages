
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mess;

@Repository
public interface MessRepository extends JpaRepository<Mess, Integer> {

	@Query("select m from Actor a join a.messageBoxes mb join mb.messages m where a.id=?1")
	Collection<Mess> findOwnMessages(int actorId);

}
