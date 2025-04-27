import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Starting Maximum Flow Finder...");
        long start = System.currentTimeMillis();

        String filePath = "benchmarks/ladder_13.txt";
        Graph graph = null;

        try {
            graph = FlowNetworkParser.parseFile((filePath));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing the graph: " + e.getMessage());
        }

        System.out.println("The text file is successfully parsed");

        Dinic dinic = new Dinic(graph);
        long maxFlow = dinic.maxFlow();
        System.out.println("Maximum flow: " + maxFlow);
//        if (graph != null){
//            graph.printGraph();
//        }

        long end = System.currentTimeMillis();
        System.out.println("Runtimes (s): " + (end-start)/1000 );
    }
}