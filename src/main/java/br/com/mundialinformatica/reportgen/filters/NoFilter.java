package br.com.mundialinformatica.reportgen.filters;

public class NoFilter implements Filter {

	public String getValue(String value) {

		return value != null ? value : "";
	}

}
