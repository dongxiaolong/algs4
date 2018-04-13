import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean[] a;
    private int N;
    private int numberopen;

    public Percolation(int n){
        N = n; // N * N grid system
        numberopen = 0; // open sites number
        if (n <= 0)
            throw new IllegalArgumentException("n must be a positive number.");
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        a = new boolean[n * n];
    }
    public void open(int row, int col){
        if (outOfIndices(row, col))
            throw new IllegalArgumentException("outOfIndices.......");
        if (isOpen(row, col))
            return;
        if (row == 1){
            weightedQuickUnionUF.union(0, (row - 1) * N + col);
            a[(row - 1) * N + col - 1] = true;
        }else if (row == N){
            weightedQuickUnionUF.union(N * N + 1, (row - 1) * N + col);
            a[(row - 1) * N + col - 1] = true;
        }
        if (!outOfIndices(row -1, col) && isOpen(row -1, col)){
            weightedQuickUnionUF.union((row -1)* N + col, (row - 2) * N + col);
            a[(row - 1) * N + col - 1] = true;
        }
        if (!outOfIndices(row + 1, col) && isOpen(row + 1, col)){
            weightedQuickUnionUF.union(row * N + col, (row - 1) * N + col);
            a[(row - 1) * N + col - 1] = true;
        }
        if (!outOfIndices(row, col - 1) && isOpen(row, col - 1)){
            weightedQuickUnionUF.union((row - 1) * N + col - 1, (row - 1) * N + col);
            a[(row - 1) * N + col - 1] = true;
        }
        if (!outOfIndices(row, col + 1) && isOpen(row, col + 1)){
            weightedQuickUnionUF.union((row - 1) * N + col, (row - 1) * N + col + 1);
        }
        if (!outOfIndices(row, col + 1)){
            a[(row - 1) * N + col - 1] = true;
        }
        numberopen++;
    }
    public boolean isOpen(int row, int col){
        return a[(row - 1) * N + col -1] == true;
    }
    public boolean isFull(int row, int col){
        return weightedQuickUnionUF.connected((row - 1) * N + col, 0);
    }
    public int numberOfOpenSites(){
        return numberopen;
    }
    public boolean percolates(){
        return weightedQuickUnionUF.connected(0, N * N + 1);
    }
    private boolean outOfIndices(int row, int col){
        if ((row > 0 && row <= N) && (col > 0 && col <= N))
            return false;
        return true;
    }
}
