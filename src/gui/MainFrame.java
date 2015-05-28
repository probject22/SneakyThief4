/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

import javax.swing.*;

import core.Map;
import core.sprite.Sprite;
import core.sprite.SpriteManager;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class MainFrame extends JFrame {
	int extra = 30;
	JPanel mapPanel;
	JPanel spritePanel;
	 private JLayeredPane lpane = new JLayeredPane();
	public MainFrame() {
		
		new BorderLayout();
		JPanel controlpanel = new ControlPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1100,700);
		this.setLocationRelativeTo(null);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.add(controlpanel, BorderLayout.EAST);
		this.setName("MainFrame");
		 lpane.setVisible(true);
		 this.add(lpane, BorderLayout.CENTER);
		 lpane.setVisible(true);
		lpane.setBounds(0, 0, 900, 700);
		
		mapPanel = new MapPanel(900, 700, extra);
		 lpane.add(mapPanel, new Integer(0), 0);
		
		spritePanel = new SpritePanel(900, 700, extra);
		 lpane.add(spritePanel, new Integer(0), 0);
		 
		 this.setVisible(true);

	}
	public void setMap(Map map){
		((MapPanel) mapPanel).setMap(map);
		((SpritePanel) spritePanel).setMap(map);
		mapPanel.repaint();
		spritePanel.repaint();
	
	}
	public void setSpriteList(Sprite[] sprites) {
	}

	public void updateGui() {
		this.repaint();
		//spritePanel.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.WHITE);
	}
}
