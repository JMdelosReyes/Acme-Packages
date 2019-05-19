
package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public UserAccount create() {
		UserAccount userAccount;

		userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority au = new Authority();
		au.setAuthority(Authority.CARRIER);
		authorities.add(au);
		userAccount.setAuthorities(authorities);

		return userAccount;
	}
	public UserAccount save(final UserAccount userAccount) {
		UserAccount result;
		if (userAccount.getId() == 0) {
			Assert.isNull(this.userAccountRepository.findByUsername(userAccount.getUsername()));
		}
		result = this.userAccountRepository.save(userAccount);
		Assert.notNull(result);
		return result;
	}

	public UserAccount findOne(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		UserAccount result;

		result = this.userAccountRepository.findOne(userAccountId);

		return result;
	}
	public Collection<UserAccount> findAll() {
		Collection<UserAccount> result;
		result = this.userAccountRepository.findAll();
		return result;
	}

	// Other business methods -------------------------------------------------
	public UserAccount findByUsername(final String username) {
		Assert.isTrue(username != "");
		Assert.notNull(username);
		//Assert.isTrue(this.actorService.uniqueUsername(username) == true);

		return this.userAccountRepository.findByUsername(username);
	}
	public Collection<String> findAllUsernames() {
		return this.userAccountRepository.findAllUsernames();
	}
}
