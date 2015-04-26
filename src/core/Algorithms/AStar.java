package core.Algorithms;

import java.util.*;

/**
 *
 * Class that generically performs the AStar algorithm. It takes a neighbourGenerator that generates the neighbours of
 * a node, a CostFunction that should evaluates the actual cost between two nodes, and a HeuristicFunction that evaluates
 * the heuristic cost between two nodes. This way we can change the heuristic without touching this class.
 *
 * To change the heuristic, look at the MapHeuristicFunction class.
 * To change the cost function, look at the MapCostFunction class.
 *
 * To apply on something else than the map, make new classes that implement HeuristicFunction, CostFunction, and
 * NeighbourGenerator.
 *
 * Created by Stan on 26/04/15.
 */
public class AStar<E,T> {


    NeighbourGenerator neighbourGenerator;
    CostFunction costFunction;
    HeuristicFunction heuristicFunction;
    ResultInterpreter<E,T> resultInterpreter;

    public AStar(
            NeighbourGenerator neighbourGenerator,
            CostFunction costFunction,
            HeuristicFunction heuristicFunction,
            ResultInterpreter<E,T> resultInterpreter){
        this.neighbourGenerator = neighbourGenerator;
        this.costFunction = costFunction;
        this.heuristicFunction = heuristicFunction;
        this.resultInterpreter = resultInterpreter;
    }

    public List<E> getShortestPath(Node from, Node to){
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        from.f = 0;
        from.g = 0;
        from.h = heuristicFunction.getHeuristic(from, to);
        open.add(from);



        while (!open.isEmpty()){
            Node current = Collections.min(open, new NodeComparator());
            open.remove(current);
            List<Node> neighbours = neighbourGenerator.getNeighbours(current);
            for (Node neighbour : neighbours) {
                if (neighbour.equals(to)) {
                    return resultInterpreter.getResult(neighbour);
                }

                neighbour.g = costFunction.getCost(current, neighbour);
                neighbour.h = heuristicFunction.getHeuristic(neighbour, to);
                neighbour.f = neighbour.g + neighbour.h;

                if (open.contains(neighbour)) {
                    int i = open.indexOf(neighbour);
                    Node neighbour2 = open.get(i);
                    if (neighbour.f < neighbour2.f)
                        open.set(i, neighbour);
                }
                else if (closed.contains(neighbour)){
                    int i = open.indexOf(neighbour);
                    Node neighbour2 = open.get(i);
                    if (neighbour.f < neighbour2.f)
                        open.set(i, neighbour);
                }
                else open.add(neighbour);

                closed.add(current);

            }

        }
        return null;
    }

    class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.f < n2.f)
                return -1;
            if (n1.f > n2.f)
                return 1;
            return 0;
        }
    }

}
