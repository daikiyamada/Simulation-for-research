package Demand;

/**
 * Created by Daiki Yamada on 2017/09/14.
 */

import java.util.*;
import GraphMaking.*;
public class RequestChain {
    public List<Request_VNFChain> VNFchaining(int num_VNF,int num_VNFC,int VNF_type,List<Request_VNFChain> Req_VNFChain,List<Integer> Req_VNF,List<Integer> Source_Node){
        ArrayList<Integer> source = new ArrayList();
        /**要求VNFChainingのパラメータ*/
        int request_type;
        Random rnd = new Random();
        /**要求VNFChainingの決定*/
        for(int a=0;a<num_VNFC;a++){
            List<Integer> multi = new ArrayList<Integer>();
            List<Integer> Cap = new ArrayList<Integer>();
            for(int b=0;b<num_VNF;){
                int type = rnd.nextInt(VNF_type);
                if(b==0) {
                    multi.add(type);
                    Cap.add(Req_VNF.get(type));
                    b++;
                }
                else {
                    int signal=0;
                    for (int c = 0; c < multi.size(); c++) {
                        if (type == multi.get(c)) signal =1;
                    }
                    if(signal==0){
                        multi.add(type);
                        Cap.add(Req_VNF.get(type));
                        b++;
                    }
                }
            }
            int sum=0;
            for(int c=0; c<num_VNF;c++){
                sum+=Cap.get(c);
            }
            Request_VNFChain req = new Request_VNFChain(multi,Cap,sum,Source_Node.get(a));
            Req_VNFChain.add(req);
            //multi.clear();
            //Cap.clear();
        }
        return Req_VNFChain;
    }
}