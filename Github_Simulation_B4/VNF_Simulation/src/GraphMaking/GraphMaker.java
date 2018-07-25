package GraphMaking;

/**
 * Created by Daiki Yamada on 2017/09/14.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;

import javax.imageio.ImageIO;
import javax.swing.*;

import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class GraphMaker {
    private DijkstraDistance<MyNode, MyEdge> dd;
    private DijkstraShortestPath<MyNode, MyEdge> ds;
    /**
     * ノード作成関数
     */
    public Graph<MyNode,MyEdge> RandomGraphmaker(int num_Node, int VNF_type) {
        Graph<MyNode, MyEdge> Randomgraph = new UndirectedSparseGraph<MyNode, MyEdge>();
        Random rnd = new Random();                                  /**乱数発生*/
        ArrayList<Integer> Nodetype_list = new ArrayList<Integer>();      /**ノードタイプ判定リスト*/
        List<MyNode> Node_List = new ArrayList<MyNode>();                /**ノードリスト*/
        int Node_type;                                              /**DC or Terminal 判定用*/

        for (int a = 0; a < num_Node; a++) {
            Node_type = rnd.nextInt(100);
            ArrayList<Integer> Connection_List = new ArrayList<Integer>();/**ノードの接続状況把握リスト*/
            if(a==0){
                /**ターミナルノード挿入*/
                ArrayList<Integer> List1 = new ArrayList<Integer>();
                MyNode t = new MyNode("Terminal", List1, 0, a, Connection_List);
                Randomgraph.addVertex(t);
                Node_List.add(t);
                Nodetype_list.add(0);
            }
            if (a!=0&&Node_type > 70) {
                /**Terminal ノード挿入*/
                ArrayList<Integer> List1 = new ArrayList<Integer>();
                MyNode t = new MyNode("Terminal", List1, 0, a, Connection_List);
                Randomgraph.addVertex(t);
                Node_List.add(t);
                Nodetype_list.add(0);
            }
            else if(a!=0&&Node_type<=70){
                /**DCノード挿入*/
                ArrayList<Integer> List2 = new ArrayList<Integer>();
                for (int b = 0; b < VNF_type; b++) {
                    /**VNF 提供可能リストの作成*/
                    int type = rnd.nextInt(100);
                    if (type > 90) List2.add(1);
                    else List2.add(1);
                }
                MyNode dc = new MyNode("Data Center", List2, 40 + rnd.nextInt(15), a, Connection_List);
                Randomgraph.addVertex(dc);
                Node_List.add(dc);
                Nodetype_list.add(1);
            }
        }
        int label = 0;
        /**リンク作成*/
        for (int a = 0; a < num_Node/2; a++) {
            for (int b = a+1; b < num_Node; b++) {
                MyEdge e = new MyEdge();
                int edge_exist = rnd.nextInt(100);
                if (edge_exist < 80) {
                    /**リンク挿入*/
                    /**node a → node bに接続*/
                    Randomgraph.addEdge(e, (MyNode) Node_List.get(a), (MyNode) Node_List.get(b));
                }
            }
        }
        return Randomgraph;
    }

    /**
     * エッジ作成関数
     */
    public static Graph<MyNode,MyEdge> Graph_NodeMaker(List<MyNode> Node_List,int num_Node,int VNF_type,int max_sumCap,int num_DC,int num_Terminal) {
        Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<MyNode, MyEdge>();

        /**辺を張る確率*/
        double p = 0.05;
        int a = 0;
        int sum_cap = 0;
        int num_DC2 = 0;
        int num_Terminal2 = 0;
        Random rnd = new Random();                                  /**乱数発生*/
        ArrayList<Integer> Nodetype_list = new ArrayList<Integer>();      /**ノードタイプ判定リスト*/
        int Node_type;                                              /**DC or Terminal 判定用*/
        int VNF_Cap;
        while(num_DC2!=num_DC||num_Terminal2!=num_Terminal) {
            Node_type = rnd.nextInt(100);
            ArrayList<Integer> Connection_List = new ArrayList<Integer>();/**ノードの接続状況把握リスト*/
            if (Node_type >= 70 && num_Terminal2 < num_Terminal) {
                /**ターミナルノード挿入*/
                ArrayList<Integer> List1 = new ArrayList<Integer>();
                MyNode t = new MyNode("Terminal", List1, 0, a, Connection_List);
                graph.addVertex(t);
                Node_List.add(t);
                Nodetype_list.add(0);
                a++;
                num_Terminal2++;
            } else if (Node_type <= 80 && num_DC2 < num_DC) {
                /**DCノード挿入*/
                ArrayList<Integer> List2 = new ArrayList<Integer>();
                for (int b = 0; b < VNF_type; b++) {
                    /**VNF 提供可能リストの作成*/
                    int type = rnd.nextInt(100);
                    if (type > 90) List2.add(0);
                    else List2.add(1);
                }
                if (num_DC2 < num_DC / 2) {
                    double pos = (4 + rnd.nextInt(8)) / 4;
                    int each_cap = (int) (pos * (max_sumCap / (7 + rnd.nextInt(3))));
                    sum_cap += each_cap;
                    MyNode dc = new MyNode("Data Center", List2, each_cap, a, Connection_List);
                    graph.addVertex(dc);
                    Node_List.add(dc);
                    Nodetype_list.add(1);
                    a++;
                    num_DC2++;
                } else if (num_DC / 2 <= num_DC2 && num_DC2 < num_DC) {
                    double pos = (4 + rnd.nextInt(12)) / 4;
                    int each_cap = (int) (pos * (max_sumCap / (7 + rnd.nextInt(3))));
                    sum_cap += each_cap;
                    MyNode dc =
                            new MyNode("Data Center", List2, each_cap, a, Connection_List);
                    graph.addVertex(dc);
                    Node_List.add(dc);
                    Nodetype_list.add(1);
                    a++;
                    num_DC2++;
                } else {
                    int each_cap = max_sumCap - sum_cap;
                    MyNode dc = new MyNode("Data Center", List2, each_cap, a, Connection_List);
                    graph.addVertex(dc);
                    Node_List.add(dc);
                    Nodetype_list.add(1);
                    a++;
                    num_DC2++;
                }
            }
        }
        return graph;
    }
    public static Graph<MyNode,MyEdge> NWS_EdgeMaker(List<MyNode> Node_List,int num_Node,double p){
        Graph<MyNode,MyEdge> graph =  new UndirectedSparseGraph<>();
        int k=1;
        /**円の作成(0～N-1)*/
        for(int a=0;a<num_Node-1;a++){
            MyEdge e = new MyEdge();
            /**リンク挿入*/
            /**node a → node bに接続*/
            graph.addEdge(e, (MyNode) Node_List.get(a), (MyNode) Node_List.get(a+1));
        }

        /**円の作成（0-N-1)*/
        MyEdge e= new MyEdge();
        graph.addEdge(e,(MyNode)Node_List.get(0),(MyNode)Node_List.get(num_Node-1));
        for(int a=0;a<num_Node-2;a++){
            graph.addEdge(new MyEdge(),Node_List.get(a),Node_List.get(a+2));
        }
        graph.addEdge(new MyEdge(),Node_List.get(1),Node_List.get(num_Node-1));
        graph.addEdge(new MyEdge(),Node_List.get(0),Node_List.get(num_Node-2));
        /**その他の辺の作成*/
        for(int a=0;a<num_Node-1;a++){
            for(int b=a+k+1;b<num_Node;b++){
                if(Math.random() <= p){
                    if(graph.findEdge(Node_List.get(a),Node_List.get(b))==null){
                        graph.addEdge(new MyEdge(),Node_List.get(a),Node_List.get(b));
                    }
                }
            }
        }
        return graph;

    }
    public static Graph<MyNode,MyEdge> ER_EdgeMaker(Graph<MyNode,MyEdge> graph,List<MyNode> Node_List,int num_Node,double p){
        int k=2;
        for(int a=0;a<num_Node-1;a++){
            for(int b=a+1;b<num_Node;b++){
                if(Math.random() <=p){
                    graph.addEdge(new MyEdge(),Node_List.get(a),Node_List.get(b));
                }
            }
        }
        /** guarantee connectivity */
        Random rnd = new Random();
        Deque<MyNode> queue = new ArrayDeque<MyNode>();
        /** ある頂点が探索済みかどうかを判別するために探索済みの頂点を保存するリスト */
        List<MyNode> visitedVertexes = new ArrayList<MyNode>();
        /** check if graph is connected based on BFS & connect if not*/
        while (true) {
            ArrayList<MyNode> buf = new ArrayList<MyNode>(Node_List);
            visitedVertexes.add(buf.get(0));
            queue.offer(buf.get(0));
            while (!queue.isEmpty()) {
                MyNode r = queue.poll();
                /** vの隣接点のうち、未探索のノードを木に追加し探索済みにする */
                for (MyNode v : graph.getNeighbors(r)) {
                    if (!visitedVertexes.contains(v)) {
                        visitedVertexes.add(v);
                        queue.offer(v);
                    }
                }
            }
            /** check if all vertices are visited */
            /** if so, break while */
            if(visitedVertexes.size() == graph.getVertexCount()) {
                break;
            }
            /** if not, connect an unvisited vertex to a randomly selected visited vertex */
            else {
                MyNode unvisitedV = new MyNode(buf.get(0).VertexID,buf.get(0).VNFList,buf.get(0).capacity,buf.get(0).Vertex_num,buf.get(0).Connected_Node);
                for (MyNode v: graph.getVertices()){
                    if(!visitedVertexes.contains(v)){
                        unvisitedV = v;
                        break;
                    }
                }
                graph.addEdge(new MyEdge(), visitedVertexes.get(rnd.nextInt(visitedVertexes.size())), unvisitedV);
            }
            /** clean */
            queue.clear();
            visitedVertexes.clear();
        }

        return graph;

    }
    public Graph<MyNode,MyEdge> LatticeGraph_Maker(Graph<MyNode,MyEdge> graph,int vertexCount){
        Collection<MyNode> node_list = graph.getVertices();
        List<MyNode> vertexes = new ArrayList<MyNode>(node_list);
        int latticeSize = (int) Math.sqrt(vertexCount);

        // 最初に一行目を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        // 1   2   3
        // + - + - +
        for(int i = 0; i < latticeSize-1; i++) {
            graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(i+1));
        }

        // 2行目以降を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        //      0   1   2
        // row0 +   +   +
        //      |   |   |
        // row1 + - + - +
        //      3   4   5
        //
        // ※一番左（baseVertex）は latticeSize * rowの値
        for(int row = 1; row < latticeSize; row++) {
            int baseVertex = latticeSize * row;
            for(int i = 0; i < latticeSize-1; i++) {
                graph.addEdge(new MyEdge(), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i-latticeSize)); // 上の頂点と接続
                graph.addEdge(new MyEdge(), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i+1)); // 右の頂点と接続
            }
            // 上の頂点と接続. 一番右の頂点は右に頂点は無いので上だけ.
            graph.addEdge(new MyEdge(), vertexes.get(baseVertex+(latticeSize-1)), vertexes.get(baseVertex+-1));
        }
        return graph;
    }
    public List<MyNode> Node_ListMaker(Graph<MyNode,MyEdge>graph){
        Collection<MyNode> Node_List = new ArrayList<MyNode>(graph.getVertices());
        List<MyNode> node_list= new ArrayList<>(Node_List);
        for(int a=0;a<Node_List.size();a++){
            for(int b=a+1;b<Node_List.size();b++){
                if(graph.findEdge(node_list.get(a),node_list.get(b))!=null){
                    node_list.get(a).Connected_Node.add(node_list.get(b).Vertex_num);
                    node_list.get(b).Connected_Node.add(node_list.get(a).Vertex_num);
                }
            }
        }
        return node_list;
    }
    public void Layout_Grpah(Graph<MyNode, MyEdge> graph) {
        Layout<MyNode, MyEdge> layout = new CircleLayout<MyNode, MyEdge>(graph);
        layout.setSize(new Dimension(800, 800));
        BufferedImage image = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        BasicVisualizationServer<MyNode, MyEdge> panel = new BasicVisualizationServer<MyNode, MyEdge>(layout, new Dimension(800, 800));
        panel.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyNode>());//vertex label
        Date now = new Date();
        DateFormat dfYMD = new SimpleDateFormat("YYYYMMDD");
        DateFormat dfHMS = new SimpleDateFormat("hhmmss");
        JFrame frame = new JFrame("Graph View: Manual Layout"+dfYMD.format(now)+"_"+dfHMS.format(now));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        /*panel.paint(g2);
        String path = "C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\Simulation\\";

        path +="Simulation"+dfYMD.format(now)+dfHMS.format(now)+".png";
        File file = new File(path);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter((new FileWriter(file,false))));
            ImageIO.write(image,"png",file);
            //pw.close();
        }catch(Exception e){
            System.out.println("Error");
            return;
        }*/
    }

    public static void Random_Layout(Graph<MyNode,MyEdge> graph){
        Dimension viewArea = new Dimension(1200,1200);
        Layout<MyNode,MyEdge> layout = new RandomLayout<MyNode,MyEdge>(graph,viewArea);
        BasicVisualizationServer<MyNode,MyEdge> panel = new BasicVisualizationServer<MyNode, MyEdge>(layout,viewArea);
        JFrame frame = new JFrame("Graph View: Random Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }



}
