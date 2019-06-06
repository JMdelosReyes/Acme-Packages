
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.VehicleRepository;
import domain.Vehicle;

@Component
@Transactional
public class StringToVehicleConverter implements Converter<String, Vehicle> {

	@Autowired
	VehicleRepository	vehicleRepo;


	@Override
	public Vehicle convert(final String text) {
		Vehicle result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.vehicleRepo.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
