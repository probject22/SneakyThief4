package core.Algorithms.AStar;

/**
 *
 * Interfact to be used for the AStar class. Represents a Heuristic Function.
 *
 * Created by Stan on 26/04/15.
 */
public interface HeuristicFunction<E> {
    double getHeuristic(Node<E> from, Node<E> to);
}
