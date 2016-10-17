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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		
		JButton btnBusca = new JButton("Busca");
		btnBusca.setBounds(10, 0, 89, 23);
		contentPane.add(btnBusca);
		
		ActionListener busca = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				procuraArquivo();
			}			
		};
		
		btnBusca.addActionListener(busca);
	}
	
	public void procuraArquivo(){
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de texto (.txt)","txt");
		// String diretorioArquivo;
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
			try {
				Exibe(CaminhoArquivo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void Exibe(String file) throws FileNotFoundException{
		ArrayList<String> Cartas = new ArrayList<String> ();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String linha = reader.readLine();
			//String [] array;
			//array = linha.split(";");
			//
			while(linha != null){
				Cartas.add(linha);
				linha = reader.readLine();
			}
			reader.close();
			for(int x=0;x< Cartas.size();x++){
				System.out.println(Cartas.get(x));				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
