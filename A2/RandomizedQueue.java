import java.util.NoSuchElementException;


import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on bag

    public RandomizedQueue()                 // construct an empty randomized queue
   {
       a = (Item[]) new Object[2];
       n = 0;
   }
   public boolean isEmpty()                 // is the queue empty?
   {
       return n == 0;
   }
   public int size()                        // return the number of items on the queue
   {
       return n;
   }
   
   private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < n; i++)
        temp[i] = a[i];
    a = temp;
}
   
   public void enqueue(Item item)           // add the item
   {
       if (item == null) throw new NullPointerException();
       if (n == a.length) resize(2*a.length);    // double size of array if necessary
       a[n++] = item;   
   }
   public Item dequeue()                    // remove and return a random item
   {
       if (n==0) throw new NoSuchElementException();
       int i = StdRandom.uniform(n);
       Item item = a[i];
       a[i] = a[n-1];
       a[n--] = null;
       if ( n == a.length/4) resize(a.length/2);
       return item;
   }
   public Item sample()                     // return (but do not remove) a random item
   { 
       if (n==0) throw new NoSuchElementException();
       int i = StdRandom.uniform(n);
       return a[i];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new ArrayIterator();
   }
   
   private class ArrayIterator implements Iterator<Item> {
       private int i = 0;
       
       public ArrayIterator() {
           if (n > 0) {
               Item[] b = (Item[]) new Object[n];
               for (i = 0; i < n; i++){
                   b[i] = a[i];
               }
               StdRandom.shuffle(b,0,n);
           }
               
       }
       
       public boolean hasNext() { return i < n; }
       public void remove() { throw new UnsupportedOperationException(); }
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           return a[i++];
       
       }
   }
/*
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<Integer> list = new RandomizedQueue<Integer>();
       for (int  i = 0; i <60; i++ )
       {
           list.enqueue(i);
           
           
       }
       for (int  i = 0; i <50; i++ )
       {
          int b = list.dequeue();
           
          System.out.println(i+": "+b);
       }System.out.println("delete");
       for (int e : list) {
           System.out.println("1: "+e);
       }
       for (int e : list) {
           System.out.println("2: "+e);
       }
       System.out.println("delete");
   }*/
}