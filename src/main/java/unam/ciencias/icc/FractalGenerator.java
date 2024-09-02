package unam.ciencias.icc;

import java.util.List;
import java.util.stream.Collectors;

public class FractalGenerator<T extends Shape> {

  private FractalRecursiveStep<T> recursiveStep;

  public FractalGenerator(FractalRecursiveStep<T> recursiveStep) {
    this.recursiveStep = recursiveStep;
  }

  public List<T> generate(List<T> input, int iterations) {
    if (iterations <= 0) {
      return input;
    }
    var nextInput = input.stream()
                         .map(shape -> recursiveStep.apply(shape))
                         .flatMap(list -> list.stream())
                         .collect(Collectors.toList());
    return generate(nextInput, iterations - 1);
  }
}
