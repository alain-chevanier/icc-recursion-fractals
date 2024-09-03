package unam.ciencias.icc;

import java.util.List;

public class KochSnowflakeRecursiveStep
    implements FractalRecursiveStep<LineSegment> {

  @Override
  public List<LineSegment> apply(LineSegment lineSegment) {
    var a = lineSegment.beg();
    var b = lineSegment.end();
    var c = lineSegment.getInnerPoint(1, 3);
    var d = lineSegment.getInnerPoint(2, 3);
    var f = calcF(new LineSegment(c, d));
    return List.of(new LineSegment(a, c),
                   new LineSegment(c, f),
                   new LineSegment(f, d),
                   new LineSegment(d, b));
  }

  private Point2D calcF(LineSegment cd) {
    var e = cd.getInnerPoint(1, 2);
    var orthogonalLineByE = StraightLine.build(cd.beg(), cd.end())
                                        .getOrthogonalAtPoint(e);
    var circle = new Circle(cd.beg(), cd.getLength());
    return circle.intersect(orthogonalLineByE, calculateSign(cd))
                 .orElseThrow();
  }

  private int calculateSign(LineSegment cd) {
    int orderSign = cd.beg().compareTo(cd.end()) < 0 ? 1 : -1;
    int slopeSign = cd.getSlope() > 0.01 ? -1 : 1;
    return slopeSign * orderSign;
  }
}
