package roboLigaMagic;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import model.Carta;

public class RoboLigaMagic {
	public List<Carta> getPrecoDeck(List<String[]> listaCartas){
		// NOTA: Exemplo de Matriz
		// [["Archangel Avacyn",4],["Chandra Nalaar",2],["Ulrich of the Krallenhorde",2]];
		List<Carta> relacaoDePrecos = new ArrayList<>();
		
		for(int loop =0;loop<listaCartas.size();loop++){
			String[] carta = listaCartas.get(loop);
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
		
		return relacaoDePrecos;
	}
	
    public List<Carta> buscarPreco(String nomeCarta, int quantidade){
    	// NOTA: Essas linhas só são necessárias para fazer essa merda funcionar no meu trabalho
    	System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		System.setProperty("http.proxyPort", "8080"); 
    	
    	// NOTA: O algoritmo não funciona o nome da carta não estiver em inglês.
    	// Se o nome estiver incompleto Informações aleatórias vão ser cuspidas na tela.
      
    	
        String urlbusca = "http://www.ligamagic.com.br/?view=cards%2Fsearch&card="; 
        	// NOTA: Essa url me permite pesquisar nomes de cartas em portugu�s
        String url; 
        
        List<Carta> resposta = new ArrayList<Carta>();
        
		try { // NOTA: Maldito java que me força a envolver as coisas em Try/Catch 
			url = urlbusca + URLEncoder.encode(nomeCarta,"UTF-8"); 
			Connection connection = Jsoup.connect(url);
			
            Document doc = connection.get();
            
            Elements precosEQuantidades = doc.select("tr > td > p"); 
            
            
            Elements precosSujo = new Elements();
            Elements quantidadeSuja =  new Elements();

            if(precosEQuantidades.size() < 1){
            	throw new IllegalArgumentException("A Carta \""+nomeCarta+"\" n�o foi encontrada!");
            }

            // TODO: Decidir como o programa deve responder caso não exita em estoque uma quantidade de carta o suficiente.
            
            for(int loop = 0;loop < precosEQuantidades.size();loop++){
            	if(loop % 2 == 0){
            		precosSujo.add(precosEQuantidades.get(loop));
            	}
            	else{
            		quantidadeSuja.add(precosEQuantidades.get(loop));
            	}
            }
            
			// NOTA: Esse algoritmo bem ad-hoc, qualquer mudan�a no site da liga pode impactar em mudan�as
  			// nesse cara. ass: Líder

            Elements tagBannerLoja = doc.select("tr > td.banner-loja > a > img");
            Elements tagColecaoCarta = doc.select(" tr > td > a.preto > img.icon");
            
            // pegar a imagem
            
            Elements imgs = doc.select("div.card-image > span#omoImage > img");          
            String imgSrc = imgs.get(0).attr("src");
            
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
           
            // TODO: Ver se não seria melhor downlodar tudo, colocar num banco de dados e depois exibir pro usuario.
        
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); // Pânico
		} catch (IOException e) {
			e.printStackTrace(); // Mais Pânico
		}
		
		return null;
        
        // NOTA: Em referenced libraries, o jar com o jsoup est� configurado para ser o que está na minha maquina de trabalho
        // se você mudar vai dar erro.
        // lembre-se de mudar esse cara depois caso voc� execute o projeto de outra maquina
        // o .jar est� dentro da pasta desse projeto
        // Ass: Líder
    }
    
    private Image getCardImage(String imgUrl) throws MalformedURLException, IOException{
    	Image image = ImageIO.read(new URL(imgUrl)); 
    	return image;
    }
    
}
