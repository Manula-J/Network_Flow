import java.util.ArrayList;

public class FlowNetwork {
    private int numNodes;
    private int source;
    private int sink;
    private ArrayList<Edge>[] adjacencyList;

    public FlowNetwork(int numNodes) {
        this.numNodes = numNodes;
        this.source = 0;
        this.sink = numNodes - 1;
        adjacencyList = new ArrayList[numNodes];
    }

    public void addEdge(int from, int to, int capacity) {
        Edge edge = new Edge(from, to, capacity);
        Edge residual = new Edge(to, from, 0);
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
