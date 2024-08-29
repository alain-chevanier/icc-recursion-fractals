package unam.ciencias.icc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

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

  public static void main(String[] a) {
    System.out.println("Doing WORK");

    var triangleHeight =  400 * (float) Math.sqrt(3);

    var triangle = new Polygon(new Point(0f, triangleHeight),
                               new Point(800f, triangleHeight),
                               new Point(400f, 0f));

    var recursiveStep = new SierpinskiTriangleRecursiveStep();
    var fractalGenerator = new FractalGenerator<Polygon>(recursiveStep);

    var fractal = fractalGenerator.generate(List.of(triangle), 5);
    System.out.println("Fractal structure generated");

    var polygonRenderer = new PolygonRenderer();
    var imageGenerator = new GeometricImageGenerator<>(polygonRenderer);
    var image = imageGenerator.paint(fractal);
    System.out.println("Done generating the image");

    persistImageToResources("fractal_image_1.png", ImageFormat.PNG, image);
    System.out.println("Done outputing the image");
  }
}
