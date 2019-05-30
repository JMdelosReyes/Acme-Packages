
package pojos;

import java.util.Collection;
import java.util.Date;

public class OfferPojo {

	private String							ticker;
	private boolean							finalMode;
	private Date							maxDateToRequest;
	private boolean							canceled;
	private double							score;
	private double							totalPrice;
	private Collection<FarePojo>			fares;
	private String							vehicle;
	private Collection<EvaluationPojo>		evaluations;
	private Collection<RequestPojo>			requests;
	private Collection<TraverseTownPojo>	traverseTowns;


	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}

	public Date getMaxDateToRequest() {
		return this.maxDateToRequest;
	}

	public void setMaxDateToRequest(Date maxDateToRequest) {
		this.maxDateToRequest = maxDateToRequest;
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Collection<FarePojo> getFares() {
		return this.fares;
	}

	public void setFares(Collection<FarePojo> fares) {
		this.fares = fares;
	}

	public String getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public Collection<EvaluationPojo> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Collection<EvaluationPojo> evaluations) {
		this.evaluations = evaluations;
	}

	public Collection<RequestPojo> getRequests() {
		return this.requests;
	}

	public void setRequests(Collection<RequestPojo> requests) {
		this.requests = requests;
	}

	public Collection<TraverseTownPojo> getTraverseTowns() {
		return this.traverseTowns;
	}

	public void setTraverseTowns(Collection<TraverseTownPojo> traverseTowns) {
		this.traverseTowns = traverseTowns;
	}

}
