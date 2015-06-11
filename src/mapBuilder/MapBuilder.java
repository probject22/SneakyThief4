/**
 * 
 */
package mapBuilder;

import core.Map;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class MapBuilder {

	/**
	 * 
	 */
	public MapBuilder(int amount, int startSize, int stopSize, int increase ) {
		int currentSize = startSize;
		while (currentSize <= stopSize){
			for (int i =0; i < amount; i++){
				String name = "maze-" + currentSize + "x" + currentSize + "-" + i + "map";
				Map map = Map.map(currentSize, currentSize);
				//TODO SAVE THE MAP IN A FILE
			}
			currentSize += increase;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
