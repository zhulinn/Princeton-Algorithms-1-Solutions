import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    TreeSet<Point2D> pointset; 
    public         PointSET() {                              // construct an empty set of points 
        pointset = new TreeSet<>();
    }
    
    public           boolean isEmpty() {                     // is the set empty? 
        return pointset.isEmpty();
    }
    public               int size() {                       // number of points in the set 
        return pointset.size();
    }
    public              void insert(Point2D p)  {            // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.NullPointerException();
        if (!pointset.contains(p)) pointset.add(p);
    }
    public           boolean contains(Point2D p) {             // does the set contain point p? 
        return pointset.contains(p);
    }
    public              void draw()    {                     // draw all points to standard draw 
        for (Point2D e : pointset) {
            e.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        if (rect == null) throw new  java.lang.NullPointerException();
        ArrayList<Point2D> inpoints = new ArrayList<>();
        for (Point2D e : pointset) {
            if (rect.distanceSquaredTo(e) == 0) inpoints.add(e);
        }
        return inpoints;
    }
    public           Point2D nearest(Point2D p)   {          // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new  java.lang.NullPointerException();
        Point2D nearpoint = null;
        double min = Double.POSITIVE_INFINITY;
        for (Point2D e : pointset) {
           if ( p.distanceSquaredTo(e) < min) {
               min =  p.distanceSquaredTo(e);
               nearpoint = e;
           }
        }
        return nearpoint;
    }

    public static void main(String[] args)  {               // unit testing of the methods (optional) 
        
    }
}
