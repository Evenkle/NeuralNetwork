package io;

import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by even on 14.05.17.
 */
public class CSVToMatrix {

    public static SimpleMatrix csvToMatrix(String fileName) {

        Scanner in;
        try {
            in = new Scanner(new FileReader(fileName));
            String line = in.nextLine();
            String[] dimensions = line.split(",");
            line = in.nextLine();
            String[] values = line.split(",");
            double[][] dValues = new double[Integer.valueOf(dimensions[0])][Integer.valueOf(dimensions[1])];
            for (int i = 0; i < Integer.valueOf(dimensions[0]); i++) {
                for (int j = 0; j < Integer.valueOf(dimensions[1]); j++) {
                    dValues[i][j] = Double.valueOf(values[i * Integer.valueOf(dimensions[1]) + j]);
                }
            }
            in.close();
            return new SimpleMatrix(dValues);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
