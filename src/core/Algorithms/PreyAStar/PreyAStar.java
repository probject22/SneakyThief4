package core.Algorithms.PreyAStar;

import core.Map;
import core.sprite.Agent;
import core.sprite.Thief;
import dataContainer.GridState;

public class PreyAStar {
	
	private int[][] costsprey;
	private int[][] costspred;
	
	public PreyAStar(Thief thief, Map belief){
		GridState[][] map = belief.getCopyOfMap();
		for(int i =0;i<map.length;i++){
			for(int j = 0;j<map[0].length;j++){
				//costsprey[i][j] = 
			}
		}
	}
	

}
