package br.com.mundialinformatica.reportgen.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterDateShort implements Filter {

	public String getValue(String value) throws FilterException {
        //Thu Sep 04 16:42:38 BRT 2014
		//Fri Sep 05 09:07:07 BRT 2014
		String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
		SimpleDateFormat  df = new SimpleDateFormat(pattern,Locale.US);
		try {
			Date date = df.parse(value);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(date);
		} catch (ParseException e) {
			throw new FilterException(e);
		}
	}

}
