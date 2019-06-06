
package forms;

import java.util.Collection;

import domain.Category;

public class AddPackageForm {

	private String					requestId;
	private double					weight;
	private double					height;
	private double					width;
	private double					length;
	private String					details;
	private Collection<Category>	categories;


	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

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

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

}
