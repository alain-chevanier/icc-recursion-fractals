package unam.ciencias.icc;

import java.util.List;

public interface FractalRecursiveStep<T extends Shape> {
  List<T> apply(T shape);
}
