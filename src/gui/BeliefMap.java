package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import core.Map;

public class BeliefMap extends JFrame {
	int extra = 30;
	JPanel mapPanel;
	 private JLayeredPane lpane = new JLayeredPane();
	public BeliefMap() {
		new BorderLayout();
		new MapPanel(900, 700, extra);
		this.add(mapPanel);
		 
		 this.setVisible(true);
	}
	public BeliefMap(Map map, String frameName) {
		new BorderLayout();
		new MapPanel(900, 700, extra);
		this.setName(frameName);
		this.add(mapPanel);
		 ((MapPanel)mapPanel).setMap(map);
		 this.setVisible(true);
	}

}
