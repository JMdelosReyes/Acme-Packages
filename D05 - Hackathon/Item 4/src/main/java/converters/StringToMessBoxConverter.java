
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MessBoxRepository;
import domain.MessBox;

@Component
@Transactional
public class StringToMessBoxConverter implements Converter<String, MessBox> {

	@Autowired
	MessBoxRepository	messBoxRepository;


	@Override
	public MessBox convert(final String text) {
		MessBox result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.messBoxRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
