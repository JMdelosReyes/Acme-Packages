
package pojos;

import java.util.Collection;
import java.util.Date;

public class SolicitationPojo {

	private Date				moment;
	private String				status;
	private Date				startDate;
	private Date				endDate;
	private Collection<String>	comments;

	private String				category;

	public static final String	PENDING		= "PENDING";
	public static final String	REJECTED	= "REJECTED";
	public static final String	ACCEPTED	= "ACCEPTED";


	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
