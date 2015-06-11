/**
 * 
 */
package mapBuilder;

import core.Map;
import dataContainer.GridState;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class MapBuilder {
	String path = "";
	/**
	 * 
	 */
	public MapBuilder(int amount, int startSize, int stopSize, int increase ) {
		int currentSize = startSize;
		while (currentSize <= stopSize){
			for (int i =0; i < amount; i++){
				String name = "maze-" + currentSize + "x" + currentSize + "-" + i + ".map";

				MapSaver saver= new MapSaver (path + name);
				saver.setSize(currentSize, currentSize);
				Map map = Map.maze(currentSize, currentSize);
				GridState[][] grid = map.getCopyOfMap();
				for (int j =0; j<grid.length; j++){
					String line = "";
					for (int l =0; l < grid[0].length; l++){
						line += grid[j][l].getFileVal();
					}
					saver.addLine(line);
				}
				saver.closeMap();
				//TODO SAVE THE MAP IN A FILE
			}
			currentSize += increase;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MapBuilder(10, 100, 500, 50 );

	}

}
