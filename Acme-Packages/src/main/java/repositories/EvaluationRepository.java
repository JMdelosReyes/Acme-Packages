
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

}
