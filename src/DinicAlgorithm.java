// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.util.*;

public class DinicAlgorithm {
    // Flow network represented by a graph
    private final Graph graph;

    // Number of nodes of the graph
    private final int numNodes;

    // Level array used for BFS
    private int[] level;

    // Next array used for DFS
    private int[] next;

    // Flag to indicate if augmentingSteps should be printed
    private final boolean showExplanation;

    public DinicAlgorithm(Graph graph, boolean showExplanation) {
        this.graph = graph;
        this.numNodes = graph.getNumNodes();
        this.level = new int[numNodes];
        this.next = new int[numNodes];
        this.showExplanation = showExplanation;
    }

    /*
    *  Executes Dini's algorithm to calculate the maximum flow by performing a series of BFS and DFS
    *
    *  @return  The maximum flow possible from source to sink
    */
    public long maxFlow() {
        long totalFlow = 0;
        int source = graph.getSource();
        int sink = graph.getSink();

        List<String> augmentingSteps = new ArrayList<>();
        boolean augmentingStepsPrinted = false;

        // While there is a path from source to sink (found using BFS)
        while (bfs(source, sink)) {
            Arrays.fill(next, 0);   // Reset the next pointers for DFS
            long flow;
            List<Integer> path = new ArrayList<>();

            // If showExplanation is true, save the steps augmentingSteps list
            if (showExplanation) {
                // Perform DFS with detailed steps
                while ((flow = dfsWithExplanation(source, sink, Long.MAX_VALUE, path)) != 0) {
                    totalFlow += flow;
                    try {
                        augmentingSteps.add("Flow pushed: " + flow + " along path: " + path);
                        path.clear();
                    } catch (OutOfMemoryError e) {
                        // In case augmentingSteps runs out of memory, print the current values and reset the list
                        printAugmentingSteps(augmentingSteps, augmentingStepsPrinted);
                        augmentingStepsPrinted = true;
                        augmentingSteps.clear();

                        augmentingSteps.add("Flow pushed: " + flow + " along path: " + path);
                        path.clear();
                    }
                }
            } else {
                // Performs DFS without detailed steps
                while ((flow = dfsWithoutExplanation(source, sink, Long.MAX_VALUE)) != 0) {
                    totalFlow += flow;
                }
            }
        }
        // Print the augmentingSteps
        printAugmentingSteps(augmentingSteps, augmentingStepsPrinted);
        return totalFlow;
    }

    /*
    *  Performs BFS to find the shortest augmenting path to sink from source
    *
    *  @param   source  - Source node of the graph
    *  @param   sink    - Final node to be reached of the graph
    *  @return  true is a path was found, otherwise false
    */
    private boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);     // Reset the level array
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(source);    // Start from the source node
        level[source] = 0;

        // BFS loop to level the nodes
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
        return level[sink] != -1;   // Return true if the sink is reached
    }

    /*
    *  Performs DFS to find the augmenting path and push flow along it, with having step by step explanations
    *
    *  @param   at          - Current node
    *  @param   sink        - Destination node
    *  @param   flow        - Flow which can be pushed through the path
    *  @param   currentPath - List of nodes traversed along the current path
    *  @return  Flow pushed along the path
    */
    private long dfsWithExplanation(int at, int sink, long flow, List<Integer> currentPath) {
        if (at == sink) {
            currentPath.add(sink);  // Add the sink to the path
            return flow;    // Return the flow when reaching the sink
        }

        List<Edge> edges = graph.getEdges(at);
        for (; next[at] < edges.size(); next[at]++) {
            Edge edge = edges.get(next[at]);
            long availableCapacity = edge.remainingCapacity();

            // If capacity available and edge is the shortest path
            if (availableCapacity > 0 && level[edge.getTo()] == level[at] + 1) {
                currentPath.add(at);    // Add the current node to path
                long bottleneck = dfsWithExplanation(edge.getTo(), sink, Math.min(flow, availableCapacity), currentPath);

                // If path valid, augment the flow
                if (bottleneck > 0) {
                    edge.augment(bottleneck);
                    return bottleneck;
                }

                // If path is invalid, backtrack and remove the nodes
                currentPath.remove(currentPath.size() - 1);
            }
        }
        return 0;   // If no path found
    }

    /*
     *  Performs DFS to find the augmenting path and push flow along it, without having step by step explanations
     *
     *  @param   at          - Current node
     *  @param   sink        - Destination node
     *  @param   flow        - Flow which can be pushed through the path
     *  @return  Flow pushed along the path
     */
    private long dfsWithoutExplanation(int at, int sink, long flow) {
        if (at == sink) {
            return flow;    // Return the flow when reaching the sink
        }

        List<Edge> edges = graph.getEdges(at);
        for (; next[at] < edges.size(); next[at]++) {
            Edge edge = edges.get(next[at]);
            long availableCapacity = edge.remainingCapacity();

            // If capacity available and edge is the shortest path
            if (availableCapacity > 0 && level[edge.getTo()] == level[at] + 1) {
                long bottleneck = dfsWithoutExplanation(edge.getTo(), sink, Math.min(flow, availableCapacity));

                // If path valid, augment the flow
                if (bottleneck > 0) {
                    edge.augment(bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0;   // If no path found
    }

    /*
    *  Prints the augmenting steps if detailed explanation is true
    *
    *  @param   augmentingSteps         - List of augmenting paths to reach sink with maximum flow in each path
    *  @param   alreadyPrintedBefore    - Flag indicating if augmenting steps are printed earlier
    */
    private void printAugmentingSteps(List<String> augmentingSteps, boolean alreadyPrintedBefore) {
        if (showExplanation) {
            if (!alreadyPrintedBefore) {
                System.out.println("... Augmenting Steps ...\n");   // Only print once
            }
            for (String step : augmentingSteps) {
                System.out.println(step);   // Print each path
            }
            System.out.println();
        }
    }
}
