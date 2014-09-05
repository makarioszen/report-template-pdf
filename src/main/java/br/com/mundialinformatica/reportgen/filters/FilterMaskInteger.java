package br.com.mundialinformatica.reportgen.filters;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterMaskInteger implements Filter {

	private final String strFilter;

	public FilterMaskInteger(String strFilter) {
		this.strFilter = strFilter;
	}

	public String getValue(String value) throws FilterException {
		try {
			String mask = strFilter.replace("maskinteger", "");
			Integer valuei = Integer.parseInt(value);
			return String.format(mask, valuei);
		} catch (Exception e) {
			throw new FilterException(e);
		}
	}

}
