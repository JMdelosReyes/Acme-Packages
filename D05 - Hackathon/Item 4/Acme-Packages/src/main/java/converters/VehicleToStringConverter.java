
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Vehicle;

@Component
@Transactional
public class VehicleToStringConverter implements Converter<Vehicle, String> {

	@Override
	public String convert(Vehicle v) {
		String result;

		if (v == null) {
			result = null;
		} else {
			result = String.valueOf(v.getId());
		}
		return result;

	}
}
