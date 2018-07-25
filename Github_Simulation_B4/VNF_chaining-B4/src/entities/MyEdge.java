package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyEdge
 *
 * edge class
 */
public class MyEdge {

    /**
     * class method to return a factory
     * used in GraphGenerator
     * @return MyEdgeFactory
     */
    public static Factory<MyEdge> getFactory() {
        return new Factory<MyEdge>() {
            @Override
            public MyEdge create() {
                return new MyEdge();
            }
        };
    }
}
