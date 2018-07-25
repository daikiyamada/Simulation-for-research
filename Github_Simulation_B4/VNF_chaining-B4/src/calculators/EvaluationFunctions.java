package calculators;

import entities.MySubtopology;

import java.util.List;

/**
 * EvaluationFunctions
 *
 * class to calculate evaluation functions
 */
public class EvaluationFunctions {

    public static double objectiveFunction(List<MySubtopology> subtopologyList){
        double result = 0;
        for (MySubtopology sub: subtopologyList)
            result += sub.getEdgeCount();
        return result / subtopologyList.size();
    }
}