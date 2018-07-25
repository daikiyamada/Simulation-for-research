package entities;

import org.apache.commons.collections15.Factory;
import java.util.*;

/**
 * Created by Hideo/**
 * MyVertex
 *
 * vertex class
 */

public class MyVertex {

    /** vertex id */
    private String vertexID;
    /** attributes for dc vertexes*/
    private List<Integer> VNFList;
    private int capacity;

    /**
     * class method to return a factory
     * used in GraphGenerator
     * @return MyVertexFactory
     */
    public static Factory<MyVertex> getFactory() {
        return new Factory<MyVertex>() {
            @Override
            public MyVertex create() {
                return new MyVertex();
            }
        };
    }

    public void setVertexID(String vertexID) {
        this.vertexID = vertexID;
    }

    public String getVertexID() {
        return vertexID;
    }

    public void setVNFList(List<Integer> VNFList) {
        this.VNFList = VNFList;
    }

    public List<Integer> getVNFList() {
        return VNFList;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
