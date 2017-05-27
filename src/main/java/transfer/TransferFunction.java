package transfer;

import org.ejml.simple.SimpleMatrix;

/**
 * TransferFunction interface
 */
public interface TransferFunction {
    SimpleMatrix transfer(SimpleMatrix values);
}
