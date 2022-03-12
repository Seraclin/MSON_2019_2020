import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class PointSetTest {

  PointSET ps;
  @Before
  public void setUp() throws Exception {
    ps = new PointSET();
  }

  @Test
  public void testIsEmpty() {
    assertTrue("Should start empty", ps.isEmpty());
  }

  @Test
  public void testSize() {
    assertEquals("Should be empty", 0, ps.size());
  }

  @Test
  public void testInsert() {
    assertTrue("Should start empty", ps.isEmpty());
    Point2D test = new Point2D(.1, .1);
    ps.insert(test);
    assertEquals("Should have one element", 1, ps.size());
    Point2D test2 = new Point2D(.1, .2);
    ps.insert(test2);
    assertEquals("Should have two elements", 2, ps.size());
  }

  @Test
  public void testContains() {
    Point2D test = new Point2D(.1, .1);
    assertFalse("Does not contain the point", ps.contains(test));
    ps.insert(test);
    assertTrue("Does contain the point", ps.contains(test));
  }

  @Test
  public void testRange() {
    RectHV rect = new RectHV(0, 0, .5, .5);
    ps.insert(new Point2D(.5, .5));
    ps.insert(new Point2D(.1, .9)); // outside
    ps.insert(new Point2D(.2, .3));
    ps.insert(new Point2D(0, 0));
    ps.insert(new Point2D(1, 1)); // outside of rect
    ps.insert(new Point2D(0, .50));
    ps.insert(new Point2D(.50, 0));


    ArrayList<Point2D> expected = new ArrayList<>();
    expected.add(new Point2D(.5, .5));
    expected.add(new Point2D(.2, .3));
    expected.add(new Point2D(0, 0));
    expected.add(new Point2D(0, .50));
    expected.add(new Point2D(.50, 0));

    for (Point2D iter: ps.range(rect)) {
      assertTrue(expected.contains(iter));
    }
  }

  @Test
  public void testNearest() {
    ps.insert(new Point2D(.1, .2));
    ps.insert(new Point2D(.1, .9));

    Point2D expected = new Point2D (.1,.2);
    Point2D test = new Point2D (.1,.1);
    assertEquals(expected, ps.nearest(test));

  }

  @Test
  public void testNearestNull() {
    Point2D test = new Point2D(.1,  .1);
    assertNull(ps.nearest(test));
  }

}
