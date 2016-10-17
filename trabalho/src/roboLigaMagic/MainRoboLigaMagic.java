package roboLigaMagic;

import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainRoboLigaMagic {

	public static void main(String[] args) {  
        String string = "Chandra Nalaar"; 
       
        buscarPreco(string);
    }
    
    public static void buscarPreco(String nomeCarta){
    	
    	// isso aqui é só pra fazer bang funcionar no meu trabalho.
    	// se você for mexer nesse cara, comente essa linha e depois lembre-se de volta-la ao normal k
    	// ass: Líder
    	System.setProperty("http.proxyHost", "spoigpxy0002.indusval.com.br");
		System.setProperty("http.proxyPort", "8080");
        
        String urlbusca = "http://www.ligamagic.com.br/?view=cards%2Fsearch&card="; 
        String url = urlbusca + URLEncoder.encode(nomeCarta); // TODO: Usar uma função que não esteja deprecated...
        
        try {		
            Connection connection = Jsoup.connect(url);
					
            Document doc = connection.get();
			
            Element teste = doc.getElementsByClass("lj").get(2); // NOTA: o primeiro é o nome e o segundo é uma outra coisa whatever
            													 // NOTA: Esse algoritmo é bem ad-hoc, qualquer mudança no site da liga pode impactar em mudanças
            												     // nesse cara. ass: Líder
            String t = teste.text();			  // NOTA: limpar esse valor
            System.out.println("Sucesso - "+url); 
            									  // TODO: pegar o resto das informações
            									  // TODO: ver se dá pra downloadar e exibir a imagemzinha da carta
            									  // TODO: Ver se não seria melhor downlodar tudo, colocar num bancode dados e depois exibir pro usuario.
            } 
            catch (Exception e) {
            	System.out.println("Fudeu fudeu");
                e.printStackTrace();
            } // NOTA: Em referenced libraries, o jar com o jsoup está configurado para ser o que está na minha maquina de trabalho
              // se você mudar vai dar erro.
              // lembre-se de mudar esse cara depois caso você execute o projeto de outra maquina
              // o .jar está dentro da pasta desse projeto
        	  // Ass: Líder
    }

}
