
package pojos;

import java.util.Collection;

public class CurriculumPojo {

	private String								fullName;
	private String								photo;
	private String								phoneNumber;
	private String								email;

	private Collection<ProfessionalRecordPojo>	professionalRecord;
	private Collection<MiscellaneousRecordPojo>	miscellaneousRecord;


	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<ProfessionalRecordPojo> getProfessionalRecord() {
		return this.professionalRecord;
	}

	public void setProfessionalRecord(Collection<ProfessionalRecordPojo> professionalRecord) {
		this.professionalRecord = professionalRecord;
	}

	public Collection<MiscellaneousRecordPojo> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(Collection<MiscellaneousRecordPojo> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

}
