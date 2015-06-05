package dataContainer;

import java.awt.Color;

/**
 * This stores all the possible states that the playfield can have. Stores colors with the states. It also contains the char that represents the state in a map file
 * @author Robert Stevens, Stan Kerstjens
 *
 */
public enum GridState {
	
	//					Color				|	Moveable	|	 char 	|	Cost	| ThiefCost		|astarCost
	Empty			(	Color.WHITE, 			true,			' ',		1.0,			1.0,			1.0			),
	Wall			(	Color.BLACK, 			false,			'-',		1.0,			1.0,			1.0			),
	Sentry			(	Color.BLUE,				true,			'P',		1.0,			1.0,			1.0			),
	Shade 			(	Color.GRAY,				true,			'S',		1.0,			1.0,			1.0			),
	Window			(	Color.YELLOW,			true,			'W',		1.5,			3.0,			3.0			),
	Door			(	new Color(102,51,0),	true,			'D',		2.5,			5.0,			5.0			),
	Target			(	Color.ORANGE,			true,			'T',		1.0,			1.0,			1.0			),
	Tree			(	new Color(128,128,0),	false,			'B',		1.0,			1.0,			1.0			),
	OuterWall		(	Color.DARK_GRAY,		false,			'+',		1.0,			1.0,			1.0			),
	unknown			(	Color.RED,				true,			'9',		1.0,			1.0,			0.1			);



	GridState(Color color, boolean moveable,  char fileVal, double agentCost, double thiefCost, double astarCost){
		m_moveable = moveable;
		this.color = color;
		this.cost = 0;
		m_fileVal = fileVal;
		this.thiefCost = thiefCost;
		this.astarCost = astarCost;
	}

	public Color color(){
		return color;
	}

	public boolean moveable(){
		return m_moveable;
	}

	public GridState stateWhenFreed(){
		return m_freedState;
	}
	public char getFileVal(){
		return m_fileVal;
	}
	
	public double getCost(){
		return cost;
	}
	
	public double getThiefCost(){
		return thiefCost;
	}
	
	public double getAstarCost(){
		return astarCost;
	}
	Color color;
	private boolean m_moveable;
	private GridState m_freedState;
	private char m_fileVal;
	private double cost;
	private double thiefCost;
	private double astarCost;
}
