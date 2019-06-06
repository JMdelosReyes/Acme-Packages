
package pojos;

import java.util.Date;

public class CommentPojo {

	private Date	moment;
	private String	userComment;
	private String	username;


	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getUserComment() {
		return this.userComment;
	}

	public void setUserComment(final String userComment) {
		this.userComment = userComment;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
