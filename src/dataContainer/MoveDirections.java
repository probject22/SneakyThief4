/**
 * 
 */
package dataContainer;

import java.awt.Color;

/**
 * This enum hold all the basic move derections
 * @author ing. Robert Stevens
 * @begin 8 apr. 2015
 * @version 1.0
 * @changes
 * @todo 
 */
public enum MoveDirections {
	/*	dx	|	dy	| start angle	| stop Angle	*/
	N	( 1, 	0, 		67.5, 			112.5 		),
	NE	( 1, 	1, 		22.5, 			67.5 		),
	
	/*
	 * East is split up in 2 parts one part that is bigger than 0 and one with is smaller then 360
	 * there is also a normal E for move commands
	 */
	EP	( 0, 	1, 		0.0, 				22.5 		),
	EN	( 0, 	1, 		337.5, 			360.0 		),
	E	( 0, 	1, 		337.5, 			22.5 		),
	
	SE	( -1, 	1, 		292.5, 			337.5 		),
	S	( -1, 	0, 		247.5, 			292.5 		),
	SW	( -1, 	-1, 	202.5, 			247.5 		),
	W	( 0, 	-1, 	157.5, 			202.5 		),
	NW	( 1, 	-1, 	112.5, 			157.5 		);
	
	MoveDirections(int x, int y,  double startAngle, double endAngle){
		this.x = x;
		this.y =y;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}
	
	public int getDx(){
		return x;
	}
	public int getDy(){
		return y;
	}
	public double getStartAngleRad(){
		return Math.toRadians(startAngle);
	}
	
	public double getEndAngleRad(){
		return Math.toRadians(endAngle);
	}
	
	private int x;
	private int y;
	private double startAngle;
	private double endAngle;
}
