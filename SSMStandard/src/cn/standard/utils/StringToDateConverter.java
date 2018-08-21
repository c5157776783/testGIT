package cn.standard.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
	private String datePattern;
	public StringToDateConverter(String datePattern){
		this.datePattern = datePattern;		
	}
	@Override
	public Date convert(String s) {
		Date date = null;
		try {
			System.out.println("StringToDateConverter s ======================== "+s);
			System.out.println("StringToDateConverter datePattern ======================== "+datePattern);
			date =  new SimpleDateFormat(datePattern).parse(s);
			System.out.println("StringToDateConverter convert date ================================= > " + date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
