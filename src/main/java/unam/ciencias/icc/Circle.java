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

  public Optional<Point2D> intercept(StraightLine line, int sign) {
    float x, y;
    if (line.isVertical()) {
      // x is the same for any y
      x = line.calculateX(0);
      y = this.calculateY(x, sign);
    } else {
      x = this.intersectX(line, sign);
      y = line.calculateY(x);
    }
    // this means distance to line is greater than radius
    if (Float.isNaN(x) || Float.isNaN(y)) {
      return Optional.empty();
    }
    return Optional.of(new Point2D(x, y));
  }

  public float calculateY(float x, int sign) {
    // (x-c_x)^2 + (y-c_y)^2 = r^2
    // (y-c_y)^2 = r^2 - (x-c_x)^2
    // y^2 - 2*c_y*y + c_y^2 + (x-c_x)^2  - r^2 =  0
    double a = 1f;
    double b = -2*center.y();
    double c = pow(center.y(), 2) + pow(x-center.x(), 2) - pow(radius, 2);
    return solveQuadraticEcuation(a, b, c, sign);
  }

  private float intersectX(StraightLine line, int sign) {
    // line equation: a*x + b*y = c
    // => y = -(a/b)*x + (c/b)
    // => let m be the slope `-(a/b)`
    // => let k be the vertical offset `(c/b)`
    // => y = m*x + k
    //
    // circle equation: (x - x_c)^2 + (y - y_c)^2 = r^2
    // If we replace `y` by `m*x + k` we get:
    // => (x - x_c)^2 + (m*x + k - y_c)^2 = r^2
    // let `s` be `k - y_c`
    // => (x - x_c)^2 + (m*x + s)^2 = r^2
    // => x^2 - 2*x_c*x + x_c^2 + m^2*x^2 + 2*m*s*x + s^2 - r^2 = 0
    // => [1 + m^2]x^2 + [2(m*s - x_c)]x + [x_c^2  + s^2 - r^2] = 0
    //
    // Now we use solve the quadratic equation using the general solution
    // let `a` be `1 + m^2`
    //     `b` be `2(m*s - x_c)`
    //     `c` be `x_c^2  + s^2 - r^2`
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
