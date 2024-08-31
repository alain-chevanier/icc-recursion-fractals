package unam.ciencias.icc;

import java.awt.Graphics2D;

public class PolygonRenderer implements ShapeRenderer<Polygon> {

  private final LineSegmentRenderer lineSegmentRenderer;

  public PolygonRenderer(LineSegmentRenderer lineSegmentRenderer) {
    this.lineSegmentRenderer = lineSegmentRenderer;
  }

  @Override
  public void render(Polygon shape, Point2D upperLeftCorner, Graphics2D graphics) {
    shape.getEdges()
      .forEach(lineSegment -> lineSegmentRenderer.render(lineSegment, upperLeftCorner, graphics));
  }
}
