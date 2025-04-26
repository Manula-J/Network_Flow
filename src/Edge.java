public class Edge {
    private int from, to;
    private Edge residual;
    private long currentFlow;
    private final long capacity;

    public Edge(int from, int to, long capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.currentFlow = 0;
    }

    public boolean isResidual() {
        return capacity == 0;
    }

    public long remainingCapacity() {
        return capacity - currentFlow;
    }

    public long getCapacity() {
        return capacity;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public void setResidual(Edge residual) {
        this.residual = residual;
    }

    public void augment(long bottleneckFlow) {
        currentFlow += bottleneckFlow;
        residual.currentFlow -= bottleneckFlow;
    }
}
