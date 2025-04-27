// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n... Starting Maximum Flow Finder ...\n");
        long start = System.currentTimeMillis();

        String filePath = "benchmarks/ladder_14.txt";
        Graph graph = null;

        try {
            graph = FlowNetworkParser.parseFile((filePath));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing the graph: " + e.getMessage());
        }

        System.out.println("The text file is successfully parsed");

        Dinic dinic = new Dinic(graph, false);
        long maxFlow = dinic.maxFlow();
        System.out.println("Maximum flow: " + maxFlow);


        long end = System.currentTimeMillis();
        System.out.println("Runtime (s): " + (end-start)/1000 );

        //        if (graph != null){
//            graph.printGraph();
//        }
    }
}