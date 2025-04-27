// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n... Starting Maximum Flow Finder ...\n");
        Scanner input = new Scanner(System.in);

        int fileChoice = -1;
        String directoryName = "benchmarks";
        List<String> filesList = FlowNetworkParser.getFiles(directoryName);

        System.out.println("Select a file to find the maximum flow:");
        for (int i = 0; i < filesList.size(); i++) {
            System.out.println((i + 1) + " - " + filesList.get(i));
        }

        while (fileChoice < 1 || fileChoice > filesList.size()) {
            System.out.print("\nEnter your choice (1-" + filesList.size() + "): ");
            try {
                fileChoice = input.nextInt();
            } catch (Exception e) {
                input.nextLine();
                fileChoice = -1;
            }
            if (fileChoice < 1 || fileChoice > filesList.size()) {
                System.out.println("Invalid choice. Try again.");
            }
        }

        long start = System.currentTimeMillis();


        String filePath = "benchmarks/" + filesList.get(fileChoice - 1);
        Graph graph = null;

        try {
            graph = FlowNetworkParser.parseFile((filePath));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing the graph: " + e.getMessage());
        }

        System.out.println("The text file is successfully parsed\n");

        Dinic dinic = new Dinic(graph, true);
        long maxFlow = dinic.maxFlow();
        System.out.println("Maximum flow: " + maxFlow);


        long end = System.currentTimeMillis();
        System.out.println("Runtime (s): " + (end-start)/1000 );

        //        if (graph != null){
//            graph.printGraph();
//        }
    }
}