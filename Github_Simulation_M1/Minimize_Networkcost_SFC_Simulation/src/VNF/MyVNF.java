package VNF;

/**
 * Created by 大樹 on 2018/06/25.
 */
import java.util.*;
public class MyVNF {
    public int VNF_id;
    public int cap_VNF;
    public int[] bandwidth_VNF;

    public MyVNF(int VNF_id,int cap_VNF,int [] bandwidth_VNF){
        this.VNF_id = VNF_id;
        this.cap_VNF = cap_VNF;
        this.bandwidth_VNF = bandwidth_VNF;
    }
}
