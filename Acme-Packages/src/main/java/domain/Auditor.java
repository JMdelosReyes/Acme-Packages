
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	private Collection<Issue>			issues;
	private Collection<Solicitation>	solicitations;


	@NotNull
	@OneToMany
	public Collection<Issue> getIssues() {
		return this.issues;
	}

	public void setIssues(final Collection<Issue> issues) {
		this.issues = issues;
	}

	@NotNull
	@OneToMany
	public Collection<Solicitation> getSolicitations() {
		return this.solicitations;
	}

	public void setSolicitations(final Collection<Solicitation> solicitations) {
		this.solicitations = solicitations;
	}

}
