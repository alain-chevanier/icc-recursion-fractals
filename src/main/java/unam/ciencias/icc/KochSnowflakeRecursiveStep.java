package unam.ciencias.icc;

import java.util.List;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class KochSnowflakeRecursiveStep implements FractalRecursiveStep<LineSegment> {
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
    var ce = new LineSegment(cd.getBeg(), e);
    float distance_c_f = (float) sqrt(pow(cd.getLength(), 2) - pow(ce.getLength(), 2));

    if (cd.isHorizontal()) {
      float sign = cd.beg().compareTo(cd.end()) < 0 ? 1f : -1f;
      return new Point2D(e.x(), e.y() + sign*distance_c_f);
    }
    if (cd.isVertical()) {
      float sign = cd.beg().compareTo(cd.end()) < 0 ? -1f : 1f;
      return new Point2D(e.x() + sign*distance_c_f, e.y());
    }
    return foo(cd);
  }

  private Point2D foo(LineSegment cd) {
    float ortogonalSlope = calcLinesOrtogonalSlope(cd.getSlope());
    float linearOffset = calcLinearOffset(cd.getInnerPoint(1, 2), ortogonalSlope);
    float x_f = calcX(cd, ortogonalSlope, linearOffset);
    float y_f = calcY(ortogonalSlope, x_f, linearOffset);
    if (Float.isNaN(x_f)) {
      System.out.println("NaN on cd: " + cd);
    }
    return new Point2D(x_f, y_f);
  }

  private float calcLinesOrtogonalSlope(float slope) {
    if (slope == 0f) {
      return Float.MAX_VALUE;
    }
    return -1f/slope;
  }

  private float calcLinearOffset(Point2D e, float ortogonalSlope) {
    return e.y() - ortogonalSlope * e.x();
  }

  private float calcX(LineSegment cd, float ortogonalSlope, float linearOffset) {
    Point2D C = cd.beg();
    float s = linearOffset - C.y();
    double a = 1 + pow(ortogonalSlope, 2);
    double b = 2 * (ortogonalSlope * s - C.x());
    double c = pow(C.x(), 2) + pow(s, 2) - pow(cd.getLength(), 2);
    int signOrder = cd.beg().compareTo(cd.end()) < 0 ? 1 : -1;
    int sign = cd.getSlope() > 0 ? -1 : 1;
    return solveQuadraticEcuation(a, b, c, signOrder*sign);
  }

  private float calcY(float ortogonalSlope, float x_f, float linearOffset) {
    return ortogonalSlope*x_f + linearOffset;
  }

  private float solveQuadraticEcuation(double a, double b, double c, int sign) {
    var radical = b*b - 4*a*c;
    if (radical < 0) {
      return Float.NaN;
    }
    return (float) ((-b + sign*sqrt(radical)) / (2 * a));
  }
}
