package neuralnetwork;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
import transfer.Sigmoid;

import java.util.Random;

/**
 * Utility functions used by the Neural Network class
 */
public class NeuralNetworkUtilities {

    /**
     * Return random initial weights for the neural network. The values are between sqr(6)/sqr(in + out)
     *
     * @param in  number of nodes in the previous layer
     * @param out number of nodes in the next layer
     * @return SimpleMatrix containing randomly initialised weights.
     */
    public static SimpleMatrix randInitWeights(int in, int out) {
        double epsilon = (Math.sqrt(6) / Math.sqrt(in + out));
        return SimpleMatrix.random(out, in, -epsilon, epsilon, new Random());
    }

    /**
     * Returns the selected row in a simpleMatrix
     *
     * @param matrix matrix to select from
     * @param row    row to select
     * @return SimpleMatrix containing a row vector equal to the row selected in the matrix
     */
    public static SimpleMatrix getRow(SimpleMatrix matrix, int row) {
        SimpleMatrix rowMatrix = new SimpleMatrix(1, matrix.numCols());
        for (int i = 0; i < matrix.numCols(); i++) {
            rowMatrix.setColumn(i, 0, matrix.get(row, i));
        }
        return rowMatrix;
    }

    /**
     * Takes inn a column vector and return the same vector now with an extra one as
     * the first element of the vector
     *
     * @param vector SimpleMatrix
     * @return SimpleMatrix
     */
    public static SimpleMatrix addBias(SimpleMatrix vector) {
        if (vector.numCols() != 1) {
            throw new IllegalArgumentException("Not a column vector");
        }
        // Make new and longer vector
        SimpleMatrix vectorWithBias = new SimpleMatrix(vector.numRows() + 1, 1);
        // Set first element of the new vector to be 1
        vectorWithBias.setColumn(0, 0, 1);
        // Inserting the old vector in to the new vector with bias
        vectorWithBias.insertIntoThis(1, 0, vector);
        return vectorWithBias;
    }

    public static SimpleMatrix gradientDecent(double learningRate, DenseMatrix64F weights, DenseMatrix64F error, DenseMatrix64F secondLayerOutput, DenseMatrix64F firstLayerOutput) {
        SimpleMatrix temp = new SimpleMatrix(weights.numRows, weights.numCols);
        for (int i = 0; i < weights.getNumRows(); i++) {
            for (int j = 0; j < weights.getNumCols(); j++) {
                double delta = error.get(i) * secondLayerOutput.get(i) * (1 - secondLayerOutput.get(i)) * firstLayerOutput.get(j);
                double weightData = weights.get(i, j);
                //System.out.println(delta * learningRate);
                temp.set(i, j, (learningRate * delta) + weightData);
            }
        }
        return temp;

    }

    public static void main(String[] args) {
        SimpleMatrix inputs = new SimpleMatrix(3, 1, true, 1, 0.4, 0.5);

        DenseMatrix64F weights = new DenseMatrix64F(2, 3, true, 3.0, 2.0, 3.0, 2.0, 1.0, 4.0);
        //System.out.println(weights + "weights");
        DenseMatrix64F error = new DenseMatrix64F(2, 1, true, 0.8, 0.5);

        SimpleMatrix output = new Sigmoid().transfer(new SimpleMatrix(weights).mult(inputs));
        SimpleMatrix weight = gradientDecent(0.1, weights, error, output.getMatrix(), inputs.getMatrix());

        System.out.println(weights);
        System.out.println(weight);


    }
}
