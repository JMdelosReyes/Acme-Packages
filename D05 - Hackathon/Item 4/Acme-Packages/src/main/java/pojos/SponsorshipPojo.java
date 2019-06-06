
package pojos;

import java.util.Date;

public class SponsorshipPojo {

	private String	banner;
	private String	target;
	private Date	expirationDate;
	private boolean	valid;


	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isValid() {
		return this.valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
