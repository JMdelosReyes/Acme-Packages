
package utilities;

public class Tickers {

	public static String generateTicker(String company) {
		String result = "";

		while (company.length() < 4)
			company += "X";

		if (company.length() > 4)
			company = company.substring(0, 4);

		final int numb = (int) (Math.random() * 9999);
		String numbS = Integer.toString(numb);

		while (numbS.length() < 4)
			numbS = "0" + numbS;

		result = result + company.toUpperCase() + "-" + numbS;
		return result;
	}

}
