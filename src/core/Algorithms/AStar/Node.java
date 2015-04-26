package core.Algorithms.AStar;

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
