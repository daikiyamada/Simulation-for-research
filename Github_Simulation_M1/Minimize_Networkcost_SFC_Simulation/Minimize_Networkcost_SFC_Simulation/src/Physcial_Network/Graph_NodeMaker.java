package Physcial_Network;

/**
 * Created by 大樹 on 2018/06/26.
 */
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.*;
import Parameter.Parameter;

public class Graph_NodeMaker {
    /**Graphのノード形成関数*/
    public Graph<MyNode,MyEdge> NodeMaker(int num_Service ,int num_Terminal) {
        Random rnd = new Random();
        Graph<MyNode, MyEdge> Graph = new UndirectedSparseGraph<MyNode, MyEdge>();
        int type_node,nums=0,numt=0,cap=0,cost=0;
        /**グラフのノード形成*/
        for(int a=0;a<num_Service+num_Terminal;){
            type_node = rnd.nextInt()%2;
            if(type_node ==0&&nums<num_Service){
                double t = Math.random();
                cap=NodeCap(t);
                cost = NodeCost(t);
                ArrayList<MySFC> Allocation_List = new ArrayList<MySFC>();
                MyNode n = new MyNode("Service",cap,cost,0,a,Allocation_List);
                Graph.addVertex(n);
                nums++;
                a++;
            }
            else if(type_node==1&&numt<num_Terminal){
                ArrayList<MySFC> Allocation_List = new ArrayList<MySFC>();
                MyNode n = new MyNode("Terminal",0,0,0,a,Allocation_List);
                Graph.addVertex(n);
                numt++;
                a++;
            }
        }
        return Graph;
    }
    private int NodeCap(double t){
        Parameter p = new Parameter();
        int cap = p.Node_cap(t);
        return cap;
    }
    private int NodeCost(double t){
        Parameter p= new Parameter();
        int cost = p.Node_Cost(t);
        return cost;
    }
}
