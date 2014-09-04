package br.com.mundialinformatica.reportgen.filters;

import java.math.BigDecimal;

public class FilterCurrency implements Filter {

	public String getValue(String value) {
		try {
			BigDecimal bValue = new BigDecimal(value);
			return String.format("R$%10.2f", bValue);
		} catch (Exception e) {
			return "[ERRO]";
		}
	}

}
