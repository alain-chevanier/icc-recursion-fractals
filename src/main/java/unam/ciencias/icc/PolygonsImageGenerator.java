package unam.ciencias.icc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.round;
import static java.lang.Math.ceil;
import static java.lang.Math.abs;

public class PolygonsImageGenerator
    implements ImageGenerator<List<Polygon>> {

  private Color backgroundColor;
  private Color lineColor;

  public PolygonsImageGenerator() {
    this(Color.WHITE, Color.BLUE);
  }

  public PolygonsImageGenerator(Color background, Color line) {
    this.backgroundColor = background;
    this.lineColor = line;
  }

  @Override
  public BufferedImage paint(List<Polygon> polygons) {
    var upperLeftCorner = calculateUpperLeftCorner(polygons);
    var lowerRightCorner = calculateLowerRightCorner(polygons);
    var bufferedImage = getCanvas(upperLeftCorner, lowerRightCorner);
    var graphics = getGraphics(bufferedImage);
    draw(graphics, polygons, upperLeftCorner);
    return bufferedImage;
  }

  private void draw(Graphics graphics, List<Polygon> polygons, Point upperLeftCorner) {
    for (var polygon : polygons) {
      drawPolygon(graphics, polygon, upperLeftCorner);
    }
  }

  private void drawPolygon(Graphics graphics, Polygon polygon, Point upperLeftCorner) {
    for (var lineSegment : polygon.getEdges()) {
      int x1 = round(fitX(lineSegment.beg(), upperLeftCorner)),
          y1 = round(fitY(lineSegment.beg(), upperLeftCorner)),
          x2 = round(fitX(lineSegment.end(), upperLeftCorner)),
          y2 = round(fitY(lineSegment.end(), upperLeftCorner));
      graphics.drawLine(x1, y1, x2, y2);
    }
  }

  private Point calculateUpperLeftCorner(List<Polygon> polygons) {
    float minX = allPoints(polygons).min((v0, v1) -> Float.compare(v0.x(), v1.x()))
                                    .map(v -> v.x())
                                    .orElseThrow(),
          minY = allPoints(polygons).min((v0, v1) -> Float.compare(v0.y(), v1.y()))
                                    .map(v -> v.y())
                                    .orElseThrow();
    return new Point(minX, minY);
  }

  private Point calculateLowerRightCorner(List<Polygon> polygons) {
    float maxX = allPoints(polygons).max((v0, v1) -> Float.compare(v0.x(), v1.x()))
                                    .map(v -> v.x())
                                    .orElseThrow(),
          maxY = allPoints(polygons).max((v0, v1) -> Float.compare(v0.y(), v1.y()))
                                    .map(v -> v.y())
                                    .orElseThrow();
    return new Point(maxX, maxY);
  }

  private BufferedImage getCanvas(Point upperLeftCorner, Point lowerRightCorner) {
    int width = (int) round(ceil(abs(lowerRightCorner.x() - upperLeftCorner.x()))) + 2,
        height = (int) round(ceil(abs(lowerRightCorner.y() - upperLeftCorner.y()))) + 2;

    var image = new Image(width, height, this.backgroundColor);
    return Image.buildBufferedImage(image);
  }

  private Graphics getGraphics(BufferedImage image) {
    var graphics = image.getGraphics();
    graphics.setColor(this.lineColor);
    return graphics;
  }

  private Stream<Point> allPoints(List<Polygon> polygons) {
    return polygons.stream()
                   .flatMap(p -> p.getVertices().stream());
  }

  private float fitX(Point point, Point upperLeftCorner) {
    return point.x() - upperLeftCorner.x();
  }

  private float fitY(Point point, Point upperLeftCorner) {
    return point.y() - upperLeftCorner.y();
  }

}
