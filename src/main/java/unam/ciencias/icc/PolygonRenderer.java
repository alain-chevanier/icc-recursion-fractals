package unam.ciencias.icc;

import java.awt.Graphics;

import static java.lang.Math.round;

public class PolygonRenderer implements ShapeRenderer<Polygon> {

  @Override
  public void render(Polygon shape, Point upperLeftCorner, Graphics graphics) {
    shape.getEdges()
      .forEach(lineSegment -> {
          int x1 = fitXInCanvas(lineSegment.beg(), upperLeftCorner),
            y1 = fitYInCanvas(lineSegment.beg(), upperLeftCorner),
            x2 = fitXInCanvas(lineSegment.end(), upperLeftCorner),
            y2 = fitYInCanvas(lineSegment.end(), upperLeftCorner);
          graphics.drawLine(x1, y1, x2, y2);
        });
  }

  private int fitXInCanvas(Point point, Point upperLeftCorner) {
    return round(point.x() - upperLeftCorner.x());
  }

  private int fitYInCanvas(Point point, Point upperLeftCorner) {
    return round(point.y() - upperLeftCorner.y());
  }
}
