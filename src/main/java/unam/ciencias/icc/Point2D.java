package unam.ciencias.icc;

import static java.lang.Math.abs;

public record Point2D (float x, float y) implements Comparable<Point2D> {
  public static final float EPSILON = 0.01f;
  public static final float ROUNDING_FACTOR = 100f;


  public Point2D (float x, float y) {
    float roundedX = Math.round(x * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    float roundedY = Math.round(y * ROUNDING_FACTOR) / ROUNDING_FACTOR;

    this.x = roundedX;
    this.y = roundedY;
  }

  @Override
  public int compareTo(Point2D other) {
    if (abs(this.x - other.x) < 0.01 ) {
      return Float.compare(this.y, other.y);
    }
    return Float.compare(this.x, other.x);
  }
}
