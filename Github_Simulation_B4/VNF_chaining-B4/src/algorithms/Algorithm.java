package algorithms;

import entities.MySubtopology;
import java.util.List;

/**
 * Algorithm
 *
 * interface for algorithm class
 */
public interface Algorithm {
    /**
     * method to compose subtopologies
     * @return a list of subtopologies
     */
    List<MySubtopology> solve();
}
