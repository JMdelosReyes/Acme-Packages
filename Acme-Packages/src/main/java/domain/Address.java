
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Address extends DomainEntity {

	private String	streetAddress;
	private String	comment;
	private Town	town;


	@NotBlank
	@SafeHtml
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	@NotBlank
	@SafeHtml
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne(optional = false)
	@Valid
	public Town getTown() {
		return this.town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

}
