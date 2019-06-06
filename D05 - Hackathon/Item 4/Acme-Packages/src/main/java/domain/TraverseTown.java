
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class TraverseTown extends DomainEntity implements Cloneable {

	private int		number;
	private Date	estimatedDate;
	private Date	arrivalDate;
	private boolean	currentTown;
	private Town	town;


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException ex) {
		}
		return o;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEstimatedDate() {
		return this.estimatedDate;
	}

	public void setEstimatedDate(final Date estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public boolean isCurrentTown() {
		return this.currentTown;
	}

	public void setCurrentTown(final boolean currentTown) {
		this.currentTown = currentTown;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Town getTown() {
		return this.town;
	}

	public void setTown(final Town town) {
		this.town = town;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getArrivalDate() {
		return this.arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

}
