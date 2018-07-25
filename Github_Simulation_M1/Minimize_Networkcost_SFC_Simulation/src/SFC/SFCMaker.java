package SFC;

/**
 * Created by 大樹 on 2018/06/25.
 */
import java.util.*;
import VNF.*;
public class SFCMaker {
    public ArrayList<MySFC> SFCMaker(int num_SFC,int num_VNF,ArrayList<MyVNF> VNFtype_List){
        ArrayList<MySFC> SFC_set = new ArrayList<MySFC>();
        for(int a=0;a<num_SFC;a++){
            ArrayList<MyVNF> VNF_set = new ArrayList<MyVNF>();
            VNF_set = SFC_VNFMaker(num_VNF,VNFtype_List);
            int max_cost = MaxCost();
            MySFC sn = new MySFC(VNF_set,max_cost,0,0,0);
            SFC_set.add(sn);
        }
        return SFC_set;
    }

    public ArrayList<MyVNF> SFC_VNFMaker(int num_VNF,ArrayList<MyVNF>VNFtype_List){
        Random rnd = new Random();
        ArrayList<MyVNF> VNF_set = new ArrayList<MyVNF>();
        for(int a=0;a<num_VNF;){
            MyVNF vnf = VNFtype_List.get(rnd.nextInt(VNFtype_List.size()));
            if(VNF_set.contains(vnf)!=true){
                VNF_set.add(vnf);
                a++;
            }
        }
        return VNF_set;
    }

    public int MaxCost(){
        Random rnd = new Random();
        int max_cost = 20+rnd.nextInt(30);
        return max_cost;
    }
}
