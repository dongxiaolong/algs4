/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation < input.txt
 *  Dependencies: None
 *
 *  This program reads standard input.
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it checks if the system is percolated.
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int gridLength;
    private boolean[] grid; // true:open, false:blocked
    private WeightedQuickUnionUF wqu; // with virtual top & bottom
    private WeightedQuickUnionUF wqu2; // without virtual bottom
    private int virtualTop;
    private int numberopen;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }
        gridLength = n;
        virtualTop = gridLength * gridLength;
        grid = new boolean[virtualTop];
        // the last two are virtual top & virtual bottom sites
        wqu = new WeightedQuickUnionUF(virtualTop + 2);
        wqu2 = new WeightedQuickUnionUF(virtualTop + 1);
    }
    public int numberOfOpenSites() {
        return numberopen;
    }
    private void validateIndecies(int row, int col) {
        if (row <= 0 || row > gridLength)
            throw new IndexOutOfBoundsException("row index out of bounds");
        if (col <= 0 || col > gridLength)
            throw new IndexOutOfBoundsException("col index out of bounds");
    }
    private int xyTo1D(int row, int col) {
        return (row - 1) * gridLength + (col - 1);
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndecies(row, col);
        int self = xyTo1D(row, col);
        if (grid[self]) {
            return;
        }
        grid[self] = true;
        if (row == 1) {
            wqu.union(self, virtualTop);
            wqu2.union(self, virtualTop);
        }
        if (row == gridLength) {
            wqu.union(self, virtualTop + 1);
        }
        int other;
        if (row > 1) { // up
            other = xyTo1D(row - 1, col);
            if (grid[other]) {
                wqu.union(self, other);
                wqu2.union(self, other);
            }
        }
        if (row < gridLength) { // down
            other = xyTo1D(row + 1, col);
            if (grid[other]) {
                wqu.union(self, other);
                wqu2.union(self, other);
            }
        }
        if (col > 1) { // left
            other = xyTo1D(row, col - 1);
            if (grid[other]) {
                wqu.union(self, other);
                wqu2.union(self, other);
            }
        }
        if (col < gridLength) { // right
            other = xyTo1D(row, col + 1);
            if (grid[other]) {
                wqu.union(self, other);
                wqu2.union(self, other);
            }
        }
        numberopen++;
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndecies(row, col);
        return grid[xyTo1D(row, col)];
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndecies(row, col);
        return wqu2.connected(virtualTop, xyTo1D(row, col));
    }
    // does the system percolate?
    public boolean percolates() {
        return wqu.connected(virtualTop, virtualTop + 1);
    }
    // test client (optional)
    public static void main(String[] args) {
        /*
        Percolation percolation = new Percolation(0);
        Percolation percolation = new Percolation(1);
        percolation.open(0, 1);
        percolation.open(1, 0);
        */
        /*
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(1, 2);
        for (int i = 1; i <= 2; i++) {
            for(int j = 1; j <= 2; j++) {
                StdOut.println("" + percolation.isFull(i, j) + " " + percolation.isOpen(i, j));
            }
        }
        StdOut.println("percolation is " + percolation.percolates());
        */
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
        StdOut.println("percolation is " + percolation.percolates());
    }
}