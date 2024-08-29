package unam.ciencias.icc;

import java.util.List;

public interface FractalGenerator {
  List<Polygon> generate(List<Polygon> input, int depth);
}
