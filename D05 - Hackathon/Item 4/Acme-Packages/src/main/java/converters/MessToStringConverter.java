package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mess;

@Component
@Transactional
public class MessToStringConverter implements Converter<Mess, String>{

	@Override
	public String convert(Mess mess) {
		String result;
		
		if(mess==null){
			result=null;
		}else {
			result=String.valueOf(mess.getId());
		}
		return result;
		
	}
}