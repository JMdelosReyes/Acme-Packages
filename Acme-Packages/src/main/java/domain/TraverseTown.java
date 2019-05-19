
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class TraverseTown extends DomainEntity implements Cloneable {

	private int		order;
	private Date	stimatedDate;
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

	public int getOrder() {
		return this.order;
	}

	public void setOrder(final int order) {
		this.order = order;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStimatedDate() {
		return this.stimatedDate;
	}

	public void setStimatedDate(final Date stimatedDate) {
		this.stimatedDate = stimatedDate;
	}

	public boolean isCurrentTown() {
		return this.currentTown;
	}

	public void setCurrentTown(final boolean currentTown) {
		this.currentTown = currentTown;
	}

	@NotNull
	@ManyToOne
	public Town getTown() {
		return this.town;
	}

	public void setTown(final Town town) {
		this.town = town;
	}

}
