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
public class MapBuilder  {
	String path = "";

	/**
	 * 
	 */
	public MapBuilder(int amount, int startSize, int stopSize, int increase, double startDifficulty, double stopDifficulty, double difficultyIncrease) {
		for (double difficulty = startDifficulty; difficulty <= stopDifficulty; difficulty += difficultyIncrease) {
			for (int currentSize = startSize; currentSize <= stopSize; currentSize += increase) {
				MapGenerator generator = new MapGenerator(currentSize, difficulty, amount, "difficulty-"+difficulty+"-"+currentSize, "maze");
				new Thread(generator).start();

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
		new MapBuilder(20, 100, 500, 100, 0.0, 1, 0.1);
		//new MapBuilder(1000, 100, 100);
	}
	
	public class MapGenerator implements Runnable{
		private int size;
		private double difficulty;
		private int amount;
		private String path;
		private String namePrefix;

		MapGenerator(int size, double difficulty, int amount, String path, String namePrefix){
			this.size = size;
			this.difficulty = difficulty;
			this.amount = amount;
			this.path = path;
			this.namePrefix = namePrefix;
		}

		@Override
		public void run() {
			File dir = new File(path);
			dir.mkdir();
			for (int i = 0; i < amount; i++) {
				String name = "/"+namePrefix+"-"+ i + ".map";

				MapSaver saver = new MapSaver(path + name);
				saver.setSize(size, size);

				Map map = Map.maze(size, size, difficulty);

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
			System.err.println("a batch is finished "+ difficulty + "-" + size +"x" + size);
		}
		
	}

}


