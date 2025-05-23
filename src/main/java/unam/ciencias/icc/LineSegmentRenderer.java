package unam.ciencias.icc;

import java.awt.Graphics2D;

import static java.lang.Math.round;

public class LineSegmentRenderer implements ShapeRenderer<LineSegment> {
  @Override
  public void render(LineSegment lineSegment, Point2D upperLeftCorner, Graphics2D graphics) {
    int x1 = fitXInCanvas(lineSegment.beg(), upperLeftCorner),
      y1 = fitYInCanvas(lineSegment.beg(), upperLeftCorner),
      x2 = fitXInCanvas(lineSegment.end(), upperLeftCorner),
      y2 = fitYInCanvas(lineSegment.end(), upperLeftCorner);
    graphics.drawLine(x1, y1, x2, y2);
  }

  private int fitXInCanvas(Point2D point, Point2D upperLeftCorner) {
    return round(point.x() - upperLeftCorner.x());
  }

  private int fitYInCanvas(Point2D point, Point2D upperLeftCorner) {
    return round(point.y() - upperLeftCorner.y());
  }
}
