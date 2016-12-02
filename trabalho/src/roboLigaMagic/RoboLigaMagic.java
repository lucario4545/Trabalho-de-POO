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
import model.CartaLoja;
import controller.Util;

public class RoboLigaMagic {
	public List<Carta> getPrecoDeck(List<String[]> listaCartas){
		List<Carta> relacaoDePrecos = new ArrayList<>();
		
		for(int loop =0;loop<listaCartas.size();loop++){
			Carta response = null;
			
			try{
				
				String[] carta = listaCartas.get(loop);
				String nomeCarta = carta[0];
				int quantidade = Integer.parseInt(carta[1]);
				
				response = buscarPreco(nomeCarta,quantidade);
			}
			
			catch(ArrayIndexOutOfBoundsException arrErr){
				continue;
			}
			
			catch(Exception e){
				System.out.println(e.getMessage()+"\n");
				continue; 
			}
				
			relacaoDePrecos.add(response);	
		}
		
		Util toolbox = new Util();
		
		toolbox.csvExport(relacaoDePrecos);
		
		return relacaoDePrecos;
	}
	
    public Carta buscarPreco(String nomeCarta, int quantidade){
    	System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		System.setProperty("http.proxyPort", "8080"); 
    	
        String urlbusca = "http://www.ligamagic.com.br/?view=cards/card&card="; 
        
        String url; 
        
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

            Elements tagBannerLoja 		= doc.select("tr > td.banner-loja > a > img");
            Elements tagColecaoCarta 	= doc.select("tr > td > a.preto > img.icon");
            Elements tagLinkLoja		= doc.select("tr > td.banner-loja > a");
            
            int quantMaxima = getQuantidadeMaxima(quantidadeSuja);
            
            if(quantidade > quantMaxima){
            	System.out.println("Quantidade de cartas muito grande solicitada\n");
            	quantidade = quantMaxima;
            }
            
            int loop = 0;
            
            Elements imagemCarta = doc.select("div.card-image > span > img ");
            Element imagem = imagemCarta.first();
            
            Carta carta = new Carta(nomeCarta,imagem.attr("src"));
            
            while(quantidade > 0){
            	
            	String quantS = quantidadeSuja.get(loop).text().split(" ")[0];
            	String precoS = precosSujo.get(loop).text().split(" ")[1];
            	
            	int q = Integer.parseInt(quantS);
            	
            	int quantidadeCartas = (q >= quantidade) ? quantidade : q;
            	String nomeLoja = tagBannerLoja.get(loop).attr("title");
     
            	double preco = Double.parseDouble(precoS.replace(",","."));
            	
            	String colecao = tagColecaoCarta.get(loop).attr("title");
            	
            	String baseLink = "http://www.ligamagic.com.br/";
            	String linkLoja = baseLink + tagLinkLoja.get(loop).attr("href");
            	
            	CartaLoja loja = new CartaLoja(quantidadeCartas,preco,colecao,linkLoja,nomeLoja);
            	
            	carta.addCartaLoja(loja);
            	
            	quantidade -= q;
            	
            	loop++;
            }
            
            return carta;
        
		} 
		catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); 
		} 
		catch (IOException e) {
			e.printStackTrace(); 
		}
		
		return null;
    }
    
    private int getQuantidadeMaxima(Elements quantidadeSuja){
    	int resposta = 0;
    	
    	for(Element element : quantidadeSuja){
    		String text = element.text().split(" ")[0];	
    		text = text.replaceAll("[,.]",""); 
    		resposta += Integer.parseInt(text);
    	}
    	
    	return resposta;
    }
    
}
