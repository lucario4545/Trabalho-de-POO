package model;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Carta {
	private String nome;
	private Image imagem;
	private ArrayList<CartaLoja> lojas = new ArrayList<CartaLoja>();
	
	public Carta(String nome,String imgSrc){
		this.nome = nome;
		this.imagem = this.getImageObject(imgSrc);
	}
	
	public Carta(String nome,Image imagem){
		this.nome = nome;
		this.imagem = imagem;
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
	
	public Image getImagem(){
		return this.imagem;
	}
	
	private Image getImageObject(String imgSrc){
		
		URL url;
		Image imagem = null;
		
		try {
			url = new URL(imgSrc);
			imagem = ImageIO.read(url);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return imagem;
	}
}
