import java.util.*;

public class Dinic {
    private final Graph graph;
    private final int numNodes;
    private int[] level;
    private int[] next;
    private final boolean showExplanation;

    public Dinic(Graph graph, boolean showExplanation) {
        this.graph = graph;
        this.numNodes = graph.getNumNodes();
        this.level = new int[numNodes];
        this.next = new int[numNodes];
        this.showExplanation = showExplanation;
    }

    public long maxFlow() {
        long totalFlow = 0;
        int source = graph.getSource();
        int sink = graph.getSink();

        List<String> augmentingSteps = new ArrayList<>();
        boolean augmentingStepsPrinted = false;

        while (bfs(source, sink)) {
            Arrays.fill(next, 0);
            long flow;
            List<Integer> path = new ArrayList<>();

            if (showExplanation) {
                while ((flow = dfsWithExplanation(source, sink, Long.MAX_VALUE, path)) != 0) {
                    totalFlow += flow;
                    try {
                        augmentingSteps.add("Flow pushed: " + flow + " along path: " + path);
                        path.clear();
                    } catch (OutOfMemoryError e) {
                        printAugmentingSteps(augmentingSteps, augmentingStepsPrinted);
                        augmentingStepsPrinted = true;
                        augmentingSteps.clear();

                        augmentingSteps.add("Flow pushed: " + flow + " along path: " + path);
                        path.clear();
                    }
                }
            } else {
                while ((flow = dfsWithoutExplanation(source, sink, Long.MAX_VALUE)) != 0) {
                    totalFlow += flow;
                }
            }
        }
        printAugmentingSteps(augmentingSteps, augmentingStepsPrinted);
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
                    if (edge.getTo() == sink) {
                        return true;
                    }
                    queue.offer(edge.getTo());
                }
            }
        }
        return level[sink] != -1;
    }

    private long dfsWithExplanation(int at, int sink, long flow, List<Integer> currentPath) {
        if (at == sink) {
            currentPath.add(sink);
            return flow;
        }

        List<Edge> edges = graph.getEdges(at);
        for (; next[at] < edges.size(); next[at]++) {
            Edge edge = edges.get(next[at]);
            long availableCapacity = edge.remainingCapacity();

            if (availableCapacity > 0 && level[edge.getTo()] == level[at] + 1) {
                currentPath.add(at);
                long bottleneck = dfsWithExplanation(edge.getTo(), sink, Math.min(flow, availableCapacity), currentPath);

                if (bottleneck > 0) {
                    edge.augment(bottleneck);
                    return bottleneck;
                }

                currentPath.remove(currentPath.size() - 1);
            }
        }
        return 0;
    }

    private long dfsWithoutExplanation(int at, int sink, long flow) {
        if (at == sink) {
            return flow;
        }

        List<Edge> edges = graph.getEdges(at);
        for (; next[at] < edges.size(); next[at]++) {
            Edge edge = edges.get(next[at]);
            long availableCapacity = edge.remainingCapacity();

            if (availableCapacity > 0 && level[edge.getTo()] == level[at] + 1) {
                long bottleneck = dfsWithoutExplanation(edge.getTo(), sink, Math.min(flow, availableCapacity));

                if (bottleneck > 0) {
                    edge.augment(bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0;
    }

    private void printAugmentingSteps(List<String> augmentingSteps, boolean alreadyPrintedBefore) {
        if (showExplanation) {
            if (!alreadyPrintedBefore) {
                System.out.println("... Augmenting Steps ...\n");
            }
            for (String step : augmentingSteps) {
                System.out.println(step);
            }
            System.out.println();
        }
    }
}
