/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Compilation:  javac-algs4 KdTree.java
 *  Execution:    java-algs4 KdTree
 *  Dependencies: Point2D.java RectHV.java 
 *
 *  Description: Represents a set of points in the unit square 
 *  (all points have x- and y-coordinates between 0 and 1) 
 *  using a 2d-tree to support efficient range search 
 *  (find all of the points contained in a query rectangle) 
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;


public class KdTree {
    private class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean orientation;

        public Node(Point2D p, RectHV re, boolean ori) {
            point = p;
            rect = re;
            lb = null;
            rt = null;
            orientation = ori; // vertical = true; horizontal = false;
        }
    }

    private final boolean VERTICAL = true;
    private final boolean HORIZONTAL = false;
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }
    /**
     * Checks if the kdtree is empty.
     *
     * @return whether or not it is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Returns the number of points in the set.
     *
     * @return number of points in the set
     */
    public int size() {
        return size;
    }
    /**
     * Add the point to the set if not already present.
     *
     * @param p the point to add
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }
    /**
     * Helper method for insert().
     * @param current     the current node we are on
     * @param p           the point to insert
     * @param orientation the orientation of the line (horizontal/vertical)
     * @param xmin        the min x coordinate of the rect
     * @param ymin        the min y coordinate of the rect
     * @param xmax        the max x coordinate of the rect
     * @param ymax        the max y coordinate of the rect
     * @return the new node to be inserted
     */
    private Node insert(Node current, Point2D p, boolean orientation,
                         double xmin, double ymin, double xmax, double ymax) {
        if (current == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), orientation);
        }
        int comp = compare(p, current.point, orientation);
        if (orientation == VERTICAL) { // vertical = true
            if (comp < 0) {
                current.lb = insert(current.lb, p, !orientation,
                        xmin, ymin, current.point.x(), ymax);
            } else if (comp > 0) {
                current.rt = insert(current.rt, p, !orientation,
                        current.point.x(), ymin, xmax, ymax);
            }
        } else { // Horizontal
            if (comp < 0) {
                current.lb = insert(current.lb, p, !orientation,
                        xmin, ymin, xmax, current.point.y());
            } else if (comp > 0) {
                current.rt = insert(current.rt, p, !orientation,
                        xmin, current.point.y(), xmax, ymax);
            }
        }
        return current;
    }
    /**
     * Checks if the set contains point p.
     *
     * @param p the point to look for.
     * @return whether or not the set contains the point p.
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null argument");
        }
        return contains(root, p, VERTICAL);
    }
    /**
     * Helper method for contains().
     * @param cur current node we are checking.
     * @param p point we want to find.
     * @param orient orientation of the node.
     * @return whether point is in the tree.
     */
    private boolean contains (Node cur, Point2D p, boolean orient) {
      if (cur == null) {
        return false;
      }
      if (cur.point.equals(p)) {
          return true;
      } else {
          int comp = compare(p, cur.point, orient);
          if (comp < 0) {
              return contains(cur.lb, p, !orient);
          } else if (comp > 0) {
              return contains(cur.rt, p, !orient);
          }
      }
      return false;
    }

    /**
     * Draw all points to standard draw.
     * Colors are Black and Red.
     */
    public void draw() {
        draw(root, VERTICAL);
    }
    private void draw(Node cur, Boolean orientation) {
        if (cur != null) {
            draw(cur.lb, !orientation);
            if (orientation == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(cur.point.x(), cur.rect.ymin(),
                        cur.point.x(), cur.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(cur.rect.xmin(), cur.point.y(),
                        cur.rect.xmax(), cur.point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            cur.point.draw();

            draw(cur.rt, !orientation);
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
        SET<Point2D> points = new SET<>();
        range(rect, root, points);
        return points;
    }

    private void range (RectHV rect, Node node, SET<Point2D> set) {
        if (node == null || !rect.intersects(node.rect)) {
            return; // quit
        }
        if (rect.contains(node.point)) {
            set.add(node.point); // add it to the set
        }
        range(rect, node.lb, set); // recurse left
        range(rect, node.rt, set); // recurse right
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
        Point2D near = null;
        near = nearest(p, root, root.point);
        return near;
    }
    private Point2D nearest (Point2D query, Node node, Point2D nearestPoint) {
        if (node == null) {
            return nearestPoint;
        }
        if (node.rect.distanceSquaredTo(query) <= nearestPoint.distanceSquaredTo(query)) {
            if (node.point.distanceSquaredTo(query) < nearestPoint.distanceSquaredTo(query)) {
                nearestPoint = node.point; // update nearest
            }
            int comp = compare(query, node.point, node.orientation);
            if (comp > 0) {
                nearestPoint = nearest(query, node.rt, nearestPoint);
                nearestPoint = nearest(query, node.lb, nearestPoint);
            } else {
                nearestPoint = nearest(query, node.lb, nearestPoint);
                nearestPoint = nearest(query, node.rt, nearestPoint);
            }
        }
        return nearestPoint;
    }
    /**
     * This method compares (x, y) of two points to determine whether to go left or right on tree.
     * @param a first point
     * @param b second point
     * @param orientation of the lines
     * @return negative if left, positive if right
     */
    private int compare(Point2D a, Point2D b, boolean orientation) {
        if (orientation == VERTICAL) {
            int comp = Double.compare(a.x(), b.x());
            if (comp == 0) {
                return Double.compare(a.y(), b.y());
            } else {
                return comp;
            }
        } else { // HORIZONTAL
            int comp = Double.compare(a.y(), b.y());
            if (comp == 0) {
                return Double.compare(a.x(), b.x());
            } else {
                return comp;
            }
        }
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}


