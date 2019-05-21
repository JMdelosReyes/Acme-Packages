
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

@Service
@Transactional
public class CategoryService {

	//Managed repositories

	@Autowired
	private CategoryRepository	categoryRepository;


	//Suporting services

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
		userAccount = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(userAccount.getAuthorities().contains(auth));

		result = this.categoryRepository.save(category);
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
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(userAccount.getAuthorities().contains(auth));

		this.categoryRepository.delete(category);

	}

	public void flush() {
		this.categoryRepository.flush();
	}

}
