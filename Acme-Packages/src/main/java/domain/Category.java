
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String	englishName;
	private String	spanishName;
	private String	englishDescription;
	private String	spanishDescription;


	@NotBlank
	@SafeHtml
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}
	@NotBlank
	@SafeHtml
	public String getSpanishName() {
		return this.spanishName;
	}

	public void setSpanishName(final String spanishName) {
		this.spanishName = spanishName;
	}
	@NotBlank
	@SafeHtml
	public String getEnglishDescription() {
		return this.englishDescription;
	}

	public void setEnglishDescription(final String englishDescription) {
		this.englishDescription = englishDescription;
	}
	@NotBlank
	@SafeHtml
	public String getSpanishDescription() {
		return this.spanishDescription;
	}

	public void setSpanishDescription(final String spanishDescription) {
		this.spanishDescription = spanishDescription;
	}

}
