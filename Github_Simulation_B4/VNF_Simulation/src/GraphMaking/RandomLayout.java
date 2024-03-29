package GraphMaking;

/**
 * Created by Daiki Yamada on 2017/09/23.
 */
import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;

public class RandomLayout<V,E> extends AbstractLayout<V,E> {

    public RandomLayout(Graph<V,E> g) {
        super(g);
    }

    public RandomLayout(Graph<V,E> g, Dimension d) {
        super(g);
        setSize(d);
    }

    @Override
    public void initialize() {
        layoutNodes();
    }

    @Override
    public void reset() {
    }

    private void layoutNodes() {
        int clearance = 20;

        Dimension d = getSize();
        int width = d.width - clearance * 2;
        int height = d.height - clearance * 2;

        for (V v : getGraph().getVertices()) {
            if (isLocked(v)) continue;
            double x = Math.random() * width + clearance;
            double y = Math.random() * height + clearance;
            transform(v).setLocation(x, y);
        }
    }

}