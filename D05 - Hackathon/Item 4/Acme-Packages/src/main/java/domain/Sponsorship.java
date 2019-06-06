
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@Table(indexes = {
	@Index(columnList = "valid, expirationDate")
})
public class Sponsorship extends DomainEntity implements Cloneable {

	private String	banner;
	private String	target;
	private boolean	valid;
	private int		count;
	private int		totalCount;
	private Date	expirationDate;


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

	@OptimisticLock(excluded = true)
	public int getCount() {
		return this.count;
	}

	public void setCount(final int count) {
		this.count = count;
	}
	@OptimisticLock(excluded = true)
	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
