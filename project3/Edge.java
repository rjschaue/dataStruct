/**
 * Edge class to hold the values for each edge in project 3
 * 
 * @author Joey Schauer
 */
public class Edge {
    /** the first vertex of the edge */
    private int vertex1;
    /** the second vertex of the edge */
    private int vertex2;
    /** the weight of the edge */
    private double weight;
    /** the next edge */
    private Edge next;
    
    /** 
     * the constructor for edge
     * @param vertex1 is the first vertex
     * @param vertex2 is the second vertex
     * @param weight is the weight of the edge
     * @param next is the next edge
     */
    public Edge(int vertex1, int vertex2, double weight, Edge next) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        setNext(next);
    }
    
    /**
     * sets next to the given edge
     * @param next is the given edge
     */
    public void setNext(Edge next) {
        this.next = next;
    }
    
    /**
     * returns the next edge
     * @return the next edge
     */
    public Edge getNext() {
        return next;
    }
    
    /**
     * returns the first vertex of the edge
     * @return the first vertex of the edge
     */
    public int getVertex1() {
        return vertex1;
    }
    
    /**
     * returns the second vertex of the edge
     * @return the second vertex of the edge
     */
    public int getVertex2() {
        return vertex2;
    }
    
    /**
     * returns the weight of the edge
     * @return the weight of the edge
     */
    public double getWeight() {
        return weight;
    }
}