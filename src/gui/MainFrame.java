/**
 * 
 */
package gui;

import dataContainer.GridState;
import dataContainer.MoveDirection;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

import java.net.URL;
import java.awt.*;

import javax.swing.*;

import com.sun.javafx.css.Style;

import java.io.*;

import core.Map;
import core.Simulator;
import core.sprite.Agent;
import core.sprite.Sprite;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class MainFrame extends JFrame {
	private SpriteManager spriteManager;
	int extra = 30;
	JPanel mapPanel;
	JPanel spritePanel;
	 private JLayeredPane lpane = new JLayeredPane();
	public MainFrame() {
		
		BorderLayout mainlayout = new BorderLayout();
		JPanel controlpanel = new ControlPanel();
		this.spriteManager = SpriteManager.instance();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,700);
		this.setLocationRelativeTo(null);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.add(controlpanel, BorderLayout.SOUTH);
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
		//TODO: Preproccess map
		//TODO: save the map to a buffer
		this.map = map;
		((MapPanel) mapPanel).setMap(map);
		((SpritePanel) spritePanel).setMap(map);
		mapPanel.repaint();
		spritePanel.repaint();
	
	}
	public void setSpriteList(Sprite[] sprites) {
		this.sprites = sprites;
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

	private Sprite[] sprites;
	private Map map;
}
