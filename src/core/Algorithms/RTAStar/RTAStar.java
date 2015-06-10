package core.Algorithms.RTAStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
*
* Class that generically performs the Real time AStar algorithm. It takes a neighbourGenerator that generates the neighbours of
* a node, a CostFunction that should evaluates the actual cost between two nodes, and a HeuristicFunction that evaluates
* the heuristic cost between two nodes. This way we can change the heuristic without touching this class.
*
* To change the heuristic, look at the MapHeuristicFunction class.
* To change the cost function, look at the MapCostFunction class.
*
* To apply on something else than the map, make new classes that implement HeuristicFunction, CostFunction, and
* NeighbourGenerator.
*
* E is the type of the result (for instance move directions)
* T is the type of the node elements (for instance map coordinates)
*
* Created by Sina Coppied mostly from Stan on 15/05/15.
*/
	abstract public class RTAStar<E,T> {


	    /**
	     * Constructor
	     *
	     */
	    public RTAStar(){}

	    /**
	     * Returns a list of outcomes as specified in the generic variable.
	     * @param fromElement starting element
	     * @param toElement final element
	     * @return
	     */
	    public E getShortestPath(T fromElement, T toElement){

	        Node<T> from = new Node<>();
	        from.element = fromElement;
	        Node<T> to = new Node<>();
	        to.element = toElement;


	        from.f = 0;
	        from.g = 0;
	        from.h = getHeuristic(from, to);

	        // generate neighbours
	        List<Node<T>> neighbours = getNeighbours(from);
	            
	        //returning node
	        Node<E> result = null;
	        //loop through neighbors
	        for (Node neighbour : neighbours) {

	            if (neighbour.equals(to)) {
	            	//return result
	            	return (E) neighbour.element;
	                }

	            // calculate functions
	            neighbour.g = getCost(from, neighbour);
	            neighbour.h = getHeuristic(neighbour, to);
	            neighbour.f = neighbour.g + neighbour.h;

	            // Return The moving neighbor min(f(x))
	            if (result == null)
	              	result = neighbour;
	            else if (result.f > neighbour.f)
	                result = neighbour;
	            }
	            
	        return result.element;
	    }

	    protected abstract List<Node<T>> getNeighbours(Node<T> current);

	    protected abstract double getCost(Node<T> current, Node<T> neighbour);

	    protected abstract double getHeuristic(Node<T> from, Node<T> to);


	    /**
	     *
	     * Node for use with the AStar algorithm
	     *
	     * Created by Stan on 26/04/15.
	     */
	    public class Node<E> {
	        public Node parent;
	        public double g; // cost value
	        public double h; // heuristic value
	        public double f; // cost + heuristic
	        public E element;// identifying element

	        /**
	         * equals method checking whether the elements of the nodes are equal
	         * @param node node to be compared with
	         * @return true if equal, false otherwise
	         */
	        public boolean equals(Node<E> node){
	            return element.equals(node.element);
	        }
	    }

	}
