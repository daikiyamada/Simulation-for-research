package SFC;

import VNF.MyVNF;
import java.util.*;
/**
 * Created by 大樹 on 2018/06/25.
 */
public class MySFC {
    public ArrayList<MyVNF> subset_VNF = new ArrayList<MyVNF>(); /**SFCに含まれるVNF*/
    public int Maximum_Cost; /**上限ネットワークコスト*/
    public int Node_Cost; /**配置コスト*/
    public int Link_Cost;
    public int Backup_Cost;
    public int [] allocated_location;

    public MySFC(ArrayList<MyVNF>subset_VNF,int Maximum_Cost,int Node_Cost,int Link_Cost,int Backup_Cost){
        this.subset_VNF=subset_VNF;
        this.Maximum_Cost =Maximum_Cost;
        this.Node_Cost = Node_Cost;
        this.Link_Cost = Link_Cost;
        this.Backup_Cost = Backup_Cost;
    }
}
