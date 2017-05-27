package training;

import neuralnetwork.NeuralNetwork;
import org.ejml.simple.SimpleMatrix;

/**
 * Cross-entropy cost function of the neural network. This function determens how well the network fits the training data
 */
public class CrossEntropyCostFunction {
    /**
     * @param X             matrix with the training examples
     * @param y             matrix with the training label
     * @param neuralNetwork A neural network with the desired dimensions
     * @return double. cost/how well the network fits the data
     */
    public static double costFunction(SimpleMatrix X, SimpleMatrix y, NeuralNetwork neuralNetwork) {
        double J = (1.0 / X.numRows());

        double tmp = 0;
        for (int i = 0; i < X.numRows(); i++) {
            SimpleMatrix output = neuralNetwork.calculate(getRow(X, i));
            System.out.println(output);
            for (int k = 0; k < y.numCols(); k++) {
                tmp += -y.get(i, k) * Math.log(output.get(k)) - (1 - y.get(i, k)) * Math.log(1 - output.get(k));
            }
        }
        J = J * tmp;
        return J;
    }

    public static SimpleMatrix getRow(SimpleMatrix matrix, int row) {
        SimpleMatrix rowMatrix = new SimpleMatrix(1, matrix.numCols());
        for (int i = 0; i < matrix.numCols(); i++) {
            rowMatrix.setColumn(i, 0, matrix.get(row, i));
        }
        return rowMatrix;
    }

    public static void main(String[] args) {
        /*
        SimpleMatrix X = new SimpleMatrix(4, 2);
        SimpleMatrix y = new SimpleMatrix(4,1);

        X.setRow(0,0, 0, 0);
        X.setRow(1,0,0, 1);
        X.setRow(2,0,1, 0);
        X.setRow(3,0,1, 1);
        y.setColumn(0,0,1,0,0,1);

        NeuralNetwork nn = new NeuralNetwork(2,1,2);

        double cost = costFunction(X,y, nn);
        System.out.println(cost);

        SimpleMatrix weights1 = new SimpleMatrix(2, 3);
        weights1.setRow(0, 0, -30, 20, 20);
        weights1.setRow(1, 0, 10, -20, -20);
        SimpleMatrix weights2 = new SimpleMatrix(1, 3);
        weights2.setRow(0, 0, -10, 20, 20);
        List<SimpleMatrix> weights = new ArrayList<>();
        weights.add(weights1);
        weights.add(weights2);

        nn.setWeights(weights);
        cost = costFunction(X,y,nn);
        System.out.println(cost);
        */

        SimpleMatrix rowMatrix = new SimpleMatrix(2, 2);
        rowMatrix.setRow(0, 0, 1, 2);
        rowMatrix.setRow(1, 0, 3, 4);

        System.out.println(getRow(rowMatrix, 1));
    }
}
