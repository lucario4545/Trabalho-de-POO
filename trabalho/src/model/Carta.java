package model;

import java.util.ArrayList;

public class Carta {
	private String nome;
	private String imgSrc;
	private ArrayList<CartaLoja> lojas = new ArrayList<CartaLoja>();
	
	public Carta(String nome,String imgSrc){
		this.nome = nome;
		this.imgSrc = imgSrc;
	}
	
	@Override
	public String toString(){
		String str = "";
		return str;
	}
	
	public void addCartaLoja(CartaLoja loja){
		this.lojas.add(loja);
	}
	
	public ArrayList<CartaLoja> getLojas(){
		return this.lojas;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getImgSrc(){
		return this.imgSrc;
	}
}
