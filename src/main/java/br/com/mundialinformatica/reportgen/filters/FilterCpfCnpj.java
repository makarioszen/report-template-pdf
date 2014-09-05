package br.com.mundialinformatica.reportgen.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterCpfCnpj implements Filter {

	public String getValue(String value) throws FilterException {

		Pattern pattern = Pattern
				.compile("((\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2}))|((\\d{3})(\\d{3})(\\d{3})(\\d{2}))");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			if (matcher.group(1) != null) {
				value = matcher.replaceAll("$2.$3.$4/$5-$6");
			} else {
				value = matcher.replaceAll("$8.$9.$10-$11");
			}

		}
		return value;
	}

}
