
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "plate")
})
public class Vehicle extends DomainEntity implements Cloneable {

	private String						type;
	private String						plate;
	private Double						maxVolume;
	private Double						maxWeight;
	private Collection<String>			pictures;
	private String						comment;
	private Collection<Solicitation>	solicitations;

	public static final String			CAR			= "CAR";
	public static final String			TRUCK		= "TRUCK";
	public static final String			VAN			= "VAN";
	public static final String			MOTORCYCLE	= "MOTORCYCLE";


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException ex) {
		}
		return o;
	}

	@NotBlank
	@Pattern(regexp = "^" + Vehicle.CAR + "|" + Vehicle.TRUCK + "|" + Vehicle.VAN + "|" + Vehicle.MOTORCYCLE + "$")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@NotBlank
	@SafeHtml
	public String getPlate() {
		return this.plate;
	}

	public void setPlate(final String plate) {
		this.plate = plate;
	}

	@NotNull
	@Min(1)
	public Double getMaxVolume() {
		return this.maxVolume;
	}

	public void setMaxVolume(final Double maxVolume) {
		this.maxVolume = maxVolume;
	}

	@NotNull
	@Min(1)
	public Double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(final Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@SafeHtml
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	@OneToMany
	public Collection<Solicitation> getSolicitations() {
		return this.solicitations;
	}

	public void setSolicitations(final Collection<Solicitation> solicitations) {
		this.solicitations = solicitations;
	}

}
