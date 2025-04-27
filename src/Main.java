// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n... Starting Maximum Flow Finder ...");
        Scanner input = new Scanner(System.in);

        boolean runProgram = true;
        boolean showExplanation = false;
        int fileChoice = -1;
        String directoryName = "benchmarks";
        List<String> filesList = FlowNetworkParser.getFiles(directoryName);

        while (runProgram) {

            System.out.println("\nSelect a file to find the maximum flow:");
            for (int i = 0; i < filesList.size(); i++) {
                System.out.println((i + 1) + " - " + filesList.get(i));
            }

            while (fileChoice < 1 || fileChoice > filesList.size()) {
                System.out.print("\nEnter your choice (1-" + filesList.size() + "): ");
                try {
                    fileChoice = input.nextInt();
                    input.nextLine();
                } catch (Exception e) {
                    input.nextLine();
                    fileChoice = -1;
                }
                if (fileChoice < 1 || fileChoice > filesList.size()) {
                    System.out.println("Invalid choice. Try again.");
                }
            }

            String filePath = "benchmarks/" + filesList.get(fileChoice - 1);
            Graph graph = null;

            System.out.println("\nDo you want to have additional information (detailed steps)?");
            if ((fileChoice >= 9 && fileChoice <= 19) || (fileChoice >= 28 && fileChoice <= 39)) {
                System.out.println("Note: For large files like this, this may flood the console, slow down the algorithm, and make outputs hard to read.");
            }
            System.out.print("Enter 'y' for Yes, 'n' for No: ");
            String additionalInfo = input.nextLine();
            if (additionalInfo.equalsIgnoreCase("y") || additionalInfo.equalsIgnoreCase("yes")) {
                showExplanation = true;
            }

            try {
                graph = FlowNetworkParser.parseFile((filePath));
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error parsing the graph: " + e.getMessage());
            }

            System.out.println("\nThe " + filesList.get(fileChoice - 1) + " is successfully parsed\n");

//          Starting the timer to measure time taken to calculate maximum flow
            long start = System.currentTimeMillis();

//          Making an instance of the DinicAlgorithm and calling the maxFlow function to find maximum flow
            try {
                DinicAlgorithm dinicAlgorithm = new DinicAlgorithm(graph, showExplanation);
                long maxFlow = dinicAlgorithm.maxFlow();
                System.out.println("Maximum flow: " + maxFlow);
            } catch (Exception e) {
                System.out.println("Error calculating the max flow: " + e.getMessage());
            }

            long end = System.currentTimeMillis();
            System.out.println("Runtime (s): " + (end - start) / 1000);

            System.out.print("\nDo you want to find maximum flow of another graph? (y/n): ");
            String userChoice = input.nextLine();
            if (userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("yes")) {
                fileChoice = -1;
                showExplanation = false;
                continue;
            }
            runProgram = false;
        }

//        if (graph != null){
//            graph.printGraph();
//        }

        System.out.println("\n... Ending Maximum Flow Finder ...");
    }
}