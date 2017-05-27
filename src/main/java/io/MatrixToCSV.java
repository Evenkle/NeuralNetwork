package io;

import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by even on 14.05.17.
 */
public class MatrixToCSV {

    public static void matrixToCSV(SimpleMatrix matrix, String fileName) {
        try {
            PrintWriter outFile = new PrintWriter(fileName + ".csv");
            outFile.print(matrix.numRows() + ",");
            outFile.print(matrix.numCols() + "\n");
            for (int i = 0; i < matrix.getNumElements(); i++) {
                outFile.print(matrix.get(i));
                if (i < matrix.getNumElements() - 1) {
                    outFile.print(",");
                } else {
                    outFile.print("\n");
                }
            }
            outFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
