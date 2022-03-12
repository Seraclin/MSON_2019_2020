import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class KdTreeTest {
  KdTree kd;
  @Before
  public void setUp() throws Exception {
    kd = new KdTree();
  }

  @Test
  public void testIsEmpty() {
    assertTrue("Should start empty", kd.isEmpty());
  }

  @Test
  public void testSize() {
    assertEquals("Should be empty", 0, kd.size());
  }

  @Test
  public void testInsert() {
    assertTrue("Should start empty", kd.isEmpty());
    Point2D test = new Point2D(.1, .1);
    kd.insert(test);
    assertEquals("Should have one element", 1, kd.size());
    assertTrue("Does contain the point", kd.contains(test));

    Point2D test2 = new Point2D(.2, .1);
    assertFalse("Does not contain the point", kd.contains(test2));
    kd.insert(test2);
    assertTrue("Does contain the point", kd.contains(test2));

  }

  @Test
  public void testContains() {
    Point2D test = new Point2D(.1, .1);
    assertFalse("Does not contain the point", kd.contains(test));
    kd.insert(test);
    assertTrue("Does contain the point", kd.contains(test));

    Point2D test2 = new Point2D(.2, .1);
    assertFalse("Does not contain the point", kd.contains(test2));
    kd.insert(test2);
    assertTrue("Does contain the point", kd.contains(test2));

    Point2D test3 = new Point2D(.2, .5);
    assertFalse("Does not contain the point", kd.contains(test3));
    kd.insert(test3);
    assertTrue("Does contain the point", kd.contains(test3));
  }

  @Test
  public void testRange() {
    RectHV rect = new RectHV(0, 0, .5, .5);
    kd.insert(new Point2D(.5, .5));
    kd.insert(new Point2D(.1, .9)); // outside
    kd.insert(new Point2D(.2, .3));
    kd.insert(new Point2D(0, 0));
    kd.insert(new Point2D(1, 1)); // outside of rect
    kd.insert(new Point2D(0, .50));
    kd.insert(new Point2D(.50, 0));


    ArrayList<Point2D> expected = new ArrayList<>();
    expected.add(new Point2D(.5, .5));
    expected.add(new Point2D(.2, .3));
    expected.add(new Point2D(0, 0));
    expected.add(new Point2D(0, .50));
    expected.add(new Point2D(.50, 0));

    for (Point2D iter: kd.range(rect)) {
      assertTrue(expected.contains(iter));
    }  }

  @Test
  public void testNearest() {
    kd.insert(new Point2D(.1, .2));
    kd.insert(new Point2D(.1, .9));

    Point2D expected = new Point2D (.1,.2);
    Point2D test = new Point2D (.1,.1);
    assertEquals(expected, kd.nearest(test));

  }

  @Test
  public void testNearestNull() {
    Point2D test = new Point2D(1, 1);
    assertNull(kd.nearest(test));
  }

}
