
package pojos;

import java.util.Collection;
import java.util.Date;

public class RequestPojo {

	private String					ticker;
	private Date					moment;
	private String					description;
	private double					maxPrice;
	private Date					deadline;
	private boolean					finalMode;
	private double					volume;
	private double					weight;
	private String					status;
	private String					streetAddress;
	private String					comment;

	private String					town;
	private String					offer;
	private Collection<PackagePojo>	packages;
	private String					issue;

	public static final String		ACCEPTED	= "ACCEPTED";
	public static final String		REJECTED	= "REJECTED";
	public static final String		DELIVERED	= "DELIVERED";
	public static final String		SUBMITTED	= "SUBMITTED";


	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}

	public double getVolume() {
		return this.volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getOffer() {
		return this.offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Collection<PackagePojo> getPackages() {
		return this.packages;
	}

	public void setPackages(Collection<PackagePojo> packages) {
		this.packages = packages;
	}

	public String getIssue() {
		return this.issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

}
