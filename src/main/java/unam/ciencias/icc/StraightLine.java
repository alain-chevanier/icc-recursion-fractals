package unam.ciencias.icc;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.abs;

/**
 * a*x + b*y = c
 * y = -(a/b)*x + c/b
 * x = - (b/a)*y + c/a
 * -> slope: -(a/b)
 *
 * p.x*a + p.y*b = c
 * q.x*a + q.y*b = c
 */
@ToString
@Getter
public class StraightLine {
  private static final float EPSILON = 0.01f;
  private final float a;
  private final float b;
  private final float c;

  public StraightLine(float a, float b, float c) {
    if (abs(a) < EPSILON && abs(b) < EPSILON) {
      throw new IllegalArgumentException("at least a or b must be different of zero");
    }
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public static StraightLine build(int slopeNumerator,
                                   int slopeDenominator,
                                   float verticalOffset) {
    return build(slopeNumerator, slopeDenominator, new Point2D(0, verticalOffset));
  }

  public static StraightLine build(int slopeNumerator,
                                   int slopeDenominator,
                                   Point2D p) {
    // y = (p/q)*x + k
    // => -(p/q)*x + y = k
    // => -p*x + q*y = k*q
    float kTimeQ = (slopeDenominator*p.y() - slopeNumerator*p.x());
    return new StraightLine(-slopeNumerator, slopeDenominator, kTimeQ);
  }

  public static StraightLine build(Point2D a, Point2D b) {
    var ab = new LineSegment(a, b);
    return build(ab.getSlope(), b);
  }

  public static StraightLine build(float slope, Point2D p) {
    // y = m*x + k
    // => k = y - m*x;
    // -m*x + y = k
    float k = p.y() - slope*p.x();
    return new StraightLine(-slope, 1, k);
  }

  public float getSlope() {
    if (abs(this.b) < EPSILON) {
      return Float.MAX_VALUE;
    }
    return - this.a / this.b;
  }

  public float calculateY(float x) {
    return (-a*x + c) / b;
  }

  public float calculateX(float y) {
    return (-b*y + c) / a;
  }

  public Optional<Point2D> intersect(StraightLine line) {
    float determinant = this.a * line.b - this.b * line.a;

    if (abs(determinant) < EPSILON) {
      return Optional.empty();
    }

    float determinantX = this.c * line.b - line.c * this.b;
    float determinantY = this.a * line.c - line.a * this.c;

    return Optional.of(new Point2D(determinantX / determinant,
                                   determinantY / determinant));
  }

  public StraightLine getOrthogonalAtPoint(Point2D p) {
    if (this.isVertical()) {
      // a*x + b*y = c
      // x = c/a
      // y = p.y => 0*x + 1*y = p.y
      return new StraightLine(0, 1, p.y());
    }
    if (this.isHorizontal()) {
      // a*x + b*y = c
      // y = c/b
      // x = p.x => 1*x + 0*y = p.x
      return new StraightLine(1, 0, p.x());
    }
    float slope = -1/getSlope();
    return build(slope, p);
  }

  public float getVerticalOffset() {
    return c / b;
  }

  public float getDistance(Point2D p) {
    var orthogonalAtPoint = getOrthogonalAtPoint(p);
    var intersection = intersect(orthogonalAtPoint);
    return new LineSegment(intersection.get(), p).getLength();

  }

  public boolean isParallel(StraightLine line) {
    float determinant = this.a * line.b - this.b * line.a;
    return abs(determinant) >= EPSILON;
  }

  public boolean isVertical() {
    return abs(this.b) < EPSILON;
  }

  public boolean isHorizontal() {
    return abs(this.a) < EPSILON;
  }
}
