/**
 * 
 */
package core.Algorithms.PreyAStar;

import static java.lang.Math.sqrt;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.AStar.AStar.Node;
import core.sprite.Agent;
import core.sprite.Guard;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;
import core.DebugConstants;
/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class PreyAStar implements PathFinder<Coordinate> {
	protected ArrayList<EvaluationNode> evaluationList = new ArrayList<EvaluationNode>();
	protected EvaluationNode bestNode;
	
	protected Map map;
	public PreyAStar(Map map) {
		this.map = map;
	}

	@Override
	public Coordinate getShortestPath(Coordinate from, Coordinate to) {
		evaluateNodeList(getNodeList(from.clone()), true);
		
		//TODO Decide which guards to use here
		for (Agent agent: SpriteManager.instance().getAgentList()){
			if (agent instanceof Guard){
				evaluateNodeList(getNodeList(agent.getCoordinates().clone()), false);
			}
		}
		return new Coordinate(bestNode.x, bestNode.y, 0);
	}
	
	protected ArrayList<Node> getNodeList(Coordinate coord){
		AStar aStar = new AStar(map);
		aStar.getShortestPath(coord, null);
		return (ArrayList<Node>) aStar.getClosedNodes().clone();
	}
	
	protected void evaluateNodeList(ArrayList<Node> nodeList, boolean prey){
		for (Node node: nodeList){
			boolean found = false;
			for (EvaluationNode tempEval: evaluationList){
				if (node.x == tempEval.x && node.y == tempEval.y){
					updateEvalNode(tempEval, node, prey);
					found = true;
					break;
				}
			}
			
			if (!found)
				addToEvaluationList(node, prey);
				
			
		}
	}
	
	protected void addToEvaluationList(Node node, boolean prey) {
		EvaluationNode tempEval = new EvaluationNode();
		tempEval.x = node.x;
		tempEval.y = node.y;
		
		if (prey){
			tempEval.preyCost = node.g;
		}
		else{
			tempEval.predatorCost = node.g;
		}
		evaluationList.add(tempEval);
		
		if (bestNode == null)
			bestNode = tempEval;
		bestNode = evaluationFunction(bestNode, tempEval);
		
	}

	protected void updateEvalNode(EvaluationNode tempEval, Node node, boolean prey) {
		if (prey){
			if (node.g < tempEval.preyCost)
				tempEval.preyCost = node.g;
		}
		else{
			if (node.g < tempEval.predatorCost)
				tempEval.predatorCost = node.g;
		}
		if (bestNode == null)
			bestNode = tempEval;
		bestNode = evaluationFunction(bestNode, tempEval);
		
			
	}
	/*TODO IMPLEMENT THIS FUNCTION */
	protected EvaluationNode evaluationFunction(EvaluationNode node1, EvaluationNode node2){
		return node1;
	}

	public class EvaluationNode{
		public int x;
		public int y;
		
		public double preyCost = -1;
		public double predatorCost = -1;
	}
	
}
