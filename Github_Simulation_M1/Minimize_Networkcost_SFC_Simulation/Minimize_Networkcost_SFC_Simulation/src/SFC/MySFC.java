package SFC;

import VNF.MyVNF;
import java.util.*;
/**
 * Created by 大樹 on 2018/06/25.
 */
public class MySFC {
    public ArrayList<MyVNF> subset_VNF = new ArrayList<MyVNF>(); /**SFCに含まれるVNF*/
    public int Node_Cost; /**配置コスト*/
    public int Link_Cost; /**Link resource*/
    public Map<MyVNF,Integer> Allocation_List;

    public MySFC(ArrayList<MyVNF>subset_VNF,int Node_Cost,int Link_Cost,Map<MyVNF,Integer> Allocation_List){
        this.subset_VNF=subset_VNF;
        this.Node_Cost = Node_Cost;
        this.Link_Cost = Link_Cost;
        this.Allocation_List = Allocation_List;
    }
}
