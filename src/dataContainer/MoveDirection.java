/**
 * 
 */
package dataContainer;

/**
 * This enum hold all the basic move directions
 * @author ing. Robert Stevens
 * @begin 8 apr. 2015
 * @version 1.0
 * @changes
 * @todo 
 */
public enum MoveDirection {
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
	
	MoveDirection(int x, int y, double startAngle, double endAngle){
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


	public static MoveDirection getMoveDirection(Coordinate from, Coordinate to){
		if(from.x == to.x-1 && from.y-1 == to.y)
			return MoveDirection.SW;
		if(from.x == to.x && from.y-1 == to.y)
			return MoveDirection.S;
		if(from.x == to.x+1 && from.y-1 == to.y)
			return MoveDirection.SE;
		if(from.x == to.x-1 && from.y == to.y)
			return MoveDirection.W;
		if(from.x == to.x+1 && from.y == to.y)
			return MoveDirection.E;
		if(from.x == to.x-1 && from.y+1 == to.y)
			return MoveDirection.NW;
		if(from.x == to.x && from.y+1 == to.y)
			return MoveDirection.N;
		if(from.x == to.x+1 && from.y+1 == to.y)
			return MoveDirection.NE;
		return null;
	}

	public double getEndAngleRad(){
		return Math.toRadians(endAngle);
	}
	
	public static MoveDirection getDirectionFromAngle(double angle){
		for (MoveDirection dir: MoveDirection.values()){
			if (angle >= dir.getStartAngleRad() && angle < dir.getEndAngleRad()){
				return dir;
			}
		}
		return null;
	}
	
	private int x;
	private int y;
	private double startAngle;
	private double endAngle;

	/**
	 * Takes an angle and discretises it to a direction.
	 * @param direction
	 * @return
	 */
	public static MoveDirection angleToMoveDirection(double direction) {
//		if (direction < PI / 8 && direction > PI / 8)
		//TODO: implement this method
		return null;
	}
}
