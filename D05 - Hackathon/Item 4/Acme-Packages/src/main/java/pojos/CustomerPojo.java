
package pojos;

import java.util.Collection;

public class CustomerPojo extends ActorPojo {

	private Collection<RequestPojo>		requests;
	private Collection<EvaluationPojo>	evaluations;


	public Collection<RequestPojo> getRequests() {
		return this.requests;
	}

	public void setRequests(Collection<RequestPojo> requests) {
		this.requests = requests;
	}

	public Collection<EvaluationPojo> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Collection<EvaluationPojo> evaluations) {
		this.evaluations = evaluations;
	}

}
