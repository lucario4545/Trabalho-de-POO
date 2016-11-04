package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField; // NOTA: Isso ainda vai ser usado um dia.
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alee.extended.image.WebImage;

import model.Carta;
import roboLigaMagic.RoboLigaMagic;

public class TelaBusca extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// TODO: Essa lógica de pra letura tem que sair daqui e tem que ser colocada no pacote controller
		// de preferência - Líder
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaBusca frame = new TelaBusca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaBusca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 109, 62);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		WebImage img = new WebImage();
		JButton btnBusca = new JButton("Busca");
		btnBusca.setBounds(10, 0, 89, 23);
		contentPane.add(btnBusca);
		
		ActionListener busca = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					String caminho = procuraArquivo();
					ArrayList<String[]> relacao = getRelacaoCartas(caminho);
					exibePrecoDeck(relacao);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}			
		};
		
		btnBusca.addActionListener(busca);
	}
	
	public String procuraArquivo(){
		
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de texto (.txt)","txt");

		String diretorioBase = System.getProperty("user.home")+"/Documents";
		File dir = new File(diretorioBase);
		
		JFileChooser choose = new JFileChooser();
		choose.setCurrentDirectory(dir);
		choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		choose.setAcceptAllFileFilterUsed(false);
		choose.addChoosableFileFilter(filtro);
		String CaminhoArquivo="";
		
		int retorno = choose.showOpenDialog(null);
		if (retorno == JFileChooser.APPROVE_OPTION){
			CaminhoArquivo = choose.getSelectedFile().getAbsolutePath();			
			this.dispose();
		}
		
		return CaminhoArquivo;
	}
	
	public ArrayList<String[]> getRelacaoCartas(String file) throws FileNotFoundException{
		
		ArrayList<String[]> Cartas = new ArrayList<String[]> (); 
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String linha = reader.readLine();
			
			while(linha != null){
				String [] array;
				array = linha.split(";");
				Cartas.add(array);
				
				linha = reader.readLine();
			}
			reader.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return Cartas;
	}
	
	public void exibePrecoDeck(ArrayList<String[]> cartas){
		RoboLigaMagic robo = new RoboLigaMagic();
		
		List<Carta> relacaoDeck = robo.getPrecoDeck(cartas);
		
		for(int loop = 0; loop< relacaoDeck.size();loop++){
			System.out.println(relacaoDeck.get(loop).toString()+"\n");
		}
		return;
	}

}
