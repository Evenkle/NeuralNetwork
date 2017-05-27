package transfer;

import org.ejml.simple.SimpleMatrix;

/**
 * Sigmoid function. THe sigmoid function limits the value between 0 and 1
 *
 * @author Even Klemsdal
 */

public class Sigmoid implements TransferFunction {

    public SimpleMatrix transfer(SimpleMatrix values) {
        for (int i = 0; i < values.numRows() * values.numCols(); i++) {
            values.set(i, 1 / (1 + Math.exp(-values.get(i))));
        }
        return values;
    }

    public SimpleMatrix sigmoidGradient(SimpleMatrix values) {
        SimpleMatrix sigmoid = transfer(values);
        for (int i = 0; i < sigmoid.getNumElements(); i++) {
            double value = sigmoid.get(i);
            sigmoid.set(i, value * (1 - value));
        }
        return sigmoid;
    }

    public static void main(String[] args) {
        SimpleMatrix vector = new SimpleMatrix(5, 2);
        vector.setColumn(0, 0, -20, -5, 0, 5, 20);
        vector.setColumn(1, 0, 20, 5, 0, -5, -20);

        Sigmoid transfer = new Sigmoid();
        System.out.println(transfer.sigmoidGradient(vector));
    }
}
