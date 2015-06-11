package core.Algorithms.RTAStar;

import gui.BeliefMapGui;

import org.junit.Test;

import core.Map;
import dataContainer.Coordinate;

public class RealTimeAStarTest {

	@Test
	public static void main(String[] args) {
		
		Map map = Map.maze(20, 20, .8);
		
		RealTimeAStar rta = new RealTimeAStar(map);
		
		BeliefMapGui nmp = new BeliefMapGui(map, "Blah");
		
		System.out.println(
			rta.getShortestPath(
					new Coordinate(1,1,0), 
					new Coordinate(18,18,0)
			)
		);
	}

}
