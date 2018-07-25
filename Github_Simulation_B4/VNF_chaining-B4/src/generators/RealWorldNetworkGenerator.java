package generators;

import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.io.GraphMLReader;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 *
 */
public class RealWorldNetworkGenerator implements GraphGenerator {

    private String filename = "graphxml/Intellifiber.xml";

    @Override
    public MyGraph<MyVertex, MyEdge> create() throws IOException, ParserConfigurationException, SAXException {
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();
        GraphMLReader<Hypergraph<MyVertex,MyEdge> ,MyVertex,MyEdge> graphMLReader = new GraphMLReader<Hypergraph<MyVertex,MyEdge> ,MyVertex,MyEdge>(MyVertex.getFactory(), MyEdge.getFactory());
        graphMLReader.load(filename, graph);
        return graph;
    }
}