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
	
	public MainFrame() {
		
		BorderLayout mainlayout = new BorderLayout();
		JPanel controlpanel = new ControlPanel();
		this.spriteManager = SpriteManager.instance();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,700);
		this.setLocationRelativeTo(null);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.add(controlpanel, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setName("MainFrame");

	}
	public void setMap(Map map){
		//TODO: Preproccess map
		//TODO: save the map to a buffer
		this.map = map;
	}
	public void setSpriteList(Sprite[] sprites) {
		this.sprites = sprites;
	}

	public void updateGui() {
		this.repaint();
	}

	private void drawMap(Graphics g) {

		if (map == null)
			return;
		Graphics2D g2 = (Graphics2D) g;

		GridState currentstate;

		int xl = map.getCopyOfMap().length;
		int yl = map.getCopyOfMap()[0].length;
		// TODO: fix this 
		for(int i = 0;i<map.getCopyOfMap().length;i++)
			for(int j = 0; j<map.getCopyOfMap()[0].length;j++)
			{
				currentstate = map.getCopyOfMap()[i][j];
				int pxPerGridState = 20;
				//CHANGE COLOR DEPENDING ON THE GRIDSTATE
				g2.setColor(currentstate.color());
				//Fabric structure is commented out.
				//g2.fill3DRect(i*pxPerGridState, j*pxPerGridState, pxPerGridState, pxPerGridState, true);
				
				g2.fillRect(((i)*pxPerGridState)+extra, ((yl-j)*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				g2.setColor(Color.BLACK);
				g2.drawRect(((i)*pxPerGridState)+extra, ((yl-j)*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				
			}

	}

	private void drawSprites(Graphics g) {
		boolean debug = false;
		Style visionLines = new Style(null, null);
		for (Sprite sprite : spriteManager.getAgentList()) {
			if (sprite == null)
				break;
			Coordinate coords = sprite.getCoordinates();
			Image img = null;
			int imgWidth = 0;
			int imgHeight = 0;
					String path = "";
					if(debug)System.err.println(coords.angle);
					switch(MoveDirection.getDirectionFromAngle(coords.angle)){
					case E:
					case EN:
					case EP:
						if(debug)System.err.println("Heading east");
						path = "resources/images/e.png";
						break;
					case N:
						if(debug)System.err.println("Heading north");
						path = "resources/images/n.png";
						break;
					case NE:
						if(debug)System.err.println("Heading NE");
						path = "resources/images/ne.png";
						break;
					case NW:
						if(debug)System.err.println("Heading NW");
						path = "resources/images/nw.png";
						break;
					case S:
						if(debug)System.err.println("Heading S");
						path = "resources/images/s.png";
						break;
					case SE:
						if(debug)System.err.println("Heading SE");
						path = "resources/images/se.png";
						break;
					case SW:
						if(debug)System.err.println("Heading SW");
						path = "resources/images/sw.png";
						break;
					case W:
						if(debug)System.err.println("Heading W");
						path = "resources/images/w.png";
						break;
					default:
						break;
						
					}
				URL url = getClass().getClassLoader().getResource(
						path);
				if (url == null) {
					System.err.println("Couldn't find file: "
							+ path);
					break;
				}
			try {
				img = ImageIO.read(url);
				imgWidth = img.getWidth(this);
				imgHeight = img.getHeight(this);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			int xl = map.getCopyOfMap().length;
			int yl = map.getCopyOfMap()[0].length;
			int x = (coords.x)*20+extra;
			int y = (yl-coords.y)*20+extra;
			Graphics2D g2d = (Graphics2D) g;

			g2d.drawImage(img,x , y, this);
			
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.RED);
			int distence = (int) (20*((Agent)sprite).getMaxVisionRange());
			double angle1 = (coords.angle -((Agent)sprite).getVisionAngleRad()/2);
			angle1 %= 2*Math.PI;
			double angle2 = (coords.angle +((Agent)sprite).getVisionAngleRad()/2);
			if(angle2 < 0) angle1+=2*Math.PI;
			angle2 %= 2*Math.PI;
			int x1 = x + (int)((distence*Math.cos(angle1))+extra);
			int y1 = y + yl-(int)((distence*Math.sin(angle1))+extra);
			int x2 = x + (int)((distence*Math.cos(coords.angle))+extra);
			int y2 = y + yl-(int)((distence*Math.sin(coords.angle))+extra);
			int x3 = x + (int)((distence*Math.cos(angle2))+extra);
			int y3 = y + yl-(int)((distence*Math.sin(angle2))+extra);
			g2d.drawLine(x+10, y+10, x1, y1);
			g2d.drawLine(x+10, y+10, x2, y2);
			g2d.drawLine(x+10, y+10, x3, y3);
			//g2d.drawLine(x+10, y+10, 5000, 5000);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.WHITE);
		
		drawMap(g);
		drawSprites(g);
	}

	private Sprite[] sprites;
	private Map map;
}
