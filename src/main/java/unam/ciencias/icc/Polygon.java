package unam.ciencias.icc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Polygon {
  private List<Point> vertices;

  /**
   * This creates a polygon using the provided vertices,
   * and asumes that there will be and edge for consecutive
   * vertices. For examples if we have a triangule with vertices
   * v_0, v_1, v_2, then there will be the edges:
   * E(v_0, v_1), E(v_1, v_2), E(v_2, v_0).
   */
  public Polygon(Point... vertices) {
    this.vertices = new ArrayList<>();
    for (var p : vertices) {
      this.vertices.add(p);
    }
  }

  public Point getVertex(int index) {
    return vertices.get(index);
  }

  /**
   * Edges are numbered from 0 to |vertices| - 1.
   * And the i-th edge contains the vertices
   * v_i and v_(i+1).
   */
  public LineSegment getEdge(int index) {
    var beg = vertices.get(index);
    var end = vertices.get((index + 1) % this.getVertices().size());
    return new LineSegment(beg, end);
  }

  public List<LineSegment> getEdges() {
    return IntStream.range(0, this.vertices.size())
                    .boxed()
                    .map(index -> this.getEdge(index))
                    .collect(Collectors.toList());
  }
}
