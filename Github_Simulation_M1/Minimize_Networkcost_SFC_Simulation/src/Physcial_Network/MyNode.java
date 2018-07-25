package Physcial_Network;

import javax.xml.soap.Node;

/**
 * Created by 大樹 on 2018/06/25.
 */
public class MyNode {
    public String Node_ID; /**NodeId*/
    public int Node_Capacity; /**計算キャパシティ*/
    public int Node_Cost; /**配置コスト*/
    public int Node_Residual_Capacity; /**残余キャパシティ*/

    public MyNode(String Node_ID,int Node_Capacity,int Node_Cost,int Node_Residual_Capacity){
        this.Node_ID = Node_ID;
        this.Node_Capacity = Node_Capacity;
        this.Node_Cost = Node_Cost;
        this.Node_Residual_Capacity = Node_Residual_Capacity;
    }
}
