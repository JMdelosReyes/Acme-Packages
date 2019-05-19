
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	private String					nif;
	private Collection<Sponsorship>	sponsorships;


	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@Pattern(regexp = "(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])")
	public String getNif() {
		return this.nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
