package unam.ciencias.icc;

import java.util.List;

public class DragonCurveRecursiveStep
  implements FractalRecursiveStep<LineSegment> {

  @Override
  public List<LineSegment> apply(LineSegment lineSegment) {
    var a = lineSegment.beg();
    var b = lineSegment.end();
    var c = calculateC(lineSegment);
    return List.of(new LineSegment(a, c),
                   new LineSegment(b, c));
  }

  private Point2D calculateC(LineSegment lineSegment) {
    float radius = lineSegment.getLength() / 2.0f;
    var center = lineSegment.getInnerPoint(1, 2);
    var circle = new Circle(center, radius);
    var line = StraightLine.build(lineSegment.beg(), lineSegment.end())
                           .getOrthogonalAtPoint(center);
    return circle.intercept(line, calculateSign(lineSegment))
                 .orElseThrow();
  }

  private int calculateSign(LineSegment cd) {
    int orderSign = cd.beg().compareTo(cd.end()) < 0 ? 1 : -1;
    int slopeSign = cd.getSlope() > 0.01 ? 1 : -1;
    return slopeSign * orderSign;
  }
}
