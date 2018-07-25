package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * MyGraph
 *
 * subtopology class
 */
public class MySubtopology {

    /** graph */
    private MyGraph<MyVertex, MyEdge> subtopology;

    /**
     * constructor
     * @param graph
     */
    public MySubtopology(MyGraph<MyVertex, MyEdge> graph){
        //assert isTree(graph): "the graph is not tree.";
        //check if it is connected and degree?
        this.subtopology = graph;
    }

    /**
     * return the number of edges
     * @return
     */
    public int getEdgeCount(){
        return subtopology.getEdgeCount();
    }

    /**
     * return the number of vertexes
     * @return
     */
    public int getVertexCount(){
        return subtopology.getVertexCount();
    }

    /**
     * return a dc list
     * @return
     */
    public List<MyVertex> getDCList(){
        List<MyVertex> dcList = new ArrayList<MyVertex>();
        for(MyVertex v: subtopology.getVertices()){
            if(v.getVertexID() == "data center")
                dcList.add(v);
        }
        return dcList;
    }
}
