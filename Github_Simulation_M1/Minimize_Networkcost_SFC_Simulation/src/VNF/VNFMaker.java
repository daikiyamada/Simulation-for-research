package VNF;

/**
 * Created by 大樹 on 2018/06/25.
 */
import java.util.*;
public class VNFMaker {
    public ArrayList<MyVNF> VNF_Maker(int num_VNFtype){
        /**VNFtypeList*/
        ArrayList<MyVNF> VNFtype_List = new ArrayList<>();
        /**要求帯域リスト*/
        int [][] bdw_list = new int [num_VNFtype][num_VNFtype];
        /**要求帯域の決定（ランダム）*/
        for(int a=0;a<num_VNFtype;a++){
            for(int b=a+1;b<num_VNFtype;b++){
                int bdw = BDW_VNF();
                bdw_list[a][b]=bdw;
                bdw_list[b][a]=bdw;
            }
        }
        /**VNFの決定*/
        for(int a=0;a<num_VNFtype;a++){
            int req = Req_VNF();
            MyVNF F = new MyVNF(a,req,bdw_list[a]);
            VNFtype_List.add(F);
        }
        return VNFtype_List;
    }
    private int Req_VNF(){
        Random rnd = new Random();
        int cap_VNF;
        /**要求計算キャパシティの設定*/
        cap_VNF = 1+rnd.nextInt(20);
        return cap_VNF;
    }
    private int BDW_VNF(){
        Random rnd = new Random();
        int bdw;
        /**要求帯域の決定*/
        bdw = 10*(3+rnd.nextInt(7));
        return bdw;
    }
}
