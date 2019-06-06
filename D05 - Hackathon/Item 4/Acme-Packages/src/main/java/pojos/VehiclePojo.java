
package pojos;

import java.util.Collection;

public class VehiclePojo {

	private String							type;
	private String							plate;
	private Double							maxVolume;
	private Double							maxWeight;
	private Collection<String>				pictures;
	private String							comment;
	private Collection<SolicitationPojo>	solicitations;


	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(final String plate) {
		this.plate = plate;
	}

	public Double getMaxVolume() {
		return this.maxVolume;
	}

	public void setMaxVolume(final Double maxVolume) {
		this.maxVolume = maxVolume;
	}

	public Double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(final Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public Collection<SolicitationPojo> getSolicitations() {
		return this.solicitations;
	}

	public void setSolicitations(final Collection<SolicitationPojo> solicitations) {
		this.solicitations = solicitations;
	}

}
