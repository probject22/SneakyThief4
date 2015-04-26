package core.Algorithms;

import java.util.List;

/**
 * Created by Stan on 26/04/15.
 */
public interface ResultInterpreter<E, T> {
    List<E> getResult(Node<T> node);
}
