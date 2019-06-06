
package pojos;

import java.util.Collection;

public class MiscellaneousRecordPojo {

	private String				title;
	private String				attachment;

	private Collection<String>	comments;


	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getAttachments() {
		return this.attachment;
	}

	public void setAttachments(final String attachments) {
		this.attachment = attachments;
	}

}
