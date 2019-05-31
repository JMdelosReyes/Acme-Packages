
package pojos;

import java.util.Collection;

public class AuditorPojo extends ActorPojo {

	private Collection<IssuePojo>			issues;
	private Collection<SolicitationPojo>	solicitations;


	public Collection<IssuePojo> getIssues() {
		return this.issues;
	}

	public void setIssues(final Collection<IssuePojo> issues) {
		this.issues = issues;
	}

	public Collection<SolicitationPojo> getSolicitations() {
		return this.solicitations;
	}

	public void setSolicitations(final Collection<SolicitationPojo> solicitations) {
		this.solicitations = solicitations;
	}

}
