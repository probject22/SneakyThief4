package core.Algorithms;

import java.util.List;

/**
 * Created by Stan on 06/05/15.
 */
public interface PathFinder<E> {
	List<E> getShortestPath(E from, E to);
}
