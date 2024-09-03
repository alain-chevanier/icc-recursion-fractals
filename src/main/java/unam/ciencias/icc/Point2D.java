package unam.ciencias.icc;

import static java.lang.Math.abs;

public record Point2D (float x, float y) implements Comparable<Point2D> {
  @Override
  public int compareTo(Point2D other) {
    if (abs(this.x - other.x) < 0.01 ) {
      return Float.compare(this.y, other.y);
    }
    return Float.compare(this.x, other.x);
  }
}
