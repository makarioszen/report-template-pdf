package br.com.mundialinformatica.reportgen.model.sample;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private String nome;
	private String endereco;
	private List<Produto> produtos;

	public Cliente() {
		nome = "Nino do Castelo";
		endereco = "Rua Escura do Castelo, sem número";
		produtos = new ArrayList<Produto>();
		produtos.add(new Produto("Maçã da bruxa Morgana", "R$150,00"));
		produtos.add(new Produto("Palito de Dente do Pedrinho", "R$10,00"));

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
