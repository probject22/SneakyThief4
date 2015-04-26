package core.Algorithms;

/**
 * Created by Stan on 26/04/15.
 */
public interface HeuristicFunction<E> {
    double getHeuristic(Node<E> from, Node<E> to);
}
