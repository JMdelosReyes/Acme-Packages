
package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BanForm {

	private int	idActor;


	@NotNull
	@Min(1)
	public int getIdActor() {
		return this.idActor;
	}

	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}

}
