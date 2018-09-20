package Physcial_Network;

/**
 * Created by 大樹 on 2018/06/26.
 */
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import Parameter.Parameter;
public class NWS_GraphMaker {
    public Graph<MyNode,MyEdge> NWS_GraphMaker(Graph<MyNode,MyEdge> Physical_Network,int k,double p){
        int numnode = Physical_Network.getVertexCount();
        int edge_num =0;
        Collection<MyNode> Node_List =Physical_Network.getVertices();
        ArrayList<MyNode> Node = new ArrayList<MyNode>(Node_List);
        Random rnd = new Random();
        Parameter par = new Parameter();
        /**リングの作成*/
        for(int a=0;a<numnode;a++){
            double t = Math.random();
            int capacity = par.Edge_cap(t);
            int cost = par.Edge_Cost(t);
            ArrayList<MySFC> Allocation_List = new ArrayList<>();
            MyEdge e = new MyEdge(edge_num,capacity,cost,capacity,Allocation_List);
            edge_num++;
            if(a==numnode-1)Physical_Network.addEdge(e,Node.get(0),Node.get(numnode-1));
            else Physical_Network.addEdge(e,Node.get(a),Node.get(a+1));
        }
        /**k-nearest　の作成*/
        for(int a=0;a<numnode;a++){
            double t = Math.random();
            int node_k = a+k;
            int capacity = par.Edge_cap(t);
            int cost = par.Edge_Cost(t);
            ArrayList<MySFC> Allocation_List = new ArrayList<>();
            MyEdge e = new MyEdge(edge_num,capacity,cost,capacity,Allocation_List);
            edge_num++;
            if(node_k<numnode){
                Physical_Network.addEdge(e,Node.get(a),Node.get(a+k));
            }
            else{
                int num = numnode - (a+1);
                num = k -num;
                Physical_Network.addEdge(e,Node.get(a),Node.get(num-1));
            }
        }
        /**リンクの付け足し*/
        Collection<MyEdge> List = Physical_Network.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>(List);
        for(int a=0;a<Edge_List.size();a++){
            Pair<MyNode> n = Physical_Network.getEndpoints(Edge_List.get(a));
            double t = Math.random();
            int capacity = par.Edge_cap(t);
            int cost = par.Edge_Cost(t);
            ArrayList<MySFC> Allocation_List = new ArrayList<>();
            MyEdge e = new MyEdge(edge_num,capacity,cost,capacity,Allocation_List);
            edge_num++;
            int node;
            for(;;) {
                node = rnd.nextInt(numnode - 1);
                if (n.getFirst().Node_num != Node.get(node).Node_num && n.getSecond().Node_num != Node.get(node).Node_num) break;
            }
            if(Math.random()<=p&&Physical_Network.findEdge(n.getFirst(),Node.get(node))==null){
                Physical_Network.addEdge(e,n.getFirst(),Node.get(node));
            }
        }
        return  Physical_Network;
    }
}
