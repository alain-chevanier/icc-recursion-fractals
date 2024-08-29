package unam.ciencias.icc;

import java.util.List;
import java.util.stream.Collectors;

public class SierpinskiTriangleGenerator implements FractalGenerator {
  @Override
  public List<Polygon> generate(List<Polygon> input, int depth) {
    if (depth <= 0) {
      return input;
    }

    var nextInput = input.stream()
                         .map(triangle -> splitTriangle(triangle))
                         .flatMap(list -> list.stream())
                         .collect(Collectors.toList());
    return generate(nextInput, depth - 1);
  }

  private List<Polygon> splitTriangle(Polygon polygon) {
    var v0 = polygon.getVertex(0);
    var v1 = polygon.getVertex(1);
    var v2 = polygon.getVertex(2);
    var midV0V1 = new Point(middlePoint(v0.x(), v1.x()), middlePoint(v0.y(), v1.y()));
    var midV1V2 = new Point(middlePoint(v1.x(), v2.x()), middlePoint(v1.y(), v2.y()));
    var midV2V0 = new Point(middlePoint(v2.x(), v0.x()), middlePoint(v2.y(), v0.y()));

    return List.of(new Polygon(v0, midV0V1, midV2V0),
                   new Polygon(midV0V1, v1, midV1V2),
                   new Polygon(midV2V0, midV1V2, v2));
  }

  private float middlePoint(float a, float b) {
    return (a + b) / 2;
  }
}
