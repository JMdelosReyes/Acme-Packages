
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Carrier extends Actor implements Cloneable {

	private String					vat;
	private double					score;
	private Collection<Curriculum>	curricula;
	private Collection<Fare>		fares;
	private Collection<Offer>		offers;
	private Collection<Vehicle>		vehicles;


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
	@SafeHtml
	@Pattern(
		regexp = "^(ATU[0-9]{8}|BE[01][0-9]{9}|BG[0-9]{9,10}|HR[0-9]{11}|CY[A-Z0-9]{9}|CZ[0-9]{8,10}|DK[0-9]{8}|EE[0-9]{9}|FI[0-9]{8}|FR[0-9A-Z]{2}[0-9]{9}|DE[0-9]{9}|EL[0-9]{9}|HU[0-9]{8}|IE([0-9]{7}[A-Z]{1,2}|[0-9][A-Z][0-9]{5}[A-Z])|IT[0-9]{11}|LV[0-9]{11}|LT([0-9]{9}|[0-9]{12})|LU[0-9]{8}|MT[0-9]{8}|NL[0-9]{9}B[0-9]{2}|PL[0-9]{10}|PT[0-9]{9}|RO[0-9]{2,10}|SK[0-9]{10}|SI[0-9]{8}|ES[A-Z]([0-9]{8}|[0-9]{7}[A-Z])|SE[0-9]{12}|GB([0-9]{9}|[0-9]{12}|GD[0-4][0-9]{2}|HA[5-9][0-9]{2}))$")
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

	@NotNull
	@OneToMany
	public Collection<Curriculum> getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Collection<Curriculum> curricula) {
		this.curricula = curricula;
	}

	@NotNull
	@OneToMany
	public Collection<Fare> getFares() {
		return this.fares;
	}

	public void setFares(final Collection<Fare> fares) {
		this.fares = fares;
	}

	@NotNull
	@OneToMany
	public Collection<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<Offer> offers) {
		this.offers = offers;
	}

	@NotNull
	@OneToMany
	public Collection<Vehicle> getVehicles() {
		return this.vehicles;
	}

	public void setVehicles(final Collection<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

}
