/**
 * 
 */
package mapBuilder;

import java.io.File;

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
	public MapBuilder(int amount, int startSize, int stopSize, int increase,
			double startDifficulty, double stopDifficulty,
			double difficultyIncrease) {
		for (double difficulty = startDifficulty; difficulty <= stopDifficulty; difficulty += difficultyIncrease) {
			for (int currentSize = startSize; currentSize <= stopSize; currentSize += increase) {
				for (int i = 0; i < amount; i++) {
					String name = "maze-" + difficulty + "-" + currentSize
							+ "x" + currentSize + "-" + i + ".map";

					MapSaver saver = new MapSaver(path + name);
					saver.setSize(currentSize, currentSize);

					Map map = Map.maze(currentSize, currentSize, difficulty);

					GridState[][] grid = map.getCopyOfMap();
					for (int j = 0; j < grid.length; j++) {
						String line = "";
						for (int l = 0; l < grid[0].length; l++) {
							line += grid[j][l].getFileVal();
						}
						saver.addLine(line);
					}
					saver.closeMap();
				}

			}
		}

	}

	public MapBuilder(int batches, int amount, int size) {
		for (int batch = 0; batch < batches; batch++) {
			File dir = new File("batch" + batch);
			dir.mkdir();
			for (int i = 0; i < amount; i++) {
				String name = "batch" + batch + "/maze-" + size + "x" + size + "-" + i + ".map";

				MapSaver saver = new MapSaver(path + name);
				saver.setSize(size, size);

				Map map = Map.maze(size, size, Math.random());

				GridState[][] grid = map.getCopyOfMap();
				for (int j = 0; j < grid.length; j++) {
					String line = "";
					for (int l = 0; l < grid[0].length; l++) {
						line += grid[j][l].getFileVal();
					}
					saver.addLine(line);
				}
				saver.closeMap();
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// new MapBuilder(1000, 100, 500, 50, 0.1, 1, 0.1);
		new MapBuilder(1000, 100, 100);
	}

}
