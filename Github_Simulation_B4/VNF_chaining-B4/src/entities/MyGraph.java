package entities;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.apache.commons.collections15.Factory;
import java.util.*;

/**
 * MyGraph
 *
 * graph class
 */

public class MyGraph<V, E> extends SparseGraph<V, E> {

    private Map<Set<MyVertex>, Integer> distanceMap = new HashMap<Set<MyVertex>, Integer>();
    private DijkstraDistance<MyVertex, MyEdge> dd;
    private DijkstraShortestPath<MyVertex, MyEdge> ds;

    /**
     * constructor
     */
    public MyGraph(){
        dd = new DijkstraDistance<MyVertex, MyEdge>((Graph<MyVertex, MyEdge>) this);
        ds = new DijkstraShortestPath<MyVertex, MyEdge>((Graph<MyVertex, MyEdge>) this);
    }

    /**
     * class method to return a factory
     * used in GraphGenerator
     * @return MyGraphFactory
     */
    public static Factory<Graph<MyVertex, MyEdge>> getFactory() {
        return new Factory<Graph<MyVertex, MyEdge>>() {
            @Override
            public MyGraph<MyVertex, MyEdge> create() {
                return new MyGraph<MyVertex, MyEdge>();
            }
        };
    }

    /**
     * return distance from the map if it is previously calculated.
     * If not, calculate and add distance to the map. Then, return it.
     * @param s
     * @param t
     * @return distance
     */
    public int getDistanceBetween(MyVertex s, MyVertex t) {
        Set<MyVertex> vertexPair = new HashSet<MyVertex>();
        vertexPair.add(s);
        vertexPair.add(t);

        Integer dist = distanceMap.get(vertexPair);
        if (dist == null){
            dist = dd.getDistance(s, t).intValue();
            distanceMap.put(vertexPair, dist);
            return dist;
        }
        return dist;
    }

    /**
     *  return a list of edges on shortest path
     * @param s
     * @param t
     * @return
     */
    public List<MyEdge> getShortestPath(MyVertex s, MyVertex t){
        return ds.getPath(s, t);
    }

    /**
     * return a list of dc vertexes
     * @return
     */
    public List<MyVertex> getDCList(){
        List<MyVertex> dcList = new ArrayList<MyVertex>();
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) this;
        for(MyVertex v: graph.getVertices()){
            if(v.getVertexID() == "data center")
                dcList.add(v);
        }
        return dcList;
    }

    public int getTotalCapacityOfDCs(){
        int result = 0;
        for(MyVertex dc: getDCList()){
            result += dc.getCapacity();
        }
        return result;
    }

    /**
     * return a list of terminal vertexes
     * @return
     */
    public List<MyVertex> getTerminalList(){
        List<MyVertex> terminalList = new ArrayList<MyVertex>();
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) this;
        for(MyVertex v: graph.getVertices()){
            if(v.getVertexID() == "terminal")
                terminalList.add(v);
        }
        return terminalList;
    }

    public void setUpVertexType(Map<String, Double> vertexRatioMap){
        double d = 0.0;
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) this;
        for (MyVertex v: graph.getVertices()){
            d = Math.random();
            if(d < vertexRatioMap.get("terminal"))
                v.setVertexID("terminal");
            else if(d < vertexRatioMap.get("terminal") + vertexRatioMap.get("switch"))
                v.setVertexID("switch");
            else
                v.setVertexID("data center");
        }
    }

    /**
     *
     * set up attribute, i.e. VNF list & capacity, for each data center
     *
     * @param dcList
     * @param numOfVNFType
     * @param maxCapacity
     */
    public void setUpAttributeForDC(List<MyVertex> dcList, int numOfVNFType, int maxCapacity){
        Random rnd = new Random();
        int listLength = 0;
        for(MyVertex dc: dcList){
            List<Integer> VNFList = new ArrayList<Integer>();
            int capacity = 0;
            int a = 3;
            listLength = a + rnd.nextInt(numOfVNFType-a);

            for (int i = 0; i < listLength; i++){
                int f = 1 + rnd.nextInt(numOfVNFType);
                while(VNFList.contains(f))
                    f = 1 + rnd.nextInt(numOfVNFType);
                VNFList.add(f);
            }
            capacity = 30 + rnd.nextInt(maxCapacity);

            dc.setVNFList(VNFList);
            dc.setCapacity(capacity);
        }
    }
}
