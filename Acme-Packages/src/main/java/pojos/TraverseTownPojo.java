
package pojos;

import java.util.Date;

public class TraverseTownPojo {

	private int		number;
	private Date	estimatedDate;
	private boolean	currentTown;
	private String	town;


	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getEstimatedDate() {
		return this.estimatedDate;
	}

	public void setEstimatedDate(Date estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public boolean isCurrentTown() {
		return this.currentTown;
	}

	public void setCurrentTown(boolean currentTown) {
		this.currentTown = currentTown;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
