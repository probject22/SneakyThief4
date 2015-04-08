/**
 * 
 */
package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import dataContainer.Constants;
import dataContainer.GridState;

/**
 * the map is stored as GridState[x][y]
 * 
 * @author ing. R.J.H.M. Stevens
 * @version 1.0
 * @begin 1-4-2015
 */
public class Map {
	private boolean debug = false;

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
	
	public Map(String name){
		intit(name);
	}
	
	public GridState[][] getCopyOfMap(){
		return map == null ? null: map.clone();
	}
	private void intit(String name){
		
		if (debug) System.out.println("the map class stared in debug modus");
		if (debug) System.out.println("The path to the map is: " + name);
		
		try{
			 BufferedReader br = new BufferedReader(new FileReader(name));
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
	
	
	private GridState[][] map;
	
}
