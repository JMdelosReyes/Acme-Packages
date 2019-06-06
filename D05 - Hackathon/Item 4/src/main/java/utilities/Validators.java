
package utilities;

import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.CreditCard;

public class Validators {

	/**
	 * Returns <tt>true</tt> if every element of the collection is a valid URL.
	 * 
	 * @param col
	 *            is the Collection<String> to be tested
	 * @return <tt>true</tt> if every element of the collection is a valid URL and <tt>false</tt> in any other case
	 */
	public static boolean checkImageCollection(final Collection<String> col) {
		for (final String image : col) {
			final String pattern = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			final Pattern r = Pattern.compile(pattern);
			final Matcher m = r.matcher(image);
			if (!m.find())
				return false;
		}
		return true;
	}

	public static boolean checkCreditCard(final CreditCard cre) {

		boolean valid = true;

		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR) % 100;
		final int month = c.get(Calendar.MONTH) + 1;
		if ((cre.getMake() == null))
			valid = false;
		else if (cre.getExpirationYear() < year)
			valid = false;
		else if (cre.getExpirationYear() == year)
			if (cre.getExpirationMonth() < month)
				valid = false;

		if (cre.getHolderName() == null)
			valid = false;
		if (cre.getNumber() == null)
			valid = false;

		return valid;
	}

	public static boolean validEmail(final String email) {
		final String pattern = "^[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}|^[a-zA-Z0-9\\s]{1,}<[a-zA-Z0-9]{1,}@[a-zA-Z0-9\\.]{1,}>";
		final Pattern r = Pattern.compile(pattern);
		final Matcher m = r.matcher(email);
		return m.find();
	}

	public static boolean validPhone(final String phone) {
		final String pattern = "^[0-9]{4,9}";
		final Pattern r = Pattern.compile(pattern);
		final Matcher m = r.matcher(phone);
		return m.find();
	}

}
