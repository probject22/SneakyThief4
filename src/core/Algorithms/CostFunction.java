package core.Algorithms;

/**
 *
 * Interface to be used with the AStar class. Represents the cost function.
 *
 * Created by Stan on 26/04/15.
 */
public interface CostFunction<E> {
    double getCost(Node<E> from, Node<E> to);
}
