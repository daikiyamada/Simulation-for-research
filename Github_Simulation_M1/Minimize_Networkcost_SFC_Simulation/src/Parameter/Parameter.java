package Parameter;
import java.util.*;
public class Parameter {

    /**SFC関連*/
    private int num_VNFtype=10;  /**VNFタイプ数*/
    private int num_SFC = 10; /**SFC数*/
    private int num_VNF = 4; /**1つのSFCに含まれるVNF数*/
    /**SFC関連Latency*/
    private double low = 0.3;
    private double normal = 0.8;
    private int low_min_cap = 8;
    private int low_max_cap = 15;
    private int normal_min_cap =5;
    private int normal_max_cap = 12;
    private int large_min_cap =2;
    private int large_max_cap = 8;
    /**Graph関連*/
    /**Graph ノード数・種類別ノード数*/
    private int num_Node =9,num_ServiceNode=6,num_TerminalNode=num_Node-num_ServiceNode;
    /**ノードの規模決定確率*/
    private double min_p =0.3;
    private double normal_p =0.8;
    /**各ノード規模の利用可能なリソース値*/
    private int small_min =2;
    private int small_max = 3;
    private int normal_min = 3;
    private int normal_max = 5;
    private int large_min =5;
    private int large_max =8;
    /**各ノード規模のリソース単価値*/
    private int small_min_cost =2;
    private int small_max_cost = 4;
    private int normal_min_cost = 4;
    private int normal_max_cost = 6;
    private int large_min_cost =5;
    private int large_max_cost =8;
    /**リンクの規模決定確率*/
    private double msmall_p = 0.1;
    private double small_p =0.3;
    private double enormal_p = 0.7;
    private double large_p = 0.9;
    /**各リンク規模の利用可能なリソース値*/
    private int emsmall = 30;
    private int esmall=50;
    private int enormal = 70;
    private int elarge=100;
    private int emlarge=200;
    /**各リンク規模のリソース単価値*/
    private int msmall_min_ecost = 2;
    private int msmall_max_ecost = 4;
    private int small_min_ecost =3;
    private int small_max_ecost = 5;
    private int normal_min_ecost = 4;
    private int normal_max_ecost=7;
    private int large_min_ecost=6;
    private int large_max_ecost =8;
    private int mlarge_min_ecost=7;
    private int mlarge_max_ecost=10;

    public int Num_VNFtype(){
        this.num_VNFtype = num_VNFtype;
        return num_VNFtype;
    }

    public int Num_SFC(){
        this.num_SFC = num_SFC;
        return num_SFC;
    }

    public int Num_VNF(){
        this.num_VNF = num_VNF;
        return num_VNF;
    }

    public int Num_Node(){
        this.num_Node = num_Node;
        return num_Node;
    }
    public int Num_ServiceNode(){
        this.num_ServiceNode = num_ServiceNode;
        return num_ServiceNode;
    }
    public int Num_TerminalNode(){
        this.num_TerminalNode=num_TerminalNode;
        return num_TerminalNode;
    }
    public int Node_cap(double t){
        int node_cap;
        Random rnd = new Random();
        if(t<=min_p) node_cap = (small_min+rnd.nextInt(small_max-small_min))*10;
        else if(min_p<t&&t<=normal_p) node_cap = (normal_min+rnd.nextInt(normal_max-normal_min))*10;
        else node_cap = (large_min+rnd.nextInt(large_max-large_min))*10;
        return node_cap;
    }
    public int Node_Cost(double t){
        Random rnd = new Random();
        int node_cost;
        if(t<=min_p) node_cost = (small_min_cost+rnd.nextInt(small_max_cost-small_min_cost));
        else if(min_p<t&&t<=normal_p) node_cost = (normal_min_cost+rnd.nextInt(normal_max_cost-normal_min_cost))*10;
        else node_cost = (large_min_cost+rnd.nextInt(large_max_cost-large_min_cost))*10;
        return node_cost;
    }
    public int Edge_cap(double t){
        int edge_cap;
        Random rnd = new Random();
        if(t<=msmall_p) edge_cap = emsmall;
        else if(msmall_p<t&&t<=small_p)edge_cap = esmall;
        else if(small_p<t && t<=enormal_p) edge_cap = enormal;
        else if(enormal_p<t && t<=large_p) edge_cap = elarge;
        else edge_cap = emlarge;
        return edge_cap;
    }
    public int Edge_Cost(double t){
        Random rnd = new Random();
        int edge_cost;
        if(t<=msmall_p) edge_cost = (msmall_min_ecost+rnd.nextInt(msmall_max_ecost-msmall_min_ecost));
        else if(msmall_p<t&&t<=small_p)edge_cost = (small_min_ecost+rnd.nextInt(small_max_ecost-small_min_ecost));
        else if(small_p<t && t<=enormal_p) edge_cost = (normal_min_ecost+rnd.nextInt(normal_max_ecost-normal_min_cost));
        else if(enormal_p<t && t<=large_p) edge_cost = (large_min_cost+rnd.nextInt(large_max_ecost-large_min_ecost));
        else edge_cost = mlarge_min_ecost+rnd.nextInt(mlarge_max_ecost-mlarge_min_ecost);
        return edge_cost;
    }
    public int VNF_cap(double t){
        Random rnd = new Random();
        int cap;
        if(t<=low) cap = low_min_cap+rnd.nextInt(low_max_cap-low_min_cap);
        else if(low<t && t<=normal) cap = normal_min_cap+rnd.nextInt(normal_max_cap - normal_min_cap);
        else cap = large_min_cap+rnd.nextInt(large_max_cap - large_min_cap);
        return cap;
    }

}
