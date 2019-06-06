
package pojos;

import java.util.Collection;

import domain.CreditCard;

public class ActorPojo {

	private String							name;
	private String							surname;
	private String							middleName;
	private String							photo;
	private String							email;
	private String							phoneNumber;
	private String							address;
	private CreditCard						creditCard;

	private Collection<MessBoxPojo>			messageBoxes;
	private Collection<SocialProfilePojo>	socialProfiles;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Collection<MessBoxPojo> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(Collection<MessBoxPojo> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	public Collection<SocialProfilePojo> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(Collection<SocialProfilePojo> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

}
