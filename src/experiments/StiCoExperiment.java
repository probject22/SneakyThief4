package experiments;

import core.BeliefMap;
import core.Map;
import core.Simulator;
import core.sprite.Guard;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class StiCoExperiment extends AbstractExperiment {
	
	public static void main(String[] args){
		
	}

	public StiCoExperiment(){
		super();
		names.add("ratiocoverage");
		names.add("guard_number");
		names.add("map_size");
		names.add("map_density");
		
	}
	
	public void runExperiment(int repeats, int guardnum, int mapwidth, int mapheight, double mapdensity){
		for(int i = 0;i<repeats;i++){
			System.out.println("run " + i);
			Map map; // = generateMap(mapwidth, mapheight, mapdensity);
			
			SpriteManager manager = SpriteManager.instance();
			/*for(int j = 0;j<guardnum;j++){
				Guard newGuard = 
						new Guard(new Coordinate((int)Math.random()*mapwidth, (int)Math.random()*mapheight, 0));
				manager.addAgent(newGuard);
			}*/
			new Simulator(guardnum, map);
			
		}
		
		double ratio = ratioCovered(map, combinedbeliefmap);
		String[] measurements;
		measurements[0] = Double.toString(ratio);
		measurements[1] = Integer.toString(guardnum);
		measurements[2] = Integer.toString(mapwidth*mapheight);
		measurements[3] = Double.toString(mapdensity);
		
		values.add(measurements);
		
		writeCsv("experiments/StiCo/data/stico_experiment.csv");
	}
	
	public double ratioCovered(Map map, BeliefMap beliefmap){
		double ratio;
		int covered = 0;
		int total = map.getMapWidth()*map.getMapHeight();
		GridState[][] mapcopy = beliefmap.getCopyOfMap();
		for(int i =0;i<beliefmap.getMapWidth();i++){
			for(int j = 0;j<beliefmap.getMapHeight();j++){
				if(mapcopy[i][j]!=GridState.unknown){
					covered += 1;
				}
			}	
		}
		ratio = covered/total;
		return ratio;
	}
}
