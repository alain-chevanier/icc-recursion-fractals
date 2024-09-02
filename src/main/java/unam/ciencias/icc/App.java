package unam.ciencias.icc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class App {

  private static final String INPUT_DIR = "src/main/resources/image_processing/";
  private static final String OUTPUT_DIR = "src/main/resources/output_image_processing/";

  public static BufferedImage loadImageFromResources(String resourcePath) {
    try {
      String filePath = INPUT_DIR + resourcePath;
      File file = new File(filePath);
      return ImageIO.read(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void persistImageToResources(
      String resourcePath, ImageFormat format, BufferedImage image) {
    try {
      String destPath = OUTPUT_DIR + resourcePath;
      File file = new File(destPath);
      ImageIO.write(image, format.toString(), file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    System.out.println("Doing WORK");
    runSierpinskiTriangleExample(7);
    runKockSnowflakeExample(5);
    runDragonCurveExample(15);
  }

  static void runSierpinskiTriangleExample(int recursiveCalls) {
    var triangleHeight = 400 * (float) Math.sqrt(3);
    var a = new Point2D(0f, triangleHeight);
    var b = new Point2D(800f, triangleHeight);
    var c = new Point2D(400f, 0f);
    var triangle = new Polygon(a, b, c);

    var recursiveStep = new SierpinskiTriangleRecursiveStep();
    var fractalGenerator = new FractalGenerator<>(recursiveStep);

    var fractal = fractalGenerator.generate(List.of(triangle), recursiveCalls);
    System.out.println("ST: Fractal structure generated -> " + fractal.size());

    var lineSegmentRenderer = new LineSegmentRenderer();
    var polygonRenderer = new PolygonRenderer(lineSegmentRenderer);
    var imageGenerator = new GeometricImageGenerator<>(polygonRenderer);
    var image = imageGenerator.paint(fractal);
    System.out.println("ST: Done generating the image");

    persistImageToResources("fractal_image_sierpinski_triangle.png", ImageFormat.PNG, image);
    System.out.println("ST: Done outputing the image");
  }

  static void runKockSnowflakeExample(int recursiveCalls) {
    var triangleHeight = 400 * (float) Math.sqrt(3);
    var a = new Point2D(0f, triangleHeight);
    var b = new Point2D(800f, triangleHeight);
    var c = new Point2D(400f, 0f);
    var ab = new LineSegment(a, b);
    var bc = new LineSegment(b, c);
    var ca = new LineSegment(c, a);

    var recursiveStep2 = new KochSnowflakeRecursiveStep();
    var fractalGenerator2 = new FractalGenerator<>(recursiveStep2);

    var fractal2 = fractalGenerator2.generate(List.of(ab, bc, ca), recursiveCalls);
    System.out.println("KS: Fractal structure generated -> " + fractal2.size());

    var lineSegmentRenderer = new LineSegmentRenderer();
    var imageGenerator2 = new GeometricImageGenerator<>(lineSegmentRenderer);
    var image2 = imageGenerator2.paint(fractal2);
    System.out.println("KS: Done generating the image");

    persistImageToResources("fractal_image_koch_snowflake.png", ImageFormat.PNG, image2);
    System.out.println("KS: Done outputing the image");
  }

  static void runDragonCurveExample(int recursiveCalls) {
    var d = new Point2D(0f, 800f);
    var e = new Point2D(800f, 0f);
    var de = new LineSegment(d, e);

    var recursiveStep3 = new DragonCurveRecursiveStep();
    var fractalGenerator3 = new FractalGenerator<>(recursiveStep3);

    var fractal3 = fractalGenerator3.generate(List.of(de), recursiveCalls);
    System.out.println("DC: Fractal structure generated -> " + fractal3.size());

    var lineSegmentRenderer = new LineSegmentRenderer();
    var imageGenerator2 = new GeometricImageGenerator<>(lineSegmentRenderer);
    var image3 = imageGenerator2.paint(fractal3);
    System.out.println("DC: Done generating the image");

    persistImageToResources("fractal_image_drago_curve.png", ImageFormat.PNG, image3);
    System.out.println("DC: Done outputing the image");
  }
}
