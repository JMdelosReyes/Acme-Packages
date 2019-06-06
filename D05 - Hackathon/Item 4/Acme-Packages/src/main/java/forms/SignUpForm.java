
package forms;

import security.UserAccount;
import domain.CreditCard;

public class SignUpForm {

	private String		name;
	private String		surname;
	private String		middleName;
	private String		photo;
	private String		email;
	private String		phoneNumber;
	private String		address;
	private CreditCard	creditCard;

	private String		vat;

	private String		nif;

	private UserAccount	userAccount;
	private String		passConfirmation;
	private String		actorType;

	private boolean		termsAccepted;


	public SignUpForm() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
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

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassConfirmation() {
		return this.passConfirmation;
	}

	public void setPassConfirmation(final String passConfirmation) {
		this.passConfirmation = passConfirmation;
	}

	public String getActorType() {
		return this.actorType;
	}

	public void setActorType(final String actorType) {
		this.actorType = actorType;
	}

	public boolean isTermsAccepted() {
		return this.termsAccepted;
	}

	public void setTermsAccepted(final boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getVat() {
		return this.vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
