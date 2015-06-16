package experiments;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.LRTAStar.LRTAStar;
import core.Algorithms.RTAStar.RealTimeAStar;
import dataContainer.Coordinate;

public class AstarVsRobertStar extends AbstractExperiment {

	public AstarVsRobertStar(){
		super();
		names.add("a_running_time");
		names.add("a_path_length");
		names.add("robStar_running_time");
		names.add("robStar_path_length");
		names.add("map_complexity");
	}
	
	public static void experiment(int repeats){
		double complexity = 0 ;
		
		String[] measurements =  new String[5];
		
		measurements[0] = ("a_running_time");
		measurements[1] = ("a_path_length");
		measurements[2] = ("robStar_running_time");
		measurements[3] = ("robStar_path_length");
		measurements[4] = ("map_complexity");
		values.add(measurements);
		
		for(int i= 0; i < repeats;i++){
			System.out.println("run " + i);
		
		String name = "maze-"+ i+ ".map";
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
		System.out.println("atar " + i);
		//test RobertA*
		pathFinder = new LRTAStar(map);
		current = home.clone();
		int rASteps  = 0;
		long rAStart = System.nanoTime();
		while(current.x != target.x || current.y != target.y){
		current = pathFinder.getShortestPath(current, target);
		//System.err.println(current);
		rASteps ++;
		}
		long rAEnd = System.nanoTime();
		
		
		
		//save measurement
		measurements =  new String[5];

		measurements[0] = Double.toString(aEnd - aStart);
		measurements[1] = Double.toString(aSteps);
		measurements[2] = Double.toString(rAEnd - rAStart);
		measurements[3] = Double.toString(rASteps);
		measurements[4] = Double.toString(complexity);
		
		
		
		//System.out.println(measurements.toString());
		values.add(measurements);
		
		
		}
		
		
		writeCsv("experiments/a_star/data/astar_experiment"+complexity+".csv");
	}
	
	public static void main(String[] args){
	
		experiment(100);
		
	}
	
}
