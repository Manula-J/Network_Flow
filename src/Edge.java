public class Edge {
    private int from, to;
    private Edge residual;
    private long currentFlow;
    private final long capacity;

    public Edge(int from, int to, long capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public boolean isResidual() {
        return capacity == 0;
    }

    public long remainingCapacity() {
        return capacity - currentFlow;
    }

    public void setCurrentFlow(long currentFlow) {
        this.currentFlow = currentFlow;
    }

    public void setResidual(Edge residual) {
        this.residual = residual;
    }
}
