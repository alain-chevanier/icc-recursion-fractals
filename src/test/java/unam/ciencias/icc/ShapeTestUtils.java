package unam.ciencias.icc;

import java.util.List;
import java.util.stream.Collectors;

public class ShapeTestUtils {
  public static <T extends Shape> List<T> sort(List<T> shapes) {
    return shapes.stream()
                 .sorted((s1, s2) -> s1.compareTo(s2))
                 .collect(Collectors.toList());
  }
}
