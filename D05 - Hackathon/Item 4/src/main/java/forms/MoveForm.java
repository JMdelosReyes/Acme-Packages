
package forms;

import javax.validation.constraints.NotNull;

import domain.MessBox;

public class MoveForm {

	private int		messageId;
	private MessBox	source;
	private MessBox	destination;


	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(final int messageId) {
		this.messageId = messageId;
	}
	@NotNull
	public MessBox getSource() {
		return this.source;
	}

	public void setSource(final MessBox source) {
		this.source = source;
	}
	@NotNull
	public MessBox getDestination() {
		return this.destination;
	}

	public void setDestination(final MessBox destination) {
		this.destination = destination;
	}

}
