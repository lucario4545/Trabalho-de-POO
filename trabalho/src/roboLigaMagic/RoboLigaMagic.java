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
import controller.Util;

public class RoboLigaMagic {
	public List<Carta> getPrecoDeck(List<String[]> listaCartas){
		// NOTA: Exemplo de Matriz
		// [["Archangel Avacyn",4],["Chandra Nalaar",2],["Ulrich of the Krallenhorde",2]];
		List<Carta> relacaoDePrecos = new ArrayList<>();
		
		for(int loop =0;loop<listaCartas.size();loop++){
			String[] carta = listaCartas.get(loop);
					
			if(carta.length != 2 || carta == null){
				continue;
			}
			
			String nomeCarta = carta[0];
			
			int quantidade = Integer.parseInt(carta[1]);
			
			List<Carta> response = null;
			
			try{
				response = buscarPreco(nomeCarta,quantidade);
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage()+"\n");
				continue;
			}
				
			relacaoDePrecos.addAll(response);	
		}
		
		Util toolbox = new Util();
		
		toolbox.xlsExport(relacaoDePrecos);
		
		return relacaoDePrecos;
	}
	
    public List<Carta> buscarPreco(String nomeCarta, int quantidade){
    	// NOTA: Essas linhas só são necessárias para fazer essa merda funcionar no meu trabalho
    	System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		System.setProperty("http.proxyPort", "8080"); 
    	
        String urlbusca = "http://www.ligamagic.com.br/?view=cards/card&card="; 
        
        String url; 
        
        List<Carta> resposta = new ArrayList<Carta>();
        
		try { 
			url = urlbusca + URLEncoder.encode(nomeCarta,"UTF-8"); 
			Connection connection = Jsoup.connect(url);
			
            Document doc = connection.get();
            
            Elements precosEQuantidades = doc.select("tr.pointer > td > p"); 
            
            Elements precosSujo = new Elements();
            Elements quantidadeSuja =  new Elements();
            
            if(precosEQuantidades.size() < 1){
            	throw new IllegalArgumentException("A Carta \""+nomeCarta+"\" não foi encontrada!");
            }
            
            for(int loop = 0;loop < precosEQuantidades.size();loop++){
            	if(loop % 2 == 0){
            		precosSujo.add(precosEQuantidades.get(loop));
            	}
            	else{
            		quantidadeSuja.add(precosEQuantidades.get(loop));
            	}
            }

            Elements tagBannerLoja = doc.select("tr > td.banner-loja > a > img");
            Elements tagColecaoCarta = doc.select(" tr > td > a.preto > img.icon");
            
            int quantMaxima = getQuantidadeMaxima(quantidadeSuja);
            
            if(quantidade > quantMaxima){
            	System.out.println("Você esta pedindo uma quantidade muito grande cartas, animal\n");
            	quantidade = quantMaxima;
            }
            
            int loop = 0;
            
            while(quantidade > 0){
            	
            	String quantS = quantidadeSuja.get(loop).text().split(" ")[0];
            	String precoS = precosSujo.get(loop).text().split(" ")[1];
            	
            	int q = Integer.parseInt(quantS);
            	
            	int quantidadeCartas = (q >= quantidade) ? quantidade : q;
            	String nomeLoja = tagBannerLoja.get(loop).attr("title");
     
            	double preco = Double.parseDouble(precoS.replace(",","."));
            	
            	String imgSrc = tagBannerLoja.get(loop).attr("src");
            	String colecao = tagColecaoCarta.get(loop).attr("title");
            	
            	quantidade -= q;	

            	resposta.add(new Carta(nomeCarta,quantidadeCartas,nomeLoja,preco,colecao,imgSrc));
            	loop++;
            }
            
            return resposta;
        
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		return null;
    }
    
    private int getQuantidadeMaxima(Elements quantidadeSuja){
    	int resposta = 0;
    	
    	for(Element element : quantidadeSuja){
    		String text = element.text().split(" ")[0];	
    		text = text.replaceAll("[,.]",""); // Isso é pro caso de cartas com quantidade muito grandes em estoque.
    		// NOTA: Ver se não já não está na hora de pensar em performance.
    
    		resposta += Integer.parseInt(text);
    	}
    	
    	return resposta;
    }
    
}
