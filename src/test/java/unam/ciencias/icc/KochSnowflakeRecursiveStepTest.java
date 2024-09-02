package unam.ciencias.icc;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static unam.ciencias.icc.ShapeTestUtils.sort;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class KochSnowflakeRecursiveStepTest {
  KochSnowflakeRecursiveStep recursiveStep;

  @BeforeEach
  void setup() {
    recursiveStep = new KochSnowflakeRecursiveStep();
  }

  @Test
  public void test_Horizontal_leftRightOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(2f, 0f);
    var d = new Point2D(4f, 0f);
    var f = new Point2D(3f, (float) Math.sqrt(3));
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Horizontal_rightLeftOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var d = new Point2D(2f, 0f);
    var c = new Point2D(4f, 0f);
    var f = new Point2D(3f, (float) -Math.sqrt(3));
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Vertical_BottomUpOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(0f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(0f, 2f);
    var d = new Point2D(0f, 4f);
    var f = new Point2D((float) -Math.sqrt(3), 3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Vertical_TopDownOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(0f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var d = new Point2D(0f, 2f);
    var c = new Point2D(0f, 4f);
    var f = new Point2D((float) Math.sqrt(3), 3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }



  @Test
  public void test_positiveSlope_leftRightOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(6f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(2f, 2f);
    var d = new Point2D(4f, 4f);
    var x_f = (3f - (float)Math.sqrt(3));
    var y_f = 6 - x_f;
    var f = new Point2D(x_f, y_f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_negativeSlope_leftRightOrder_Case() {
    var a = new Point2D(0f, 6f);
    var b = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(2f, 4f);
    var d = new Point2D(4f, 2f);
    var x_f = (3f + (float)Math.sqrt(3));
    var y_f = x_f;
    var f = new Point2D(x_f, y_f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_positiveSlope_rightLeftOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(6f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var d = new Point2D(2f, 2f);
    var c = new Point2D(4f, 4f);
    var x_f = (3f + (float)Math.sqrt(3));
    var y_f = 6 - x_f;
    var f = new Point2D(x_f, y_f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_negativeSlope_rightLeftOrder_Case() {
    var b = new Point2D(0f, 6f);
    var a = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var d = new Point2D(2f, 4f);
    var c = new Point2D(4f, 2f);
    var x_f = (3f - (float)Math.sqrt(3));
    var y_f = x_f;
    var f = new Point2D(x_f, y_f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(c, f),
                                 new LineSegment(f, d),
                                 new LineSegment(d, b));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }
}
