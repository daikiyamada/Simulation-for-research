/**
 * Created by Daiki Yamada on 2017/09/23.
 */
import IO.DataCabinet;
import IO.ResultsWriter;
import algorithms.Algorithm;
import algorithms.RandomizedSMTPbasedAlgorithm;
import algorithms.SMTPbasedAlgorithm;
import calculators.EvaluationFunctions;
import entities.MyEdge;
import entities.MyGraph;
import entities.MySubtopology;
import entities.MyVertex;
import generators.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * Main
 *
 * This class sets conditions and conducts simulations.
 */

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("Simulation Start");
        System.out.println("-------------------------------------------------");
        /** parameters for simulation */
        int simulationTimes = 1000;
        int numOfVNFType = 5;
        int numOfRequest = 10;
        int minNumOfVNFInRequest = 2;
        int maxNumOfVNFInRequest = 6;
        int maxCapacity = 30; //min is 6. if max is 10, then could be 15. refer setUpAttributeForDC method in Main class
        int numOfVertexes = 70;

        /** execute simulations */
        /** experiment on Lattice graph */
        execute("Lattice", numOfVertexes, "vertexRatioMap0", "SMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Lattice", numOfVertexes, "vertexRatioMap0", "RandomizedSMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Lattice", numOfVertexes, "vertexRatioMap1", "SMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Lattice", numOfVertexes, "vertexRatioMap1", "RandomizedSMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        /** experiment on ER graph */
//        execute("ER", numOfVertexes, "vertexRatioMap0", "SMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("ER", numOfVertexes, "vertexRatioMap0", "RandomizedSMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("ER", numOfVertexes, "vertexRatioMap1", "SMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("ER", numOfVertexes, "vertexRatioMap1", "RandomizedSMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);

        /** experiment on Bridged ER graph */
//        execute("BridgedER", numOfVertexes, "vertexRatioMap0", "SMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedER", numOfVertexes, "vertexRatioMap0", "RandomizedSMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedER", numOfVertexes, "vertexRatioMap1", "SMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedER", numOfVertexes, "vertexRatioMap1", "RandomizedSMTPbasedAlgorithm"
//                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        /** experiment on Intellifiber */
        execute("Intellifiber", numOfVertexes, "vertexRatioMap0"
                , "SMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Intellifiber", numOfVertexes, "vertexRatioMap0"
                , "RandomizedSMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Intellifiber", numOfVertexes, "vertexRatioMap1"
                , "SMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("Intellifiber", numOfVertexes, "vertexRatioMap1"
                , "RandomizedSMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        /** experiment on NWS */
        execute("NWS", numOfVertexes, "vertexRatioMap0", "SMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("NWS", numOfVertexes, "vertexRatioMap0", "RandomizedSMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("NWS", numOfVertexes, "vertexRatioMap1", "SMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        execute("NWS", numOfVertexes, "vertexRatioMap1", "RandomizedSMTPbasedAlgorithm"
                , minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        /** experiment on Bridged NWS graph */
//        execute("BridgedNWSgraph", numOfVertexes, "vertexRatioMap0"
//                , "SMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedNWSgraph", numOfVertexes, "vertexRatioMap0"
//                , "RandomizedSMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedNWSgraph", numOfVertexes, "vertexRatioMap1"
//                , "SMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
//        execute("BridgedNWSgraph", numOfVertexes, "vertexRatioMap1"
//                , "RandomizedSMTPbasedAlgorithm", minNumOfVNFInRequest, maxNumOfVNFInRequest, numOfVNFType, numOfRequest, maxCapacity, simulationTimes);
        System.out.println("Simulation End");
    }

    /**
     * method to execute simulations
     *
     * @param graphName
     * @param vertexNum
     * @param vertexRatioName
     * @param algorithmName
     * @param simulationTimes
     */
    private static void execute(String graphName, int vertexNum, String vertexRatioName, String algorithmName, int minNumOfVNFInRequest, int maxNumOfVNFInRequest, int numOfVNFType, int numOfRequest, int maxCapacity, int simulationTimes) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("● Experiment: objective function vs # of VNF in a request");
        Map<String, Double> vertexRatioMap = getVertexRatio(vertexRatioName);
        System.out.println("Condition: " + graphName + "[t" + vertexRatioMap.get("terminal") + ":s" + vertexRatioMap.get("switch") + ":d" + vertexRatioMap.get("data center") + "], " + algorithmName);

        /** preparation for data collection */
        int requestSize = maxNumOfVNFInRequest - minNumOfVNFInRequest + 1;
        List<List<Double>> dataLists = new ArrayList<List<Double>>();
        /** prepare cabinets for data collection */
        DataCabinet objectiveFunctionCabinet = new DataCabinet(requestSize, simulationTimes);
        DataCabinet objectiveFunctionOPTCabinet = new DataCabinet(requestSize, simulationTimes);
        /** collect data for service request */
        List<Double> numOfVNFInRequestList = new ArrayList<Double>();
        for (int i = minNumOfVNFInRequest; i <= maxNumOfVNFInRequest; i++){
            numOfVNFInRequestList.add((double) i);
        }
        dataLists.add(numOfVNFInRequestList);

        /** execute simulations for SFC Embedding */
        int dataIndex = 0;
        for (int i = minNumOfVNFInRequest; i <= maxNumOfVNFInRequest; i++){
            System.out.println("# of VNF in each request: " + i);
            for (int j=0; j < simulationTimes; j++) {
                //System.out.println("==SIMULATION==");
                /** create graph */
                MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphName, vertexNum).create();

                /** set vertex type in the graph */
                graph.setUpVertexType(vertexRatioMap);
                /** make sure that proportion of dc is around specified ratio with accident error */
                /** if # of dc is less than, # of VNFs in a request, request can not be satisfied */
                double numOfVertex = graph.getVertexCount();
                while(graph.getDCList().size() / numOfVertex < vertexRatioMap.get("data center")){
                    graph.setUpVertexType(vertexRatioMap);
                }

                /** set VNF list and capacity to each data center. prepare dc list, then set up them. **/
                List<MyVertex> dcList = graph.getDCList();
                graph.setUpAttributeForDC(dcList, numOfVNFType, maxCapacity);

                //System.out.println("total cap (before): " + graph.getTotalCapacityOfDCs());
                /** create service requests */
                ServiceRequestGenerator requestGenerator = new ServiceRequestGenerator(numOfVNFType, i);
                List<List<Integer>> requestList = new ArrayList<List<Integer>>();
                for (int k = 0; k < numOfRequest; k++){
                    requestList.add(requestGenerator.create());
                }

//                /** data collection for OPT*/
//                double valueOfOPT = new OPTAlgorithm(graph, requestList).solve();
//                objectiveFunctionOPTCabinet.cumulate(dataIndex, valueOfOPT);
                /** compose subtopologies while updating capacity info */
                List<MySubtopology> subtopologyList = getAlgorithm(algorithmName, graph, requestList).solve();
                /** data collection */
                double valueOfObjectiveFunction = EvaluationFunctions.objectiveFunction(subtopologyList);
                objectiveFunctionCabinet.cumulate(dataIndex, valueOfObjectiveFunction);

                //System.out.println("total cap (after): " + graph.getTotalCapacityOfDCs());
            }
            /** print simulation data */
            System.out.println("  value of obj func     : " + objectiveFunctionCabinet.getAveragedValue(dataIndex));
            //System.out.println("  value of obj func (OPT)        : " + objectiveFunctionOPTCabinet.getAveragedValue(dataIndex));
            dataIndex++;
        }
        dataLists.add(objectiveFunctionCabinet.getAveragedDataList());
        /** output results into csv file */
        ResultsWriter writer = new ResultsWriter();
        String filePath = writer.getFullPath(graphName, vertexRatioName, algorithmName);
        writer.write(dataLists, filePath, "# of VNF in request", "value of objective function");

        //String filePath1 = writer.getFullPath(graphName, vertexRatioName, "OPT");
        //writer.write(dataLists, filePath1, "# of VNF in request", "value of objective function");
        System.out.println("-------------------------------------------------");
    }

    /**
     * return graph generator according to graph name
     *
     * @param graphName
     * @param vertexNum
     * @return graph generator
     */
    private static GraphGenerator getGraphGenerator (String graphName, int vertexNum){
        if (graphName == "NWS")
            return new NWSGraphGenerator(80, 0.01, 2);
        else if (graphName == "Lattice")
            return new LatticeGraphGenerator(81);
        else if (graphName == "BridgedNWSgraph")
            return new BridgedNWSGraphGenerator(20, 0.5, 2, 50, 0.01, 2);
        else if (graphName == "ER")
            return new ConnectedERGraphGenerator(70, 0.1);
        else if (graphName == "BridgedER")
            return new BridgedERGraphGenerator(20, 0.5, 60, 0.01);
        else if (graphName == "Intellifiber")
            return new RealWorldNetworkGenerator();
        return null;
    }

    /**
     * return algorithm according to algorithm name
     *
     * @param algorithmName
     * @param graph
     * @param requestList
     * @return
     */
    private static Algorithm getAlgorithm (String algorithmName, MyGraph<MyVertex, MyEdge> graph, List<List<Integer>> requestList){
        if (algorithmName == "SMTPbasedAlgorithm")
            return new SMTPbasedAlgorithm(graph, requestList);
        if (algorithmName == "RandomizedSMTPbasedAlgorithm")
            return new RandomizedSMTPbasedAlgorithm(graph, requestList);
        return null;
    }

    private static Map<String, Double> getVertexRatio(String name){
        /** [s:t:d] */
        /** [1:1:2] */
        Map<String, Double> vertexRatioMap2 = new HashMap<String, Double>();
        vertexRatioMap2.put("terminal", 0.25);
        vertexRatioMap2.put("switch", 0.25);
        vertexRatioMap2.put("data center", 0.5);

        /** [1:1:1] */
        Map<String, Double> vertexRatioMap0 = new HashMap<String, Double>();
        vertexRatioMap0.put("terminal", 0.33);
        vertexRatioMap0.put("switch", 0.33);
        vertexRatioMap0.put("data center", 0.33);

        /** [2:2:1] */
        Map<String, Double> vertexRatioMap1 = new HashMap<String, Double>();
        vertexRatioMap1.put("terminal", 0.4);
        vertexRatioMap1.put("switch", 0.4);
        vertexRatioMap1.put("data center", 0.2);

        if (name == "vertexRatioMap0")
            return vertexRatioMap0;
        else if (name == "vertexRatioMap1")
            return vertexRatioMap1;
        else if (name == "vertexRatioMap2")
            return vertexRatioMap2;
        return null;
    }
}
