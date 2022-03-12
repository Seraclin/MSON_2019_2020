import java.util.ArrayList;
import java.util.Arrays;

/*************************************************************************
 *  Compilation:  javac-algs4 FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java LineSegment.java
 *
 *   Given a point p, the following method determines
 *   whether p participates in a set of 4 or more collinear points.
 *   Think of p as the origin.
 *   - For each other point q, determine the slope it makes with p.
 *   - Sort the points according to the slopes they makes with p.
 *   - Check if any 3 (or more) adjacent points in
 *      the sorted order have equal slopes with respect to p.
 *      If so, these points, together with p, are collinear.
 *
 *************************************************************************/
public class FastCollinearPoints {
    private ArrayList<LineSegment> segmentList;
    private Point[] pointsCopy;

    public FastCollinearPoints(Point[] points) {
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
     * Finds all line segments containing 4 or more points.
     */
    private void generateSegments() {
        for (int i = 0; i < pointsCopy.length; i++) { // points[i] is origin
            Point[] pointsSlope = Arrays.copyOf(pointsCopy, pointsCopy.length);
            Arrays.sort(pointsSlope, pointsCopy[i].slopeOrder());
            int count = 1;
            Point start = null;
            for (int j = 0; j < pointsSlope.length - 1; j++) {
                if (pointsSlope[j].slopeTo(pointsCopy[i]) == pointsSlope[j + 1].slopeTo(pointsCopy[i])) {
                    count++;
                    if (count == 2) {
                        start = pointsSlope[j];
                        count++;
                    } else if (count >= 4 && j + 1 == pointsSlope.length - 1) {
                        if (start.compareTo(pointsCopy[i]) > 0) {
                            segmentList.add(new LineSegment(pointsCopy[i], pointsSlope[j + 1]));
                        }
                        count = 1;
                    }
                } else if (count >= 4) {
                    if (start.compareTo(pointsCopy[i]) > 0) {
                        segmentList.add(new LineSegment(pointsCopy[i], pointsSlope[j]));
                    }
                    count = 1;
                } else {
                    count = 1;
                }

            }
        }
    }

    /**
     * Method returns the number of line segments that are collinear.
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segmentList.size();
    }

    /**
     * This method returns the array of line segments that are collinear.
     *
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