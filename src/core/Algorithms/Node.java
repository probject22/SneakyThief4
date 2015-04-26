package core.Algorithms;

/**
 * Created by Stan on 26/04/15.
 */
public class Node<E> {
        Node parent;
        double g;
        double h;
        double f;
        E element;


        public boolean equals(Node<E> node){
                return element.equals(node.element);
        }
}
