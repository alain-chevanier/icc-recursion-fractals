package unam.ciencias.icc;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class SierpinskiTriangleGeneratorTest {
  @Test
  public void test_Case() {
    var triangleHeight =  400 * (float) Math.sqrt(3);
    var triangle = new Polygon(new Point(0f, triangleHeight),
                               new Point(800f, triangleHeight),
                               new Point(400f, 0f));
    var fractalGenerator = new SierpinskiTriangleGenerator();

    var actualOutput = fractalGenerator.generate(List.of(triangle), 0);

    var expectedOutput = new Polygon(new Point(0f, triangleHeight),
                                     new Point(800f, triangleHeight),
                                     new Point(400f, 0f));
    assertPolygonsAreSimilar(null, null);
  }

  void assertPolygonsAreSimilar(Polygon a, Polygon b) {

  }

  void assertPointsAreClose(Point a, Point b) {

  }
}
