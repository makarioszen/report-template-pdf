package br.com.mundialinformatica.reportgen.model.sample;

import br.com.mundialinformatica.reportgen.annotation.TemplateMapColumn;
import br.com.mundialinformatica.reportgen.annotation.TemplateMapObject;

public class Produto {
	@TemplateMapColumn
	private String nome;
	@TemplateMapColumn
	private String valor;
	@TemplateMapObject
	private ProdutoTipo tipo;

	public Produto(String nome, String valor) {
		super();
		this.nome = nome;
		this.valor = valor;
		tipo = new ProdutoTipo();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Produto [" + (nome != null ? "nome=" + nome + ", " : "")
				+ (valor != null ? "valor=" + valor : "") + "]";
	}

}
