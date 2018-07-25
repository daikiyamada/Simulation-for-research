/**
 * Created by 大樹 on 2018/06/25.
 */
import VNF.*;
import SFC.*;
import java.util.*;
public class Main {
    static class VNFmaker extends VNFMaker{}
    static class SFCmaker extends SFCMaker{}
    public static void main(String[] args){
        int num_VNFtype=10;  /**VNFタイプ数*/
        int num_SFC = 10; /**SFC数*/
        int num_VNF = 4; /**1つのSFCに含まれるVNF数*/
        /**Graph ノード数・種類別ノード数*/
        int num_Node =10,num_ServiceNode=7,num_TerminalNode=num_Node-num_ServiceNode;

        /**VNFtype list for VNF Maker*/
        ArrayList<MyVNF> VNFtype_List = new ArrayList<MyVNF>();
        VNFmaker vm = new VNFmaker();
        VNFtype_List=vm.VNF_Maker(num_VNFtype);

/*        for(int a=0;a<num_VNFtype;a++){
            System.out.print("id="+a+" ");
            System.out.print("capacity="+VNFtype_List.get(a).cap_VNF+" ");
            for(int b=0;b<num_VNFtype;b++){
                System.out.print("Band="+VNFtype_List.get(a).bandwidth_VNF[b]+" ");
            }
            System.out.println();
        }*/

        /**Service Function Chain for SFC Maker*/
        ArrayList<MySFC> SFC_set = new ArrayList<MySFC>();
        SFCmaker SFC = new SFCmaker();
        SFC_set = SFC.SFCMaker(num_SFC,num_VNF,VNFtype_List);

/*        for(int a=0;a<num_SFC;a++){
            System.out.print("id="+a+"  VNF=");
            for(int b=0;b<num_VNF;b++){
                System.out.print(SFC_set.get(a).subset_VNF.get(b).VNF_id+" ");
            }
            System.out.print("max_cost="+SFC_set.get(a).Maximum_Cost);
            System.out.println();
        }*/
        /**Graph Maker*/
        /**Path Computaion*/
        /**Placement Computation*/
        /**Backup Computation*/
        /**Function Evaluation*/
        /**Output*/
    }
}
