// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

public class Edge {
    // Source and Destination node of the edge
    private int from, to;

    // Residual edge corresponding to the edge
    private Edge residual;

    // Current flow through the edge
    private long currentFlow;

    // Maximum capacity of the edge
    private final long capacity;

    public Edge(int from, int to, long capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.currentFlow = 0;
    }

    // Getters
    public long getCapacity() {
        return capacity;
    }
    public int getTo() {
        return to;
    }
    public int getFrom() {
        return from;
    }

    // Setters
    public void setResidual(Edge residual) {
        this.residual = residual;
    }

    /*
    *  Returns the remaining capacity of the edge
    *
    *  @return  Remaining capacity
    */
    public long remainingCapacity() {
        return capacity - currentFlow;
    }

    /*
    *  Augments the flow along the edge by the specified bottleneck flow value
    *
    *  @param   bottleneckFlow - The amount of flow to add to the edge
    */
    public void augment(long bottleneckFlow) {
        currentFlow += bottleneckFlow;
        residual.currentFlow -= bottleneckFlow;
    }
}
