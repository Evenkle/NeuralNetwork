package training;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by even on 04.05.17.
 */
public interface CostFunction {

    SimpleMatrix calculateError(SimpleMatrix actual, SimpleMatrix target);
}
