import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    private Point min(Point lhs, Point rhs) {
        return lhs.compareTo(rhs) <= 0 ? lhs : rhs;
    }

    private Point max(Point lhs, Point rhs) {
        return lhs.compareTo(rhs) >= 0 ? lhs : rhs;
    }

    private void checkNoRepeated(Point[] points) {
        for (int i = 1; i < points.length; ++i) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkNoNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        checkNoNull(points);

        Point[] copiedPoints = new Point[points.length];
        System.arraycopy(points, 0, copiedPoints, 0, points.length);
        Arrays.sort(copiedPoints);
        checkNoRepeated(copiedPoints);
        segments = new ArrayList<LineSegment>();
        for (int first = 0; first < copiedPoints.length; ++first) {
            for (int second = first + 1; second < copiedPoints.length; ++second) {
                for (int third = second + 1; third < copiedPoints.length; ++third) {
                    for (int four = third + 1; four < copiedPoints.length; ++four) {
                        double firstToSecondSlope = copiedPoints[first].slopeTo(copiedPoints[second]);
                        double firstToThirdSlope = copiedPoints[first].slopeTo(copiedPoints[third]);
                        double firstToFourSlope = copiedPoints[first].slopeTo(copiedPoints[four]);

                        if (firstToSecondSlope == firstToThirdSlope && firstToThirdSlope == firstToFourSlope) {
                            Point firstMin = min(copiedPoints[first], copiedPoints[second]);
                            Point secondMin = min(copiedPoints[third], copiedPoints[four]);
                            Point min = min(firstMin, secondMin);

                            Point firstMax = max(copiedPoints[first], copiedPoints[second]);
                            Point secondMax = max(copiedPoints[third], copiedPoints[four]);
                            Point max = max(firstMax, secondMax);

                            segments.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
