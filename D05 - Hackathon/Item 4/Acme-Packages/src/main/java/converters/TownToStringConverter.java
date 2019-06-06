
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Town;

@Component
@Transactional
public class TownToStringConverter implements Converter<Town, String> {

	@Override
	public String convert(Town town) {
		String result;

		if (town == null) {
			result = null;
		} else {
			result = String.valueOf(town.getId());
		}
		return result;

	}
}
