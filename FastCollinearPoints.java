import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int lineNumber;
    private Node last;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new NullPointerException();
        }

        lineNumber = 0;

        int num = points.length;
        Point[] clone = new Point[num];

        for (int i = 0; i < num; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }

            for (int j = i + 1; j < num; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            clone[i] = points[i];
        }
        Arrays.sort(clone);

        if (num < 4) {
            return;
        }

        for (int i = 0; i < num - 1; i++) {
            int tempPointsNum = 0;
            Point[] tempPoints = new Point[num - 1];

            for (int j = 0; j < num; j++) {
                if (i != j) tempPoints[tempPointsNum++] = clone[j];
            }

            Arrays.sort(tempPoints, clone[i].slopeOrder());

            int count = 0;
            Point min = null;
            Point max = null;

            for (int j = 0; j < (tempPointsNum - 1); j++) {
                if (clone[i].slopeTo(tempPoints[j]) == clone[i].slopeTo(
                        tempPoints[j + 1])) {
                    if (min == null) {
                        if (clone[i].compareTo(tempPoints[j]) > 0) {
                            max = clone[i];
                            min = tempPoints[j];
                        } else {
                            max = tempPoints[j];
                            min = clone[i];
                        }
                    }

                    if (min.compareTo(tempPoints[j + 1]) > 0) {
                        min = tempPoints[j + 1];
                    }

                    if (max.compareTo(tempPoints[j + 1]) < 0) {
                        max = tempPoints[j + 1];
                    }

                    count++;

                    if (j == (tempPointsNum - 2)) {
                        if (count >= 2 && clone[i].compareTo(min) == 0) {
                            addLine(min, max);
                        }

                        count = 0;
                        min = null;
                        max = null;
                    }
                } else {
                    if (count >= 2 && clone[i].compareTo(min) == 0) {
                        addLine(min, max);
                    }

                    count = 0;
                    min = null;
                    max = null;
                }
            }
        }
    }

    private void addLine(Point a, Point b) {
        if (last != null) {
            Node newNode = new Node();
            newNode.prev = last;
            newNode.value = new LineSegment(a, b);
            last = newNode;
        } else {
            last = new Node();
            last.value = new LineSegment(a, b);
        }
        lineNumber++;
    }

    public int numberOfSegments() // the number of line segments
    {
        return lineNumber;
    }

    public LineSegment[] segments() // the line segments
    {
        LineSegment[] lines = new LineSegment[lineNumber];
        Node current = last;

        for (int i = 0; i < lineNumber; i++) {
            lines[i] = current.value;
            current = current.prev;
        }

        return lines;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

    private class Node {
        private LineSegment value;
        private Node prev;
    }
}