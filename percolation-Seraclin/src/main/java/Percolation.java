import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Name:    Samantha Lin
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *
 *  Description:  Modeling Percolation. Fun.
 ******************************************************************************/
public class Percolation {

    private boolean[][] grid; // keeps track if open
    private int openSites;
    private WeightedQuickUnionUF uni; // keeps track if full (has zero)
    private WeightedQuickUnionUF uniBackwash; // another WeightedUnion for dealing with backwash
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[n][n];
        uni = new WeightedQuickUnionUF((n * n) + 2);
        uniBackwash = new WeightedQuickUnionUF((n * n) + 1);

        for (int i = 1; i <= n; i++) {
            uni.union(0, i);
        }
        for (int i = 0; i <= n; i++) {
            uniBackwash.union(0, i);
        }
        for (int i = n * n - n + 1; i < n * n; i++) {
            uni.union(n * n + 1, i);
        }
    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        int pos1D = getIndex(row, col); // 1D position of 2D array
        if (row == 1) {
            uni.union(0, pos1D);
            uniBackwash.union(0, pos1D);
        }
        if (row == grid.length) {
            uni.union(grid.length * grid.length + 1, pos1D);
        }
        if (col < grid.length && isOpen(row, col + 1)) { // right open site
            uni.union(pos1D, getIndex(row, col + 1));
            uniBackwash.union(pos1D, getIndex(row, col + 1));

        }
        if (col > 1 && isOpen(row, col - 1)) { // left open site
            uni.union(pos1D, getIndex(row, col - 1));
            uniBackwash.union(pos1D, getIndex(row, col - 1));
        }
        if (row > 1 && isOpen(row - 1, col)) { // top open site
            uni.union(pos1D, getIndex(row - 1, col));
            uniBackwash.union(pos1D, getIndex(row - 1, col));

        }
        if (row < grid.length && isOpen(row + 1, col)) { // bottom open site
            uni.union(pos1D, getIndex(row + 1, col));
            uniBackwash.union(pos1D, getIndex(row + 1, col));
        }
    }
    private int getIndex(int row, int col) {
        return (row - 1) * grid.length + (col);
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        // check if the tile is connected to top row in order to be full
        return uni.connected(0, getIndex(row, col)) && uniBackwash.connected(0, getIndex(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uni.connected(0, (grid.length * grid.length) + 1);
    }

    public static void main(String[] args) {
        // TODO: test client (optional)
    }
}
