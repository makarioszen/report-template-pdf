package br.com.mundialinformatica.reportgen.exceptions;

public class FilterException extends Exception {

	public FilterException(String msg) {
		super(msg);
	}

	public FilterException(Exception e) {
		super(e);
	}

}
