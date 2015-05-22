package core.Algorithms;

import java.util.List;

/**
 * Created by Sina on 22/05/15.
 */

public interface ThiefPath<E> {
	E getBestEscape(E from, List<E> guards,E to);
}