
package utilities;

import java.util.UUID;

import org.joda.time.LocalDateTime;

public class Tickers {

	public static String generateTicker(String company) {
		String result = "";

		while (company.length() < 4) {
			company += "X";
		}

		if (company.length() > 4) {
			company = company.substring(0, 4);
		}

		final int numb = (int) (Math.random() * 9999);
		String numbS = Integer.toString(numb);

		while (numbS.length() < 4) {
			numbS = "0" + numbS;
		}

		result = result + company.toUpperCase() + "-" + numbS;
		return result;
	}
	public static String generateTicker() {
		String result = "";
		final LocalDateTime now = LocalDateTime.now();
		String year = "" + now.getYear();
		year = year.substring(2);

		final int month = now.getMonthOfYear();

		String moth;
		if (month < 10) {
			moth = "0" + month;
		} else {
			moth = "" + month;
		}

		final int day = now.getDayOfMonth();

		String da;
		if (day < 10) {
			da = "0" + day;
		} else {
			da = "" + day;
		}

		result = result + year + moth + da + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
		return result;
	}

}
