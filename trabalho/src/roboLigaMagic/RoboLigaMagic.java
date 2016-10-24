package roboLigaMagic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Carta;

public class RoboLigaMagic {
	
	public void getPrecoDeck(String[][] listaCartas){
		
		Carta[] relacaoDePrecos = new Carta[listaCartas.length];
		
		
		for(int loop =0;loop<listaCartas.length;loop++){
			String[] carta = listaCartas[loop];
			String nomeCarta = carta[0];
			int quantidade = Integer.parseInt(carta[1]);
			
			relacaoDePrecos[loop] = buscarPreco(nomeCarta,quantidade);
		}
		
		
	}
	
    public Carta buscarPreco(String nomeCarta, int quantidade){
    	
    	// NOTA: Se o robô não encontrar o nome da carta exatamente, ele vai cuspir Informações aleatórias bizarras.
        // Isso também vale caso o nome da carta esteja qualquer lingua que não o inglês na busca ¬¬
    	
    	// isso aqui é só pra fazer bang funcionar no meu trabalho.
    	// se você for mexer nesse cara, comente essa linha e depois lembre-se de volta-la ao normal k
    	// ass: Líder
    	// System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		// System.setProperty("http.proxyPort", "8080");
        
		// TODO: Criar a lógica pra pegar as cartas dos outros lugares caso no lugar mais barato não
		// Seja o suficiente
		
        String urlbusca = "http://www.ligamagic.com.br/?view=cards%2Fsearch&card="; 
        String url;
        
         
        
		try { // NOTA: Maldito java que me força a envolver as coisas em Try/Catch ¬¬
			url = urlbusca + URLEncoder.encode(nomeCarta,"UTF-8"); 
			Connection connection = Jsoup.connect(url);
			
            Document doc = connection.get();
            
            Elements precosEQuantidades = doc.select("tr > td > p"); 
            
            // NOTA: o primeiro é o nome e o segundo é uma outra coisa whatever
			// NOTA: Esse algoritmo é bem ad-hoc, qualquer mudança no site da liga pode impactar em mudanças
  			// nesse cara. ass: Líder
            
            
            Element precoElemento = precosEQuantidades.get(0);
            Element quantidadeElemento    =  precosEQuantidades.get(1);
            
            String precoSujo = precoElemento.text();
            String quantidadeSuja = quantidadeElemento.text();
            String precos[] = precoSujo.split(" ");
            String preco = precos[precos.length-1];
            String quantidades[] = quantidadeSuja.split(" ");
            String quantidade = quantidades[0];
            
            Elements tagBannerLoja = doc.select("tr > td.banner-loja > a > img");
            Element tagLojaMaisBarata = (Element) tagBannerLoja.get(0);
            String nomeLoja = tagLojaMaisBarata.attr("title");
            
            System.out.println("preco = "+preco+" ; Quantidade: "+quantidade+" Loja: "+nomeLoja);
  						
            // TODO: ver se dá pra downloadar e exibir a imagemzinha da carta
            // TODO: Ver se não seria melhor downlodar tudo, colocar num banco de dados e depois exibir pro usuario.
            
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace(); // Pânico
		} catch (IOException e) {
			e.printStackTrace(); // Mais Pânico
		}
        
        // NOTA: Em referenced libraries, o jar com o jsoup está configurado para ser o que está na minha maquina de trabalho
        // se você mudar vai dar erro.
        // lembre-se de mudar esse cara depois caso você execute o projeto de outra maquina
        // o .jar está dentro da pasta desse projeto
        // Ass: Líder
    }

}
