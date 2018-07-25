package GraphMaking;
/**
 * Created by Daiki Yamada on 2017/09/13.
 */
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Factory;
import java.util.*;

public class MyNode implements Cloneable{
    /**
     * VertexID
     **/
     public int Vertex_num;
    /**
     * DCNode
     **/
    public List<Integer> VNFList;
    public int capacity;
    public String VertexID;
    public List<Integer> Connected_Node;
    public int Visited =1;
    public List<Integer> Allocated_VNFList;
    private static MyNode node;
    private MyNode s;
    public MyNode(String VertexID,List<Integer> VNFList,int capacity,int Vertex_num,List<Integer> Connected_Node){
        this.VertexID = VertexID;
        this.VNFList = VNFList;
        this.capacity = capacity;
        this.Vertex_num = Vertex_num;
        this.Connected_Node = Connected_Node;
    }

    @Override
    public MyNode clone(){
        MyNode node2 = new MyNode(this.VertexID,this.VNFList,this.capacity,this.Vertex_num,this.Connected_Node);
        try{
            node2 = (MyNode)super.clone();
            //node2.s = this.s.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        return node2;
    }



    public String toString(){
        return VertexID+Vertex_num;
    }
}
