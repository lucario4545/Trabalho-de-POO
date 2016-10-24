package roboLigaMagic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Carta;

public class RoboLigaMagic {
	public List<Carta> getPrecoDeck(String[][] listaCartas){
		// NOTA: Exemplo de Matriz
		// [["Archangel Avacyn",4],["Chandra Nalaar",2],["Ulrich of the Krallenhorde",2]];
		List<Carta> relacaoDePrecos = new ArrayList<>();
		
		for(int loop =0;loop<listaCartas.length;loop++){
			String[] carta = listaCartas[loop];
			String nomeCarta = carta[0];
			int quantidade = Integer.parseInt(carta[1]);
			
			List<Carta> response = buscarPreco(nomeCarta,quantidade);
			
			relacaoDePrecos.addAll(response);	
		}
		
		return relacaoDePrecos;
	}
	
//	public static void main(String[] args){
//		String nome = "Archangel Avacyn";
//		//String nome = "Equinox"; // NOTA: Nome de teste para cartas que não existem em estoque em nenhum lugar
//		int quantidade = 50;
//		
//		List<Carta>  = buscarPreco(nome,quantidade);
//		System.out.println("Sucesso Sucesso Suuuuuuuuuuuuucesso");
//		
//	}
	
    public static List<Carta> buscarPreco(String nomeCarta, int quantidade){
    	// NOTA: Essas linhas só são necessárias para fazer essa merda funcionar no meu trabalho
    	System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		System.setProperty("http.proxyPort", "8080"); 
    	
    	// NOTA: O algoritmo não funciona o nome da carta não estiver em inglês.
    	// Se o nome estiver incompleto Informações aleatórias vão ser cuspidas na tela.
      
    	
        String urlbusca = "http://www.ligamagic.com.br/?view=cards/card&card="; 
        String url; 
        
        List<Carta> resposta = new ArrayList<Carta>();
        
		try { // NOTA: Maldito java que me força a envolver as coisas em Try/Catch 
			url = urlbusca + URLEncoder.encode(nomeCarta,"UTF-8"); 
			Connection connection = Jsoup.connect(url);
			
            Document doc = connection.get();
            
            Elements precosEQuantidades = doc.select("tr > td > p"); 
            
            
            Elements precosSujo = new Elements();
            Elements quantidadeSuja =  new Elements();
            
            // TODO: Decidir como o programa deve responder caso não exista em estoque em nenhum lugar uma cópia da carta
            // TODO: Decidir como o programa deve responder caso não exita em estoque uma quantidade de carta o suficiente.
            
            for(int loop = 0;loop < precosEQuantidades.size();loop++){
            	if(loop % 2 == 0){
            		precosSujo.add(precosEQuantidades.get(loop));
            	}
            	else{
            		quantidadeSuja.add(precosEQuantidades.get(loop));
            	}
            }
            
            
            // NOTA: o primeiro é o nome e o segundo é uma outra coisa whatever
			// NOTA: Esse algoritmo bem ad-hoc, qualquer mudança no site da liga pode impactar em mudanças
  			// nesse cara. ass: Líder

            Elements tagBannerLoja = doc.select("tr > td.banner-loja > a > img");
            
            int loop = 0;
            
            while(quantidade > 0){
            	
            	String quantS = quantidadeSuja.get(loop).text().split(" ")[0];
            	String precoS = precosSujo.get(loop).text().split(" ")[1];
            	
            	int q = Integer.parseInt(quantS);
            	
            	int quantidadeCartas = (q >= quantidade) ? quantidade : q;
            	String nomeLoja = tagBannerLoja.get(loop).attr("title");
     
            	double preco = Double.parseDouble(precoS.replace(",","."));
            	
            	quantidade -= q;	

            	resposta.add(new Carta(nomeCarta,quantidadeCartas,nomeLoja,preco));
            	loop++;
            }
            
            return resposta;
             
            // TODO: ver se dá pra downloadar e exibir a imagemzinha da carta
           
            // TODO: Ver se não seria melhor downlodar tudo, colocar num banco de dados e depois exibir pro usuario.
        
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); // Pânico
		} catch (IOException e) {
			e.printStackTrace(); // Mais Pânico
		}
		
		return null;
        
        // NOTA: Em referenced libraries, o jar com o jsoup está configurado para ser o que está na minha maquina de trabalho
        // se você mudar vai dar erro.
        // lembre-se de mudar esse cara depois caso você execute o projeto de outra maquina
        // o .jar está dentro da pasta desse projeto
        // Ass: Líder
    }
    
}
