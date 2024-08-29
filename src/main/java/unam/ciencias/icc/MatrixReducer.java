package unam.ciencias.icc;

public interface MatrixReducer<T> {
  T reduce(int row, int column, T accValue, T value);
}
