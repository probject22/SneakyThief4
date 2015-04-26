package core.Algorithms.MAPS;

import dataContainer.Coordinate;
import dataContainer.MoveDirection;

import java.util.Map;
import java.util.Set;

/**
 * Created by Stan on 26/04/15.
 */
public class MTES {
    Coordinate current;
    Map map;
    private Map<Coordinate, Integer> history;


    public void updateLocation(Coordinate location){
        this.current = location;
    }

    public void updateHistory(Map<Coordinate,Integer> history){
        this.history = history;
    }

    private Map<Coordinate, Integer> fillHistory(Map map) {
        return null;
    }


    public MoveDirection getMovingDirection(){

        RTTEh rtteh = new RTTEh();
        MoveDirection d = rtteh.getMoveDirection();

        if (d != null){
            Set<Coordinate> n = getMinimumVisitNeighbours();
            Coordinate c = getMaximumUtility(n);
            return MoveDirection.getMoveDirection(current,c);
        } else {
            if (emptyHistory()) {
                clearHistory();
                getMovingDirection();
            } else{
                return null;
            }
        }

        //unreachable but otherwise java won't compile.
        return null;
    }

    /**
     * Checks whether all values in the history are 0.
     * @return
     */
    private boolean emptyHistory(){
        for (Map.Entry<Coordinate, Integer> coordinateIntegerEntry : history.entrySet()) {
            if(coordinateIntegerEntry.getValue() > 0)
                return false;
        }
        return true;
    }

    private void clearHistory(){

    }

    private Coordinate getMaximumUtility(Set<Coordinate> n) {
        return null;
    }

    private Set<Coordinate> getMinimumVisitNeighbours() {

        return null;
    }
}
