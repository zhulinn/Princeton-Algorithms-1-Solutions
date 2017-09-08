import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private int num;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int sum = 0;
    
    public Percolation(int n)    // create n-by-n grid, with all sites blocked
    { 
     if (n <= 0) throw new IllegalArgumentException ("Invaid input");
     num = n;
     uf = new WeightedQuickUnionUF(n*n+1);   
     uf2 = new WeightedQuickUnionUF(n*n+2);
     open = new boolean[n+1][n+1];
    } 
  
    private void valite(int row,int col)
    {
     if (row <= 0 || row > num || col <= 0 || col > num) 
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    private  int xyTo1D(int row, int col)
    {
     return (row - 1) * num + (col - 1);
    }
    
    public    void open(int row, int col)    // open site (row, col) if it is not open already
   {
    valite(row, col);
    if (!isOpen(row, col)) {
        open[row][col] = true;
        sum++;
        int p = xyTo1D(row, col);
        if (row > 1 && open[row-1][col]) 
        {
         int q = xyTo1D(row-1, col);
         uf.union(p, q);
         uf2.union(p, q);
        }
        if (col > 1 && open[row][col-1]) 
        {
         int q = xyTo1D(row, col-1);
         uf.union(p, q);
         uf2.union(p, q);
        }
        if (row < num && open[row+1][col]) 
        {
         int q = xyTo1D(row+1, col);
         uf.union(p, q);
         uf2.union(p, q);
        }
        if (col < num && open[row][col+1]) 
        {
         int q = xyTo1D(row, col+1);
         uf.union(p, q);
         uf2.union(p, q);
        }
        if (row == 1)
        {
         uf.union(p, num*num);
         uf2.union(p, num*num);
        }
        if (row == num)             
        {
            uf2.union(p, num*num+1);
     
        }
        
    }

   }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
    valite(row, col);
    return open[row][col];
   }
    public boolean isFull(int row, int col)  // is site (row, col) full? connected to an  top open site
   {
    valite(row, col);
    int p = xyTo1D(row, col);
    return uf.find(p) == uf.find(num*num);
   }
    public int numberOfOpenSites()       // number of open sites
   {
    return sum;
   }
    public boolean percolates()              // does the system percolate?
   {
     
    	return uf2.find(num*num+1) == uf2.find(num*num);
   }
    public static void main(String[] args)   // test client (optional)
   {
    Percolation a = new Percolation(10);
    a.open(1, 1);
    a.open(1, 2);
    a.open(1,3);
    a.open(2,3);
    a.open(3,3);
    a.open(4,3);
    a.open(5,3);
    a.open(6,3);
    a.open(7,3);
    a.open(8,3);
    a.open(9,4);
    a.open(9,3);
   a.open(10,10);
    
    System.out.println(a.percolates());
   }
} 
