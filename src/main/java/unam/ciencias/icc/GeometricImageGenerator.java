package unam.ciencias.icc;

import java.awt.Color;
import java.awt.Graphics;
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
  private ShapeRenderer<T> renderer;


  public GeometricImageGenerator(ShapeRenderer<T> renderer) {
    this.backgroundColor = Color.WHITE;
    this.lineColor = Color.BLUE;
    this.renderer = renderer;
  }

  @Override
  public BufferedImage paint(List<T> shapes) {
    var allVertices = collectVertices(shapes);
    var upperLeftCorner = calculateUpperLeftCorner(allVertices);
    var lowerRightCorner = calculateLowerRightCorner(allVertices);
    var bufferedImage = getCanvas(upperLeftCorner, lowerRightCorner);
    var graphics = getGraphics(bufferedImage);
    shapes.forEach(s -> renderer.render(s, upperLeftCorner, graphics));
    return bufferedImage;
  }

  @Override
  public BufferedImage paint(T shape) {
    var upperLeftCorner = calculateUpperLeftCorner(shape.getVertices());
    var lowerRightCorner = calculateLowerRightCorner(shape.getVertices());
    var bufferedImage = getCanvas(upperLeftCorner, lowerRightCorner);
    var graphics = getGraphics(bufferedImage);
    renderer.render(shape, upperLeftCorner, graphics);
    return bufferedImage;
  }

  private List<Point> collectVertices(List<T> shapes) {
    return shapes.stream()
        .map(s -> s.getVertices())
        .flatMap(vs -> vs.stream())
        .collect(Collectors.toList());
  }

  private Point calculateUpperLeftCorner(List<Point> vertices) {
    float minX = vertices.stream()
                         .min((v0, v1) -> Float.compare(v0.x(), v1.x()))
                         .map(v -> v.x())
                         .orElseThrow(),
          minY = vertices.stream()
                         .min((v0, v1) -> Float.compare(v0.y(), v1.y()))
                         .map(v -> v.y())
                         .orElseThrow();
    return new Point(minX, minY);
  }

  private Point calculateLowerRightCorner(List<Point> vertices) {
    float maxX = vertices.stream()
                         .max((v0, v1) -> Float.compare(v0.x(), v1.x()))
                         .map(v -> v.x())
                         .orElseThrow(),
          maxY = vertices.stream()
                         .max((v0, v1) -> Float.compare(v0.y(), v1.y()))
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
}
