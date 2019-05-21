
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

}
