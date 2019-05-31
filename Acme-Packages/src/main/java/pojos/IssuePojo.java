
package pojos;

import java.util.Collection;
import java.util.Date;

public class IssuePojo {

	private String					ticker;
	private Date					moment;
	private boolean					closed;
	private String					description;

	private Collection<CommentPojo>	comments;
	private OfferPojo				offer;


	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public boolean isClosed() {
		return this.closed;
	}

	public void setClosed(final boolean closed) {
		this.closed = closed;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Collection<CommentPojo> getComments() {
		return this.comments;
	}

	public void setComments(Collection<CommentPojo> comments) {
		this.comments = comments;
	}

	public OfferPojo getOffer() {
		return this.offer;
	}

	public void setOffer(final OfferPojo offer) {
		this.offer = offer;
	}

}
