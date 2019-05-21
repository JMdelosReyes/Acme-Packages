
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "ticker")
})
public class Request extends DomainEntity implements Cloneable {

	private String				ticker;
	private Date				moment;
	private String				description;
	private double				maxPrice;
	private Date				deadline;
	private boolean				finalMode;
	private double				volume;
	private double				weight;
	private String				status;

	private Offer				offer;
	private Collection<Package>	packages;
	private Address				address;
	private Issue				issue;

	public static final String	ACCEPTED	= "ACCEPTED";
	public static final String	REJECTED	= "REJECTED";
	public static final String	DELIVERED	= "DELIVERED";
	public static final String	SUBMITTED	= "SUBMITTED";


	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Min(1)
	public double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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

	@SafeHtml
	@Pattern(regexp = "^" + Request.ACCEPTED + "|" + Request.REJECTED + "|" + Request.DELIVERED + "|" + Request.SUBMITTED)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Valid
	@ManyToOne(optional = true)
	public Offer getOffer() {
		return this.offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	@NotEmpty
	@OneToMany
	public Collection<Package> getPackages() {
		return this.packages;
	}

	public void setPackages(Collection<Package> packages) {
		this.packages = packages;
	}

	@Valid
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	@Valid
	@OneToOne(optional = true)
	public Issue getIssue() {
		return this.issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

}
