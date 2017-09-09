
import java.util.TreeSet;

import edu.princeton.cs.algs4.*;

public class KdTree {
    
    private Node root;
    private int N;
    private TreeSet<Point2D> list;
    private Node nearest;
    private double championSquar;
    private static final boolean BY_X = true;
    private static final boolean BY_Y = false;
    private static final boolean toLB = false;
    private static final boolean toRT = true;
    
        //    Pruning rule: if the query rectangle does not intersect the rectangle corresponding to a node,
    //      there is no need to explore that node (or its subtrees).
    
    //    My solution uses complex ways to check whether the current rect where the node is in intersects with the querry rect
    //    However, There are a built-in methods "intersects" of "rect" class you can dirrectly use.
    //     I did not realize that untill I finished it. Sorry about that~~

       
    private static class isInterResult {
        boolean isInter;
        boolean isInside;
        boolean where;
        
        public isInterResult(boolean isInter, boolean isInside, boolean where) {
            this.isInter = isInter;
            this.isInside = isInside;
            this.where = where;   // if isIn == true && where == true  -> point in the line
        }
    }
    
    private static class rect2D {
        double xmin, ymin, xmax,ymax;
        public rect2D (double xmin, double ymin, double xmax, double ymax) {
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
        }
        
    }
    
    private static class Node {

        Node left, right;
        rect2D rect;
        Point2D point;
        boolean orientation;
        
        public  Node(Point2D p, boolean orientation, rect2D rect) {
            this.point = p;
            this.orientation = orientation;   // this node use x-coordinate or y-coordinate
            this.rect = rect;
        }
        
        //######    Here we should use the build-in "intersects" method in "rect" class   ####
        public isInterResult isInter(rect2D qrect) {
            double xmin = qrect.xmin;
            double xmax = qrect.xmax;
            double ymin = qrect.ymin;
            double ymax = qrect.ymax;
            double xvalue = point.x();
            double yvalue = point.y();
            
            
            if (orientation == BY_X) {
                if (xmin < xvalue) {
                    
                    if ( xvalue < xmax) {
                        
                       if (yvalue <= ymax && yvalue >=ymin) 
                           return new isInterResult(true, true, false);
                       else 
                           return new isInterResult(true, false, false);
                       
                    } else if (xvalue > xmax) {
                        return new isInterResult(false, false, toLB);
                    } else {
                        if (yvalue <= ymax && yvalue >=ymin)
                            return new isInterResult(false, true, false);
                        else 
                            return new isInterResult(false, false, toLB);
                    }
                
                } else if (xmin > xvalue) { //if (xmin >= xvalue) 
                    return new isInterResult (false, false ,toRT);
                } else {
                    if (yvalue <= ymax && yvalue >=ymin)
                        return new isInterResult(false, true, false);
                    else 
                        return new isInterResult(false, false, toRT);
               
                
                }
            } else  {  // if (orientation == BY_Y)
                if (ymin < yvalue) {
                    
                    if ( yvalue < ymax) {
                        
                       if (xvalue <= xmax && xvalue >= xmin) 
                           return new isInterResult(true, true, false);
                       else 
                           return new isInterResult(true, false, false);
                       
                    } else if (yvalue > ymax) {
                        return new isInterResult(false, false, toLB);
                    } else {
                        if (xvalue <= xmax && xvalue >= xmin)
                            return new isInterResult(false, true, false);
                        else 
                            return new isInterResult(false, false, toLB);
                    }
                
                } else if (ymin > yvalue) { //if (xmin >= xvalue) 
                    return new isInterResult (false, false ,toRT);
                } else {
                    if (xvalue <= xmax && xvalue >= xmin)
                        return new isInterResult(false, true, false);
                    else 
                        return new isInterResult(false, false, toRT);
                }
                
                
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
    
    private Node put(Node x, Point2D p, boolean orientation, rect2D rect) {
        double xmin = rect.xmin;
        double xmax = rect.xmax;
        double ymin = rect.ymin;
        double ymax = rect.ymax;

        if (x == null) {
            N++;
            return new Node(p, orientation, rect);
        }
        double xvalue = x.point.x();
        double yvalue = x.point.y();
        if (x.orientation == BY_X) {  // the orientation of parent 
            double cmp = p.x() - x.point.x();
            if (cmp < 0) x.left = put(x.left, p, !x.orientation, new rect2D(xmin, ymin, xvalue, ymax)); // the orientation is !parent's
            else if (cmp > 0) x.right = put(x.right, p, !x.orientation,  new rect2D(xvalue, ymin, xmax, ymax));
            else if (p.y() != x.point.y()) x.right = put(x.right, p, !x.orientation, new rect2D(xvalue, ymin, xmax, ymax));
            
        } else if (x.orientation == BY_Y) {
            double cmp =p.y() - x.point.y();
            if (cmp < 0) x.left = put(x.left, p, !x.orientation, new rect2D(xmin, ymin, xmax, yvalue)); 
            else if (cmp > 0) x.right = put(x.right, p, !x.orientation, new rect2D(xmin,yvalue, xmax, ymax));
            else if (p.x() != x.point.x()) x.right = put(x.right, p, !x.orientation, new rect2D(xmin,yvalue, xmax, ymax));
        }
        return x;
    }
    
    public              void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.NullPointerException();

        root = put(root, p, BY_X, new rect2D(0, 0, 1, 1));  
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
            StdDraw.line(p.point.x(), p.rect.ymin, p.point.x(), p.rect.ymax);
        }
        
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(p.rect.xmin, p.point.y(), p.rect.xmax, p.point.y());
        }
        
        
    }
    
   private void  query(Node x, rect2D rect) {
       if (x != null) {
           if (x.isInter(rect).isInside == true)  list.add(x.point);
           
           if (x.isInter(rect).isInter) {
               query(x.left, rect);
               query(x.right, rect);
           } else {  // not in
               if (x.isInter(rect).where == toLB) query(x.left, rect);
               if (x.isInter(rect).where == toRT) query(x.right,rect);
           }
       } 
   }
    
    public Iterable<Point2D> range(RectHV rect)    {         // all points that are inside the rectangle 
        list = new TreeSet<>();
        rect2D rect2d = new rect2D(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax());
        query(root, rect2d);
        return list;
    }
    

    
    private void queryNearest(Node x, Point2D p) {
        if (x != null) {
            RectHV rect = new RectHV(x.rect.xmin, x.rect.ymin, x.rect.xmax, x.rect.ymax);
            if (rect.distanceSquaredTo(p) < championSquar) {
                double tmp = x.point.distanceSquaredTo(p);
                if ( tmp < championSquar) {
                    championSquar = tmp;
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
    }
    
}
