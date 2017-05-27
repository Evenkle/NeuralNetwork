package neuralnetwork;

import io.CSVToMatrix;
import io.MatrixToCSV;
import org.ejml.simple.SimpleMatrix;
import training.CostFunction;
import training.DifferenceCostFunction;
import transfer.Sigmoid;
import transfer.TransferFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * A neural network with 3 layers
 */
public class NeuralNetwork {

    private TransferFunction transferFunction;
    private CostFunction costFunction;

    private int input;
    private int hidden;
    private int output;

    private double learning_rate;

    private SimpleMatrix weight_input_hidden;
    private SimpleMatrix weight_hidden_output;

    public NeuralNetwork(int input, int hidden, int output, double learningRate) {

        this.input = input;
        this.hidden = hidden;
        this.output = output;

        this.learning_rate = learningRate;

        // for easy switching of the transfer function or the error function.
        this.transferFunction = new Sigmoid();
        this.costFunction = new DifferenceCostFunction();

        // make a matrix with the weights form one layer to the next. The + 1 is to account for bias
        this.weight_input_hidden = NeuralNetworkUtilities.randInitWeights(input + 1, hidden);
        this.weight_hidden_output = NeuralNetworkUtilities.randInitWeights(hidden + 1, output);
    }


    /**
     * Get the weights that the neural network currently is using
     *
     * @return List containing SimpleMatrix for every layer transition
     */
    public List<SimpleMatrix> getWeights() {
        List<SimpleMatrix> weights = new ArrayList<>();
        weights.add(weight_input_hidden);
        weights.add(weight_hidden_output);
        return weights;
    }


    /**
     * Set the weights in the neural network to some pre computed values
     *
     * @param in_hidden  the weights form the input layer to the hidden layer
     * @param hidden_out the weights form the hidden layer to the output layer
     */
    public void setWeights(SimpleMatrix in_hidden, SimpleMatrix hidden_out) {
        if (in_hidden.numRows() != this.hidden || in_hidden.numCols() != this.input + 1) {
            throw new IllegalArgumentException("in_hidden shape do not conclude");
        } else if (hidden_out.numRows() != this.output || hidden_out.numCols() != this.hidden + 1) {
            throw new IllegalArgumentException("hidden_out shape do not conclude");
        }
        this.weight_input_hidden = in_hidden;
        this.weight_hidden_output = hidden_out;
    }


    /**
     * Set the transfer function of the network to be another function
     *
     * @param transfer the new transfer function
     */
    public void setTransferFunction(TransferFunction transfer) {
        this.transferFunction = transfer;
    }


    /**
     * Sends input through the Neural Network
     *
     * @param input SimpleMatrix with dimensions 1 x n. Must be consistent with the declared size of the network!
     * @return SimpleMatrix
     */
    public SimpleMatrix calculate(SimpleMatrix input) {
        if (input.numRows() != this.input) {
            throw new IllegalArgumentException("Input does not match the network");
        }
        // add bias to the inputs
        input = NeuralNetworkUtilities.addBias(input);

        // calculate the hidden layer values
        SimpleMatrix hiddenInputs = weight_input_hidden.mult(input);
        // apply transfer function
        SimpleMatrix hiddenOutputs = transferFunction.transfer(hiddenInputs);
        // add bias to the hidden layer values
        hiddenOutputs = NeuralNetworkUtilities.addBias(hiddenOutputs);

        // calculate the output
        SimpleMatrix finalInput = weight_hidden_output.mult(hiddenOutputs);
        // apply the transfer function and return;
        return transferFunction.transfer(finalInput);
    }


    /**
     * Run bakwards propagation and gradient decent to minimize the error of the neural network
     *
     * @param input  SimpleMatrix containing the training data. Each row in the matix represent one input to the network
     * @param target SimpleMatrix containing the label of each of the training data. each row is one output
     */
    public void train(SimpleMatrix input, SimpleMatrix target) {
        if (input.numRows() != this.input) {
            throw new IllegalArgumentException("Input does not match the network");
        } else if (target.numRows() != this.output) {
            throw new IllegalArgumentException("target output must match the network");
        }

        // add bias to the inputs
        input = NeuralNetworkUtilities.addBias(input);

        // calculate the hidden layer values
        SimpleMatrix hiddenInputs = weight_input_hidden.mult(input);
        // apply transfer function
        SimpleMatrix hiddenOutputs = transferFunction.transfer(hiddenInputs);
        // add bias to the hidden layer values
        SimpleMatrix hiddenOutputsWithBias = NeuralNetworkUtilities.addBias(hiddenOutputs);

        // calculate the output
        SimpleMatrix finalInput = weight_hidden_output.mult(hiddenOutputsWithBias);
        // apply the transfer function and return;
        SimpleMatrix finalOutput = transferFunction.transfer(finalInput);

        // calculate the error
        SimpleMatrix outputError = costFunction.calculateError(finalOutput, target);
        SimpleMatrix hiddenError = weight_hidden_output.transpose().mult(outputError);

        // update the weights
        weight_input_hidden = NeuralNetworkUtilities.gradientDecent(learning_rate, weight_input_hidden.getMatrix(), hiddenError.getMatrix(), hiddenOutputs.getMatrix(), input.getMatrix());
        weight_hidden_output = NeuralNetworkUtilities.gradientDecent(learning_rate, weight_hidden_output.getMatrix(), outputError.getMatrix(), finalOutput.getMatrix(), hiddenOutputsWithBias.getMatrix());
    }


    /**
     * Export the weights to a csv file
     */
    public void exsport() {
        MatrixToCSV.matrixToCSV(weight_input_hidden, "wih");
        MatrixToCSV.matrixToCSV(weight_hidden_output, "who");
    }


    /**
     * Import weights from file
     *
     * @param wihFileName weights input-hidden
     * @param whoFileName weights hidden-output
     */
    public void importWeights(String wihFileName, String whoFileName) {
        SimpleMatrix inHidden = CSVToMatrix.csvToMatrix(wihFileName);
        SimpleMatrix hiddenOut = CSVToMatrix.csvToMatrix(whoFileName);
        setWeights(inHidden, hiddenOut);
    }

    public static void main(String[] args) {

    }
}
