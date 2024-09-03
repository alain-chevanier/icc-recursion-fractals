package unam.ciencias.icc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class App {

  private static final String INPUT_DIR = "src/main/resources/image_processing/";
  private static final String OUTPUT_DIR = "src/main/resources/output_image_processing/";
  private static final boolean GENERATE_INTERMADIATE_IMAGE = false;

  public static void main(String[] args) {
    System.out.println("Doing WORK");
    runSierpinskiTriangleExample(6);
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

    var input = List.of(triangle);
    var lineSegmentRenderer = new LineSegmentRenderer();
    var polygonRenderer = new PolygonRenderer(lineSegmentRenderer);
    var imageGenerator = new GeometricImageGenerator<>(polygonRenderer);

     var image = imageGenerator.paint(input);
     persistImageToResources(fractalGenerator.getRecursiveStep().getClass().getSimpleName() +
                             "_0.png", ImageFormat.PNG, image);
     doIterations(recursiveCalls, input, fractalGenerator, imageGenerator);

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

    var lineSegmentRenderer = new LineSegmentRenderer();
    var imageGenerator2 = new GeometricImageGenerator<>(lineSegmentRenderer);

    var input = List.of(ab, bc, ca);
    var image2 = imageGenerator2.paint(input);
    persistImageToResources(fractalGenerator2.getRecursiveStep().getClass().getSimpleName() +
                            "_0.png", ImageFormat.PNG, image2);
    doIterations(recursiveCalls, input, fractalGenerator2, imageGenerator2);
  }

  static void runDragonCurveExample(int recursiveCalls) {
    var d = new Point2D(0f, 800f);
    var e = new Point2D(800f, 0f);
    var de = new LineSegment(d, e);

    var recursiveStep3 = new DragonCurveRecursiveStep();
    var fractalGenerator3 = new FractalGenerator<>(recursiveStep3);

    var lineSegmentRenderer = new LineSegmentRenderer();
    var imageGenerator2 = new GeometricImageGenerator<>(lineSegmentRenderer);

    var input = List.of(de);
    var image3 = imageGenerator2.paint(input);
    persistImageToResources(fractalGenerator3.getRecursiveStep().getClass().getSimpleName() +
                            "_0.png", ImageFormat.PNG, image3);

    doIterations(recursiveCalls, input, fractalGenerator3, imageGenerator2);
  }

  static <T extends Shape> void doIterations(int recursiveCalls,
                                             List<T> input,
                                             FractalGenerator<T> fractalGenerator,
                                             ImageGenerator<T> imageGenerator) {
    String className = fractalGenerator.getRecursiveStep().getClass().getSimpleName();
    for (int iteration = 1; iteration <= recursiveCalls; iteration++) {
      var fractal = fractalGenerator.generate(input, 1);
      input = fractal;
      var image = imageGenerator.paint(fractal);

      if (GENERATE_INTERMADIATE_IMAGE || iteration == recursiveCalls) {
        persistImageToResources(className + "_" +
                                iteration + ".png", ImageFormat.PNG, image);
      }

    }
  }

  static BufferedImage loadImageFromResources(String resourcePath) {
    try {
      String filePath = INPUT_DIR + resourcePath;
      File file = new File(filePath);
      return ImageIO.read(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static void persistImageToResources(String resourcePath,
                                      ImageFormat format,
                                      BufferedImage image) {
    try {
      String destPath = OUTPUT_DIR + resourcePath;
      File file = new File(destPath);
      ImageIO.write(image, format.toString(), file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
