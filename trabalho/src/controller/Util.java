package controller;

import java.util.List;

import model.Carta;
import java.io.PrintWriter;


public class Util {
	public double getValorTotal(List<Carta> lista){
		double resposta = 0;
		
		for(Carta carta : lista){
			resposta += carta.getQuantidade() * carta.getPreco();
		}
		
		return resposta;
	}
	
	public void xlsExport(List<Carta> lista){
		
		lista = this.sortListaCartas(lista);
		
		try{
		    PrintWriter writer = new PrintWriter("relacao-de-precos2.csv", "UTF-8");
		    writer.println("Quantidade;Nome;Coleção;Preco Unitário;Preco Total;Loja");
		    
		    for(Carta carta : lista){
		    	String line = "";
		    	
		    	line+=carta.getQuantidade()+";";
		    	line+=carta.getNome()+";";
		    	line+=carta.getColecao()+";";
		    	line+=String.format("%.2f;",carta.getPreco());
		    	line+=String.format("%.2f;",(carta.getPreco() * carta.getQuantidade()));
		    	line+=carta.getLoja()+";";
		    	
		    	writer.println(line);
		    }
		    
		    writer.println();
		    writer.println(";;Total;"+String.format("%.2f;",this.getValorTotal(lista)));
		    
		    writer.close();
		} catch (Exception e) {
		   e.getStackTrace();
		}
	}
	
	public List<Carta> sortListaCartas(List<Carta> lista){
		for(int loop = 0;loop<lista.size();loop++){
			for(int loop2 = loop+1;loop2<lista.size();loop2++){
				String loja1 = lista.get(loop).getLoja();
				String loja2 = lista.get(loop2).getLoja();
				
				int comp = loja1.compareTo(loja2);
				
				if(comp > 0){
					Carta aux = lista.get(loop);
					lista.set(loop,lista.get(loop2));
					lista.set(loop2,aux);
				}
			}
		}
		
		return lista;
	}
}
