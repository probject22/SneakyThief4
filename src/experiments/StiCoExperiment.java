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
		StiCoExperiment StiCo = new StiCoExperiment();
		StiCo.runExperiment(1000, 10, 200, 200, 0.4);
	}

	public StiCoExperiment(){ 
		super();
		names.add("ratiocoverage");
		names.add("guard_number");
		names.add("map_size");
		names.add("map_density");
		
	}
	
	public void runExperiment(int repeats, int guardnum, int mapwidth, int mapheight, double mapdensity){
		double ratio = 0;
		for(int i = 1;i<guardnum;i++){
			for(int k =0;k<repeats;k++){
				Map map = null; 
				
				SpriteManager manager = SpriteManager.instance();
				for(int j = 0;j<i;j++){
					boolean valid = false;
					while(valid == false){
			  	 		Coordinate coord = new Coordinate((int)Math.random()*mapwidth, (int)Math.random()*mapheight, 0);
						if(map.isMoveable(coord)){
			  			valid = true;
						}	
						Guard newGuard = new Guard(coord);
						manager.addAgent(newGuard);
					}
					new Simulator(guardnum, map);
					//ratio = ratioCovered(map, combinedbeliefmap);
				}
			}
			String[] measurements = new String[4];
			measurements[0] = Double.toString(ratio);
			measurements[1] = Integer.toString(guardnum);
			measurements[2] = Integer.toString(mapwidth*mapheight);
			measurements[3] = Double.toString(mapdensity);
			
			values.add(measurements);
		}
		writeCsv("experiments/StiCo/data/stico_experiment.csv");
	}
	//returns the ratio covered by the agents running StiCo
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
