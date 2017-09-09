import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private LineSegment[] lineSegments;
   
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>(); 
        ArrayList<Double> had = new ArrayList<Double>(); 
        Point[] old = points.clone();
        boolean isdone = false;
        // finds all line segments containing 4 points
        for (int k = 0; k < old.length; k++) {  //every point
             if (old[k] == null) throw new java.lang.NullPointerException();
         
             Arrays.sort(points, old[k].slopeOrder());
             
             for (int j = 1; j < points.length-2; j++) {
                 if (points[0].slopeTo(points[j]) == points[0].slopeTo(points[j+1]) && points[0].slopeTo(points[j]) == points[0].slopeTo(points[j+2])) {
                    int n = 3;
                    while (j + n < points.length && points[0].slopeTo(points[j + n]) == points[0].slopeTo(points[j])) {
                        n++;
                    }
                    for (double e : had) {
                        if (points[0].slopeTo(points[j]) == e) {
                            isdone = true;
                            break;
                        }
                    }
                    if (isdone) {
                        isdone = false;
                        break;
                    }
                    had.add(points[0].slopeTo(points[j]));
                    Arrays.sort(points,j, j + n);
                    
                    if (points[0].compareTo(points[j]) < 0)  {
                        lines.add(new LineSegment(points[0], points[j+n-1]));
                    }
                    else if (points[0].compareTo(points[j+n-1]) > 0) {
                        lines.add(new LineSegment(points[j], points[0]));
                    }
                    else {
                        lines.add(new LineSegment(points[j], points[j+n-1]));
                    }
                    break;
                 }     
             }
        }
        
        
        lineSegments = new LineSegment[lines.size()];
        int i = 0;
        for (LineSegment line : lines) {
           lineSegments[i++] = line; 
        }
        
    }
    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.length;
    }
    public LineSegment[] segments() {
        return lineSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
