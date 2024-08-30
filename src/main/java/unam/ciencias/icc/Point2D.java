package unam.ciencias.icc;

public record Point2D (float x, float y) implements Comparable<Point2D> {
  @Override
  public int compareTo(Point2D other) {
    if (this.x == other.x) {
      return Float.compare(this.y, other.y);
    }
    return Float.compare(this.x, other.x);
  }
}
