import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class FastCollinearPointsTest {
  
  FastCollinearPoints fcp;
  @Before
  public void setUp() throws Exception {
    fcp = generateFCP("input10.txt");
  }
  
  private FastCollinearPoints generateFCP(String filename) {
    In in = new In("collinear-test-files/" + filename);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    } 
    return new FastCollinearPoints(points);
  }

  @Test
  public void testNumberOfSegments() {
    assertEquals(2, fcp.numberOfSegments());
  }

  @Test
  public void testSegments() {
    LineSegment[] test = fcp.segments();
    LineSegment a = new LineSegment(new Point(28000,13500), new Point(3000, 26000));
    assertEquals("Line A", a.toString(), test[0].toString());
    LineSegment b = new LineSegment(new Point(1000,18000), new Point(4000, 30000));
    assertEquals("Line B", b.toString(), test[1].toString());
  }
  @Test (timeout = 500, expected = IllegalArgumentException.class)
  public void testNull() {
    Point[] points = new Point[3];
    points[0] = new Point(1,2);
    points[1] = new Point(2,2);
    points[2] = null;
    fcp = new FastCollinearPoints(points);
  }
  @Test (timeout = 500, expected = IllegalArgumentException.class)
  public void testRepeat() {
    Point[] points = new Point[3];
    points[0] = new Point(1,2);
    points[1] = new Point(2,2);
    points[2] = new Point(1,2);
    fcp = new FastCollinearPoints(points);
  }

}
