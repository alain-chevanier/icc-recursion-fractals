package unam.ciencias.icc;

public interface MatrixEntryConsumer<T> {
  void accept(int row, int column, T value);
}
