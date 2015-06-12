package experiments;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.RTAStar.MapRTAStar;
import core.Algorithms.RTAStar.RealTimeAStar;
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
			System.out.println("run " + i);
		double complexity = Math.random();
		String name = "maze-100x100-"+ i+ ".map";
		Map map = new Map(name);
		PathFinder<Coordinate> pathFinder;
		Coordinate home = new Coordinate(1,1,0);
		Coordinate target = new Coordinate(98,98,0);
		
		//test A*
		pathFinder = new AStar(map);
		Coordinate current = home.clone();
		int aSteps  = 0;
		long aStart = System.nanoTime();
		while(current.x != target.x || current.y != target.y){
		current = pathFinder.getShortestPath(current, target);
		aSteps ++;
		}
		long aEnd = System.nanoTime();
		
		//test RTA*
		pathFinder = new RealTimeAStar(map);
		current = home.clone();
		int rTASteps  = 0;
		long rTAStart = System.nanoTime();
		while(current.x != target.x || current.y != target.y){
		current = pathFinder.getShortestPath(current, target);
		rTASteps ++;
		}
		long rTAEnd = System.nanoTime();
		
		
		
		//save measurement
		String[] measurements =  new String[5];

		measurements[0] = Double.toString(aEnd - aStart);
		measurements[1] = Double.toString(aSteps);
		measurements[2] = Double.toString(rTAEnd - rTAStart);
		measurements[3] = Double.toString(rTASteps);
		measurements[4] = Double.toString(complexity);
		
		//System.out.println(measurements.toString());
		values.add(measurements);
		
		
		}
		
		writeCsv("experiments/a_star/data/astar_experiment.csv");
	}
	
	public static void main(String[] args){
	
		experiment(20);
		
	}
	
}
