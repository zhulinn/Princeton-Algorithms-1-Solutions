
import edu.princeton.cs.algs4.*;

public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> pqtwin;
    private SearchNode goal;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int priority;
        private SearchNode previous;
        
        public SearchNode (Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
            this.priority = board.manhattan();
        }
        
        public SearchNode (Board board, SearchNode previous) {
            this.board = board;
            this.moves = previous.moves + 1;
            this.previous = previous;
            this.priority = board.manhattan() + this.moves;
        }
        
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
        
        public boolean isValid() {
            if (moves < 2) 
                return true;
            else 
                return !this.board.equals(this.previous.previous.board);
        }
    }
    
    private SearchNode doSearch() {
        
        SearchNode lastNode = pq.delMin();
        SearchNode lastTwinNode = pqtwin.delMin();
        while (! (lastNode.board.isGoal() || lastTwinNode.board.isGoal())) {
            for (Board neighborboard : lastNode.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighborboard, lastNode);
                if (neighborNode.isValid()) pq.insert(neighborNode);
            }
            for (Board neighborboard : lastTwinNode.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighborboard, lastTwinNode);
                if (neighborNode.isValid()) pqtwin.insert(neighborNode);
            }
            lastNode = pq.delMin();
            lastTwinNode = pqtwin.delMin();
        }
        if (lastNode.board.isGoal()) {
            isSolvable = true;
            return lastNode;
        }
        else { 
            isSolvable = false;
            return lastTwinNode;
        }
    }
    
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new java.lang.NullPointerException();
        
        pq = new MinPQ<SearchNode>();
        pqtwin = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial));
        pqtwin.insert(new SearchNode(initial.twin()));
        goal = doSearch();
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
        return isSolvable;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable) return -1;
        return goal.moves;
        
        
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable)  return null;
        Stack<Board> boards = new Stack<>();
        boards.push(goal.board);      
        while (goal.previous != null) {
            boards.push(goal.previous.board);
            goal = goal.previous;
        }
        return boards;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}