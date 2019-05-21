
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {

}
