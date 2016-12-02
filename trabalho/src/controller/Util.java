package controller;

import java.util.List;

import model.Carta;
import model.CartaLoja;

import java.io.PrintWriter;

public class Util { 
	public double getValorTotal(List<Carta> lista){
		double resposta = 0;
		
		for(Carta carta : lista){
			for(CartaLoja loja : carta.getLojas()){
				resposta += loja.getQuantidade() * loja.getPreco();
			}
		}
		
		return resposta;
	}
	
	public void csvExport(List<Carta> lista){
		
		try{
		    PrintWriter writer = new PrintWriter("relacao-de-precos2.csv", "Windows-1252");
		    writer.println("Quantidade;Nome;Coleção;Preco Unitário;Preco Total;Loja");
		    
		    
		    for(Carta carta : lista){
//		    	System.out.println(carta.getImgSrc());
		    	String line = "";
		    	
		    	for(CartaLoja loja : carta.getLojas()){
		    		
		    		line+=loja.getQuantidade()+";";
			    	line+=carta.getNome()+";";
			    	line+=loja.getColecao()+";";
			    	line+=String.format("%.2f;",loja.getPreco());
			    	line+=String.format("%.2f;",(loja.getPreco() * loja.getQuantidade()));
			    	line+=loja.getLoja()+";";
			    	
			    	writer.println(line);
		    	}
		    }
		    
		    writer.println();
		    writer.println(";;Total;"+String.format("%.2f;",this.getValorTotal(lista)));
		    
		    writer.close();
		} 
		
		catch (Exception e) {
		   e.getStackTrace();
		}
	}
}
