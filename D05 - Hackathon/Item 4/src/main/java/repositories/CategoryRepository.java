
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Package;
import domain.Solicitation;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select s from Solicitation s where s.category.id=?1")
	Collection<Solicitation> CategoryInUseSolicitation(int categoryId);

	@Query("select p from Package p join p.categories s where s.id=?1")
	Collection<Package> CategoryInUsePackage(int categoryId);

	@Query("select c.spanishName from Category c")
	Collection<String> SpanishCategories();

	@Query("select c.englishName from Category c")
	Collection<String> englishCategories();
}
