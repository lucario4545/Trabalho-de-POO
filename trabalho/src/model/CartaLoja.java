package model;

public class CartaLoja {
	private int quantidade;
	private double preco;
	private String colecao;
	private String linkLoja;
	private String nomeLoja;
	
	public CartaLoja(int quantidade, double preco, String colecao, String linkLoja, String loja) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		this.colecao = colecao;
		this.linkLoja = linkLoja;
		this.nomeLoja = loja;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getColecao() {
		return colecao;
	}
	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	public String getLinkLoja() {
		return linkLoja;
	}
	public void setLinkLoja(String linkLoja) {
		this.linkLoja = linkLoja;
	}
	public String getLoja() {
		return nomeLoja;
	}
	public void setLoja(String loja) {
		this.nomeLoja = loja;
	}
	
	
}
