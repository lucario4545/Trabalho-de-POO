package model;

public class Carta {
	private String nome;
	private int quantidade;
	private String loja;
	private double preco;

	public Carta(String nome,int quantidade,String loja,double preco){
		this.nome = nome;
		this.quantidade = quantidade;
		this.loja = loja;
		this.preco = preco;
	}
	
	public Carta(){
		this("",0,"",0.0);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
}
