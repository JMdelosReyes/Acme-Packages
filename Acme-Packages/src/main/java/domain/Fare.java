
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fare extends DomainEntity {

	private double	minWeight;
	private double	maxWeight;
	private double	minVolume;
	private double	maxVolume;
	private double	price;


	@Min(0)
	public double getMinWeight() {
		return this.minWeight;
	}

	public void setMinWeight(final double minWeight) {
		this.minWeight = minWeight;
	}

	public double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(final double maxWeight) {
		this.maxWeight = maxWeight;
	}
	@Min(0)
	public double getMinVolume() {
		return this.minVolume;
	}

	public void setMinVolume(final double minVolume) {
		this.minVolume = minVolume;
	}

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

}
