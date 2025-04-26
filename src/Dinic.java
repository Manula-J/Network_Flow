import java.util.*;

public class Dinic {
    private final Graph graph;
    private final int numNodes;
    private int[] level;
    private int[] next;

    public Dinic(Graph graph) {
        this.graph = graph;
        this.numNodes = graph.getNumNodes();
        this.level = new int[numNodes];
        this.next = new int[numNodes];
    }

    public long maxFlow() {
        long totalFlow = 0;
        int source = graph.getSource();
        int sink = graph.getSink();

        while (bfs(source, sink)) {
            Arrays.fill(next, 0);
            long flow;

            while ((flow = dfs(source, sink, Long.MAX_VALUE)) != 0) {
                totalFlow += flow;
            }
        }

        return totalFlow;
    }

    private boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        level[source] = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (Edge edge : graph.getEdges(node)) {
                if (edge.remainingCapacity() > 0 && level[edge.getTo()] == -1) {
                    level[edge.getTo()] = level[node] + 1;
                    queue.offer(edge.getTo());
                }
            }
        }
        return level[sink] == -1;
    }

    private long dfs(int at, int sink, long flow) {
        if (at == sink) {
            return flow;
        }

        List<Edge> edges = graph.getEdges(at);
        for (; next[at] < edges.size(); next[at]++) {
            Edge edge = edges.get(next[at]);
            if (edge.remainingCapacity() > 0 && level[edge.getTo()] == level[at] + 1) {
                long bottleneck = dfs(edge.getTo(), sink, Math.min(flow, edge.remainingCapacity()));
                if (bottleneck > 0) {
                    edge.augment(bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0;
    }
}
