package experiments;

import core.Map;

public class AStarExperiment extends AbstractExperiment {

	public AStarExperiment(){
		super();
		names.add("a_running_time");
		names.add("a_path_length");
		names.add("rta_running_time");
		names.add("rta_path_length");
		names.add("map_complexity");
	}
	
	public void experiment(int repeats){
		double complexity = 0;
		
		Map map = Map.maze(100,100);
		
		long start = System.nanoTime();
		long end = System.nanoTime();
		
		//measurement
		double[] measurements =  new double[names.size()];
		values.add(measurements);
		
		long elapsed = end-start;
		
		
		
		
		
		
		writeCsv("data/astar_experiment.csv");
	}
	
}
