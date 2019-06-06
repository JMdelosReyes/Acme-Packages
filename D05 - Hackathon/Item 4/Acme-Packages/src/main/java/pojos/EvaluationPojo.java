
package pojos;

import java.util.Date;

public class EvaluationPojo {

	private Date	moment;
	private Integer	mark;
	private String	comment;

	private String	offer;
	private String	customer;


	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Integer getMark() {
		return this.mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOffer() {
		return this.offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

}
