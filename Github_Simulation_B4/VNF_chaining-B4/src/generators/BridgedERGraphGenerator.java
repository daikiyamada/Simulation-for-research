package generators;

import edu.uci.ics.jung.graph.util.Pair;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class for generating a bridged (connected) ER graph
 */
public class BridgedERGraphGenerator implements GraphGenerator{
    /** 頂点数 */
    private int vertexCount1;
    private int vertexCount2;
    /** 辺を張る確率p */
    private double p1 = 0.1;
    private double p2 = 0.1;

    /** length of bridge */
    private int length = 4;

    /**
     * constructor
     * @param vertexCount1 頂点数
     * @param possibility1 辺を張る確率
     * @param vertexCount2 頂点数
     * @param possibility2 辺を張る確率
     */
    public BridgedERGraphGenerator(int vertexCount1, double possibility1, int vertexCount2, double possibility2) {
        this.vertexCount1 = vertexCount1;
        this.p1 = possibility1;
        this.vertexCount2 = vertexCount2;
        this.p2 = possibility2;
    }
    public BridgedERGraphGenerator(int vertexCount){
        vertexCount1 = vertexCount2 = vertexCount;
    }

    @Override
    public MyGraph<MyVertex, MyEdge> create() throws ParserConfigurationException, SAXException, IOException {
        // create 2 graph and merge
        GraphGenerator gg1 = new ConnectedERGraphGenerator(vertexCount1, p1);
        GraphGenerator gg2 = new ConnectedERGraphGenerator(vertexCount2, p2);
        MyGraph<MyVertex, MyEdge> g1 = gg1.create();
        MyGraph<MyVertex, MyEdge> g2 = gg2.create();

        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();
        for (MyEdge e : g1.getEdges()) {
            Pair<MyVertex> endpoints = g1.getEndpoints(e);
            graph.addEdge( e, endpoints.getFirst(), endpoints.getSecond());
        }
        for (MyEdge e : g2.getEdges()) {
            Pair<MyVertex> endpoints = g2.getEndpoints(e);
            graph.addEdge( e, endpoints.getFirst(), endpoints.getSecond());
        }

        // make bridges
        Random rnd = new Random();
        List<MyVertex> vertices1 = new ArrayList<MyVertex>(g1.getVertices());
        List<MyVertex> vertices2 = new ArrayList<MyVertex>(g2.getVertices());

        for (int i = 0; i < 2; i++) {
            MyVertex v1 = vertices1.get(rnd.nextInt(vertices1.size()));
            vertices1.remove(v1);
            MyVertex v2 = vertices2.get(rnd.nextInt(vertices2.size()));
            vertices2.remove(v2);

            MyVertex relayNode = new MyVertex();
            graph.addEdge( new MyEdge(), v1, relayNode);
//            System.err.println(v1 + " - " + relayNode);
            for (int l = 0; l < length-2; l++) {
                MyVertex prevRelayNode = relayNode;
                relayNode = new MyVertex();
//                System.err.println(prevRelayNode + " - " + relayNode);
                graph.addEdge( new MyEdge(), prevRelayNode, relayNode);

            }
//            System.err.println(relayNode + " - " + v2);
            graph.addEdge( new MyEdge(), relayNode, v2);
        }

        return graph;
    }
}

