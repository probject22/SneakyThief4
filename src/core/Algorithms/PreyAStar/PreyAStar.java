/**
 * 
 */
package core.Algorithms.PreyAStar;

import java.util.ArrayList;

import core.Map;
import core.Algorithms.PathFinder;
import core.Algorithms.AStar.AStar;
import core.Algorithms.AStar.AStar.Node;
import core.sprite.Agent;
import core.sprite.Guard;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;

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
	
	/**
	 * This function returns the best node out of two nodes
	 * @param node1 input-1
	 * @param node2 input-2
	 * @return the best node of the two
	 */
	protected EvaluationNode evaluationFunction(EvaluationNode node1, EvaluationNode node2){
		EvaluationNode bestnode;
		double n1score = node1.predatorCost-node1.preyCost;
		double n2score = node2.predatorCost-node2.preyCost;
		if(n1score<n2score){
			return node1;
		}
		else{
			return node2;
		}
	}

	public class EvaluationNode{
		public int x;
		public int y;
		
		public double preyCost = -1;
		public double predatorCost = -1;
	}

	@Override
	public int getPathLengt() {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
