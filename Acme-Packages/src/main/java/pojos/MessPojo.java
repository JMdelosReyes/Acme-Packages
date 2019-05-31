
package pojos;

import java.util.Collection;
import java.util.Date;

public class MessPojo {

	private Date				sendDate;
	private String				subject;
	private String				body;
	private String				priority;
	private Collection<String>	tags;
	private String				sender;
	private Collection<String>	recipients;


	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Collection<String> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(Collection<String> recipients) {
		this.recipients = recipients;
	}

}
