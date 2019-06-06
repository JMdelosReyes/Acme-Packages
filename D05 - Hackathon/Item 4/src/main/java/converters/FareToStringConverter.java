
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Fare;

@Component
@Transactional
public class FareToStringConverter implements Converter<Fare, String> {

	@Override
	public String convert(Fare fare) {
		String result;

		if (fare == null) {
			result = null;
		} else {
			result = String.valueOf(fare.getId());
		}
		return result;

	}
}
