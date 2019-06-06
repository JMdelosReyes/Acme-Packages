
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Fare;
import domain.Vehicle;

public class OfferForm {

	private Integer				id;
	private boolean				finalMode;
	private Date				maxDateToRequest;
	private boolean				canceled;
	private Collection<Fare>	fares;
	private Vehicle				vehicle;


	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
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

	public Collection<Fare> getFares() {
		return this.fares;
	}

	public void setFares(Collection<Fare> fares) {
		this.fares = fares;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
