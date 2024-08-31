package unam.ciencias.icc;

/**
 * MatrixDeterminantCalculator
 */
public class MatrixDeterminantCalculator {
  public float calculateDeterminant(Matrix<Float> matrix) {
    if (matrix.getRows() != 2 || matrix.getColumns() != 2) {
      throw new IllegalArgumentException("Only 2x2 matrix are supported");
    }
    return matrix.getValue(0, 0) * matrix.getValue(1, 1)
        - matrix.getValue(0, 1) * matrix.getValue(1, 0);
  }
}
