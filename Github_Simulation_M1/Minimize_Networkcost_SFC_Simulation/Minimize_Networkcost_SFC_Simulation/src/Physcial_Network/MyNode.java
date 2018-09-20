package Physcial_Network;

import SFC.MySFC;

import javax.xml.soap.Node;

/**
 * Created by 大樹 on 2018/06/25.
 */
import java.util.*;

public class MyNode {
    public String Node_ID; /**NodeId*/
    public int Node_num;
    public int Node_Capacity; /**計算キャパシティ*/
    public int Node_Cost; /**配置コスト*/
    public int Node_Residual_Capacity; /**残余キャパシティ*/
    public ArrayList<MySFC> Allocation_List;

    public MyNode(String Node_ID,int Node_Capacity,int Node_Cost,int Node_Residual_Capacity,int Node_num, ArrayList<MySFC> Allocation_List){
        this.Node_ID = Node_ID;
        this.Node_Capacity = Node_Capacity;
        this.Node_Cost = Node_Cost;
        this.Node_Residual_Capacity = Node_Residual_Capacity;
        this.Node_num = Node_num;
        this.Allocation_List = Allocation_List;
    }
    @Override
    public String toString(){
        return Node_ID+Node_num;
    }
}
