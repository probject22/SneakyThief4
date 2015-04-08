/**
 * 
 */
package core;

import dataContainer.GridState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

/**
 * the map is stored as GridState[x][y]
 * 
 * @author ing. R.J.H.M. Stevens
 * @version 1.0
 * @begin 1-4-2015
 */
public class Map {
	private boolean debug = false;

	/**
	 * If no mapfile is set load the default map
	 */
	public Map(){
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
	public Map(String path){
		intit(path);
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
			 }
			 //Close the input stream
			 br.close();
		 }catch (Exception e){//Catch exception if any
			 System.err.println("Error: " + e.getMessage());
		 }finally{
		 }
	}
	
	/**
	 * The real map
	 */
	private GridState[][] map;
	
}
