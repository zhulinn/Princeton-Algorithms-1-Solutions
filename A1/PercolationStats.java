import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
 private int trials;
 private double[] thresholds;
 
  public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
  {
   if (n <= 0 || trials <= 0) throw new IllegalArgumentException ("Invaid input");
   this.trials = trials;
   thresholds = new double[trials];
   for (int i = 0; i < trials; i++)
         {  
             Percolation experiment = new Percolation(n);   
             while (!experiment.percolates()) {
                 int row = StdRandom.uniform(1, n+1);
                 int col = StdRandom.uniform(1, n+1);
                 experiment.open(row, col);
             }
             double threshold = ((double) experiment.numberOfOpenSites()) / (n * n);
             thresholds[i] = threshold;
         }
    }
 

    public double mean()                          // sample mean of percolation threshold
    {
     return StdStats.mean(thresholds);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(thresholds);

    }
    
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }
 
    public static void main(String[] args)        // test client (described below)
    {
        int n =  Integer.parseInt(args[0]);
        int trials =  Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
            
        
    }
   
} 