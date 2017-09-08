import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final char[] board;
    private final int dimension;
    private final int zero;
    private final int hamming;
    private final int manhattan;
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        dimension = blocks.length;
        board = new char[dimension*dimension];
        int i = 0;
        int t = 0;
        for (int[] row : blocks) 
            for (int e : row) {
                if (e == 0) t = i;
                
                this.board[i++] = (char)e;
            }
        zero = t;
        
        t = 0;
        char elem = 1;
        for (char e : board) 
                if (e != elem++) 
                    if (e != 0) t++;
        hamming = t;
        
        t = 0;
        elem = 1;
        int toi;
        int toj;
        int j;
        for (char e : board) {
            if (e != elem) {
                    if (e != 0) {
                    toi = (int)Math.ceil( e / (double) dimension) - 1;
                    toj = e % dimension - 1;
                    if (toj < 0) toj = dimension - 1;
                    i = (int)Math.ceil( elem / (double) dimension) - 1;
                    j =  elem % dimension - 1;
                    if (j < 0) j = dimension - 1;
                    t += Math.abs(toi - i) + Math.abs(toj - j);      
                    }             
            }
            elem++;
        }
        manhattan = t;
    } 
                                         
    public int dimension() {
        // board dimension n
        return  dimension;
    }
    
    public int hamming() {
        // number of blocks out of place
        
        return hamming;
    }
    
    public int manhattan()  {
        // sum of Manhattan distances between blocks and goal

        return manhattan;
    }
    public boolean isGoal()  {
        // is this board the goal board?
        return hamming() == 0 ;
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int i = 1;
        int[][] twinblocks = new int[dimension][dimension];
        if (board[i-1] == 0 || board[i] == 0) i++;
        char temp = board[i-1];
        char temp2 = board[i];
        for(int  j = 1; j <= board.length; j++) {
            int n = (int)Math.ceil(j/ (double)dimension) - 1;
            int m  = j % dimension - 1 ;
            if (m < 0) m = dimension - 1;
            
            if (i == j - 1) {
                twinblocks[n][m] = temp;
            } else if ((i-1) == j - 1) {
                twinblocks[n][m] = temp2;
            } else {
                twinblocks[n][m] = board[j - 1];
            }
        }
        return new Board(twinblocks);
    }
    public boolean equals(Object y) { 
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that =  (Board) y;
        if (this.dimension != that.dimension) return false;
        if (this.zero != that.zero) return false;
        return Arrays.equals(this.board, that.board);
    }
    
    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> neighbors = new Queue<>();
        int zero_i = (int)Math.ceil((zero+1)/(double)dimension) - 1;
        int zero_j  = (zero+1) % dimension - 1 ;
        if (zero_j < 0) zero_j = dimension - 1;
        if (zero_i > 0) {
            int[][] blocks = to2d(board);
            blocks[zero_i][zero_j] = blocks[zero_i - 1][zero_j] ;
            blocks[zero_i - 1][zero_j] = 0;
            neighbors.enqueue(new Board(blocks));
        }
        if (zero_i < dimension - 1) {
            int[][] blocks = to2d(board);
            blocks[zero_i][zero_j] = blocks[zero_i + 1][zero_j] ;
            blocks[zero_i + 1][zero_j] = 0;
            neighbors.enqueue(new Board(blocks));
        }
        if (zero_j > 0) {
            int[][] blocks = to2d(board);
            blocks[zero_i][zero_j] = blocks[zero_i][zero_j - 1] ;
            blocks[zero_i][zero_j - 1] = 0;
            neighbors.enqueue(new Board(blocks));
        }
        if (zero_j < dimension - 1) {
            int[][] blocks = to2d(board);
            blocks[zero_i][zero_j] = blocks[zero_i][zero_j + 1] ;
            blocks[zero_i][zero_j + 1] = 0;
            neighbors.enqueue(new Board(blocks));
        }
        return neighbors;
    }
    

    
    
    private int[][] to2d(char[] board) {
        int[][] twinblocks = new int[dimension][dimension];
        int n;
        int m;
        for(int  j = 1; j <= board.length; j++) {
            n = (int)Math.ceil(j/(double)dimension) - 1;
            m = j % dimension - 1 ;
            if (m < 0) m = dimension - 1;
            twinblocks[n][m] = board[j - 1];
        }
        return twinblocks;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < board.length; i++) {
            s.append(String.format("%2d ", (int)board[i]));
            if ((i + 1) % dimension == 0) s.append("\n");     
        }
        
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)        
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board twin = initial.twin();
        StdOut.println(initial);
        StdOut.println(twin);
        for (Board e : initial.neighbors())
            StdOut.println(e);

    }
}