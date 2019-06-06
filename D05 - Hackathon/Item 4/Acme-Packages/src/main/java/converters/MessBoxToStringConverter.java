package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MessBox;


@Component
@Transactional
public class MessBoxToStringConverter implements Converter<MessBox, String>{

	@Override
	public String convert(MessBox messBox) {
		String result;
		
		if(messBox==null){
			result=null;
		}else {
			result=String.valueOf(messBox.getId());
		}
		return result;
		
	}
	
}
