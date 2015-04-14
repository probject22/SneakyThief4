/**
 * 
 */
package gui;

import core.Map;
import core.sprite.Sprite;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class MainFrame extends JFrame {
	private SpriteManager spriteManager;
	public MainFrame() {
		this.spriteManager = SpriteManager.instance();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
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
				int pxPerGridState = 50;
				//CHANGE COLOR DEPENDING ON THE GRIDSTATE
				g2.setColor(currentstate.color());
				//Fabric structure is commented out.
				//g2.fill3DRect(i*pxPerGridState, j*pxPerGridState, pxPerGridState, pxPerGridState, true);
				g2.fillRect(i*pxPerGridState, j*pxPerGridState, pxPerGridState, pxPerGridState);
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

			URL url = getClass().getClassLoader().getResource("images/agent.png");
			if (url == null) {
				System.err.println("Couldn't find file: "
						+ "images/agent.png");
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
			AffineTransform transform = new AffineTransform();
			
			transform.translate((coords.x-imgWidth / 2)*5, (coords.y - imgHeight / 2)*5);
			transform.rotate(coords.angle, imgWidth/2, imgHeight/2); // about its center
			transform.scale(0.2, 0.2);
			g2d.drawImage(img, transform, this);
			
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
