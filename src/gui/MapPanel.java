package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;

import javax.swing.JPanel;

import core.Map;
import dataContainer.GridState;

public class MapPanel extends JPanel{
	
	private Map map;
	private int extra;
	private boolean update = false;
	
	public MapPanel(int width, int height, int extra) {
		this.setBounds(0, 0, width, height);
		this.extra = extra;
		
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
				//IF THIS IS CHANGED, CHANGE SPRITEPANEL.SCALE AS WELL
				//40->20, 100->6, 200 ->3
				int cx = map.getMapWidth();
				int pxPerGridState= ((61*cx*cx)/48000)-((329*cx)/800)+(413/12)+1;
				
				//CHANGE COLOR DEPENDING ON THE GRIDSTATE
				g2.setColor(currentstate.color());
				//Fabric structure is commented out.
				//g2.fill3DRect(i*pxPerGridState, j*pxPerGridState, pxPerGridState, pxPerGridState, true);
				
				g2.fillRect(((i)*pxPerGridState)+extra, ((yl-j)*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				g2.setColor(Color.BLACK);
				g2.drawRect(((i)*pxPerGridState)+extra, ((yl-j)*pxPerGridState)+extra, pxPerGridState, pxPerGridState);
				
			}

	}
	public void setMap(Map map){
		//TODO: Preproccess map
		//TODO: save the map to a buffer
		this.map = map;
		update = true;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.WHITE);
		
		drawMap(g);
	}

}
