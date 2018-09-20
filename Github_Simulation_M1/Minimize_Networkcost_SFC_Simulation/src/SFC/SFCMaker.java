package SFC;

/**
 * Created by 大樹 on 2018/06/25.
 */
import java.util.*;
import VNF.*;
import Parameter.*;
public class SFCMaker {
    public ArrayList<MySFC> SFCMaker(){
        Parameter par = new Parameter();
        VNFMaker vnfmaker = new VNFMaker();
        ArrayList<MySFC> SFC_set = new ArrayList<MySFC>();
        for(int a=0;a<par.Num_SFC();a++){
            ArrayList<MyVNF> VNFtype_List = new ArrayList<>();
            ArrayList<MyVNF> VNF_set = new ArrayList<MyVNF>();
            Map<MyVNF,Integer> Allocation_List = new HashMap<>();
            double t = Math.random();
            VNFtype_List =vnfmaker.VNF_Maker();
            VNF_set = SFC_VNFMaker(VNFtype_List,t);
            MySFC sn = new MySFC(VNF_set,0,0,Allocation_List);
            SFC_set.add(sn);
        }
        return SFC_set;
    }

    public ArrayList<MyVNF> SFC_VNFMaker(ArrayList<MyVNF>VNFtype_List,double t){
        Random rnd = new Random();
        Parameter par = new Parameter();
        ArrayList<MyVNF> VNF_set = new ArrayList<MyVNF>();
        for(int a=0;a<par.Num_VNF();){
            MyVNF vnf = VNFtype_List.get(rnd.nextInt(VNFtype_List.size()));
                vnf.cap_VNF = par.VNF_cap(t);
                VNF_set.add(vnf);
                VNFtype_List.remove(vnf);
                a++;
        }
        return VNF_set;
    }
}
