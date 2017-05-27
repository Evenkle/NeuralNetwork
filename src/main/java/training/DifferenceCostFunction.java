package training;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by even on 04.05.17.
 */
public class DifferenceCostFunction implements CostFunction {
    @Override
    public SimpleMatrix calculateError(SimpleMatrix actual, SimpleMatrix target) {
        SimpleMatrix error = new SimpleMatrix(actual.numRows(), 1);
        for (int i = 0; i < actual.getNumElements(); i++) {
            error.set(i, target.get(i) - actual.get(i));
        }
        return error;
    }

    public static void main(String[] args) {
        CostFunction costFunction = new DifferenceCostFunction();

        SimpleMatrix target = new SimpleMatrix(3, 1);
        target.setColumn(0, 0, 3, 2);

        SimpleMatrix actual = new SimpleMatrix(2, 1);
        actual.setColumn(0, 0, 3, 4);

        SimpleMatrix result = costFunction.calculateError(actual, target);
        System.out.println(result);
    }
}
