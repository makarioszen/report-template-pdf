package br.com.mundialinformatica.reportgen.model.sample;

import br.com.mundialinformatica.reportgen.annotation.TemplateMapColumn;

public class Endereco {
	@TemplateMapColumn
	private String logradouro;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

}
