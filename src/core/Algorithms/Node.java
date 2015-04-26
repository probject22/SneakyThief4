package core.Algorithms;

/**
 *
 * Node for use with the AStar algorithm
 *
 * Created by Stan on 26/04/15.
 */
public class Node<E> {
        Node parent;
        double g; // cost value
        double h; // heuristic value
        double f; // cost + heuristic
        E element;// identifying element

        /**
         * equals method checking whether the elements of the nodes are equal
         * @param node node to be compared with
         * @return true if equal, false otherwise
         */
        public boolean equals(Node<E> node){
                return element.equals(node.element);
        }
}
