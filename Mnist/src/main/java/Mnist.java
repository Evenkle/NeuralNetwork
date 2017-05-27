import neuralnetwork.NeuralNetwork;
import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An implementation of the mnist dataset with handwritten digits. Using the Nerual network i train it with the data-set
 * and test it on the test set.
 */
public class Mnist {

    private static final double LEARNING_RATE = 0.1;
    private static final int HIDDEN_NODES = 500;
    private static final int EPOCHS = 5;

    public static void run() {

        long startTime = System.currentTimeMillis();

        NeuralNetwork mnist = new NeuralNetwork(784, HIDDEN_NODES, 10, LEARNING_RATE);

        List<SimpleMatrix> targets = new ArrayList<>();
        List<SimpleMatrix> inputs = new ArrayList<>();

        // loading the data set
        System.out.println("Loading the data set ...");
        try {
            Scanner scanner = new Scanner(new FileReader("mnist_dataset/mnist_train.csv"));
            while (scanner.hasNextLine()) {
                // read from file
                String line = scanner.nextLine();
                String[] values = line.split(",");

                // prep the trarget
                int intTarget = Integer.valueOf(values[0]);
                SimpleMatrix target = new SimpleMatrix(10, 1);
                for (int i = 0; i < 10; i++) {
                    if (i == intTarget)
                        target.set(i, 0.99);
                    else
                        target.set(i, 0.01);
                }
                // prep the data
                SimpleMatrix input = new SimpleMatrix(784, 1);
                for (int i = 0; i < 784; i++) {
                    double value = (Double.valueOf(values[i + 1]) / 255.0 * 0.99) + 0.01;
                    input.set(i, value);
                }
                // add the data to the lists
                inputs.add(input);
                targets.add(target);
            }
            scanner.close();
            System.out.println("Loading was a success!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // epochs is the number of iterations through the data set
        System.out.println("Training(This can take som time) ... ");
        for (int i = 0; i < EPOCHS; i++) {
            for (int j = 0; j < inputs.size(); j++) {
                // train with the data!
                mnist.train(inputs.get(j), targets.get(j));
            }
            System.out.println((i + 1) + " out of " + EPOCHS + " epochs done!");
        }

        List<SimpleMatrix> testInputs = new ArrayList<>();
        List<Integer> testTargets = new ArrayList<>();

        // loading the test set
        System.out.println("Loading the test set");
        try {
            Scanner scanner = new Scanner(new FileReader("mnist_dataset/mnist_test.csv"));
            while (scanner.hasNextLine()) {
                // read from file
                String line = scanner.nextLine();
                String[] values = line.split(",");

                // prep the trarget
                testTargets.add(Integer.valueOf(values[0]));

                // prep the data
                SimpleMatrix input = new SimpleMatrix(784, 1);
                for (int i = 0; i < 784; i++) {
                    double value = (Double.valueOf(values[i + 1]) / 255.0 * 0.99) + 0.01;
                    input.set(i, value);
                }
                // add the data to the lists
                testInputs.add(input);
            }
            scanner.close();
            System.out.println("Loading was a success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Testing the network ...");
        List<Integer> scoreboard = new ArrayList<>();
        for (int i = 0; i < testInputs.size(); i++) {
            SimpleMatrix out = mnist.calculate(testInputs.get(i));

            int nnLable = -1;
            double maxValue = Double.MIN_VALUE;
            for (int j = 0; j < 10; j++) {
                if (out.get(j) > maxValue) {
                    maxValue = out.get(j);
                    nnLable = j;
                }
            }

            if (nnLable == testTargets.get(i)) {
                scoreboard.add(1);
            } else {
                scoreboard.add(0);
            }
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        System.out.println("Done!");

        System.out.println("Final result");
        //System.out.println(scoreboard);
        double performance = scoreboard.stream().mapToDouble(Integer::intValue).sum() / scoreboard.size();
        System.out.println("Performance = " + performance);
        System.out.println("Time = " + (time / 1000));
    }

    public static void main(String[] args) {
        Mnist.run();
    }
}
