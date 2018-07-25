package algorithms;

import entities.MyEdge;
import entities.MyGraph;
import entities.MySubtopology;
import entities.MyVertex;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to compose subtopologies
 */
public class SMTPbasedAlgorithm implements Algorithm{

    private MyGraph<MyVertex, MyEdge> graph;
    private List<List<Integer>> requestList;
    private int constantQ = 4;

    public SMTPbasedAlgorithm(MyGraph<MyVertex, MyEdge> graph, List<List<Integer>> requestList){
        this.graph = graph;
        this.requestList = requestList;
    }

    @Override
    public List<MySubtopology> solve() {
        List<MySubtopology> subtopologyList = new ArrayList<MySubtopology>();
        int count = 1;
        for (List<Integer> r: requestList){
            //System.out.println("################################### REQUEST");
            MySubtopology subtopology = composeATree(r);
            subtopologyList.add(subtopology);
            /** update capacity information in a graph */
            updateCapacityOfDC(graph, subtopology);
            count++;
        }
        return subtopologyList;
    }

    private MySubtopology composeATree(List<Integer> request){
        /** prepare lists for terminal nodes and data centers */
        List<MyVertex> terminalNodeList = graph.getTerminalList();
        List<MyVertex> dcList = graph.getDCList();

        /** select a terminal node */
        Map<MyVertex, Double> densityMap = new HashMap<MyVertex, Double>();
        for (MyVertex t: terminalNodeList) {
            double x = 0;
            double xx = 0;
            for (MyVertex v : graph.getVertices()) {
                if (graph.getDistanceBetween(t, v) <= constantQ){
                    xx++;
                    if (dcList.contains(v)){
                        if (ListUtils.product(v.getVNFList(), request).size() > 0 && v.getCapacity() > 0){
                            x++;
                        }
                    }
                }
            }
            double val = x / xx;
            densityMap.put(t, val);
        }
        /** find a terminal node with maximum datacenter density */
        Map.Entry<MyVertex, Double> maxEntry = null;
        for (Map.Entry<MyVertex, Double> entry : densityMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        MyVertex root = maxEntry.getKey();
        /** prepare a tree with a root */
        MyGraph<MyVertex, MyEdge> tree = new MyGraph<MyVertex, MyEdge>();
        tree.addVertex(root);

        /** find appropriate dcs which satisfy the request & compose a tree*/
        /** find a dc with min dist for each NF in a request */
        for (int i: request) {
            //System.out.println("###################function: "+i);
            List<MyVertex> ListA = new ArrayList<MyVertex>();
            /** find active DCs which accommodate request i */
            for (MyVertex dc: dcList){
                if (dc.getVNFList().contains(i) && dc.getCapacity() > 0)
                    ListA.add(dc);
            }
            /** find a shortest paths to all DCs from any of the vertices in the tree */
            Map<List<MyVertex>, Integer> distMap = new HashMap<List<MyVertex>, Integer>();
            for (MyVertex v: tree.getVertices()){
                /** calculate lengths of shortest paths to all DCs from any of the vertices in the tree */
                for (MyVertex dc: ListA){
                    List<MyVertex> vertexPair = new ArrayList<MyVertex>();
                    vertexPair.add(v);
                    vertexPair.add(dc);
                    distMap.put(vertexPair, graph.getDistanceBetween(v, dc));
                }
            }
            /** find an entry (shortest path) with min dist */
            Map.Entry<List<MyVertex>, Integer> minEntry = null;
            for (Map.Entry<List<MyVertex>, Integer> entry : distMap.entrySet()) {
                if (minEntry == null || minEntry.getValue() > entry.getValue()) {
                    minEntry = entry;
                }
            }

            //System.out.println("size of dc: "+dcList.size());
            //System.out.println("size of A: "+ListA.size());
            /** add a dc and other vertexes on the path to the root into a tree */
            for (MyEdge e: graph.getShortestPath(minEntry.getKey().get(0), minEntry.getKey().get(1))){
                /** only edges, which are not in a tree, are added */
                if(!tree.containsEdge(e)) {
                    edu.uci.ics.jung.graph.util.Pair<MyVertex> endpoints = graph.getEndpoints(e);
                    for(MyVertex v0: endpoints){
                        if(!tree.containsVertex(v0)){
                            tree.addVertex(v0);
                            tree.addEdge(e, v0, graph.getOpposite(v0, e));
                        }
                    }
                }
            }
            /** remove a selected dc from list (we assume each dc appears 1 time in a request at most) */
            dcList.remove(minEntry.getKey().get(1));
        }
        return new MySubtopology(tree);
    }

    /**
     * update capacity information of DCs in graph
     * @param graph
     * @param subtopology
     */
    private void updateCapacityOfDC(MyGraph<MyVertex, MyEdge> graph, MySubtopology subtopology){
        List<MyVertex> dcList = subtopology.getDCList();
        for(MyVertex v: graph.getVertices()){
            if(v.getVertexID() == "data center"){
                if(dcList.contains(v)){
                    v.setCapacity(v.getCapacity() - 1);
                }
            }
        }
    }
}
