
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	//Curriculum by professional record
	@Query("select c from Curriculum c join c.professionalRecords p where p.id = ?1")
	Curriculum findCurriculumByProRecord(int id);

	//Curriculum by professional record
	@Query("select c from Curriculum c join c.miscellaneousRecords p where p.id = ?1")
	Curriculum findCurriculumByMisRecord(int id);
}
