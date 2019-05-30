
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fare extends DomainEntity implements Cloneable {

	private double	maxWeight;
	private double	maxVolume;
	private double	price;


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException ex) {
		}
		return o;
	}

	@Min(1)
	public double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(final double maxWeight) {
		this.maxWeight = maxWeight;
	}

	@Min(1)
	public double getMaxVolume() {
		return this.maxVolume;
	}

	public void setMaxVolume(final double maxVolume) {
		this.maxVolume = maxVolume;
	}

	@Min(1)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@Transient
	public String getLabel() {
		return "Vol: " + this.getMaxVolume() + " Weight: " + this.getMaxWeight() + " Price: " + this.getPrice();
	}

}
