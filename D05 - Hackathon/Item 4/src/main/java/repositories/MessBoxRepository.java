
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessBox;

@Repository
public interface MessBoxRepository extends JpaRepository<MessBox, Integer> {

	@Query("select mb from Actor a join a.messageBoxes mb where mb.name like ?1 AND a.id=?2")
	MessBox findMessageBoxByNameAndActorId(String name, int actorId);

	@Query("select mb from MessBox mb join mb.messages m where m.id=?1")
	Collection<MessBox> findByMessageId(int messageId);

	@Query("select a.messageBoxes from Actor a where a.id=?1")
	Collection<MessBox> findMessageBoxesByActorId(int actorId);

	@Query("select mb from Actor a join a.messageBoxes mb where mb.isSystem=true AND a.id=?1")
	Collection<MessBox> findSystemBoxes(int actorId);

	@Query("select mb from Actor a join a.messageBoxes mb join mb.parent par where par.id=?1 AND a.id=?2")
	Collection<MessBox> findChildrenMessBoxesByParent(int messBoxId, int actorId);

	@Query("select mb from Actor a join a.messageBoxes mb where a.id=?1 and mb.parent=null")
	Collection<MessBox> findMessBoxesNoChildren(int actorId);

	@Query("select mb from Actor a join a.messageBoxes mb where a.id=?1 and (select count(mb2) from MessBox mb2 where mb2.parent=mb)=0")
	Collection<MessBox> findMessBoxesNoChildrenByActorId(int actorId);
}
