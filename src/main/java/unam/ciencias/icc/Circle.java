package unam.ciencias.icc;

import java.util.Optional;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Circle {
  private final Point2D center;
  private final float radius;

  public Circle(float radius) {
    this(new Point2D(0, 0), radius);
  }

  public Circle(Point2D center, float  radius) {
    this.center = center;
    this.radius = radius;
  }

  public Optional<Point2D> intersect(StraightLine line, int sign) {
    float distanceToLine = line.getDistance(center);
    if (distanceToLine > radius) {
      return Optional.empty();
    }
    if (line.isVertical()) {
      float x = line.getC() / line.getA();
      return Optional.of(new Point2D(x, getY(x, sign)));
    }
    float x = calcX(line, sign);
    return Optional.of(new Point2D(x, line.getY(x)));
  }

  public float getY(float x, int sign) {
    // (x-c_x)^2 + (y-c_y)^2 = r^2
    // (y-c_y)^2 = r^2 - (x-c_x)^2
    // y^2 - 2*c_y*y + c_y^2 + (x-c_x)^2  - r^2 =  0
    double a = 1f;
    double b = -2*center.y();
    double c = pow(center.y(), 2) + pow(x-center.x(), 2) - pow(radius, 2);
    return solveQuadraticEcuation(a, b, c, sign);
  }

  private float calcX(StraightLine line, int sign) {
    float s = line.getVerticalOffset() - center.y();
    double a = 1 + pow(line.getSlope(), 2);
    double b = 2 * (line.getSlope() * s - center.x());
    double c = pow(center.x(), 2) + pow(s, 2) - pow(radius, 2);
    return solveQuadraticEcuation(a, b, c, sign);
  }

  private float solveQuadraticEcuation(double a, double b, double c, int sign) {
    var radical = b*b - 4*a*c;
    if (radical < 0) {
      return Float.NaN;
    }
    return (float) ((-b + sign*sqrt(radical)) / (2 * a));
  }
}
