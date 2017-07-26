import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int T;
    private double stddev;
    private double mean;
    private final double[] openSites;
    public PercolationStats(int n, int trails){
        if(n <= 0 || trails <=0) throw new IllegalArgumentException("either n or trail is <= 0");
        T = trails;
        double N = n*n;
        openSites = new double[T];
        for(int i=0;i<T; i++) {
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            openSites[i] = (double) percolation.numberOfOpenSites() / N;
        }
    }
    public double mean(){
        mean = StdStats.mean(openSites);
        return  mean;
    }

    public double stddev(){
        stddev = StdStats.stddev(openSites);
        return  stddev;
    }

    public double confidenceLo(){
        return mean - ((1.96*stddev)/Math.sqrt(T));
    }

    public double confidenceHi(){
        return mean + ((1.96*stddev)/Math.sqrt(T));
    }

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() +", " + stats.confidenceHi()+"]");
    }
}
