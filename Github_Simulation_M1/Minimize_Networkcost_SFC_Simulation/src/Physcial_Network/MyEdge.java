package Physcial_Network;

/**
 * Created by 大樹 on 2018/06/25.
 */
public class MyEdge {
    public String Edge_ID; /**EdgeID*/
    public int Capacity; /**帯域キャパシティ*/
    public int Cost; /**辺配置コスト*/
    public int Residual_Capacity; /**残余帯域*/

    public MyEdge(String Edge_ID,int Capacity,int Cost,int Residual_Capacity){
        this.Edge_ID = Edge_ID;
        this.Capacity = Capacity;
        this.Cost = Cost;
        this.Residual_Capacity = Residual_Capacity;
    }
}
