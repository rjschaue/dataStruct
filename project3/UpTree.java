import java.util.ArrayList;

/**
 * A class that implements the up tree algorithm
 *
 * @author Joey Schauer
 */
public class UpTree {
    /** gets the parent of the up tree */
    int[] parent;
    
    /**
     * constructs the up tree
     *
     * @param size is the size of the up tree
     */
    public UpTree(int size) {
        parent = new int[size];
        makeSet(size);
    }

    /**
     * makes a set with the given size
     * @param size is the size of the sets
     */
    public void makeSet(int size) {
        for(int i = 0; i < size; i++) {
            parent[i] = -1;
        }
    }

    /**
     * joins the two up trees together
     * @param x is one of the trees
     * @param y is the other tree
     */
    public int union(int x, int y) {
        if (parent[x] <= parent[y]) {
            parent[x] = parent[x] + parent[y];
            parent[y] = x;
            return x;
        } else {
            parent[y] = parent[x] + parent[y];
            parent[x] = y;
            return y;
        }
    }

    /**
     * finds the tree that holds the given value
     * @param x is the value to find
     */
    public int find(int x) {
        while(parent[x] > -1) {
            x = parent[x];
        }
        return x;
    }

}