import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
   private int n;
   private Node first;
   private Node last;
   
   // helper linked list class
   private class Node {
       private Item item;
       private Node next;
       private Node previous;
   }

   public Deque()                           // construct an empty deque
   {
       first = null;
       last = null;
       n = 0;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
       return n==0;
   }
  
   public int size()                        // return the number of items on the deque
   {
       return n;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if (item == null) throw new NullPointerException("Item is null!");
       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       if (isEmpty()) 
           last = first;
       else 
           oldfirst.previous = first;
       n++;
   }
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null) throw new NullPointerException();
       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.next = null;
       last.previous = oldlast;
       if (isEmpty()) 
           first = last;
       else           
           oldlast.next = last;
       n++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
       if (isEmpty()) throw new NoSuchElementException ();
       Item item = first.item;
       first = first.next;
       n--;
       if (isEmpty()) 
           last = null;   // to avoid loitering
       else 
           first.previous = null;
       return item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if (isEmpty()) throw new NoSuchElementException ();
       Item item = last.item;
       last = last.previous;
       n--;
       if (isEmpty()) 
           first = null;   // to avoid loitering
       else 
           last.next = null;
       return item;
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new ListIterator();  
   }
   // an iterator, doesn't implement remove() since it's optional
   private class ListIterator implements Iterator<Item> {
       private Node current = first;

       public boolean hasNext()  { return current != null;                     }
       public void remove()      { throw new UnsupportedOperationException();  }

       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next; 
           return item;
       }
   }
   /*
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<Integer> list = new Deque<Integer>();
       for (int  i = 0; i <101; i++ )
       {
           list.addFirst(i);
           list.addLast(i);
           
       }
       for ( int e : list)
           System.out.println(e);
       
       System.out.println("delete");
       for (int  i = 0; i <101; i++ )
       {
           list.removeFirst();
          
           
       }
       
       for ( int e : list)
           System.out.println(e);

   }*/
}