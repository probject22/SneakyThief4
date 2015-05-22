/**
 * 
 */
package core;

import dataContainer.Coordinate;
import dataContainer.GridState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.*;

/**
 * the map is stored as GridState[x][y]
 * 
 * @author ing. R.J.H.M. Stevens
 * @version 1.0
 * @begin 1-4-2015
 */
public class Map {
	/**
	 * the amount of meters in every square of the grid.
	 */
	public static Map mapClass;
	public static double meters_per_unit = 1;
	private boolean debug = DebugConstants.mapDebug;
	// height and width
	private int mapHeight;
	private int mapWidth;
	/**
	 * If no mapfile is set load the default map
	 */
	public Map(){
		mapClass = this;
		URL url = Map.class.getResource("/resources/maps/default.map");
		if (url != null)
			intit(url.toString().replace("file:", ""));
		else
		{
			System.err.println("The default map can't be found");
			System.exit(-1);
		}
	}
	/**
	 * Load a costom map
	 * @param path The path to the map file (e.g. maps/default.map)
	 */
	public Map(String name){
		mapClass = this;
		URL url = Map.class.getResource("/resources/maps/" + name);
		if (url != null)
			intit(url.toString().replace("file:", ""));
		else
		{
			System.err.println("The default map can't be found");
			System.exit(-1);
		}
	}
	
	
	/**
	 * Get the map
	 * @return a CLONE of the map
	 */
	public GridState[][] getCopyOfMap(){
		return map == null ? null: map.clone();
	}
	
	
	
	/**
	 * Initialise the class and load in the map file
	 * @param path the path to the map file
	 */
	private void intit(String path){
		
		if (debug) System.out.println("the map class stared in debug modus");
		if (debug) System.out.println("The path to the map is: " + path);
		
		try{
			 BufferedReader br = new BufferedReader(new FileReader(path));
			 String strLine;
			 int state = 0;
			 
			 int width = 0;
			 int height = 0;
			 int x = 0;
			 int y = 0;
			 //Read File Line By Line
			 while ((strLine = br.readLine()) != null)   {
				 	// Print the content on the console
				 if (debug)	System.out.println (strLine);
				 	switch(state){
				 		case 0: 
				 			width = Integer.parseInt(strLine);
				 			state = 1;
				 			break;
				 		case 1:
				 			height = Integer.parseInt(strLine);
				 			state = 2;
				 			map = new GridState[width][height];
				 			break;
				 		case 2:
				 			for (char cx: strLine.toCharArray()){
				 				for (GridState grids: GridState.values())
				 					if (grids.getFileVal() == cx){
				 						map[x][y] = grids;
				 						break;
				 					}
				 				x++;	
				 			}
				 			x = 0;
				 			y++;
				 			
				 	}
			 // Setting height and Width
				 	mapHeight = height;
				 	mapWidth  = width;
			 
			 }
			 //Close the input stream
			 br.close();
		 }catch (Exception e){//Catch exception if any
			 System.err.println("Error: " + e.getMessage());
		 }finally{
		 }
	}

	/**
	 * Checks whether a position on the map can be moved to.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isMoveable(int x, int y){
		if ( x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return false;
		//else
		return map[x][y].moveable();
	}

	public boolean isMoveable(Coordinate coordinate){
		return isMoveable(coordinate.x, coordinate.y);
	}
	
	/**
	 * The real map
	 */
	protected GridState[][] map;

	/**
	 * Width and Height getters
	 */
	public int getMapHeight() {
		return mapHeight;
	}
	public int getMapWidth() {
		return mapWidth;
	}

	public java.util.Map<Coordinate,GridState> getIntersectingGridstates(Coordinate from, Coordinate to){

		java.util.Map<Coordinate, GridState> intersections = new HashMap<>();

		Queue<Coordinate> fringe = new ArrayDeque<>();
		List<Coordinate> history = new ArrayList<>();
		fringe.add(from);

		while (!fringe.isEmpty()){
			Coordinate current = fringe.poll();
			if (!history.contains(current) && current.onLine(from, to)){
				fringe.add(current.left());
				fringe.add(current.top());
				fringe.add(current.bottom());
				fringe.add(current.right());
				intersections.put(current,map[current.x][current.y]);
				history.add(current);
			}
		}

		return intersections;
	}
	public static Map generate(double complexity) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
