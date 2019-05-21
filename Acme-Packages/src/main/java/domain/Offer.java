
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity implements Cloneable {

	private String						ticker;
	private boolean						finalMode;
	private Date						maxDateToRequest;
	private boolean						canceled;
	private double						score;
	private double						totalPrice;
	private Collection<Fare>			fares;
	private Vehicle						vehicle;
	private Collection<Evaluation>		evaluations;
	private Collection<Request>			requests;
	private Collection<TraverseTown>	traverseTowns;


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException ex) {
		}
		return o;
	}

	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaxDateToRequest() {
		return this.maxDateToRequest;
	}

	public void setMaxDateToRequest(final Date maxDateToRequest) {
		this.maxDateToRequest = maxDateToRequest;
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public void setCanceled(final boolean canceled) {
		this.canceled = canceled;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@NotNull
	@ManyToMany
	public Collection<Fare> getFares() {
		return this.fares;
	}

	public void setFares(final Collection<Fare> fares) {
		this.fares = fares;
	}

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@NotNull
	@OneToMany(mappedBy = "offer")
	public Collection<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(final Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@NotNull
	@OneToMany(mappedBy = "offer")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@NotNull
	@OneToMany
	public Collection<TraverseTown> getTraverseTowns() {
		return this.traverseTowns;
	}

	public void setTraverseTowns(final Collection<TraverseTown> traverseTowns) {
		this.traverseTowns = traverseTowns;
	}

}
