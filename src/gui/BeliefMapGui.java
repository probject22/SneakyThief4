package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import core.Map;

public class BeliefMapGui extends JFrame {
	int extra = 30;
	JPanel mapPanel;
	 private JLayeredPane lpane = new JLayeredPane();
	public BeliefMapGui(Map map, String frameName) {
		new BorderLayout();
		this.setSize(900,700);
		mapPanel = new MapPanel(900, 700, extra);
		this.add(mapPanel);
		 ((MapPanel)mapPanel).setMap(map);
		 this.setVisible(true);
	}
	
	public void updateGui() {
		this.repaint();
		//spritePanel.repaint();
	}

	public void close(){
		setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
	}
}
