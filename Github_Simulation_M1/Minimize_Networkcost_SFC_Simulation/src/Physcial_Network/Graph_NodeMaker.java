package Physcial_Network;

/**
 * Created by 大樹 on 2018/06/26.
 */
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.*;
public class Graph_NodeMaker {
    /**Graphのノード形成関数*/
    public Graph<MyNode,MyEdge> NodeMaker(int num_Service ,int num_Terminal) {
        Random rnd = new Random();
        Graph<MyNode, MyEdge> Graph = new UndirectedSparseGraph<MyNode, MyEdge>();
        int type_node,nums=0,numt=0,cap=0,cost=0;
        /**グラフのノード形成*/
        for(int a=0;a<num_Service+num_Terminal;){
            type_node = rnd.nextInt()%2;
            cap=NodeCap();
            cost = NodeCost();
            if(type_node ==0&&nums<=num_Service){
                MyNode n = new MyNode("Service",cap,cost,0);
                Graph.addVertex(n);
            }
            else if(type_node==1&&numt<=num_Terminal){
                MyNode n = new MyNode("Terminal",0,0,0);
                Graph.addVertex(n);
            }
        }
        return Graph;
    }
    private int NodeCap(){
        Random rnd = new Random();
        int cap = 20+rnd.nextInt(5)*10;
        return cap;
    }
    private int NodeCost(){
        Random rnd = new Random();
        int cost = 3+rnd.nextInt(17);
        return cost;
    }
}
