import java.util.*;
import java.io.*;

/**
 * Main class for project 3
 *
 * @author Joey Schauer
 */
public class proj3 {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        /** creates a scanner for system input */
		Scanner input = new Scanner(System.in);
        /** prompts for input file name and stores it's value */
		System.out.print("Enter input file name: ");
		String inputFileName = input.nextLine();
        /** prompts for output file name and stores it's value */
		System.out.print("Enter output file name: ");
		String outputFileName = input.nextLine();    
        
        
        try {
            /** scanner for input file */
			Scanner fileReader = new Scanner(new File(inputFileName));
            /** creates output file to send values to */
            PrintStream outputFile = new PrintStream(new File(outputFileName));    
            /** creates a graph to send edges to */
            AdjacencyList graph = new AdjacencyList();
            /** creates a heap to send edges to */
            Heap heap = new Heap();
            
            /** reads through the file line by line to create new edges and insert them into the graph and heap */
            while(fileReader.hasNextLine()) {
                int vertex1 = fileReader.nextInt();
                if (!fileReader.hasNextInt()) {
                    break;
                }
                int vertex2 = fileReader.nextInt();
                double weight = fileReader.nextDouble();
                Edge edge = new Edge(vertex1, vertex2, weight, null);
                graph.addEdge(edge);
                heap.insert(edge);
                fileReader.nextLine();
            }
            /** prints out the heap */
            heap.printHeap(outputFile);
            /** calls the kriskal MST algorithm */
            LinkedList<Edge> edgesMST = kruskalMST(graph);
            /** prints out the edges in the MST */
            for (int i = 0; i < graph.size(); i++) {
                LinkedList<Edge> edges = new LinkedList<Edge>();
                for (int k = 0; k < edgesMST.size(); k++) {                    
                    if (edgesMST.get(k).getVertex1() == i || edgesMST.get(k).getVertex2() == i) {
                        edges.add(edgesMST.remove(k));
                        k--;
                    }
                }
                edges.sort(new Comparator<Edge>() {
                    @Override
                    public int compare(Edge edge1, Edge edge2) {
                        int vertexEdge1 = 0;
                        int vertexEdge2 = 0;
                        if (edge1.getVertex1() == edge2.getVertex1()) {
                            vertexEdge1 = edge1.getVertex2();
                            vertexEdge2 = edge2.getVertex2();
                        } else if (edge1.getVertex1() == edge2.getVertex2()) {
                            vertexEdge1 = edge1.getVertex2();
                            vertexEdge2 = edge2.getVertex1();
                        } else if (edge1.getVertex2() == edge2.getVertex1()) {
                            vertexEdge1 = edge1.getVertex1();
                            vertexEdge2 = edge2.getVertex2();
                        } else if (edge1.getVertex2() == edge2.getVertex2()) {
                            vertexEdge1 = edge1.getVertex1();
                            vertexEdge2 = edge2.getVertex1();
                        }
                        if (vertexEdge1 > vertexEdge2) {
                            return 1;
                        } else if (vertexEdge1 < vertexEdge2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                for (int m = 0; m < edges.size(); m++) {
                    if (edges.get(m).getVertex1() <= edges.get(m).getVertex2()) {
                        outputFile.printf("%4d", edges.get(m).getVertex1());
                        outputFile.print(" ");
                        outputFile.printf("%4d", edges.get(m).getVertex2());
                        outputFile.println();
                    } else {
                        outputFile.printf("%4d", edges.get(m).getVertex2());
                        outputFile.print(" ");
                        outputFile.printf("%4d", edges.get(m).getVertex1());
                        outputFile.println();
                    }
            
                } 
            }
            /** prints out the edges in the adjacency list */
            graph.printAdjacencyList(outputFile);
			fileReader.close();                       
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
		}                  
		input.close();        
    }
    /**
     * This method uses the kruskal algorithm on a given graph
     * 
     * @param graph is the graph to be processed
     */     
    @SuppressWarnings("unchecked")
    public static LinkedList kruskalMST(AdjacencyList graph) {
        LinkedList<Edge> edgesMST = new LinkedList<Edge>();
        Heap heap = new Heap();
        for (int i = 0; i < graph.size(); i++) {
            for (int k = 0; k < graph.getVertexEdges(i).size(); k++) {
                LinkedList<Edge> edges = graph.getVertexEdges(i);
                Edge edge = edges.get(k);
                heap.insert(edge);
            }
        }
        int components = graph.size();
        UpTree upTree = new UpTree(components);
        while(components > 1) {
            Edge edge = heap.deleteMin();
            int u = upTree.find(edge.getVertex1());
            int v = upTree.find(edge.getVertex2());
            if (u != v) {
                upTree.union(u,v);
                edgesMST.add(edge);
                components--;
            }
        }
        return edgesMST;
    }
    
}