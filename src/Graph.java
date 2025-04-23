import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int numNodes;
    private int source;
    private int sink;
    private List<Edge>[] adjacencyList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.source = 0;
        this.sink = numNodes - 1;
    }

    public void addEdge(int from, int to, int capacity) {
        Edge edge = new Edge(from, to, capacity);
        Edge residual = new Edge(from, to, 0);
        edge.setResidual(residual);
        residual.setResidual(edge);

        if (adjacencyList[from] == null) {
            adjacencyList[from] = new ArrayList<>();
        }
        adjacencyList[from].add(edge);

        if (adjacencyList[to] == null) {
            adjacencyList[to] = new ArrayList<>();
        }
        adjacencyList[to].add(residual);

    }
}
