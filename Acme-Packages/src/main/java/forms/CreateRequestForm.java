
package forms;

import domain.Package;
import domain.Request;

public class CreateRequestForm {

	private Request	request;
	private Package	pac;


	public Request getRequest() {
		return this.request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Package getPac() {
		return this.pac;
	}

	public void setPac(Package pac) {
		this.pac = pac;
	}

}
