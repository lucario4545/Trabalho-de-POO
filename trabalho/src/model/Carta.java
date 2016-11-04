package model;

public class Carta {
	private String nome;
	private int quantidade;
	private String loja;
	private double preco;
	private String colecao;
	private String imgSrc;

	public Carta(String nome,int quantidade,String loja,double preco,String colecao, String imgSrc){
		this(nome,quantidade,loja,preco);
		this.colecao = colecao;
		this.imgSrc = imgSrc;
	}
	
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
	
	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	
	@Override
	public String toString(){
		
		String msg = "Nome Carta: "+this.nome+"; Quantidade: "+this.quantidade+";\n";
		msg += "Coleção: "+this.colecao+";  Loja: "+this.loja+"; Preco: R$ "+this.preco+"\n\n";
		return msg;
	}

	
}
