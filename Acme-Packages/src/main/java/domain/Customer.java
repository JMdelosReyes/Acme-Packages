
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor implements Cloneable {

	private Collection<Request>		requests;
	private Collection<Evaluation>	evaluations;
	private Finder					finder;


	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@NotNull
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	public Collection<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}

}
