/******************************************************************************
 *  Name:    Samantha Lin
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Compilation:  javac-algs4 PointSET.java
 *  Execution:    java-algs4 PointSET
 *  Dependencies: Point2D.java RectHV.java
 *
 *  Description: Represents a set of points in the unit square
 *  (all points have x- and y-coordinates between 0 and 1)
 *  using a red-black BST to support range search
 *  (find all of the points contained in a query rectangle)
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    /**
     * Checks if the set is empty.
     *
     * @return whether the set is empty or not.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * The number of points in the set.
     *
     * @return the number of points in the set.
     */
    public int size() {
        return set.size();
    }

    /**
     * Add a point to the set (if not already in the set).
     *
     * @param p the point to add
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null argument");
        }
        set.add(p);
    }

    /**
     * Checks whether the set contains the specified point.
     *
     * @param p the point to check if contained in the set.
     * @return whether the point is in the set.
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null argument");
        }
        return set.contains(p);
    }

    /**
     * Draw all points to standard draw.
     * Colors are Black and Red.
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D iter : set) {
            iter.draw();
        }
    }

    /**
     * Iterates through all points that are inside the rectangle or on boundary.
     *
     * @param rect the rectangular area to check
     * @return an iterable of points in that rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null argument");
        }
        SET<Point2D> range1 = new SET<>();
        for (Point2D iter : set) {
            if (iter.x() <= rect.xmax() && iter.x() >= rect.xmin() && iter.y() <= rect.ymax() && iter.y() >= rect.ymin()) {
                range1.add(iter);
            }
        }
        return range1;
    }
    /**
     * Finds the nearest neighbor in the set to point p; null if the set is empty.
     * @param p the point to find the neighbor
     * @return the nearest neighbor to p
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (isEmpty()) {
            return null;
        }
        Point2D closest = set.min();
        for (Point2D iter : set) {
            if (iter.distanceTo(p) < closest.distanceTo(p)) {
                closest = iter;
            }
        }
        return closest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}


