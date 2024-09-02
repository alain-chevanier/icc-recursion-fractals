package unam.ciencias.icc;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

@Getter
@ToString
@EqualsAndHashCode
public class LineSegment implements Shape {
  private final Point2D beg;
  private final Point2D end;
  private final float slope;
  private final float length;

  public LineSegment(Point2D beg, Point2D end) {
    this.beg = beg;
    this.end = end;
    this.slope = calcSlope();
    this.length = calcLength();
  }

  public Point2D beg() {
    return this.beg;
  }

  public Point2D end() {
    return this.end;
  }

  @Override
  public List<Point2D> getVertices() {
    return List.of(beg, end);
  }

  public Point2D getInnerPoint(float k) {
    var a = beg;
    var b = end;
    return new Point2D(getInnerNumber(a.x(), b.x(), k),
                       getInnerNumber(a.y(), b.y(), k));
  }

  public Point2D getInnerPoint(int p, int q) {
    var a = beg;
    var b = end;
    return new Point2D(getInnerNumber(a.x(), b.x(), p, q),
                       getInnerNumber(a.y(), b.y(), p, q));
  }

  public boolean isHorizontal() {
    return Math.abs(beg.y() - end.y()) < 0.01;
  }

  public boolean isVertical() {
    return Math.abs(beg.x() - end.x()) < 0.01;
  }

  private float getInnerNumber(float a, float b, float k) {
    return k*b + (1-k)*a;
  }

  private float getInnerNumber(float a, float b, int p, int q) {
    return (p*b + (q-p)*a) / q;
  }

  private float calcLength() {
    var xs = end.x() - beg.x();
    var ys = end.y() - beg.y();
    return (float) sqrt(pow(xs, 2) + pow(ys, 2));
  }

  private float calcSlope() {
    var a = beg;
    var b = end;
    if (b.x() == a.x()) {
      return Float.MAX_VALUE;
    }
    return (b.y() - a.y()) / (b.x() - a.x());
  }
}
