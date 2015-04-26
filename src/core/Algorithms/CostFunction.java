package core.Algorithms;

/**
 * Created by Stan on 26/04/15.
 */
public interface CostFunction<E> {
    double getCost(Node<E> from, Node<E> to);
}
