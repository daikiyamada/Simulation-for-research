package VNF;

/**
 * Created by 大樹 on 2018/06/25.
 */
import Parameter.Parameter;
import java.util.*;
public class VNFMaker {
    public ArrayList<MyVNF> VNF_Maker(){
        Parameter par = new Parameter();
        /**VNFtypeList*/
        ArrayList<MyVNF> VNFtype_List = new ArrayList<>();
        /**VNFの決定*/
        for(int a=0;a<par.Num_VNFtype();a++){
            MyVNF F = new MyVNF(a,0);
            VNFtype_List.add(F);
        }
        return VNFtype_List;
    }

}
