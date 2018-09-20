package Physcial_Network;

import SFC.MySFC;
import java.util.*;
/**
 * Created by 大樹 on 2018/06/25.
 */
public class MyEdge {
    public int Edge_ID; /**EdgeID*/
    public int Capacity; /**帯域キャパシティ*/
    public int Cost; /**辺配置コスト*/
    public ArrayList<MySFC> Allocation_List;
    public int Residual_Capacity; /**残余帯域*/
    public MyEdge(int Edge_ID,int Capacity,int Cost,int Residual_Capacity,ArrayList<MySFC> Allocation_List){
        this.Edge_ID = Edge_ID;
        this.Capacity = Capacity;
        this.Cost = Cost;
        this.Residual_Capacity = Residual_Capacity;
        this.Allocation_List = Allocation_List;
    }
}
