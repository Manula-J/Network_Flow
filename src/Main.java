import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String filePath = "benchmarks/ladder_1.txt";
        Graph graph = null;

        try {
            graph = FlowNetworkParser.parseFile((filePath));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing the graph: " + e.getMessage());
        }

        System.out.println("Parser working successfully");

        Dinic dinic = new Dinic(graph);
        long maxFlow = dinic.maxFlow();
        System.out.println("Maximum flow: " + maxFlow);
        if (graph != null){
            graph.printGraph();
        }
    }
}