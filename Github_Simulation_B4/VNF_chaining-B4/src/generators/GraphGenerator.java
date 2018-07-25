package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * GraphGenerator
 *
 * interface for graph generator classes
 */
public interface GraphGenerator {
    /**
     * method to return a graph
     * @return  created graph
     */
    MyGraph<MyVertex, MyEdge> create() throws IOException, ParserConfigurationException, SAXException;
}
