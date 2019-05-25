
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Package extends DomainEntity implements Cloneable {

	private double					weight;
	private double					height;
	private double					width;
	private double					length;
	private Double					price;
	private String					description;
	private Collection<Category>	categories;


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException ex) {
		}
		return o;
	}

	@Min(100)
	public double getWeight() {
		return this.weight;
	}

	public void setWeight(final double weight) {
		this.weight = weight;
	}

	@Min(1)
	public double getHeight() {
		return this.height;
	}

	public void setHeight(final double height) {
		this.height = height;
	}

	@Min(1)
	public double getWidth() {
		return this.width;
	}

	public void setWidth(final double width) {
		this.width = width;
	}

	@Min(1)
	public double getLength() {
		return this.length;
	}

	public void setLength(final double length) {
		this.length = length;
	}

	@Min(1)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotEmpty
	@ManyToMany
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

}
