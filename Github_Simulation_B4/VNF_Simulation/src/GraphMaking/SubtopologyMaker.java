package GraphMaking;

/**
 * Created by Daiki Yamada on 2017/09/14.
 */
import java.util.*;

import Demand.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import javax.xml.soap.Node;

public class SubtopologyMaker {
    class TN extends Terminal_Node {
    }

    class Maker extends GraphMaker {
    }

    public List<Node_List> STMaker(List<MyNode> Node_List, List<Integer> SourceNode, int num_VNFChain, int num_Node, int num_VNFC) {
        /**サブトポロジー用リスト*/
        List<Node_List> Subgraph_List = new ArrayList<Node_List>();
        /**BFSによる全域木作成*/
        for (int a = 0; a < num_VNFChain; a++) {
            /**リンク用配列*/
            int[][] Connection = new int[num_Node][num_Node];
            /**ノードリスト*/
            List<MyNode> Node_List2 = new ArrayList<MyNode>(Node_List);
            /**レイアウト用サブグラフ*/
            Graph<MyNode, MyEdge> Subgraph = new UndirectedSparseGraph<MyNode, MyEdge>();
            Queue<MyNode> queue = new ArrayDeque<MyNode>(); //Queue for BFS
            /**ソースノードの挿入*/
            MyNode node = Node_List.get(SourceNode.get(a));
            queue.add(node);
            while (queue.size() != 0) {
                /**サブトポロジーの作成*/
                MyNode n = queue.poll();
                Subgraph.addVertex(n);
                n.Visited = 1;
                Node_List.set(n.Vertex_num, n);
                List<Integer> Connection_Node = new ArrayList<>();
                /**BFSによる全域木の作成*/
                for (int b = 0; b < n.Connected_Node.size(); b++) {
                    /**ノードnにつながっているノードの参照*/
                    MyNode visit = Node_List.get(n.Connected_Node.get(b));
                    /**visitノードの訪問有無の確認*/
                    if (visit.Visited == 0) {
                        /**visitノードの訪問済みチェック*/
                        visit.Visited = 1;
                        Node_List.set(n.Connected_Node.get(b), visit);
                        /**キュー代入*/
                        queue.add(Node_List.get(n.Connected_Node.get(b)));
                        /**リンク情報の入力*/
                        Connection[n.Vertex_num][n.Connected_Node.get(b)] = 1;
                        Connection[n.Connected_Node.get(b)][n.Vertex_num] = 1;
                        Connection_Node.add(n.Connected_Node.get(b));
                        /**10/13*/
                        Subgraph.addVertex(Node_List.get(n.Connected_Node.get(b)));
                        MyEdge e = new MyEdge();
                        Subgraph.addEdge(e, n, Node_List.get(n.Connected_Node.get(b)));
                    }
                }
                MyNode node2 = new MyNode(n.VertexID, n.VNFList, n.capacity, n.Vertex_num, Connection_Node);
                Node_List2.set(n.Vertex_num, node2);
            }
            Node_List subgraph = new Node_List(Connection,Node_List2, Subgraph);
            subgraph.Node_list2 = Node_List2;
            Subgraph_List.add(subgraph);

            for (int c = 0; c < Node_List.size(); c++) {
                MyNode e = Node_List.get(c);
                e.Visited = 0;
                Node_List.set(c, e);
            }
        }
        return Subgraph_List;
    }
    public Graph<MyNode, MyEdge> ER_PathMaker(List<MyNode> Node_List, List<Integer> SourceNode, int num_VNFChain, int num_Node, int num_VNFC, int Max_hop) {
        /**サブトポロジー用リスト*/
        //Random rnd = new Random();
        int bfs1=0, bfs2, num = 0;
        /**BFS,DFSによるパス作成*/
        //int bd = rnd.nextInt(100);
        /**レイアウト用サブグラフ*/
        Graph<MyNode, MyEdge> Subgraph = new UndirectedSparseGraph<MyNode, MyEdge>();
        Queue<MyNode> queue = new ArrayDeque<MyNode>(); //Queue for BFS
        Deque<MyNode> Stack = new ArrayDeque<>();
        for(int a=0;a<num_Node;a++){
            MyNode node = Node_List.get(a);
            node.Visited=1;
            Node_List.set(a,node);
        }
        //System.out.println("bd="+bd);
        /**ソースノードの挿入*/
        for (int a = 0; a < num_Node; a++) {
            if (SourceNode.get(num_VNFChain) == Node_List.get(a).Vertex_num) {
                MyNode node = Node_List.get(a);
                queue.add(node);
                num = a;
            }
        }
        /**ソースノードの挿入*/
        for (int a = 0; a < num_Node; a++) {
            if (SourceNode.get(num_VNFChain) == Node_List.get(a).Vertex_num) {
                MyNode node = Node_List.get(a);
                Stack.add(node);
                node.Visited = 0;
                Node_List.set(a, node);
                Subgraph.addVertex(node);
            }
        }
        /**サブトポロジーの作成*/
        /**パスの生成*/
        for (int c = 0; c < Max_hop; c++) {
            MyNode n = Stack.poll();
            List<Integer> bfs11 = new ArrayList<Integer>();
            /**DFSによるパスの作成*/
            /**片方のDFS*/
            if(n !=null){
                /**重複なしランダム配列の作成*/
                for (int b = 0; b < n.Connected_Node.size(); b++) {
                    bfs11.add(b);
                }
            }
            else break;


            /**隣接ノード決定*/
            do{
                bfs1 = bfs11.remove(0);
                Collections.shuffle(bfs11);
                for (int a = 0; a < num_Node; a++) {
                    if (n.Connected_Node.get(bfs1) == Node_List.get(a).Vertex_num) {
                        bfs1 = a;
                        break;
                    }
                }
                MyNode visit2 = Node_List.get(bfs1);
                if(visit2.Connected_Node.size()!=0) {
                    /**未訪問の場合*/
                    if (Node_List.get(bfs1).Visited == 1) {
                        visit2.Visited = 0;
                        Node_List.set(bfs1, visit2);
                        Stack.add(visit2);
                        Subgraph.addVertex(visit2);
                        Subgraph.addEdge(new MyEdge(), n, visit2);
                        bfs11.clear();
                        break;
                    }
                }
            }while(bfs11.size()!=0);
            //  }
        }        return Subgraph;
    }

    public Graph<MyNode, MyEdge> NWS_PathMaker(List<MyNode> Node_List, List<Integer> SourceNode, int num_VNFChain, int num_Node, int num_VNFC, int Max_hop) {
        /**サブトポロジー用リスト*/
        //Random rnd = new Random();
        int bfs1=0, bfs2, num = 0;
        /**BFS,DFSによるパス作成*/
        //int bd = rnd.nextInt(100);
        /**レイアウト用サブグラフ*/
        Graph<MyNode, MyEdge> Subgraph = new UndirectedSparseGraph<MyNode, MyEdge>();
        Queue<MyNode> queue = new ArrayDeque<MyNode>(); //Queue for BFS
        Deque<MyNode> Stack = new ArrayDeque<>();
        for(int a=0;a<num_Node;a++){
            MyNode node = Node_List.get(a);
            node.Visited=1;
            Node_List.set(a,node);
        }
        //System.out.println("bd="+bd);
        /**ソースノードの挿入*/
        for (int a = 0; a < num_Node; a++) {
            if (SourceNode.get(num_VNFChain) == Node_List.get(a).Vertex_num) {
                MyNode node = Node_List.get(a);
                queue.add(node);
                num = a;
            }
        }
        /**BFSによるパスの作成*/
       /* if (bd <=15) {
            /**サブトポロジーの作成*/
            /**始点をはさむように作成*/
         /*   MyNode n = queue.poll();
            n.Visited = 0;
            Subgraph.addVertex(n);
            Node_List.set(num, n);
            /**ノードnにつながっているノードの参照*/
           /* int k = 0, rd;
            List<Integer> rdlist = new ArrayList<Integer>();
            for (int a = 0; a < n.Connected_Node.size(); a++) {
                rdlist.add(a);
            }
            Collections.shuffle(rdlist);
            /**キュー一括管理*/
            /*for (int a = 0; a < 2; a++) {
                rd = rdlist.get(a);

                for (int b = 0; b < num_Node; b++) {
                    if (n.Connected_Node.get(rd) == Node_List.get(b).Vertex_num) {
                        MyNode visit1 = Node_List.get(b);
                        visit1.Visited = 0;
                        Node_List.set(b, visit1);
                        queue.add(Node_List.get(b));
                        Subgraph.addVertex(Node_List.get(b));
                        Subgraph.addEdge(new MyEdge(), n, Node_List.get(b));
                        break;
                    }
                }
            }
            /**2ホップ以降のパス作成*/
            /*for (int c = 1; c < Max_hop; c++) {
                List<Integer> bfs11 = new ArrayList<Integer>();
                /**片方のDFS*/
              /*  MyNode n1 = queue.poll();
                MyNode n2 = queue.poll();
                MyNode n3, n4;

                if (n1.Connected_Node.size() >= n2.Connected_Node.size()) {
                    n3 = n2;
                    n4 = n1;
                } else {
                    n3 = n1;
                    n4 = n2;
                }

                /**n3のノード探索*/
            /**重複なしランダム配列の作成*/
                /*for (int a = 0; a < n3.Connected_Node.size(); a++) {
                    bfs11.add(a);
                }

                /**隣接ノード決定*/
               /* do{
                    bfs1 = bfs11.get(0);
                    bfs11.remove(0);
                    Collections.shuffle(bfs11);
                    for (int a = 0; a < num_Node; a++) {
                        if (n3.Connected_Node.get(bfs1) == Node_List.get(a).Vertex_num) {
                            bfs1 = a;
                            break;
                        }
                    }

                    MyNode visit1 = Node_List.get(bfs1);
                /**未訪問の場合*/
                /*if (Node_List.get(bfs1).Visited == 1) {
                    visit1.Visited = 0;
                    Node_List.set(bfs1, visit1);
                    queue.add(visit1);
                    Subgraph.addVertex(visit1);
                    Subgraph.addEdge(new MyEdge(), n3, visit1);
                    bfs11.clear();
                    break;
                }
                }while(bfs11.size()!=0);

                /**n4のノード探索*/
            /**重複なしランダム配列の作成*/

                /*for (int b = 0; b < n4.Connected_Node.size(); b++) {
                    bfs11.add(b);
                }

                /**隣接ノード決定*/
                /*do{
                    bfs1 = bfs11.get(0);
                    bfs11.remove(0);
                    Collections.shuffle(bfs11);
                    for (int a = 0; a < num_Node; a++) {
                        if (n4.Connected_Node.get(bfs1) == Node_List.get(a).Vertex_num) {
                            bfs1 = a;
                            break;
                        }
                    }
                    MyNode visit2 = Node_List.get(bfs1);
                    /**未訪問の場合*/
                  /*  if (Node_List.get(bfs1).Visited == 1) {
                        visit2.Visited = 0;
                        Node_List.set(bfs1, visit2);
                        queue.add(visit2);
                        Subgraph.addVertex(visit2);
                        Subgraph.addEdge(new MyEdge(), n4, visit2);
                        bfs11.clear();
                        break;
                    }
                }while(bfs11.size()!=0);
            }
        }
    /**始点を端点にパスの生成*/


    //else if (bd >15) {
        /**ソースノードの挿入*/
        for (int a = 0; a < num_Node; a++) {
            if (SourceNode.get(num_VNFChain) == Node_List.get(a).Vertex_num) {
                MyNode node = Node_List.get(a);
                Stack.add(node);
                node.Visited = 0;
                Node_List.set(a, node);
                Subgraph.addVertex(node);
            }
        }
        /**サブトポロジーの作成*/
        /**パスの生成*/
        for (int c = 0; c < Max_hop; c++) {
            MyNode n = Stack.poll();
            List<Integer> bfs11 = new ArrayList<Integer>();
            /**DFSによるパスの作成*/
            /**片方のDFS*/

            /**重複なしランダム配列の作成*/
            for (int b = 0; b < n.Connected_Node.size(); b++) {
                bfs11.add(b);
            }

            /**隣接ノード決定*/
            do{
                bfs1 = bfs11.remove(0);
                Collections.shuffle(bfs11);
                for (int a = 0; a < num_Node; a++) {
                    if (n.Connected_Node.get(bfs1) == Node_List.get(a).Vertex_num) {
                        bfs1 = a;
                        break;
                    }
                }
                MyNode visit2 = Node_List.get(bfs1);
                /**未訪問の場合*/
                if (Node_List.get(bfs1).Visited == 1) {
                    visit2.Visited = 0;
                    Node_List.set(bfs1, visit2);
                    Stack.add(visit2);
                    Subgraph.addVertex(visit2);
                    Subgraph.addEdge(new MyEdge(), n, visit2);
                    bfs11.clear();
                    break;
                }
            }while(bfs11.size()!=0);
      //  }
    }        return Subgraph;
        }

        public Node_List SubNodeList(Graph<MyNode,MyEdge> graph,int num_Node){
        int [][] Connection_List = new int[num_Node][num_Node];
        Collection<MyNode> Node_list = new ArrayList<MyNode>(graph.getVertices());
        List<MyNode> nodelist2 = new ArrayList<MyNode>(Node_list);
        //List<MyNode> node_list = new ArrayList<MyNode>(nodelist2.size());
        for (int a=0;a<graph.getVertices().size();a++){
            for(int b=a+1;b<graph.getVertices().size();b++){
                if(graph.findEdge(nodelist2.get(a),nodelist2.get(b))!=null){
                    Connection_List[nodelist2.get(a).Vertex_num][nodelist2.get(b).Vertex_num]=1;
                    Connection_List[nodelist2.get(b).Vertex_num][nodelist2.get(a).Vertex_num]=1;
                }
            }
        }
        List<MyNode> node_list = new ArrayList<MyNode>();
        for(int a=0;a<nodelist2.size();a++){
            List<Integer> Connected_Node = new ArrayList<>();
            MyNode node = new MyNode(nodelist2.get(a).VertexID,nodelist2.get(a).VNFList,nodelist2.get(a).capacity,nodelist2.get(a).Vertex_num,Connected_Node);
            node_list.add(node);
        }
        int e=0,f=0;
        for(int a=0;a<num_Node;a++){
            for(int b=a+1;b<num_Node;b++){
                if(Connection_List[a][b]==1){
                    for(int c=0;c<node_list.size();c++){
                        if(node_list.get(c).Vertex_num==a) e=c;
                        if(node_list.get(c).Vertex_num==b) f=c;
                    }
                    node_list.get(e).Connected_Node.add(b);
                    node_list.get(f).Connected_Node.add(a);
                    node_list.get(e).Visited=0;
                    node_list.get(f).Visited=0;
                }
            }
        }

        Node_List subnodelist = new Node_List(Connection_List,node_list,graph);
            return subnodelist;
        }

}