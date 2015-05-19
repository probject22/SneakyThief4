package dataContainer;

import java.awt.Color;

/**
 * This stores all the possible states that the playfield can have. Stores colors with the states. It also contains the char that represents the state in a map file
 * @author Robert Stevens, Stan Kerstjens
 *
 */
public enum GridState {
	
	//					Color				|	Moveable	|	 char
	Empty			(	Color.WHITE, 			true,			' '			),
	Wall			(	Color.BLACK, 			false,			'-'			),
	Sentry			(	Color.BLUE,				true,			'P'			),
	Shade 			(	Color.GRAY,				true,			'S'			),
	Window			(	Color.YELLOW,			true,			'W'			),
	Door			(	new Color(102,51,0),	true,			'D'			),
	Target			(	Color.ORANGE,			true,			'T'			),
	Tree			(	new Color(128,128,0),	false,			'B'			),
	OuterWall		(	Color.DARK_GRAY,		false,			'+'			),
	unknown			(	Color.RED,				true,			'9'			);



	GridState(Color color, boolean moveable,  char fileVal){
		m_moveable = moveable;
		this.color = color;
		m_fileVal = fileVal;
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

	Color color;
	private boolean m_moveable;
	private GridState m_freedState;
	private char m_fileVal;
}
