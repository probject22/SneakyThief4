package dataContainer;

import java.awt.Color;

/**
 * This stores all the possible states that the playfield can have. Stores colors with the states.
 * @author Robert Stevens, Stan Kerstjens
 *
 */
public enum GridState {
	
	//TODO remove State when freed
	//					Color				|	Moveable	|	State when Freed | char
	Empty			(	Color.WHITE, 			true,			null,			' '			),
	Wall			(	Color.BLACK, 			false,			null,			'-'			),
	Sentry			(	Color.BLUE,				true,			null,			'P'			),
	Shade 			(	Color.GRAY,				true,			null,			'S'			),
	Window			(	Color.YELLOW,			true,			null,			'W'			),
	Door			(	new Color(102,51,0),	true,			null,			'D'			),
	Target			(	Color.ORANGE,			true,			null,			'T'			),
	Tree			(	new Color(128,128,0),	false,			null,			'B'			),
	OuterWall		(	Color.DARK_GRAY,		false,			null,			'+'			);



	GridState(Color color, boolean moveable, GridState freedState, char fileVal){
		this(color,moveable,freedState, null, null, fileVal);
	}
	GridState(Color color, boolean moveable, GridState freedState, GridState enterGuard, GridState enterIntruder, char fileVal){
		this.color = color;
		m_moveable = moveable;
		m_freedState = freedState;
		m_guardState = enterGuard;
		m_intruderState = enterIntruder;
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
	private GridState m_guardState;
	private GridState m_intruderState;
	private char m_fileVal;
}
