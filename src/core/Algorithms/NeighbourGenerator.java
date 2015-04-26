package core.Algorithms;

import java.util.List;

/**
 * Interface for use with the AStar algorithm. Generates neighbouring nodes for a specific node.
 * Created by Stan on 26/04/15.
 */
public interface NeighbourGenerator<E> {

    List<Node> getNeighbours(Node<E> node);
}
