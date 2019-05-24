
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity implements Cloneable {

	private String				systemName;
	private String				banner;
	private String				englishMessage;
	private String				spanishMessage;
	private int					finderTime;
	private int					finderResults;
	private Collection<String>	spamWords;
	private String				countryCode;
	private Collection<String>	makes;
	private double				vat;
	private Collection<String>	messPriorities;


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
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getEnglishMessage() {
		return this.englishMessage;
	}

	public void setEnglishMessage(final String englishMessage) {
		this.englishMessage = englishMessage;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpanishMessage() {
		return this.spanishMessage;
	}

	public void setSpanishMessage(final String spanishMessage) {
		this.spanishMessage = spanishMessage;
	}

	@Range(min = 1, max = 24)
	public int getFinderTime() {
		return this.finderTime;
	}

	public void setFinderTime(final int finderTime) {
		this.finderTime = finderTime;
	}

	@Range(min = 10, max = 100)
	public int getFinderResults() {
		return this.finderResults;
	}

	public void setFinderResults(final int finderResults) {
		this.finderResults = finderResults;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getMakes() {
		return this.makes;
	}

	public void setMakes(final Collection<String> makes) {
		this.makes = makes;
	}

	@Range(min = 0, max = 100)
	public double getVat() {
		return this.vat;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getMessPriorities() {
		return this.messPriorities;
	}

	public void setMessPriorities(Collection<String> messPriorities) {
		this.messPriorities = messPriorities;
	}

}
