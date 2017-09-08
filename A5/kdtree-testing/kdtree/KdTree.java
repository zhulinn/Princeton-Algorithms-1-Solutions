import java.util.ArrayList;

import com.sun.org.apache.regexp.internal.recompile;

import edu.princeton.cs.algs4.*;

public class KdTree {
    
    private Node root;
    private int N;
    private ArrayList<Point2D> list;
    private Node nearest;
    private double championSquar;
    private static final boolean BY_X = true;
    private static final boolean BY_Y = false;
    private static final boolean toLB = false;
    private static final boolean toRT = true;
    
    
    private static class isInResult {
        boolean isIn;
        boolean where;
        public isInResult(boolean isIn, boolean where) {
            this.isIn = isIn;
            this.where = where;
        }
    }
    
    private static class Node {

        Node left, right;
        RectHV rect;
        Point2D point;
        boolean orientation;
        
        public  Node(Point2D p, boolean orientation, RectHV rect) {
            this.point = p;
            this.orientation = orientation;   // this node use x-coordinate or y-coordinate
            this.rect = rect;
        }
        
        public isInResult isIn(RectHV rect) {
            // is in?
            if ((point.x() <= rect.xmax() || point.x() >= rect.xmin()) && (point.y() <= rect.ymax() || point.y() >= rect.ymin())) {
                    return new isInResult(true, false);
            } 
            // left or right?
            if (orientation == BY_X) {
                if (point.x() > rect.xmax()) return new isInResult(false, toLB);
                else return new isInResult(false, toRT);
            // below or top?   
            } else {
                if (point.y() > rect.ymax()) return new isInResult(false, toLB);
                else return new isInResult(false, toRT); 
            }
        }
        
        public boolean positon(Point2D p) {
            if (orientation == BY_X) {
                if (point.x() > p.x()) return toLB;
                else return toRT;
            } else {
                if (point.y() > p.y()) return toLB;
                else return toRT;
            }
        }

    }
    

    public         KdTree() {                              // construct an empty set of points 
        root = null;
        N = 0;
    }
    
    public           boolean isEmpty() {                     // is the set empty? 
        return root == null;
    }
    
    public               int size() {                        // number of points in the set 
        return N;   
    }
    
    private Node put(Node x, Point2D p, boolean orientation, RectHV rect) {
        if (x == null) {
            N++;
            return new Node(p, orientation, rect);
        }
        if (x.orientation == BY_X) {  // the orientation of parent 
            double cmp = p.x() - x.point.x();
            if (cmp < 0) x.left = put(x.left, p, !x.orientation, new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax())); // the orientation is !parent's
            else if (cmp > 0) x.right = put(x.right, p, !x.orientation,  new RectHV(x.point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
            else if (p.y() != x.point.y()) x.right = put(x.right, p, !x.orientation, new RectHV(x.point.x(), rect.ymin(), rect.xmin(), rect.ymax()));
            
        } else if (x.orientation == BY_Y) {
            double cmp =p.y() - x.point.y();
            if (cmp < 0) x.left = put(x.left, p, !x.orientation, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.point.y())); 
            else if (cmp > 0) x.right = put(x.right, p, !x.orientation, new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax()));
            else if (p.x() != x.point.x()) x.right = put(x.right, p, !x.orientation, new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax()));
        }
        return x;
    }
    
    public              void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.NullPointerException();

        root = put(root, p, BY_X, new RectHV(0, 0, 1, 1));  
    }
    
    public           boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new java.lang.NullPointerException();

        Node x = root;
        while (x != null) {
            if (x.orientation == BY_X) {  // the orientation of parent 
                
                double cmp = p.x() - x.point.x();
                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;   
                else if (x.point.equals(p)) {
                    return true;
                } else 
                    x = x.right;
                
            } else if (x.orientation == BY_Y) {
                
                double cmp =p.y() - x.point.y();
                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;  
                else if (x.point.equals(p))
                    return true;
                else 
                    x = x.right;
               
            }
        }
        
        return false;
    }
    
    public              void draw() {                        // draw all points to standard draw 
        next(root);
        
    }
    
    private void next(Node p) {
        if (p.left != null) next(p.left);
        drawpoint(p);
        drawline(p);    
        if (p.right != null) next(p.right);
    }
    
    private void drawpoint(Node p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        p.point.draw();
    }
    
    private void drawline(Node p) {
        StdDraw.setPenRadius();
        if (p.orientation == BY_X) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.point.x(), p.rect.ymin(), p.point.x(), p.rect.ymax());
        }
        
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(p.rect.xmin(), p.point.y(), p.rect.xmax(), p.point.y());
        }
        
        
    }
    
   private void query(Node x, RectHV rect) {
       if (x != null) {
           if (x.isIn(rect).isIn) {
               list.add(x.point);
               query(x.left, rect);
               query(x.right, rect);
           }
           else if (x.isIn(rect).where == toLB) query(x.left, rect);
           else if (x.isIn(rect).where == toRT) query(x.right,rect);
       } 
   }
    
    public Iterable<Point2D> range(RectHV rect)    {         // all points that are inside the rectangle 
        list = new ArrayList<>();
        query(root, rect);
        return list;
    }
    

    
    private void queryNearest(Node x, Point2D p) {
        if (x != null) {
            if (x.rect.distanceSquaredTo(p) < championSquar) {
                if (x.point.distanceSquaredTo(p) < championSquar) {
                    championSquar = x.point.distanceSquaredTo(p);
                    nearest = x;
                }
                if (x.positon(p) == toLB) {
                    queryNearest(x.left, p);
                    queryNearest(x.right,p);
                } else {
                    queryNearest(x.right,p);
                    queryNearest(x.left, p);
                }
            }
        }

    }
    
    public           Point2D nearest(Point2D p)  {           // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new java.lang.NullPointerException();
        nearest = null;
        championSquar = Double.MAX_VALUE;
        queryNearest(root,p);
        return nearest.point;
    }

    public static void main(String[] args) throws InterruptedException{
        int n = Integer.parseInt(args[0]);
        KdTree set = new KdTree();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.2, 0.8);
            double y = StdRandom.uniform(0.2, 0.8);
            set.insert(new Point2D(x, y));
        }
        Thread.sleep(1000);  
    }
    
}
