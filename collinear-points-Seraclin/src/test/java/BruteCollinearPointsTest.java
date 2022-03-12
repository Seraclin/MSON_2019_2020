import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import org.junit.rules.ExpectedException;

import javax.sound.sampled.Line;

public class BruteCollinearPointsTest {
  
  BruteCollinearPoints bcp;
  @Before
  public void setUp() throws Exception {
    Point[] points = new Point[7];
    points[0] = new Point(1,1);
    points[1] = new Point(2,2);
    points[2] = new Point(3,3);
    points[3] = new Point(4,4);
    points[4] = new Point(1,3);
    points[5] = new Point(1,4);
    points[6] = new Point(1,6);

    bcp = new BruteCollinearPoints(points);
  }

  @Test
  public void testNumberOfSegments() {
    assertEquals(2, bcp.numberOfSegments());
  }

  @Test
  public void testSegments() {
    LineSegment[] test = bcp.segments();
    LineSegment a = new LineSegment(new Point(1,1), new Point(4, 4));
    assertEquals(a.toString(), test[0].toString());
    LineSegment b = new LineSegment(new Point(1,1), new Point(1, 6));
    assertEquals(b.toString(), test[1].toString());
  }

  @Test (timeout = 500, expected = IllegalArgumentException.class)
  public void testNull() {
    Point[] points = new Point[3];
    points[0] = new Point(1,2);
    points[1] = new Point(2,2);
    points[2] = null;
    bcp = new BruteCollinearPoints(points);
  }
  @Test (timeout = 500, expected = IllegalArgumentException.class)
  public void testRepeat() {
    Point[] points = new Point[3];
    points[0] = new Point(1,2);
    points[1] = new Point(2,2);
    points[2] = new Point(1,2);
    bcp = new BruteCollinearPoints(points);
  }
}
