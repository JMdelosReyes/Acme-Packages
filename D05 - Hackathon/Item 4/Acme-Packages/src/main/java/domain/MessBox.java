
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "name, isSystem")
})
public class MessBox extends DomainEntity implements Cloneable {

	private String				name;
	private boolean				isSystem;
	private MessBox				parent;
	private Collection<Mess>	messages;


	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException e) {
		}
		return o;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(final boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Valid
	@ManyToOne(optional = true)
	public MessBox getParent() {
		return this.parent;
	}

	public void setParent(final MessBox parent) {
		this.parent = parent;
	}

	@NotNull
	@ManyToMany
	public Collection<Mess> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Mess> messages) {
		this.messages = messages;
	}

}
