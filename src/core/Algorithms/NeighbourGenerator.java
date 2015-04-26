package core.Algorithms;

import java.util.List;

/**
 * Created by Stan on 26/04/15.
 */
public interface NeighbourGenerator<E> {

    List<Node> getNeighbours(Node<E> node);
}
