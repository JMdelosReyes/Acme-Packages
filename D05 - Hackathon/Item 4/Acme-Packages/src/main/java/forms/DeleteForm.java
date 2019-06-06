
package forms;

import javax.validation.constraints.NotNull;

import domain.MessBox;

public class DeleteForm {

	private int		idMessage;
	private MessBox	container;


	public int getIdMessage() {
		return this.idMessage;
	}

	public void setIdMessage(final int idMessage) {
		this.idMessage = idMessage;
	}
	@NotNull
	public MessBox getContainer() {
		return this.container;
	}

	public void setContainer(final MessBox container) {
		this.container = container;
	}

}
