package Physcial_Network;

import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by 大樹 on 2018/06/26.
 */
public class Lattice_GraphMaker {
    public Graph<MyNode,MyEdge> LatticeGraph_Maker(Graph<MyNode,MyEdge> graph){
        int vertexCount = graph.getVertexCount();
        Collection<MyNode> node_list = graph.getVertices();
        List<MyNode> vertexes = new ArrayList<MyNode>(node_list);
        int latticeSize = (int) Math.sqrt(vertexCount);
        int num_edge=0;
        Random rnd = new Random();
        // 最初に一行目を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        // 1   2   3
        // + - + - +
        for(int i = 0; i < latticeSize-1; i++) {
            int capacity = rnd.nextInt(70)+30;
            graph.addEdge(new MyEdge(num_edge,capacity,rnd.nextInt(15)+2,capacity), vertexes.get(i), vertexes.get(i+1));
            num_edge++;
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
                int capacity1 = rnd.nextInt(70)+30;
                int capacity2 = rnd.nextInt(70)+30;
                graph.addEdge(new MyEdge(num_edge,capacity1,rnd.nextInt(15)+2,capacity1), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i-latticeSize)); // 上の頂点と接続
                num_edge++;
                graph.addEdge(new MyEdge(num_edge,capacity2,rnd.nextInt(15)+2,capacity2), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i+1)); // 右の頂点と接続
                num_edge++;
            }
            // 上の頂点と接続. 一番右の頂点は右に頂点は無いので上だけ.
            int capacity = rnd.nextInt(70)+30;
            graph.addEdge(new MyEdge(num_edge,capacity,rnd.nextInt(15)+2,capacity), vertexes.get(baseVertex+(latticeSize-1)), vertexes.get(baseVertex+-1));
        }
        return graph;
    }
}
