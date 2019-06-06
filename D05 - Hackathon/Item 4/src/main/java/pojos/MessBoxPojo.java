
package pojos;

import java.util.Collection;

public class MessBoxPojo {

	private String					name;
	private Collection<MessPojo>	messages;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<MessPojo> getMessages() {
		return this.messages;
	}

	public void setMessages(Collection<MessPojo> messages) {
		this.messages = messages;
	}

}
