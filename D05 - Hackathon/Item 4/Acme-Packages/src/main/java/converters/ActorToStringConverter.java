
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;

@Component
@Transactional
public class ActorToStringConverter implements Converter<Actor, String> {

	@Override
	public String convert(final Actor act) {
		String result;
		if (act == null)
			result = null;
		else
			result = String.valueOf(act.getId());
		return result;
	}

}
