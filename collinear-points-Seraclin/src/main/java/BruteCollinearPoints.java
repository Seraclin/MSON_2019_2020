import java.util.ArrayList;
import java.util.Arrays;

/*************************************************************************
 *  Compilation:  javac-algs4 BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java LineSegment.java
 *   A program that examines 4 points at a time
 *   and checks whether they all lie on the same line segment,
 *   returning all such line segments.
 *   To check whether the 4 points p, q, r, and s are collinear,
 *   check whether the three slopes between p and q,
 *   between p and r, and between p and s are all equal.
 *************************************************************************/
public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentList;
    private Point[] pointsCopy;

    public BruteCollinearPoints(Point[] points) {
        segmentList = new ArrayList<LineSegment>();
        if (points == null) {
            throw new IllegalArgumentException("Point array is null");
        }
        pointsCopy = Arrays.copyOf(points, points.length);
        checkNullPoints(points);
        Arrays.sort(pointsCopy);
        checkRepeatPoints(pointsCopy);
        generateSegments();
    }
    /**
     * Finds all line segments containing 4 points.
     */
    private void generateSegments() {
        for (int i = 0; i < pointsCopy.length; i++) {
            for (int i2 = i + 1; i2 < pointsCopy.length; i2++) {
                for (int i3 = i2 + 1; i3 < pointsCopy.length; i3++) {
                    for (int i4 = i3 + 1; i4 < pointsCopy.length; i4++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[i2]) == pointsCopy[i].slopeTo(pointsCopy[i3])
                                && pointsCopy[i].slopeTo(pointsCopy[i2]) == pointsCopy[i].slopeTo(pointsCopy[i4])) {
                            LineSegment tempLineSegment = new LineSegment(pointsCopy[i], pointsCopy[i4]);
                            if (!segmentList.contains(tempLineSegment)) {
                                segmentList.add(tempLineSegment);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Method returns the number of line segments that are collinear.
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segmentList.size();
    }

    /**
     * This method returns the array of line segments that are collinear.
     * @return an array containing the line segments from the smallest point to the biggest point.
     */
    public LineSegment[] segments() {
        return segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    /**
     * Helper method to check for repeated points.
     * @param p the point array
     */
    private void checkRepeatPoints(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i].compareTo(p[i + 1]) == 0) {
                throw new IllegalArgumentException("Repeated points.");
            }
        }
    }

    /**
     * Helper method to check for null points.
     * @param p the point array
     */
    private void checkNullPoints(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) {
                throw new IllegalArgumentException("Point is null");
            }
        }
    }
}