package generators;

import java.util.*;

/**
 * Created by Hideo on 2017/07/17.
 */
public class ServiceRequestGenerator {

    private int numOfVNFType;
    private int numOfVNFInRequest;

    public ServiceRequestGenerator(int numOfVNFType, int numOfVNFInRequest){
        this.numOfVNFType = numOfVNFType;
        this.numOfVNFInRequest = numOfVNFInRequest;
    }

    /**
     *
     * @return
     */
    public List<Integer> create(){
        List<Integer> request = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < numOfVNFInRequest; i++){
            request.add(1 + rnd.nextInt(numOfVNFType));
        }
        return request;
    }
}