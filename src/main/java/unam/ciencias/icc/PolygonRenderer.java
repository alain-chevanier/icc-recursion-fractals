package unam.ciencias.icc;

import java.awt.Graphics2D;

import static java.lang.Math.round;

public class PolygonRenderer implements ShapeRenderer<Polygon> {

  @Override
  public void render(Polygon shape, Point2D upperLeftCorner, Graphics2D graphics) {
    shape.getEdges()
      .forEach(lineSegment -> {
          int x1 = fitXInCanvas(lineSegment.beg(), upperLeftCorner),
            y1 = fitYInCanvas(lineSegment.beg(), upperLeftCorner),
            x2 = fitXInCanvas(lineSegment.end(), upperLeftCorner),
            y2 = fitYInCanvas(lineSegment.end(), upperLeftCorner);
          graphics.drawLine(x1, y1, x2, y2);
        });
  }

  private int fitXInCanvas(Point2D point, Point2D upperLeftCorner) {
    return round(point.x() - upperLeftCorner.x());
  }

  private int fitYInCanvas(Point2D point, Point2D upperLeftCorner) {
    return round(point.y() - upperLeftCorner.y());
  }
}
