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
		StiCo.runExperiment(5, 10);
		System.exit(0);
	}

	public StiCoExperiment(){ 
		super();
		names.add("ratio"+"coverage");
		names.add("guard_number");
		names.add("map_type");
		names.add("map_size");
		names.add("map_density");
		
	}
	
	public void runExperiment(int repeats, int guardnum){
		double ratio = 0;

		SpriteManager manager = SpriteManager.instance();
		
		for(int k =0;k<repeats;k++){
			//TODO: GET A MAP
			Map map = new Map("maze-"+k+".map");//null; // = generateMap(mapwidth, mapheight, mapdensity); 

			GridState[][] grid = map.getCopyOfMap();
			int x = grid.length;
			int y = grid[0].length;
			double density = 0;
			for (int r =0; r < x; r++)
				for(int s = 0; s < y; s++)
					if (grid[r][s].moveable())
						density++;

			double mapdensity = density/((x*y)*1.0);
			BeliefMap beliefMap = new BeliefMap(map);
			for(int i = 1;i<=guardnum;i++){
					System.err.println("k "+ k + " g "+ i);
					manager.resetAgentList();
					new Simulator(i, map, beliefMap);
					
					ratio = ratioCovered(beliefMap);
					
					String[] measurements = new String[5];
					measurements[0] = Double.toString(ratio);
					measurements[1] = Integer.toString(i);
					measurements[2] = "maze";//MAP TYPE
					measurements[3] = Integer.toString(grid.length*grid[0].length);
					measurements[4] = Double.toString(mapdensity);
					
					values.add(measurements);
					
			}
		}
		System.out.println("Writing results...");
		writeCsv("experiments/StiCo/data/stico_experiment_0.3.csv");
		
	}
	//returns the ratio covered by the agents running StiCo
	public double ratioCovered(BeliefMap beliefmap){
		double ratio;
		double covered = 0;
		double total = beliefmap.getMap().length*beliefmap.getMap()[0].length;
		GridState[][] mapcopy = beliefmap.getCopyOfMap();
		for(int i =0;i<beliefmap.getMap().length;i++){
			for(int j = 0;j<beliefmap.getMap()[0].length;j++){
				if(mapcopy[i][j]!= GridState.unknown&&mapcopy[i][j]!= GridState.unknownOBJECT){
					covered ++;
				}
			}	
		}
		ratio = covered/total;
		return ratio;
	}
}
