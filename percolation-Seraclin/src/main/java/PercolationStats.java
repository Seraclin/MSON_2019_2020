import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/******************************************************************************
 *  Name: Samantha Lin
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *  Description:  Percolation stats. Funner.
 ******************************************************************************/
public class PercolationStats {
    private double[] trialResults;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            int open = 0;
            while (!test.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!test.isOpen(row, col)) {
                    open++;
                    test.open(row, col);
                }
            }
            trialResults[i] = ((double) open) / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(trialResults);
    }

    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / (Math.sqrt(trialResults.length)));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / (Math.sqrt(trialResults.length)));
    }

    public static void main(String[] args) {
        // test client (described at http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
        int n1 = StdIn.readInt();
        int t1 = StdIn.readInt();
        PercolationStats stat = new PercolationStats(n1, t1);
        StdOut.println("mean                    = " + stat.mean());
        StdOut.println("stddev                  = " + stat.stddev());
        StdOut.println("95% confidence interval = [" + stat.confidenceLo() + ", " + stat.confidenceHi() + "]");


    }
}
