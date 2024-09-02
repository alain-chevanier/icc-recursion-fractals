package unam.ciencias.icc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;

import static unam.ciencias.icc.ShapeTestUtils.sort;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class SierpinskiTriangleRecursiveStepTest {

  SierpinskiTriangleRecursiveStep recursiveStep;

  @BeforeEach
  void setup() {
    recursiveStep = new SierpinskiTriangleRecursiveStep();
  }

  @Test
  public void test_Case() {
    var triangleHeight =  400 * (float) Math.sqrt(3);
    var triangle = new Polygon(new Point2D(0f, 0f),
                               new Point2D(800f, 0f),
                               new Point2D(400f, triangleHeight));

    var actualOutput = recursiveStep.apply(triangle);

    var subTriangleHeight = 200 * (float) Math.sqrt(3);
    var expectedOutput = List.of(
        new Polygon(new Point2D(0f, 0f),
                    new Point2D(400f, 0f),
                    new Point2D(200f, subTriangleHeight)),
        new Polygon(new Point2D(400f, 0f),
                    new Point2D(800f, 0f),
                    new Point2D(600f, subTriangleHeight)),
        new Polygon(new Point2D(200f, subTriangleHeight),
                    new Point2D(600f, subTriangleHeight),
                    new Point2D(400f, triangleHeight)));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }
}
