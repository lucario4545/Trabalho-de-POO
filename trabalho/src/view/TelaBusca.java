package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import model.Carta;
import roboLigaMagic.RoboLigaMagic;
public class TelaBusca {

	private JFrame frmPesquisadorDeCartas;
	@SuppressWarnings("rawtypes")
	private JComboBox BoxCartas = new JComboBox();
	@SuppressWarnings("unused")
	private List<Carta> listaCartas = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaBusca window = new TelaBusca();
					window.frmPesquisadorDeCartas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaBusca() {
		initialize();
	}

	private void initialize() {
		
		frmPesquisadorDeCartas = new JFrame();
		frmPesquisadorDeCartas.setResizable(false);
		frmPesquisadorDeCartas.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\photo.png"));
		frmPesquisadorDeCartas.setTitle("Pesquisador de Cartas - Magic the Gathering");
		frmPesquisadorDeCartas.setBounds(100, 100, 800, 600);
		frmPesquisadorDeCartas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menuBar = new JMenuBar();
		frmPesquisadorDeCartas.setJMenuBar(menuBar);
		
		
		JMenu MenuOptions = new JMenu("Editar...");
		MenuOptions.setMnemonic('E');
		menuBar.add(MenuOptions);
		
		JScrollPane SPValorFinal = new JScrollPane();
		SPValorFinal.setBounds(474, 43, 300, 276);
		frmPesquisadorDeCartas.getContentPane().add(SPValorFinal);
		
		JTextArea TValorFinal = new JTextArea();
		TValorFinal.setEditable(false);
		SPValorFinal.setViewportView(TValorFinal);
		
		
		JMenuItem MenuAbrir = new JMenuItem("Abrir");
		MenuAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho = procuraArquivo();
				ArrayList<String[]> relacao;
				List<Carta> relacaoDeck = null;
				try {
					relacao = getRelacaoCartas(caminho);
					relacaoDeck = getPrecoDeck(relacao);
					String valorFinal = TelaBusca.this.getValorTotal(relacaoDeck);
					TValorFinal.setText(valorFinal);
					
				} 
				
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		MenuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		MenuOptions.add(MenuAbrir);
				
		JMenuItem MenuSair = new JMenuItem("Sair");
		MenuSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		MenuSair.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		MenuOptions.add(MenuSair);
		frmPesquisadorDeCartas.getContentPane().setLayout(null);
		
		JScrollPane SPPrecos = new JScrollPane();
		SPPrecos.setBounds(10, 330, 764, 200);
		frmPesquisadorDeCartas.getContentPane().add(SPPrecos);
		
		JTextArea TPrecos = new JTextArea();
		TPrecos.setEditable(false);
		SPPrecos.setViewportView(TPrecos);
		
		JPanel PCarta = new JPanel();
		PCarta.setBounds(201, 43, 189, 264);
		frmPesquisadorDeCartas.getContentPane().add(PCarta);
		PCarta.setLayout(null);		
		
		JTextPane TXTPrecos = new JTextPane();
		TXTPrecos.setEditable(false);
		TXTPrecos.setBackground(UIManager.getColor("Button.background"));
		TXTPrecos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TXTPrecos.setText("Pre\u00E7os");
		TXTPrecos.setBounds(10, 302, 63, 28);
		frmPesquisadorDeCartas.getContentPane().add(TXTPrecos);
		
		JTextPane TXTCarta = new JTextPane();
		TXTCarta.setText("Carta\r\n");
		TXTCarta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TXTCarta.setEditable(false);
		TXTCarta.setBackground(SystemColor.menu);
		TXTCarta.setBounds(201, 11, 63, 28);
		frmPesquisadorDeCartas.getContentPane().add(TXTCarta);
		
		JTextPane TXTValorFinal = new JTextPane();
		TXTValorFinal.setText("Valor Final");
		TXTValorFinal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TXTValorFinal.setEditable(false);
		TXTValorFinal.setBackground(SystemColor.menu);
		TXTValorFinal.setBounds(474, 11, 103, 28);
		frmPesquisadorDeCartas.getContentPane().add(TXTValorFinal);

		BoxCartas.setBounds(10, 43, 150, 20);
		frmPesquisadorDeCartas.getContentPane().add(BoxCartas);
		BoxCartas.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
            		Carta Choosen = (Carta) BoxCartas.getSelectedItem();
         
					PCarta.removeAll();
					
					ImageIcon AAA  = new ImageIcon(Choosen.getImagem());
					Image imag = AAA.getImage().getScaledInstance(189, 264, Image.SCALE_SMOOTH);
					ImageIcon b= new ImageIcon(imag);					
					JLabel lblNewLabel = new JLabel(b);
					
					lblNewLabel.setBounds(0, 0, 189, 264);
					PCarta.add(lblNewLabel);
					
					TPrecos.setText(Choosen.getRelacaoPrecos());
                }
            }
       });

	}
	
	public String procuraArquivo(){
		
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de texto (.txt)","txt");

		String diretorioBase = System.getProperty("user.home")+"/Desktop";
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
		}
		
		return CaminhoArquivo;
	}
	
	public ArrayList<String[]> getRelacaoCartas(String file) throws FileNotFoundException{
		
		ArrayList<String[]> Cartas = new ArrayList<String[]> (); 
		
		try {
			
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String linha = reader.readLine();
			BoxCartas.removeAllItems();
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
	
	@SuppressWarnings("unchecked")
	public List<Carta> getPrecoDeck(ArrayList<String[]> cartas){
		System.out.println("Inicio");
		
		RoboLigaMagic robo = new RoboLigaMagic();
		
		List<Carta> relacaoDeck = null;
		relacaoDeck = robo.getPrecoDeck(cartas);
		
		for(Carta carta : relacaoDeck){
			BoxCartas.addItem(carta);
		}
		
		return relacaoDeck;
	}
	
	public String getValorTotal(List<Carta> relacaoDeck){
		String msg = "";
		
		double valorTotal = 0;
		for(Carta carta: relacaoDeck){
			msg+=carta.getQuantidadeTotal()+"x  "+carta.getNome()+"  "+" R$ "+String.format("%.2f", carta.getPrecoTotal())+"\n";
			valorTotal+=carta.getPrecoTotal();
		}
		
		msg+="\nValor Total: R$ "+String.format("%.2f",valorTotal);
		
		return msg;
	}
	
	

}
