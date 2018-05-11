import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int lineNumber;
    private Node last;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
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
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                for (int m = j + 1; m < num; m++) {
                    for (int n = m + 1; n < num; n++) {
                        double d01 = clone[i].slopeTo(clone[j]);
                        double d02 = clone[j].slopeTo(clone[m]);
                        double d03 = clone[m].slopeTo(clone[n]);

                        if (d01 == d02 && d01 == d03) {
                            if (last != null) {
                                Node newNode = new Node();
                                newNode.prev = last;
                                newNode.value = new LineSegment(clone[i],
                                        clone[n]);
                                last = newNode;
                            } else {
                                last = new Node();
                                last.value = new LineSegment(clone[i],
                                        clone[n]);
                            }

                            lineNumber++;
                        }
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

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