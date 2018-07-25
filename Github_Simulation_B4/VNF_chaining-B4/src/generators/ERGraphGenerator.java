package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hideo on 2017/08/02.
 */
public class ERGraphGenerator implements GraphGenerator {
    /** 頂点数 */
    private int vertexCount;
    /** 辺を張る確率p */
    private double p = 0.1;

    /**
     * constructor
     * @param vertexCount 頂点数
     */
    public ERGraphGenerator(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * constructor
     * @param vertexCount 頂点数
     * @param possibility 辺を張る確率
     */
    public ERGraphGenerator(int vertexCount, double possibility) {
        this.vertexCount = vertexCount;
        this.p = possibility;
    }

    @Override
    public MyGraph<MyVertex, MyEdge> create() throws IOException, ParserConfigurationException, SAXException {
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();

        // 頂点の作成
        List<MyVertex> vertexes = new ArrayList<MyVertex>();
        for(int i = 0; i < this.vertexCount; i++) {
            MyVertex v = new MyVertex();
            graph.addVertex(v);
            vertexes.add(v);
        }

        // ランダムに辺の作成
        for(int i = 0; i < this.vertexCount-1; i++) {
            for(int j = i+2; j < this.vertexCount; j++) {
                if(Math.random() <= this.p) {
                    graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(j));
                }
            }
        }

        return graph;
    }
}
