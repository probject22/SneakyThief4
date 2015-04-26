package core.Algorithms;

import java.util.List;

/**
 *
 * Inteface for use with the AStar class. Interprets the resulting node form the AStar algorithm. Node has element of type
 * T, and the interpreter will give a list of type E.
 *
 * Created by Stan on 26/04/15.
 */
public interface ResultInterpreter<E, T> {
    List<E> getResult(Node<T> node);
}
