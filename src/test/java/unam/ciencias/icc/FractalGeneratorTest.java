package unam.ciencias.icc;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class FractalGeneratorTest {
  @Mock
  FractalRecursiveStep<LineSegment> recursiveStepMock;

  @InjectMocks
  FractalGenerator<LineSegment> fractalGenerator;

  @Test
  public void test_NoCalls() {
    var ab = new LineSegment(new Point2D(0f, 0f),
                             new Point2D(100f, 100f));

    var actualOutput = fractalGenerator.generate(List.of(ab), 0);

    var expectedOutput = List.of(ab);
    assertThat(actualOutput, is(equalTo(expectedOutput)));
    verify(recursiveStepMock, times(0)).apply(any());
  }

  @Test
  public void test_RecursionTree() {
    when(recursiveStepMock.apply(any(LineSegment.class)))
        .thenAnswer(i -> split(i.getArgument(0)));

    var ab = new LineSegment(new Point2D(0f, 0f),
                             new Point2D(100f, 100f));

    int recursiveIterations = 10;

    var actualOutput = fractalGenerator.generate(List.of(ab), recursiveIterations);

    assertThat(actualOutput.size(), is(equalTo(1024)));
    verify(recursiveStepMock, times(1023)).apply(any(LineSegment.class));
  }

  List<LineSegment> split(LineSegment segment) {
    var a = segment.beg();
    var b = segment.end();
    var middlePoint = segment.getInnerPoint(1, 2);
    return List.of(new LineSegment(a, middlePoint),
                   new LineSegment(middlePoint, b));
  }
}
