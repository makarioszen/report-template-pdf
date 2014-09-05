package br.com.mundialinformatica.reportgen.filters;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public interface Filter {
	public String getValue(String value) throws FilterException;

}
