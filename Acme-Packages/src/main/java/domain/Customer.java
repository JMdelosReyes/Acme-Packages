
package domain;

import java.util.Collection;

public class Customer extends Actor implements Cloneable {

	private Collection<Request>		requests;
	private Collection<Evaluation>	evaluations;
	private Finder					finder;


	//TENGO QUE PONER LAS ANOTACIONES TODAVIA
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	public Collection<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}

}
