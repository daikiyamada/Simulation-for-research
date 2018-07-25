/**
 * Created by Daiki Yamada on 2017/09/03.
 */
import java.util.*;

import GraphMaking.MyNode;
import GraphMaking.*;
import Demand.*;
import Result_Out.*;
import Execution.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Main {
    static class GraphMake extends GraphMaker{}
    static class VNFChain extends RequestChain{}
    static class Subgraph extends SubtopologyMaker{}
    static class TN extends Terminal_Node{}
    static class Req extends Request_VNF{}
    static class Exout extends Executer{}
    static class IO extends Chain_Out{}
    static class IO2 extends Nodeinfo_Out{}
static  class IO3 extends SubHop_Calculator{}
    static class IO4 extends Result_Out{}
    public static void main(String[] args){
        System.out.println("シミュレーション開始");
        System.out.println("------------------------------------------");
        /**シミュレーション実行時間の計算*/
        long start = System.currentTimeMillis();
        /** Parameter of simulation */
        /**実験回数*/
        int num_exe =500;
        int exe_num =47;
        /**NFChainのVNF個数*/
        int num_VNFC = 3;
        int max_numVNF = 3;
        /**VNFChain個数*/
        int num_VNFChain = 5;
        int max_numVNFChain = 5;
        /**使用するVNFの個数*/
        int VNF_type = 10;
        /**ノード数*/
        int num_Node = 20;
        int num_DCnode =13;
        int num_Terminal = 7;
        /**Rホップ以内*/
        int Max_hop =4;
        /**限界計算能力のマックスキャパシティ*/
        int max_capacity=0;
        /**グラフの辺を張る確率*/
        List<Double> p2 = new ArrayList<Double>();
//        p2.add(0.001);
  //      p2.add(0.01);
        p2.add(0.05);
    //    p2.add(0.1);
      //  p2.add(0.2);
        /**提供可能リストの許可率*/
        List<Integer> available = new ArrayList<>();
//        available.add(100);
        available.add(90);
  //      available.add(80);
    //    available.add(70);
      //  available.add(60);
        List<Integer> Req_VNF = new ArrayList<Integer>();                       /**リクエストを収容するリスト*/
        IO4 io4 = new IO4();
        /**平均ホップ数・標準偏差用の引数*/
        double average_maxhop = 0;
        double sum_maxhop = 0;
        int[][] hop = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] min_Hop = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] max_Hop = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] num_simulation = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] num_DC = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] min_numDC = new int[max_numVNFChain + 1][max_numVNF + 1];
        int[][] max_numDC = new int[max_numVNFChain + 1][max_numVNF + 1];
        double[][] ave_numDC = new double[max_numVNFChain + 1][max_numVNF + 1];
        List<Integer> Max_list = new ArrayList<Integer>();
        List<Degree> OPT_List = new ArrayList<>();
        int assess = 0;
        for (int k = 0; k < num_exe; k++) {
            /**各VNFの要求計算能力の決定*/
            Req req = new Req();
            Req_VNF = req.Req_VNF(Req_VNF, VNF_type);
            for (int a = 0; a < Req_VNF.size(); a++) {
                if (max_capacity <= Req_VNF.get(a)) max_capacity = Req_VNF.get(a);
         }
            int max_sumCap = max_numVNF * max_numVNFChain * max_capacity;
            /** グラフ(ノード)の作成 **/
            List<MyNode> node_list = new ArrayList<MyNode>();                      /**全体グラフのノードリスト**/
            Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<MyNode, MyEdge>();
            GraphMake g = new GraphMake();
            /**グラフのノード設定*/
            graph = g.Graph_NodeMaker(node_list, num_Node, VNF_type, max_sumCap,num_DCnode,num_Terminal);
            /**辺を張る確率を変える場合のfor文*/
            for(int x=0;x<p2.size();x++) {
                    for (int a = 0; a < max_numVNFChain + 1; a++) {
                        for (int b = 0; b < max_numVNF + 1; b++) {
                            min_numDC[a][b] = 1000000;
                            min_Hop[a][b] = 100000;
                        }
                    }
                    Graph<MyNode, MyEdge> graph_ver2 = new UndirectedSparseGraph<>();
                    //node_list=g.Nodemaker(num_Node,VNF_type);
                    //node_list=g.Edgemaker(g.graph,node_list,num_Node);
                    for (MyNode n : graph.getVertices()) {
                        graph_ver2.addVertex(n);
                    }
                    /**グラフ（エッジ）の設定*/
                    graph_ver2 = g.NWS_EdgeMaker(node_list, num_Node, p2.get(x));
                    //graph_ver2 = g.ER_EdgeMaker(graph,node_list,num_Node,p2.get(x));
                    //graph_ver2 = g.LatticeGraph_Maker(graph,num_Node);
                    //g.Layout_Grpah(graph_ver2);
                    node_list = g.Node_ListMaker(graph_ver2);


                for (int j = num_VNFChain; j < max_numVNFChain + 1; j++) {
                    List<Integer> Source_node = new ArrayList<Integer>();                   /**ソースノードの収容リスト*/
                    List<Subtopology> Subgraph = new ArrayList<Subtopology>();
                    /**始点の決定*/
                    TN tn = new TN();
                    Source_node = tn.terminal_node(Source_node, node_list, j);
                    /**サブグラフの作成*/
                    Subgraph sg = new Subgraph();
                    IO3 io3 = new IO3();
                    List<Graph<MyNode, MyEdge>> Subgraph_List = new ArrayList<>();
                    List<Node_List> Subgraph_Nodelist = new ArrayList<>();
                    /**VNFChain数だけサブトポロジーの作成*/
                    //  Subgraph_Nodelist=sg.STMaker(node_list,Source_node,num_VNFChain,num_Node,num_VNFC);
                    Graph<MyNode, MyEdge> subgraph;
                    List<Graph<MyNode, MyEdge>> subgraph_list = new ArrayList<Graph<MyNode, MyEdge>>();

                    for (int a = 0; a < j; a++) {
                        subgraph = sg.NWS_PathMaker(node_list, Source_node, a, num_Node, num_VNFC, Max_hop);
                        //subgraph = sg.ER_PathMaker(node_list, Source_node, a, num_Node, num_VNFC, Max_hop);
                        //g.Layout_Grpah(subgraph);
                        subgraph_list.add(subgraph);
                    }
                    for (int a = 0; a < j; a++) {
                        Node_List subnodelist = sg.SubNodeList(subgraph_list.get(a), num_Node);
                        Subgraph_Nodelist.add(subnodelist);
                        Subgraph_List.add(subgraph_list.get(a));
                    }
                    /**サブトポロジーリストの作成*/

                    /**各ノード間のホップ数計算*/
                    for (int c = 0; c < j; c++) {
                        int[][] Hop = new int[num_Node][num_Node];
                        Hop = io3.Calculator(Subgraph_Nodelist.get(c).Connection_List, num_Node);
                        Subtopology s = new Subtopology(c, Subgraph_Nodelist.get(c).Connection_List, Hop);
                        Subgraph.add(s);
                    }
                    //System.out.println("終了４");


                    /**ノード情報の出力*/
                    //IO2 io2 = new IO2();
                    //io2.Nodeinfo_out(node_list);

                    for (int i = num_VNFC; i <= max_numVNF; i++) {
                        /**要求VNFChainの確定*/
                        List<Request_VNFChain> ReqChain = new ArrayList<>();
                        VNFChain c = new VNFChain();
                        //IO io = new IO();
                        ReqChain = c.VNFchaining(i, j, VNF_type, ReqChain, Req_VNF,Source_node);
                        //  io.VNFChain_Out(ReqChain, Source_node);
                        /**提供可能リスト許可率変更時のfor文*/
                        for (int y = 0; y < available.size(); y++) {
                     /*       for (int a = 0; a < max_numVNFChain + 1; a++) {
                                for (int b = 0; b < max_numVNF + 1; b++) {
                                    min_numDC[a][b] = 1000000;
                                    min_Hop[a][b] = 100000;
                                }
                            }
                        /**提供可能リスト変更*/
                       /* for (int a = 0; a < num_Node; a++) {
                            Random rnd = new Random();
                            List<Integer> List2 = new ArrayList<>();
                            if (node_list.get(a).VertexID == "Data Center") {
                                for (int b = 0; b < VNF_type; b++) {
                                    int type = rnd.nextInt(100);
                                    if (type > available.get(y)) node_list.get(a).VNFList.set(b, 0);
                                    else node_list.get(a).VNFList.set(b, 1);
                                }
                            }
                        }*/
                        /**総当りの配置*/
                        Exout out = new Exout();
                        int Hop = 10000000;
                        int[][] Allocation_Pattern = new int[num_Node][num_Node];
                        System.out.println("配置（k,VNFChain,VNF,p,vnf)=(" + k + "," + j + "," + i +","+p2.get(x)+","+available.get(y)+ ")");
                        Allocation OPT_allocation = new Allocation(10000, Allocation_Pattern,p2.get(x),available.get(y),j);
                        OPT_allocation = out.executer(Subgraph_Nodelist, Source_node, ReqChain, j, i, Subgraph, num_Node,p2.get(x),available.get(y),j);
                        /**重複具合の計算*/
                        System.out.println("求めるホップ数" + i + j + "=" + OPT_allocation.num_Hop);
                        /**提供可能リスト・辺の密度変更なしの場合*/
                        if (OPT_allocation.num_Hop != 100000) {
                            List<Integer> num_DC2 = new ArrayList<>();
                            /**配置状況*/
                            List<Integer> num_Dup = new ArrayList<Integer>();
                            num_Dup=Counter_Duplication(num_Node,subgraph_list,exe_num,available.get(y),p2.get(x),OPT_allocation.num_Hop,j);
             //               Allocation_tender(OPT_allocation,ReqChain,num_VNFChain,num_VNFC,Subgraph,exe_num,p2.get(x),available.get(y));
                            Counter_Degree(OPT_allocation,j,subgraph_list,exe_num,p2.get(x),available.get(y),num_Dup,Max_hop);
                            /**ホップ数の最小・最大計算*/
                            if (min_Hop[j][i] >= OPT_allocation.num_Hop) min_Hop[j][i] = OPT_allocation.num_Hop;
                            if (max_Hop[j][i] <= OPT_allocation.num_Hop) max_Hop[j][i] = OPT_allocation.num_Hop;
                            /**使用ＤＣ数の計算*/
                            num_DC2 = Counter_DCnum(OPT_allocation, num_VNFChain, num_VNFC);
                            if (max_numDC[j][i] <= Collections.max(num_DC2))
                                max_numDC[j][i] = Collections.max(num_DC2);
                            if (min_numDC[j][i] >= Collections.min(num_DC2))
                                min_numDC[j][i] = Collections.min(num_DC2);
                            for (Integer d : num_DC2) {
                                num_DC[j][i] += d;
                            }
                            hop[j][i] += OPT_allocation.num_Hop;
                            sum_maxhop += OPT_allocation.num_Hop;
                            Max_list.add(OPT_allocation.num_Hop);
                            num_simulation[j][i]++;
                            //k++;
                            io4.write(OPT_allocation, Subgraph_Nodelist, j, i, ReqChain, Source_node, k, node_list,exe_num,p2.get(x),available.get(y),j);
                        } else if (OPT_allocation.num_Hop == 100000) {
                            assess++;
                            Max_list.add(OPT_allocation.num_Hop);
                            System.out.println("配置不可");
                        }
                    }
                }
            }
        }
                    System.out.println(k + "回目のシミュレーション終了");
                }


                ave_numDC = Average_DCnum(num_DC, num_VNFChain, max_numVNFChain, num_VNFChain, max_numVNF, num_simulation);
                average_maxhop = sum_maxhop / ((num_exe*(max_numVNFChain-num_VNFChain+1)*p2.size()*available.size()) - assess);
                double sum_dis = 0;
                int num_unpos = 0;
                for (int a = 0; a < Max_list.size(); a++) {
                    if (Max_list.get(a) != 100000) {
                        double dis = (Max_list.get(a) - average_maxhop) * (Max_list.get(a) - average_maxhop);
                        sum_dis += dis;
                        num_unpos++;
                    }
                }
                double standard = Math.sqrt((sum_dis / num_unpos));

                int num = 0;
                double[][] average = new double[max_numVNFChain + 1][max_numVNF + 1];
                for (int a = num_VNFChain; a < max_numVNFChain + 1; a++) {
                    for (int b = num_VNFC; b < max_numVNF + 1; b++) {
                        average[a][b] = (double) hop[a][b] / num_simulation[a][b];
                    }
                }
                int k = 0;
                double[][] dislist = new double[max_numVNFChain + 1][max_numVNF + 1];
                for (int c = 0; c < Max_list.size(); ) {
                    for (int a = num_VNFChain; a < max_numVNFChain + 1; a++) {
                        for (int b = num_VNFC; b < max_numVNF + 1; b++) {
                            if (Max_list.get(c) != 100000) {
                                double dis = (double) (Max_list.get(c) - average[a][b]) * (Max_list.get(c) - average[a][b]);
                                dislist[a][b] += dis;
                            }
                            c++;
                        }
                    }
                }
                double[][] standardlist = new double[max_numVNFChain + 1][max_numVNF + 1];
                for (int a = num_VNFChain; a < max_numVNFChain + 1; a++) {
                    for (int b = num_VNFC; b < max_numVNF + 1; b++) {
                        standardlist[a][b] = Math.sqrt((dislist[a][b] / num_simulation[a][b]));
                    }
                }

                io4.write2(average_maxhop, standard, average, standardlist, num_VNFC, max_numVNF, num_VNFChain, max_numVNFChain, num_simulation, min_Hop, max_Hop, min_numDC, max_numDC, ave_numDC, Max_list, exe_num);
//        io4.write3(average_maxhop, standard, average, standardlist, num_VNFC, max_numVNF, num_VNFChain, max_numVNFChain, num_simulation, min_Hop, max_Hop, min_numDC, max_numDC, ave_numDC, Max_list,p2,available,exe_num);
        long end = System.currentTimeMillis();
        System.out.println("実行時間="+(end-start)/1000+"s");

    }

    public static List<Integer> Counter_DCnum(Allocation OPT_allocation, int num_VNFChain, int num_VNF){
        /*各VNFChainの使用ＤＣ数チェックリスト*/
        List<Integer> num_DCList = new ArrayList<>();
        for(int a=0;a<num_VNFChain;a++){
            /*データセンタ使用状況把握のためのリスト*/
            List<Integer> num_dc = new ArrayList<>();
            for(int b=0;b<num_VNF;b++){
                /*重複防止*/
                if(num_dc.contains(OPT_allocation.Allocation_Pattern[a][b])!=true){
                    num_dc.add(OPT_allocation.Allocation_Pattern[a][b]);
                }
            }
            num_DCList.add(num_dc.size());
        }
        return num_DCList;
    }

    public static double[][] Average_DCnum(int[][] num_DC,int num_VNFChain,int maxnum_VNFChain,int num_VNF,int maxnum_VNF,int[][] num_simulation){
        double average_numDC[][] = new double[maxnum_VNFChain+1][maxnum_VNF+1];
        for(int a=num_VNFChain;a<maxnum_VNFChain+1;a++){
            for(int b=num_VNF;b<maxnum_VNF+1;b++){
                average_numDC[a][b]=(double)num_DC[a][b]/(num_simulation[a][b]*a);
            }
        }
        return average_numDC;
    }

    public static List<Integer> Counter_Duplication(int num_Node,List<Graph<MyNode, MyEdge>> subgraph_list,int exe_num,int VNFList,double p,int num_hop,int num_VNFChain){
        List<Integer> NodeList = new ArrayList<>();
        int average_Duplication=0;
        for(int a=0;a<num_Node;a++) NodeList.add(0);
        for(int a=0;a<subgraph_list.size();a++){
            Collection<MyNode> Node = subgraph_list.get(a).getVertices();
            List<MyNode> Nodes = new ArrayList<>(Node);
            for(int b=0;b<Nodes.size();b++){
                int num_node = Nodes.get(b).Vertex_num;
                int num = NodeList.get(num_node)+1;
                NodeList.set(num_node,num);
            }
        }
        for(int a=0;a<num_Node;a++){
            if(NodeList.get(a)!=0) average_Duplication++;
        }
        Duplication OPT = new Duplication(average_Duplication,num_hop,num_VNFChain,p,VNFList);
        IO4 io4 = new IO4();
        io4.write_Duplication(average_Duplication,NodeList,exe_num,p,VNFList,num_Node,num_hop,num_VNFChain);
        return NodeList;
    }
    public static void Allocation_tender(Allocation OPT,List<Request_VNFChain> ReqChain,int num_VNFChain,int num_VNF,List<Subtopology> Subgraph,int exe_num,double p, int VNFList){
        int [][] numHop_SD = new int[num_VNFChain][num_VNF];
        List<Double> average_list = new ArrayList<>();
        double average=0;
        for(int a=0;a<num_VNFChain;a++){
            for(int b=0;b<num_VNF;b++){
                numHop_SD[a][b]=Subgraph.get(a).Hop[ReqChain.get(a).Source_Node][OPT.Allocation_Pattern[a][b]];
            }
        }
        for(int a=0;a<num_VNFChain;a++){
            double ave =0;
            for(int b=0;b<num_VNF;b++){
                ave+=numHop_SD[a][b];
            }
            ave=ave/num_VNF;
            average_list.add(ave);
        }
        for(int a=0;a<average_list.size();a++) {
            average+=average_list.get(a);
        }
        average = average/average_list.size();
        IO4 io4 = new IO4();
        io4.writer_SDHop(numHop_SD,average,average_list,OPT.num_Hop,exe_num,p,VNFList,num_VNFChain,num_VNF);
    }
    /**最大重複度と最大実用DCの重複度の割合*/
    public static void Counter_Degree(Allocation OPT,int num_VNFChain,List<Graph<MyNode,MyEdge>> Subgraph_List,int exe_num,double p, int VNFList,List<Integer> NodeList,int R){
        for(int a=0;a<num_VNFChain;a++){
            Collection<MyNode> Node = Subgraph_List.get(a).getVertices();
            List<MyNode> node = new ArrayList<MyNode>(Node);
            int num_Dup=0;
            for(int b=0;b<R+1;b++){
                if(node.get(b).VertexID=="Data Center"){
                    num_Dup+=NodeList.get(node.get(b).Vertex_num);
                }
            }
            IO4 io4 = new IO4();
            io4.writer_Degree(num_Dup,OPT.Each_Hop.get(a),VNFList,num_VNFChain,p,exe_num);
        }
    }
}
