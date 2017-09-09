import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
    private LineSegment[] lines;

    
    public BruteCollinearPoints(Point[] points)  {  // finds all line segments containing 4 points
        LinkedList<LineSegment> lineSegments = new LinkedList<LineSegment>();


        if (points == null) throw new java.lang.NullPointerException();
        for (int p = 0; p < points.length; p++) {
            
            if (points[p] == null) throw new java.lang.NullPointerException();
            
            for (int q = p + 1; q < points.length; q++) {
                
                if (points[q] == null) throw new java.lang.NullPointerException();
                if (points[p].compareTo(points[q]) == 0)  throw new java.lang.IllegalArgumentException();
                
                for (int r = q + 1; r < points.length; r++) {
                    
                    if (points[r] == null) throw new java.lang.NullPointerException();
                    if (points[p].compareTo(points[r]) == 0 || points[q].compareTo(points[r]) == 0 ) throw new java.lang.IllegalArgumentException();
                    
                    if (points[p].slopeTo(points[q]) != points[p].slopeTo(points[r])) continue; // 3 points in line
                    for (int s = r + 1; s < points.length; s++) {
                        
                        if (points[s] == null) throw new java.lang.NullPointerException();
                        if (points[p].compareTo(points[s]) == 0 || points[q].compareTo(points[s]) == 0 || points[r].compareTo(points[s]) == 0 ) throw new java.lang.IllegalArgumentException();     

                        
                        if (points[p].slopeTo(points[r]) != points[p].slopeTo(points[s])) continue; //4 points in line
   
                        
                        
                        Point fourpoints[] = {points[p], points[q], points[r], points[s]};                
                        lineSegments.add(formline(fourpoints));
                    }  
                }
            }
        }
        lines = new LineSegment[lineSegments.size()];
        int i = 0;
        for(LineSegment line : lineSegments) {
            lines[i++] = line;
        }
    }
    public int numberOfSegments() {

        return lines.length;       // the number of line segments
        
    }
    public LineSegment[] segments()   {
        
        return lines;
        
    }
    
    private LineSegment formline(Point[] points) {
        Arrays.sort(points);

        return new LineSegment(points[0], points[3]);
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
