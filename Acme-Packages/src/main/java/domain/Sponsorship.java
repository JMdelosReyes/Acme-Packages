
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String	banner;
	private String	target;
	private boolean	valid;
	private int		count;
	private int		totalCount;
	private Date	expirationDate;


	@NotBlank
	@URL
	@SafeHtml
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}
	@NotBlank
	@URL
	@SafeHtml
	public String getTarget() {
		return this.target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public boolean isValid() {
		return this.valid;
	}

	public void setValid(final boolean valid) {
		this.valid = valid;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
