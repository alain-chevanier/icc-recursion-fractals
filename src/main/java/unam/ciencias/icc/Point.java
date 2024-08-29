package unam.ciencias.icc;

public record Point (float x, float y) implements Comparable<Point> {
  @Override
  public int compareTo(Point other) {
    if (this.x == other.x) {
      return Float.compare(this.y, other.y);
    }
    return Float.compare(this.x, other.x);
  }
}
