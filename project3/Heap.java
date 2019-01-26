import java.io.*;

/**
 * This class implements the heap data structure
 * 
 * @author Joey Schauer
 */
public class Heap {
    /** the maximum capacity for the heap */
    private static final int INITIAL_CAPACITY = 5000;
    /** the array of edges for this heap */
    private Edge[] element;
    /** the size of the heap */
    private int size;
    
    /**
     * null constructor for the heap
     */
    public Heap() {
        this(INITIAL_CAPACITY);
    }
    
    /**
     * constructor with a given capacity
     * @param the capacity for the heap
     */
    public Heap(int capacity) {
        element = new Edge[capacity];
        size = 0;
    }
      
    /**
     * inserts the given edge into the heap
     * @param e is the edge to be inserted
     */
    public void insert(Edge e) {
        element[size] = e;
        size++;
        upHeap(size-1);
    }
      
    /**
     * private method up heap to maintain the integrity of the heap
     * @param i is the element to manipulate
     */
    private void upHeap(int i) {
        if (i > 0) {
            if (element[(i - 1) / 2].getWeight() > element[i].getWeight()) {
                swap((i - 1) / 2, i);
                upHeap((i - 1) / 2);
            }
        }
    }
      
    /**
     * private method to swap two elements
     * @param i1 is the first element to swap
     * @param i2 is the second element to swap
     */
    private void swap(int i1, int i2) {
        Edge e = element[i1];
        element[i1] = element[i2];
        element[i2] = e;
    }
    
    /**
     * deletes the smallest value in the heap
     * @return the deleted edge
     */
    public Edge deleteMin() {
        Edge e = element[0];
        size = size - 1;
        swap(0, size);
        downHeap(0);
        return e;
    }
    
    /**
     * private method down heap to maintain the integrity of the heap
     * @param i is the element to manipulate
     */
    private void downHeap(int i) {
        int k = 0;
        if (2 * i + 2 < size) {
            if (element[2 * i + 2].getWeight() <= element[2 * i + 1].getWeight()) {
                k = 2 * i + 2;
            } else {
                k = 2 * i + 1;
            }
        } else if (2 * i + 1 < size) {
            k = 2 * i + 1;
        }
        if (k > 0 && element[i].getWeight() > element[k].getWeight()) {
            swap(i, k);
            downHeap(k);
        }
    }
    
    /**
     * prints the heap in a specified orientation
     * @param outputFile is the file to output the heap to
     */
    public void printHeap(PrintStream outputFile) {
        for (int i = 0; i < size; i++) {
            if (element[i].getVertex1() <= element[i].getVertex2()) {
                outputFile.printf("%4d", element[i].getVertex1());
                outputFile.print(" ");
                outputFile.printf("%4d", element[i].getVertex2());
                outputFile.println();
            } else {
                outputFile.printf("%4d", element[i].getVertex2());
                outputFile.print(" ");
                outputFile.printf("%4d", element[i].getVertex1());
                outputFile.println();
            }
            
        }      
    }
}