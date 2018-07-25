package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Hideo on 2017/08/02.
 */
public class ConnectedERGraphGenerator implements GraphGenerator {
    /** 頂点数 */
    private int vertexCount;
    /** 辺を張る確率p */
    private double p = 0.1;

    /**
     * constructor
     * @param vertexCount 頂点数
     */
    public ConnectedERGraphGenerator(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * constructor
     * @param vertexCount 頂点数
     * @param possibility 辺を張る確率
     */
    public ConnectedERGraphGenerator(int vertexCount, double possibility) {
        this.vertexCount = vertexCount;
        this.p = possibility;
    }

    @Override
    public MyGraph<MyVertex, MyEdge> create() throws IOException, ParserConfigurationException, SAXException {
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();

        /** 頂点の作成 */
        List<MyVertex> vertexes = new ArrayList<MyVertex>();
        for(int i = 0; i < this.vertexCount; i++) {
            MyVertex v = new MyVertex();
            graph.addVertex(v);
            vertexes.add(v);
        }

        /** ランダムに辺の作成 */
        for(int i = 0; i < this.vertexCount-1; i++) {
            for(int j = i+2; j < this.vertexCount; j++) {
                if(Math.random() <= this.p) {
                    graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(j));
                }
            }
        }

        /** guarantee connectivity */
        Random rnd = new Random();
        Deque<MyVertex> queue = new ArrayDeque<MyVertex>();
        /** ある頂点が探索済みかどうかを判別するために探索済みの頂点を保存するリスト */
        List<MyVertex> visitedVertexes = new ArrayList<MyVertex>();
        /** check if graph is connected based on BFS & connect if not*/
        while (true) {
            ArrayList<MyVertex> buf = new ArrayList<MyVertex>(graph.getVertices());
            visitedVertexes.add(buf.get(0));
            queue.offer(buf.get(0));
            while (!queue.isEmpty()) {
                MyVertex r = queue.poll();
                /** vの隣接点のうち、未探索のノードを木に追加し探索済みにする */
                for (MyVertex v : graph.getNeighbors(r)) {
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
                MyVertex unvisitedV = new MyVertex();
                for (MyVertex v: graph.getVertices()){
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
}
