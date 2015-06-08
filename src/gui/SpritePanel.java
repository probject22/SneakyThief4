package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.sun.javafx.css.Style;

import core.Map;
import core.sprite.Agent;
import core.sprite.Guard;
import core.sprite.Sprite;
import core.sprite.SpriteManager;
import core.sprite.Thief;
import dataContainer.Coordinate;
import dataContainer.MoveDirection;

public class SpritePanel extends JPanel {
	private int extra;
	private Map map;
	private SpriteManager spriteManager;

	public SpritePanel(int width, int height, int extra) {
		this.setBounds(0, 0, width, height);
		this.extra = extra;
	}

	public void setMap(Map map) {
		// TODO: Preproccess map
		// TODO: save the map to a buffer
		this.map = map;
		this.spriteManager = SpriteManager.instance();
	}

	private void drawSprites(Graphics g) {
		if (map == null)
			return;
		boolean debug = false;
		Style visionLines = new Style(null, null);
		for (Sprite sprite : spriteManager.getAgentList()) {
			Graphics2D g2d = (Graphics2D) g;
			Coordinate coords = sprite.getCoordinates();
			int xl = map.getCopyOfMap().length;
			int cx = map.getMapWidth();
			int scale= ((61*cx*cx)/48000)-((329*cx)/800)+(413/12)+1;
			if (sprite == null)
				break;
			else if (map.getMapHeight() < 100) {
				Image img = null;
				int imgWidth = 0;
				int imgHeight = 0;
				String path = "";
				if (debug)
					System.err.println(coords.angle);
				switch (MoveDirection.getDirectionFromAngle(coords.angle)) {
				case E:
				case EN:
				case EP:
					if (debug)
						System.err.println("Heading east");
					path = "resources/images/e.png";
					break;
				case N:
					if (debug)
						System.err.println("Heading north");
					path = "resources/images/n.png";
					break;
				case NE:
					if (debug)
						System.err.println("Heading NE");
					path = "resources/images/ne.png";
					break;
				case NW:
					if (debug)
						System.err.println("Heading NW");
					path = "resources/images/nw.png";
					break;
				case S:
					if (debug)
						System.err.println("Heading S");
					path = "resources/images/s.png";
					break;
				case SE:
					if (debug)
						System.err.println("Heading SE");
					path = "resources/images/se.png";
					break;
				case SW:
					if (debug)
						System.err.println("Heading SW");
					path = "resources/images/sw.png";
					break;
				case W:
					if (debug)
						System.err.println("Heading W");
					path = "resources/images/w.png";
					break;
				default:
					break;

				}
				URL url = getClass().getClassLoader().getResource(path);
				if (url == null) {
					System.err.println("Couldn't find file: " + path);
					break;
				}
				try {
					img = ImageIO.read(url);
					imgWidth = img.getWidth(this);
					imgHeight = img.getHeight(this);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				int yl = map.getCopyOfMap()[0].length;
				int x = (coords.x) * scale + extra;
				int y = (yl - coords.y) * scale + extra;
				

				g2d.drawImage(img, x, y, this);
			}
			else{
				g2d.setColor(Color.RED);
				g2d.drawRect(coords.x*scale+extra,(map.getCopyOfMap()[0].length-coords.y)*scale+extra, 1, 1);
			}

			
			int yl = map.getCopyOfMap()[0].length;
			int x = (coords.x) * scale + extra;
			int y = (yl - coords.y) * scale + extra;
			
			
			g2d.setStroke(new BasicStroke(3));

			if (sprite instanceof Thief)
				g2d.setColor(Color.RED);
			else if (sprite instanceof Guard)
				g2d.setColor(Color.GREEN);
			else
				g2d.setColor(Color.BLACK);

			int distence = (int) (scale * ((Agent) sprite).getMaxVisionRange());
			double angle1 = (coords.angle - ((Agent) sprite)
					.getVisionAngleRad() / 2);
			angle1 %= 2 * Math.PI;
			double angle2 = (coords.angle + ((Agent) sprite)
					.getVisionAngleRad() / 2);
			if (angle2 < 0)
				angle1 += 2 * Math.PI;
			angle2 %= 2 * Math.PI;
			int x1 = x + (int) ((distence * Math.cos(angle1)) + extra);
			int y1 = y + yl - (int) ((distence * Math.sin(angle1)) + extra);
			int x2 = x + (int) ((distence * Math.cos(coords.angle)) + extra);
			int y2 = y + yl
					- (int) ((distence * Math.sin(coords.angle)) + extra);
			int x3 = x + (int) ((distence * Math.cos(angle2)) + extra);
			int y3 = y + yl - (int) ((distence * Math.sin(angle2)) + extra);
			g2d.drawLine(x + 10, y + 10, x1 - 15, y1 + 15);
			g2d.drawLine(x + 10, y + 10, x2 - 15, y2 + 15);
			g2d.drawLine(x + 10, y + 10, x3 - 15, y3 + 15);
			// g2d.drawLine(x+10, y+10, 5000, 5000);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(new Color(0, 0, 0, 0));
		drawSprites(g);
	}

}
