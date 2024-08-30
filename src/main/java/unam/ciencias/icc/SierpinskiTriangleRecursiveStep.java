package unam.ciencias.icc;

import java.util.List;

public class SierpinskiTriangleRecursiveStep
    implements FractalRecursiveStep<Polygon> {

  @Override
  public List<Polygon> apply(Polygon polygon) {
    var v0 = polygon.getVertex(0);
    var v1 = polygon.getVertex(1);
    var v2 = polygon.getVertex(2);
    var midV0V1 = new Point2D(middlePoint(v0.x(), v1.x()), middlePoint(v0.y(), v1.y()));
    var midV1V2 = new Point2D(middlePoint(v1.x(), v2.x()), middlePoint(v1.y(), v2.y()));
    var midV2V0 = new Point2D(middlePoint(v2.x(), v0.x()), middlePoint(v2.y(), v0.y()));

    return List.of(new Polygon(v0, midV0V1, midV2V0),
                   new Polygon(midV0V1, v1, midV1V2),
                   new Polygon(midV2V0, midV1V2, v2));
  }

  private float middlePoint(float a, float b) {
    return (a + b) / 2;
  }
}
