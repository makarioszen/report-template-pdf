package br.com.mundialinformatica.reportgen.filters;

import java.math.BigDecimal;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterCurrency implements Filter {

	public String getValue(String value) throws FilterException {
		try {
			BigDecimal bValue = new BigDecimal(value);
			return String.format("R$ %.2f", bValue);
		} catch (Exception e) {
			throw new FilterException(e);
		}
	}

}
