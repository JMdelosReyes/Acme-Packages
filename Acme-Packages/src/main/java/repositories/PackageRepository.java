
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Package;
import domain.Request;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

	@Query("select cus from Customer cus join cus.requests r join r.packages p where p.id=?1")
	Customer findCustomerByPackageId(int pacId);
	@Query("select r from Request r join r.packages p where p.id=?1")
	Request findRequestByPackageId(int pacId);

}
