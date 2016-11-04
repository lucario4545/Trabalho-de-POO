package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Carta;

import com.alee.extended.image.WebImage;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

public class NovaGUI extends WebFrame implements KeyListener, ChangeListener, FocusListener{
	
	/**
	 * GUI Class Components
	 */
	private JPanel contentPane = new JPanel();
	private static JTextField textField;
	static WebImage img = new WebImage();
	static WebLabel total;
	static WebPanel panel;
	static WebSpinner multiplier;
	static int count = 0;
	protected HashMap<String, Component> componentMap;
	private JLabel lblMultiplier;
	

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = panel.getComponents();
        for (int i=0; i < components.length; i++) {
            componentMap.put(components[i].getName(), components[i]);
        }
	}
	
	WebTextField createNewRow(Carta carta){ //
        count++;
        
        //CREATE NEW TEXTFIELD
        WebTextField textFieldNew = new WebTextField();
		Dimension size = new Dimension(0, 27);
		textFieldNew.setPreferredSize(size);
		GridBagConstraints gbc_textFieldNew = new GridBagConstraints();
		gbc_textFieldNew.gridwidth = 2;
		gbc_textFieldNew.insets = new Insets(5, 5, 5, 5);
		gbc_textFieldNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNew.gridx = 0;
		textFieldNew.setName(Integer.toString(count));
		gbc_textFieldNew.gridy = count;
		textFieldNew.addKeyListener(this);
		textFieldNew.addFocusListener(this);
		textFieldNew.setFocusTraversalKeysEnabled(false);
		textFieldNew.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		panel.add(textFieldNew, gbc_textFieldNew);
		textFieldNew.requestFocus();
		
		//CREATE NEW LABEL
		WebLabel lblNew = new WebLabel("$0.00");
		GridBagConstraints gbc_lblNew = new GridBagConstraints();
		gbc_lblNew.insets = new Insets(5, 5, 5, 5);
		gbc_lblNew.gridx = 2;			
		gbc_lblNew.gridy = count;
		lblNew.setName(Integer.toString(count) + " label");
		lblNew.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		panel.add(lblNew, gbc_lblNew);
//	    createComponentMap();
	    img.setImage(query.img);
	    this.revalidate();
	    this.repaint();
	    return textFieldNew;
	}

}
