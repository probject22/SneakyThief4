package experiments;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.RTAStar.MapRTAStar;
import dataContainer.Coordinate;

public class AStarExperiment extends AbstractExperiment {

	public AStarExperiment(){
		super();
		names.add("a_running_time");
		names.add("a_path_length");
		names.add("rta_running_time");
		names.add("rta_path_length");
		names.add("map_complexity");
	}
	
	public static void experiment(int repeats){
		
		for(int i= 0; i < repeats;i++){
		double complexity = 0;
		
		Map map = Map.maze(100,100);
		PathFinder<Coordinate> pathFinder;
		Coordinate home = new Coordinate(0,0,0);
		Coordinate target = new Coordinate(100,100,0);
		
		//test A*
		pathFinder = new AStar(map);
		Coordinate current = home;
		int aSteps  = 0;
		long aStart = System.nanoTime();
		while(current != target){
		current = pathFinder.getShortestPath(current, target);
		aSteps ++;
		}
		long aEnd = System.nanoTime();
		
		//test RTA*
		pathFinder = new MapRTAStar(map);
		current = home;
		int rTASteps  = 0;
		long rTAStart = System.nanoTime();
		while(current != target){
		current = pathFinder.getShortestPath(current, target);
		rTASteps ++;
		}
		long rTAEnd = System.nanoTime();
		
		
		
		//save measurement
		double[] measurements =  new double[names.size()];
		measurements[0] = aEnd - aStart;
		measurements[1] = aSteps;
		measurements[2] = rTAEnd - rTAStart;
		measurements[3] = rTASteps;
		measurements[4] = complexity;
		
		//System.out.println(measurements.toString());
		values.add(measurements);
		
		
		}
		
		writeCsv("data/astar_experiment.csv");
	}
	
	public static void main(String[] args){
	
		experiment(1);
		
	}
	
}
