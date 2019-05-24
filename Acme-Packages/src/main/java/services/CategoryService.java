
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Package;
import domain.Solicitation;

;

@Service
@Transactional
public class CategoryService {

	//Managed repositories

	@Autowired
	private CategoryRepository	categoryRepository;

	//Suporting services
	@Autowired
	private ActorService		actorService;


	// Constructors

	public CategoryService() {
		super();
	}

	//Simple CRUD methods

	public Category create() {
		//Check if is an administrator
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(userAccount.getAuthorities().contains(auth));

		Category result;

		result = new Category();
		result.setEnglishDescription("");
		result.setSpanishDescription("");
		result.setEnglishName("");
		result.setSpanishName("");

		return result;

	}

	public Category save(final Category category) {
		Assert.notNull(category);

		Category result;
		UserAccount userAccount;
		//Check that is an administrator
		Assert.isTrue(this.actorService.findActorType().equals("Administrator"));
		if (category.getId() == 0) {
			result = this.categoryRepository.save(category);
		} else {
			Category old = this.categoryRepository.findOne(category.getId());
			Assert.isTrue(category.getSpanishName().equals(old.getSpanishName()));
			Assert.isTrue(category.getEnglishName().equals(old.getEnglishName()));
			result = this.categoryRepository.save(category);
		}
		Assert.notNull(result);
		return result;
	}
	public Collection<Category> findAll() {
		Collection<Category> result;
		result = this.categoryRepository.findAll();
		return result;
	}

	public Category findOne(final int categoryId) {
		Assert.isTrue(categoryId != 0);
		Category result;
		result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);

		//Check that is an administrator
		Assert.isTrue(this.actorService.findActorType().equals("Administrator"));

		Collection<Package> lista = this.categoryRepository.CategoryInUsePackage(category.getId());
		Collection<Solicitation> lista2 = this.categoryRepository.CategoryInUseSolicitation(category.getId());

		Assert.isTrue(lista.isEmpty());
		Assert.isTrue(lista2.isEmpty());

		this.categoryRepository.delete(category);
	}
	public void flush() {
		this.categoryRepository.flush();
	}

}
