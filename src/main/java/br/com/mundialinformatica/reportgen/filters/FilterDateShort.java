package br.com.mundialinformatica.reportgen.filters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilterDateShort implements Filter {

	public String getValue(String value) {
		                //Thu Sep 04 16:42:38 BRT 2014	
		String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date date = df.parse(value);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(date);
		} catch (ParseException e) {
			return "[ERRO]";
		}
	}

}
