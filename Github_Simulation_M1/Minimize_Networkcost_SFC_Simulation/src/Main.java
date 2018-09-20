/**
 * Created by 大樹 on 2018/06/25.
 */
import Parameter.Parameter;
import Physcial_Network.*;
import SFC.*;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Main {
    static class SFCmaker extends SFCMaker {
    }

    static class NodeMaker extends Graph_NodeMaker {
    }

    static class NWS extends NWS_GraphMaker {
    }

    static class Lattice extends Lattice_GraphMaker {
    }

    // static class Layout extends Graph_Layout{}
    public static void main(String[] args) {

        /**VNFtype list for VNF Maker*/
        Parameter p = new Parameter();
        /**Service Function Chain for SFC Maker*/
        ArrayList<MySFC> SFC_set = new ArrayList<MySFC>();
        SFCmaker SFC = new SFCmaker();
        SFC_set = SFC.SFCMaker();
            /**Graph Maker*/
            //  Layout layout = new Layout();
            /**Node Maker*/
       Graph<MyNode, MyEdge> Physical_Network = new UndirectedSparseGraph<>();
        NodeMaker ng = new NodeMaker();
        Physical_Network = ng.NodeMaker(p.Num_ServiceNode(),p.Num_TerminalNode());
        /**NWS Graph*/
        int k=2;
        double pp = 0.2;
        NWS nws = new NWS();
        Physical_Network = nws.NWS_GraphMaker(Physical_Network,k,pp);
            /**Lattice Graph(正方格子グラフ)*/
      /*  Lattice lat = new Lattice();
        Physical_Network=lat.LatticeGraph_Maker(Physical_Network);*/
        Layout_Graph(Physical_Network);
        /**Path Computaion*/
            /**Placement Computation*/
            /**Backup Computation*/
            /**Function Evaluation*/
            /**Output*/
        }
    public static void Layout_Graph(Graph<MyNode, MyEdge> graph) {
        Layout<MyNode, MyEdge> layout = new CircleLayout<MyNode, MyEdge>(graph);
        layout.setSize(new Dimension(800, 800));
        BufferedImage image = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        BasicVisualizationServer<MyNode, MyEdge> panel = new BasicVisualizationServer<MyNode, MyEdge>(layout, new Dimension(800, 800));
        panel.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyNode>());//vertex label
        Date now = new Date();
        DateFormat dfYMD = new SimpleDateFormat("YYYYMMDD");
        DateFormat dfHMS = new SimpleDateFormat("hhmmss");
        JFrame frame = new JFrame("Graph View: Manual Layout"+dfYMD.format(now)+"_"+dfHMS.format(now));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

    }

}

