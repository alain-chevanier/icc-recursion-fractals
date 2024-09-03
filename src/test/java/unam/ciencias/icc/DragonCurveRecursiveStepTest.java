package unam.ciencias.icc;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static unam.ciencias.icc.ShapeTestUtils.sort;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class DragonCurveRecursiveStepTest {
  DragonCurveRecursiveStep recursiveStep;

  @BeforeEach
  void setup() {
    recursiveStep = new DragonCurveRecursiveStep();
  }

  @Test
  public void test_Horizontal_LeftRightOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(3f, -3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Horizontal_RightLeftOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(3f, 3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Vertical_BottomUpOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(0f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(3f, 3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_Vertical_TopDownOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(0f, 6f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(-3f, 3f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_PositiveSlope_LeftRightOrder_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(6f, 4f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(5f, -1f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_PositiveSlope_rightLeftOrder_Case() {
    var b = new Point2D(0f, 0f);
    var a = new Point2D(6f, 4f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(1f, 5f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_NegativeSlope_leftRightOrder_Case() {
    var a = new Point2D(0f, 4f);
    var b = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(1f, -1f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

  @Test
  public void test_NegativeSlope_RightLeftOrder_Case() {
    var b = new Point2D(0f, 4f);
    var a = new Point2D(6f, 0f);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(5f, 5f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }

   @Test
  public void test_Edge_Case() {
    var a = new Point2D(0f, 0f);
    var b = new Point2D(5.1106326E-8f, -400f);

    System.out.println(Math.abs(a.x() - b.x()) < 0.01);
    var lineSegment = new LineSegment(a, b);

    var actualOutput = recursiveStep.apply(lineSegment);

    var c = new Point2D(-200f, -200f);
    var expectedOutput = List.of(new LineSegment(a, c),
                                 new LineSegment(b, c));

    assertThat(sort(actualOutput), is(equalTo(sort(expectedOutput))));
  }


}
