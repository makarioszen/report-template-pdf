package br.com.mundialinformatica.reportgen.model.sample;

import java.util.ArrayList;
import java.util.List;

import br.com.mundialinformatica.reportgen.annotation.TemplateMapArray;
import br.com.mundialinformatica.reportgen.annotation.TemplateMapColumn;
import br.com.mundialinformatica.reportgen.annotation.TemplateMapObject;

public class Cliente {
	@TemplateMapColumn
	private String nome;
	@TemplateMapObject
	private Endereco endereco;
	@TemplateMapArray
	private List<Produto> produtos;
	@TemplateMapArray
	private List<Produto> servicos;

	public Cliente() {
		nome = "Nino do Castelo";
		endereco = new Endereco();
		endereco.setLogradouro("Rua Escura do Castelo, sem número");
		produtos = new ArrayList<Produto>();
		produtos.add(new Produto("Maçã da bruxa Morgana", "R$150,00"));
		produtos.add(new Produto("Palito de Dente do Pedrinho", "R$10,00"));
		servicos = new ArrayList<Produto>();
		servicos.add(new Produto("Massagem terapeutica", "R$250,00"));

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
