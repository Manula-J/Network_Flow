import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int numNodes;
    private final int source;
    private final int sink;
    private List<Edge>[] adjacencyList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.source = 0;
        this.sink = numNodes - 1;

        adjacencyList = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjacencyList[i] = new ArrayList<Edge>();
        }
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getSource() {
        return source;
    }

    public int getSink() {
        return sink;
    }

    public List<Edge> getEdges(int node) {
        return adjacencyList[node];
    }

    public void addEdge(int from, int to, int capacity) {
        Edge edge = new Edge(from, to, capacity);
        Edge residual = new Edge(to, from, 0);

        edge.setResidual(residual);
        residual.setResidual(edge);

        adjacencyList[from].add(edge);
        adjacencyList[to].add(residual);
    }

    public void printGraph() {
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i] != null) {
                System.out.print("Node " + i + " -> ");
                for (Edge edge : adjacencyList[i]) {
                    // Only print original (non-residual) edges for clarity
                    if (edge.getCapacity() > 0) {
                        System.out.print("[" + edge.getTo() + " (cap: " + edge.getCapacity() + ")] ");
                    }
                }
                System.out.println();
            }
        }
    }
}
