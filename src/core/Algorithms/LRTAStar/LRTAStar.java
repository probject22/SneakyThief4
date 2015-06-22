package core.Algorithms.LRTAStar;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import core.DebugConstants;
import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar.Node;
import core.sprite.Agent;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class LRTAStar implements PathFinder<Coordinate> {

	private Coordinate goal;
	private Map map;
	boolean debug = DebugConstants.lrtastarDebug;
	private int[][] costMap;
	
	public LRTAStar(Map map) {
		this.map = map;
		generateCostMap();
	}

	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		
		if (this.goal == null || !(this.goal.x == to.x && this.goal.y == to.y)){
			//System.err.println("resetting costmap");
			if (goal != null)
			if(debug)System.err.println("goal"+ goal);
			//System.err.println(to);
			this.goal = to.clone();
			generateCostMap();
		}
		
		if (from.equals(to))
			return to;
		
		//System.err.println("cl "+costMap.length + " fx " + from.x + "cll "+costMap[0].length + " fy " + from.y);
		if (costMap.length > from.x && costMap[0].length > from.y)
		costMap[from.x][from.y] += 5;
		
		double bestEstemator = 0;
		Coordinate bestCoord = null;
		GridState[][] grid = map.getCopyOfMap();
		ArrayList<Coordinate> neighbors = getNeighbors(from);
		for(Coordinate tempCoord: neighbors){
			double estemator =0;
			//heuristic
			double dx = tempCoord.x - goal.x;
			double dy = tempCoord.y - goal.y;
			estemator +=  sqrt(dx * dx + dy * dy);
			
			//The cost you have to pay to get to this node
			estemator += grid[tempCoord.x][tempCoord.y].getAstarCost();
			
			//the memory
			if (costMap.length > tempCoord.x && costMap[0].length > tempCoord.y)
				estemator += (double)costMap[tempCoord.x][tempCoord.y];
			
			if (bestCoord == null || estemator < bestEstemator){
				bestCoord = tempCoord;
				bestEstemator = estemator;
			}
			
		}
		
		return bestCoord;
	}
	
	private ArrayList<Coordinate> getNeighbors(Coordinate coord){
		ArrayList<Coordinate> options = new ArrayList<Coordinate>();
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		
		options.add(new Coordinate(coord.x-1, coord.y-1,	0));
		options.add(new Coordinate(coord.x  , coord.y-1,	0));
		options.add(new Coordinate(coord.x+1, coord.y-1,	0));
		options.add(new Coordinate(coord.x-1, coord.y,	0));
		options.add(new Coordinate(coord.x+1, coord.y,	0));
		options.add(new Coordinate(coord.x-1, coord.y+1,	0));
		options.add(new Coordinate(coord.x  , coord.y+1,	0));
		options.add(new Coordinate(coord.x+1, coord.y+1,	0));
		
		GridState[][] grid = map.getCopyOfMap();
		
		for (Coordinate tempCoord: options){
			if (tempCoord.x < grid.length && tempCoord.x >= 0 && tempCoord.y < grid[0].length && tempCoord.y >= 0){
				if (grid[tempCoord.x][tempCoord.y].moveable()){
					SpriteManager manager = SpriteManager.instance();
					List<Agent> agents = manager.getAgentList();
					for(Agent agent: agents){
						Coordinate agentCoords = agent.getCoordinates();
							if(agentCoords.x != tempCoord.x||agentCoords.y!=tempCoord.y)
									out.add(tempCoord);	
					}
					if (agents.size() == 0)
						out.add(tempCoord);	
					
				}
			}
		}
	
		return out;
	}
	
	private void generateCostMap(){
		GridState[][] grid = map.getCopyOfMap();
		costMap = new int[grid.length][grid[0].length];
		for (int i = 0; i < costMap.length; i++)
			for (int j = 0; j < costMap[0].length; j++)
				costMap[i][j] = 0;
	}

	@Override
	public int getPathLengt() {
		// TODO Auto-generated method stub
		return 1;
	}

}
