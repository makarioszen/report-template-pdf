package br.com.mundialinformatica.reportgen.filters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.com.mundialinformatica.reportgen.exceptions.FilterException;

public class FilterCurrency implements Filter {

	public String getValue(String value) throws FilterException {
		try {
			Double bValue = new Double(value);
			DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00",
					new DecimalFormatSymbols(new Locale("pt", "BR")));
			formatoDois.setMinimumFractionDigits(2);
			formatoDois.setParseBigDecimal(true);
			return "R$"+formatoDois.format(bValue);
		} catch (Exception e) {
			throw new FilterException(e);
		}
	}

}
