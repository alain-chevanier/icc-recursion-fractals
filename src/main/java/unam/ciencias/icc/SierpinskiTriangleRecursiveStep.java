package unam.ciencias.icc;

import java.util.List;

public class SierpinskiTriangleRecursiveStep
    implements FractalRecursiveStep<Polygon> {

  @Override
  public List<Polygon> apply(Polygon polygon) {
    if (polygon.getVertices().size() != 3) {
      throw new IllegalArgumentException("This only handles triangles");
    }

    var v0 = polygon.getVertex(0);
    var v1 = polygon.getVertex(1);
    var v2 = polygon.getVertex(2);
    var midV0V1 = polygon.getEdge(0).getInnerPoint(1, 2);
    var midV1V2 = polygon.getEdge(1).getInnerPoint(1, 2);
    var midV2V0 = polygon.getEdge(2).getInnerPoint(1, 2);

    return List.of(new Polygon(v0, midV0V1, midV2V0),
                   new Polygon(midV0V1, v1, midV1V2),
                   new Polygon(midV2V0, midV1V2, v2));
  }
}
