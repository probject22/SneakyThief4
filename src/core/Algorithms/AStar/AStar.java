package core.Algorithms.AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * Class that generically performs the AStar algorithm. It takes a neighbourGenerator that generates the neighbours of
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
 * Created by Stan on 26/04/15.
 */
abstract public class AStar<E,T> {

	private int maxDepth = 300;

    /**
     * Constructor
     *
     */
    public AStar(){}

    /**
     * Returns a list of outcomes as specified in the generic variable.
     * @param fromElement starting element
     * @param toElement final element
     * @return
     */
    public E getShortestPath(T fromElement, T toElement){
    	boolean debug = false;

        Node<T> from = new Node<>();
        from.element = fromElement;
        Node<T> to = new Node<>();
        to.element = toElement;

        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        from.f = 0;
        from.g = 0;
        from.h = getHeuristic(from, to);
        open.add(from);


        // loop through the fringe
        while (!open.isEmpty()){
        	

            // get the value with the lowest cost prospection
            Node current = Collections.min(open, new NodeComparator());
            
            if(current.g< maxDepth){
        		return getResult(current);
        	}

            if (debug) System.out.println("Current: " + current.element);
            // remove the current element from the fringe
            open.remove(current);

            // generate neighbours
            List<Node<T>> neighbours = getNeighbours(current);

            //loop through neighbours
            for (Node neighbour : neighbours) {
                // Set parent
                neighbour.parent = current;

                if (neighbour.equals(to)) {
                    //return result
                    return getResult(neighbour);
                }

                // calculate functions
                neighbour.g = getCost(current, neighbour);
                neighbour.h = getHeuristic(neighbour, to);
                neighbour.f = neighbour.g + neighbour.h;

                // Check whether to add the new neighbour to the fringe
                if (open.contains(neighbour)) {
                    int i = open.indexOf(neighbour);
                    Node neighbour2 = open.get(i);
                    if (neighbour.f < neighbour2.f)
                        open.set(i, neighbour);
                }
                else if (closed.contains(neighbour)){
                    int i = open.indexOf(neighbour);
                    Node neighbour2 = open.get(i);
                    if (neighbour.f < neighbour2.f)
                        open.add(neighbour);
                }
                else open.add(neighbour);

                // remove the current node from the fringe
                closed.add(current);

            }

        }
        return null;
    }

    protected abstract List<Node<T>> getNeighbours(Node<T> current);

    protected abstract E getResult(Node<T> neighbour);

    protected abstract double getCost(Node<T> current, Node<T> neighbour);

    protected abstract double getHeuristic(Node<T> from, Node<T> to);

    /**
     * Compares two nodes based on their f
     */
    class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.f < n2.f)
                return -1;
            if (n1.f > n2.f)
                return 1;
            return 0;
        }
    }

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
