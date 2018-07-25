package Execution;

/**
 * Created by Daiki Yamada on 2017/09/18.
 */
import java.util.*;

import GraphMaking.MyNode;
import GraphMaking.*;
import Demand.*;

import Result_Out.*;
public class Executer {
    /**
     * 総当り配置用のパラメータ
     */

    class ResultsWriter extends ResultWriter{}
    public Allocation executer( List<Node_List> Subgraph, List<Integer> Source_Node, List<Request_VNFChain> Req_VNFChain, int num_VNFChain, int num_VNFC,List<Subtopology> Subgraph2,int num_Node,double p ,int ava,int vnf) {
        int [][] Optimal_Allocated_VNFChain = new int[num_VNFChain][num_VNFC];
        int [][] Allocated_VNFChain = new int[num_VNFChain][num_VNFC];
        int pattern =0;
        /**配置リスト初期化*/
        for(int a=0;a<num_VNFChain;a++){
            for(int b=0;b<num_VNFC;b++){
                Optimal_Allocated_VNFChain[a][b]=10000;
                Allocated_VNFChain[a][b]=10000;
            }
        }

        System.out.println("シミュレーション実行中１");
        /**配置用クラスの宣言*/
        Allocation Optimal_allocation = new Allocation(100000,Optimal_Allocated_VNFChain,p,ava,vnf);
        Allocation allocation = new Allocation(100000,Allocated_VNFChain,p,ava,vnf);

        /**DFSによる配置*/
        Optimal_allocation=Allocation(Subgraph, Source_Node, num_VNFChain, num_VNFC, 0, 0, Req_VNFChain,Subgraph2,allocation,Optimal_allocation,num_Node);
        System.out.println("シミュレーション実行中２");
        return Optimal_allocation;
    }

    public Allocation Allocation(List<Node_List> Subgraph, List<Integer> Source_Node, int num_VNFChain, int num_VNFC, int VNF_inChain, int number_VNFChain, List<Request_VNFChain> Req_VNFChain,List<Subtopology> Subgraph2,Allocation allocation,Allocation Optimal_allocation,int num_Node) {
        Deque<MyNode> stack = new ArrayDeque<MyNode>(); //Queue for DFS
        /**訪問確認リスト*/
        List<Integer> visited_Node = new ArrayList<>();
        /**訪問ノードリストの初期化*/
        for (int a = 0; a < num_Node; a++) visited_Node.add(1);
        for(int a=0;a<Subgraph.get(number_VNFChain).Node_list2.size();a++){
            visited_Node.set(Subgraph.get(number_VNFChain).Node_list2.get(a).Vertex_num,0);
        }
        /**始点の入力*/
        for (int a = 0; a < Subgraph.get(number_VNFChain).Node_list2.size(); a++) /**始点の入力*/ {
            if (Source_Node.get(number_VNFChain) == Subgraph.get(number_VNFChain).Node_list2.get(a).Vertex_num) {
                stack.add(Subgraph.get(number_VNFChain).Node_list2.get(a));
                break;
            }
        }

        while (stack.size() != 0) {/**１つのVNFの配置*/
            MyNode node = stack.pop();
            int sum_capacity = 0;
            /**データセンタ&訪問していないとき*/
            if (node.VertexID == "Data Center" && visited_Node.get(node.Vertex_num) == 0) {
              //  System.out.println(Req_VNFChain.get(number_VNFChain).VNFList.get(VNF_inChain) + "of Chain" + number_VNFChain + "→" + node.VertexID + node.Vertex_num + "配置中");
                visited_Node.set(node.Vertex_num,1);

                /**未訪問ノードのスタック代入*/
                for (int a = 0; a < node.Connected_Node.size(); a++) {
                    for (int k = 0; k < Subgraph.get(number_VNFChain).Node_list2.size(); k++) {
                        if (Subgraph.get(number_VNFChain).Node_list2.get(k).Vertex_num == node.Connected_Node.get(a))
                            if (visited_Node.get(node.Connected_Node.get(a)) == 0) {
                                stack.add(Subgraph.get(number_VNFChain).Node_list2.get(k));
                            }
                    }
                }

                /**当該データセンタノードの配置済み計算量の計算*/
                for (int a = 0; a < num_VNFChain; a++) {
                    for (int b = 0; b < num_VNFC; b++) {
                        if (allocation.Allocation_Pattern[a][b] == node.Vertex_num) {
                            sum_capacity += Req_VNFChain.get(a).VNF_Capacity.get(b);
                        }
                    }
                }

                /**VNF提供可能リストの確認*/
                int available = 0;
                int num1 = Req_VNFChain.get(number_VNFChain).VNFList.get(VNF_inChain);
                if (node.VNFList.get(num1) == 1) {
                    available = 1;
                }

                /**DCノードのキャパシティ > 配置済み計算能力＋要求計算能力*/
                if (available == 1 && sum_capacity + Req_VNFChain.get(number_VNFChain).VNF_Capacity.get(VNF_inChain) <= node.capacity) {
                  //  System.out.println("case1");
                    allocation.Allocation_Pattern[number_VNFChain][VNF_inChain] = node.Vertex_num;
                    /**最後のVNFではないとき*/
                    if (VNF_inChain < num_VNFC - 1) Optimal_allocation= Allocation(Subgraph, Source_Node, num_VNFChain, num_VNFC, VNF_inChain + 1, number_VNFChain, Req_VNFChain,Subgraph2,allocation,Optimal_allocation,num_Node);

                    /**最後のVNFのとき*/
                    else if (VNF_inChain == num_VNFC - 1) {
                        /**最後のVNFChainじゃないとき*/
                        if (number_VNFChain < num_VNFChain - 1) Optimal_allocation=Allocation(Subgraph, Source_Node, num_VNFChain, num_VNFC, 0, number_VNFChain + 1, Req_VNFChain,Subgraph2,allocation,Optimal_allocation,num_Node);

                        /**最後のVNFChainのとき*/
                        else if(number_VNFChain==num_VNFChain-1){
                            int hop=0;
                            /**ホップ数の計算*/
                            allocation.Each_Hop=Counter_Hop(allocation.Allocation_Pattern,Subgraph2,num_VNFChain,num_VNFC,Source_Node);
                            for(int k=0;k<num_VNFChain;k++){
                                if(hop<allocation.Each_Hop.get(k)){
                                    hop = allocation.Each_Hop.get(k);
                                }
                            }

                            /**最大ホップ数の最小化*/
             /*                   for(int i=0;i<num_VNFChain;i++){
                                    System.out.print("VNFChain"+i+"配置=");
                                    for(int k=0;k<num_VNFC;k++){
                                        System.out.print(allocation.Allocation_Pattern[i][k]+" ");
                                    }
                                    System.out.println();
                                }
                                System.out.println("Max_Hop="+hop);
                                System.out.println();
               */                 if (Optimal_allocation.num_Hop > hop) {
                                   Optimal_allocation.num_Hop = hop;
                                   for(int a=0;a<num_VNFChain;a++){
                                       for(int b=0;b<num_VNFC;b++){
                                           Optimal_allocation.Allocation_Pattern[a][b] = allocation.Allocation_Pattern[a][b];
                                       }
                                   }
                                    Optimal_allocation.Each_Hop=allocation.Each_Hop;
                                   // allocation.Allocation_Pattern[number_VNFChain][VNF_inChain]=10000;
                                System.out.println("計算中");
                                }
                        }
                    }
                }

            } else if (node.VertexID == "Terminal") {
                visited_Node.set(node.Vertex_num, 1);
                for (int a = 0; a < node.Connected_Node.size(); a++) {
                    for (int k = 0; k < Subgraph.get(number_VNFChain).Node_list2.size(); k++) {
                    if (Subgraph.get(number_VNFChain).Node_list2.get(k).Vertex_num == node.Connected_Node.get(a)) {
                        if (visited_Node.get(node.Connected_Node.get(a)) == 0) {
                                stack.add(Subgraph.get(number_VNFChain).Node_list2.get(k));
                            }
                        }
                    }
                }
            }
  //          System.out.println(stack.size());
        }
            return Optimal_allocation;
    }
    public static List<Integer>  Counter_Hop(int[][] Allocation_Pattern,List<Subtopology> Subgraph,int num_VNFChain,int num_VNF,List<Integer> Source_Node) {
        List<Integer> Num_Hop = new ArrayList<Integer> ();/**1つの配置による各サービスのホップ数格納リスト*/
            for(int b=0;b<num_VNFChain;b++){
             int sum_hop =0;
                for(int c=0;c<num_VNF;c++){
                    if(c==0){
                        sum_hop+=Subgraph.get(b).Hop[Source_Node.get(b)][Allocation_Pattern[b][c]];
                        sum_hop+=Subgraph.get(b).Hop[Allocation_Pattern[b][num_VNF-1]][Source_Node.get(b)];
                    }
                    else sum_hop+=Subgraph.get(b).Hop[Allocation_Pattern[b][c-1]][Allocation_Pattern[b][c]];
                }
                Num_Hop.add(sum_hop);
        }
        return Num_Hop;
 }

}
