
package pojos;

import java.util.Collection;

public class CarrierPojo extends ActorPojo {

	private String						vat;
	private double						score;
	private Collection<CurriculumPojo>	curricula;
	private Collection<FarePojo>		fares;
	private Collection<OfferPojo>		offers;
	private Collection<VehiclePojo>		vehicles;


	public String getVat() {
		return this.vat;
	}

	public void setVat(final String vat) {
		this.vat = vat;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	public Collection<CurriculumPojo> getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Collection<CurriculumPojo> curricula) {
		this.curricula = curricula;
	}

	public Collection<FarePojo> getFares() {
		return this.fares;
	}

	public void setFares(final Collection<FarePojo> fares) {
		this.fares = fares;
	}

	public Collection<OfferPojo> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<OfferPojo> offers) {
		this.offers = offers;
	}

	public Collection<VehiclePojo> getVehicles() {
		return this.vehicles;
	}

	public void setVehicles(final Collection<VehiclePojo> vehicles) {
		this.vehicles = vehicles;
	}

}
