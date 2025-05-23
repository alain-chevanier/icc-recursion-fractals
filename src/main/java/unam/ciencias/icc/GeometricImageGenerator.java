package unam.ciencias.icc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static java.lang.Math.ceil;
import static java.lang.Math.abs;

public class GeometricImageGenerator<T extends Shape>
    implements ImageGenerator<T> {
  private Color backgroundColor;
  private Color lineColor;
  private int padding;
  private ShapeRenderer<T> renderer;

  public GeometricImageGenerator(ShapeRenderer<T> renderer) {
    this.padding = 20;
    this.backgroundColor = Color.WHITE;
    this.lineColor = Color.BLUE;
    this.renderer = renderer;
  }

  @Override
  public BufferedImage paint(T shape) {
    var upperLeftCorner = calculateUpperLeftCorner(shape.getVertices());
    var lowerRightCorner = calculateLowerRightCorner(shape.getVertices());
    var bufferedImage = getCanvas(upperLeftCorner, lowerRightCorner);
    var graphics = getGraphics(bufferedImage);
    var renderingStartingPoint = calculateRenderingStartingPoint(upperLeftCorner);
    renderer.render(shape, renderingStartingPoint, graphics);
    return bufferedImage;
  }

  @Override
  public BufferedImage paint(List<T> shapes) {
    var allVertices = collectVertices(shapes);
    var upperLeftCorner = calculateUpperLeftCorner(allVertices);
    var lowerRightCorner = calculateLowerRightCorner(allVertices);
    var bufferedImage = getCanvas(upperLeftCorner, lowerRightCorner);
    var graphics = getGraphics(bufferedImage);
    var renderingStartingPoint = calculateRenderingStartingPoint(upperLeftCorner);
    shapes.forEach(s -> renderer.render(s, renderingStartingPoint, graphics));
    return bufferedImage;
  }

  private List<Point2D> collectVertices(List<T> shapes) {
    return shapes.stream()
        .map(s -> s.getVertices())
        .flatMap(vs -> vs.stream())
        .collect(Collectors.toList());
  }

  private Point2D calculateUpperLeftCorner(List<Point2D> vertices) {
    float minX = vertices.stream()
                         .min((v0, v1) -> Float.compare(v0.x(), v1.x()))
                         .map(v -> v.x())
                         .orElseThrow(),
          minY = vertices.stream()
                         .min((v0, v1) -> Float.compare(v0.y(), v1.y()))
                         .map(v -> v.y())
                         .orElseThrow();
    return new Point2D(minX, minY);
  }

  private Point2D calculateLowerRightCorner(List<Point2D> vertices) {
    float maxX = vertices.stream()
                         .max((v0, v1) -> Float.compare(v0.x(), v1.x()))
                         .map(v -> v.x())
                         .orElseThrow(),
          maxY = vertices.stream()
                         .max((v0, v1) -> Float.compare(v0.y(), v1.y()))
                         .map(v -> v.y())
                         .orElseThrow();
    return new Point2D(maxX, maxY);
  }

  private Point2D calculateRenderingStartingPoint(Point2D upperLeftCorner) {
    return new Point2D(upperLeftCorner.x() - padding,
                       upperLeftCorner.y() - padding);
  }

  private BufferedImage getCanvas(Point2D upperLeftCorner, Point2D lowerRightCorner) {
    int width = (int) round(ceil(abs(lowerRightCorner.x() - upperLeftCorner.x())))
                      + padding*2,
        height = (int) round(ceil(abs(lowerRightCorner.y() - upperLeftCorner.y())))
                       + padding*2;

    var image = new Image(width, height, this.backgroundColor);
    return Image.buildBufferedImage(image);
  }

  private Graphics2D getGraphics(BufferedImage image) {
    var graphics = image.createGraphics();
    graphics.setColor(this.lineColor);
    return graphics;
  }
}
