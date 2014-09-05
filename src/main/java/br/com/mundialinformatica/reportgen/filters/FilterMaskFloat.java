package br.com.mundialinformatica.reportgen.filters;

import java.math.BigDecimal;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterMaskFloat implements Filter {
	private final String strFilter;

	public FilterMaskFloat(String strFilter) {
		this.strFilter = strFilter;
	}

	public String getValue(String value) throws FilterException {
		try {
			String mask = strFilter.replace("maskfloat", "");
			BigDecimal valuei = new BigDecimal(value);
			return String.format(mask, valuei);
		} catch (Exception e) {
			throw new FilterException(e);
		}
	}

}
