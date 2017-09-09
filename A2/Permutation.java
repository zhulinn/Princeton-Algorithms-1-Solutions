import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.*;

public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        String[] s = StdIn.readAllStrings();
        
       
        RandomizedQueue<String> list = new RandomizedQueue<String>();
        for (int i = 0; i < s.length; i++){
            list.enqueue(s[i]);
          
        }
            
        for (int i = 0; i < n; i++){
            StdOut.println(list.dequeue());
        }
        

    }

}
