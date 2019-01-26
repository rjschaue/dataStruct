import java.util.*;
import java.io.*;

/**
 * The class for the adjacency list data structure
 *
 * @author Joey Schauer
 */
public class AdjacencyList {
    /** the maximum capacity for the adjacency list */
    private static final int INITIAL_CAPACITY = 1000;
    /** the array of linked lists of edges */
    private LinkedList<Edge>[] adjacencyList;
    
    /**
     * the null constructor for the adjacency list
     */
    @SuppressWarnings("unchecked")
    public AdjacencyList() {
        adjacencyList = new LinkedList[INITIAL_CAPACITY];
        
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            adjacencyList[i] = new LinkedList<Edge>();
        }
    }
    
    /**
     * adds an edge to the adjacency list
     * @param edge is the edge to be added
     */
    public void addEdge(Edge edge) {
        adjacencyList[edge.getVertex1()].add(edge);
        adjacencyList[edge.getVertex2()].add(edge);
    } 
    
    /**
     * returns the linked list of edges associated with the given vertex
     * @param vertex is the verted to get edges for
     * @return is the linked list of edges for the given vertex
     */
    public LinkedList getVertexEdges(int vertex) {
        return adjacencyList[vertex];
    }
    
    /**
     * returns the size of the adjacency list
     * @return the size of the adjacency list
     */
    public int size() {
        int size = 0;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            if (adjacencyList[i].size() > 0) {
                size++;
            }
        }
        return size;
    }
    
    /**
     * prints out the adjacency list to the given output file
     * @param outputFile is the output file to print the adjacency list to
     */
    public void printAdjacencyList(PrintStream outputFile) {
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            if (adjacencyList[i].size() > 0) {
                int[] intArray = new int[1000]; 
                int intArraySize = 0;
                for (int k = 0; k < adjacencyList[i].size(); k++) {
                    Edge edge = adjacencyList[i].get(k);
                    if (edge.getVertex1() != i) {
                        intArray[intArraySize] = edge.getVertex1();
                        intArraySize++;
                    } else if (edge.getVertex2() != i) {
                        intArray[intArraySize] = edge.getVertex2();
                        intArraySize++;
                    }                                         
                }
                Arrays.sort(intArray, 0, intArraySize);
                for (int m = 0; m < intArraySize; m++) {
                    outputFile.printf("%4d", intArray[m]);
                    if (m < intArraySize - 1) {
                        outputFile.print(" ");
                    }                   
                } 
                outputFile.println();
            }
        }
    }
}