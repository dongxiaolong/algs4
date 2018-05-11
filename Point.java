import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(this.x, this.y);
    }                              // draws this point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    @Override
    public int compareTo(Point that) {
        if ((this.x == that.x) && (this.y == that.y)) {
            return 0;
        } else if ((that.y > this.y) ||
                ((that.y == this.y) && (that.x > this.x))) {
            return -1;
        } else {
            return 1;
        }
    }


    public double slopeTo(Point point) {
        int dx = point.x - this.x;
        int dy = point.y - this.y;
        if (dx == 0 && dy == 0) return Double.NEGATIVE_INFINITY;
        if (dx == 0) return Double.POSITIVE_INFINITY;
        if (dy == 0) return +0;
        else return (double) dy / (double) dx;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(this);
    }

    private class SlopeOrder implements Comparator<Point> {
        private Point p0;

        public SlopeOrder(Point invokePoint) {
            p0 = invokePoint;
        }

        public int compare(Point p1, Point p2) {
            double d01 = p0.slopeTo(p1);
            double d02 = p0.slopeTo(p2);

            if (d01 < d02) {
                return -1;
            } else if (d01 > d02) {
                return 1;
            } else {
                return 0;
            }
        }
    }


}