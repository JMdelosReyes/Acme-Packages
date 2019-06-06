
package forms;

import javax.validation.constraints.NotNull;

import domain.MessBox;

public class CopyForm {

	private int		messageId;
	private MessBox	destination;


	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	@NotNull
	public MessBox getDestination() {
		return this.destination;
	}

	public void setDestination(final MessBox destination) {
		this.destination = destination;
	}

}
