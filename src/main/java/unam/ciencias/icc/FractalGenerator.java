package unam.ciencias.icc;

import java.util.List;

public interface FractalGenerator<T extends Shape> {
  List<T> generate(List<T> input, int depth);
}
