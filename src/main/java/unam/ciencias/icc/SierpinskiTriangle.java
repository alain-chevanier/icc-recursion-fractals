package unam.ciencias.icc;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class SierpinskiTriangle implements Shape {

  private List<Polygon> triangles;

  @Override
  public List<Point> getVertices() {
    return this.triangles.stream()
        .map(p -> p.getVertices())
        .flatMap(v -> v.stream())
        .collect(Collectors.toList());
  }
}
