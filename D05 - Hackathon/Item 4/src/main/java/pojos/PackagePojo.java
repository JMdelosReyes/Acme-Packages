
package pojos;

import java.util.Collection;

public class PackagePojo {

	private double						weight;
	private double						height;
	private double						width;
	private double						length;
	private Double						price;
	private String						details;
	private Collection<CategoryPojo>	categories;


	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return this.length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Collection<CategoryPojo> getCategories() {
		return this.categories;
	}

	public void setCategories(Collection<CategoryPojo> categories) {
		this.categories = categories;
	}

}
