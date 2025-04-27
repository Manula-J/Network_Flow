// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.util.ArrayList;
import java.util.List;

public class Graph {
    // Number of nodes in the graph
    private final int numNodes;

    // Source node index of the graph
    private final int source;

    // Sink node index of the graph
    private final int sink;

    // List to store edges for each node
    private List<Edge>[] adjacencyList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.source = 0;
        this.sink = numNodes - 1;

        // Initialize the adjacency list for each node
        adjacencyList = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjacencyList[i] = new ArrayList<Edge>();
        }
    }

    // Getters
    public int getNumNodes() {
        return numNodes;
    }
    public int getSource() {
        return source;
    }
    public int getSink() {
        return sink;
    }

    // Get the list of edges for the give node
    public List<Edge> getEdges(int node) {
        return adjacencyList[node];
    }

    /*
    *  Adds an edge between two given nodes with a specified capacity
    *  Also create a residual edge in the opposite direction with 0 initial capacity
    *
    *  @param   from      - index of the starting node
    *  @param   to        - index of the ending node
    *  @param   capacity  - maximum capable flow on the edge
    */
    public void addEdge(int from, int to, int capacity) {
        // Create both a forward and residual edge
        Edge edge = new Edge(from, to, capacity);
        Edge residual = new Edge(to, from, 0);

        // Set residual edge as the reverse of the original and vice versa
        edge.setResidual(residual);
        residual.setResidual(edge);

        // Add both edges to the adjacency list
        adjacencyList[from].add(edge);
        adjacencyList[to].add(residual);
    }

    /*
     *  Prints the graph's edges in the console
     *  This method is not used in the current implementation as of now, but can be helpful in debugging
     */
    public void printGraph() {
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i] != null) {
                System.out.print("Node " + i + " -> ");
                for (Edge edge : adjacencyList[i]) {
                    // Only print original (non-residual) edges
                    if (edge.getCapacity() > 0) {
                        System.out.print("[" + edge.getTo() + " (cap: " + edge.getCapacity() + ")] ");
                    }
                }
                System.out.println();
            }
        }
    }
}
