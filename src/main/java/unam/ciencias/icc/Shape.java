package unam.ciencias.icc;

import java.util.List;

public interface Shape extends Comparable<Shape> {
  List<Point2D> getVertices();

  default Point2D getLowestLeftMost() {
    return getVertices().stream()
                        .min((v0, v1) -> v0.compareTo(v1))
                        .orElseThrow();
  }

  default Point2D getHighestRightMost() {
    return getVertices().stream()
                        .max((v0, v1) -> v0.compareTo(v1))
                        .orElseThrow();
  }

  @Override
  default int compareTo(Shape other) {
    var myLowestLeftMost = this.getLowestLeftMost();
    var otherLowerLeftMost = other.getLowestLeftMost();
    int compareLowestLeftMost = myLowestLeftMost.compareTo(otherLowerLeftMost);
    if (compareLowestLeftMost == 0) {
      var myHighestRightMost = this.getHighestRightMost();
      var otherHighestRightMost = other.getHighestRightMost();
      return myHighestRightMost.compareTo(otherHighestRightMost);
    }
    return compareLowestLeftMost;
  }
}
