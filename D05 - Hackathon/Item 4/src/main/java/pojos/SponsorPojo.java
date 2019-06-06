
package pojos;

import java.util.Collection;

public class SponsorPojo extends ActorPojo {

	private String						nif;
	private Collection<SponsorshipPojo>	sponsorships;


	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Collection<SponsorshipPojo> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(Collection<SponsorshipPojo> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
