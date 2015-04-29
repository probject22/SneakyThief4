/**
 * 
 */
package gui;

import dataContainer.GridState;
import dataContainer.MoveDirection;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

import java.net.URL;
import java.awt.*;

import javax.swing.*;

import java.io.*;

import core.Map;
import core.Simulator;
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
				
				g2.fillRect((i*pxPerGridState)+extra, (j*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				g2.setColor(Color.BLACK);
				g2.drawRect((i*pxPerGridState)+extra, (j*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				
			}

	}

	private void drawSprites(Graphics g) {
		for (Sprite sprite : spriteManager.getAgentList()) {
			if (sprite == null)
				break;
			Coordinate coords = sprite.getCoordinates();
			Image img = null;
			int imgWidth = 0;
			int imgHeight = 0;
					String path = "";
					switch(MoveDirection.getDirectionFromAngle(coords.angle)){
					case E:
					case EN:
					case EP:
						path = "resources/images/east.png";
						break;
					case N:
						path = "resources/images/north.png";
						break;
					case NE:
						path = "resources/images/north.png";
						break;
					case NW:
						path = "resources/images/north.png";
						break;
					case S:
						path = "resources/images/south.png";
						break;
					case SE:
						path = "resources/images/south.png";
						break;
					case SW:
						path = "resources/images/south.png";
						break;
					case W:
						path = "resources/images/west.png";
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
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(img,(coords.x)*20+extra,(coords.y*20)+extra,this);
			/*AffineTransform transform = new AffineTransform();
			
			//transform.translate((coords.x-imgWidth / 2)+extra, (coords.y - imgHeight / 2)+extra);
			transform.rotate(coords.angle, imgWidth/2, imgHeight/2); // about its center
			transform.translate((coords.x*20)+extra, ((coords.y*20))+extra);
			transform.scale(0.15, 0.15);
			g2d.drawImage(img, transform, this);*/
			
			//TODO draw the vision field
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
