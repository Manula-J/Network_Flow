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

        // Variable to determine if the program should continue running
        boolean runProgram = true;

        // Variable to determine if detailed steps of the calculation is required
        boolean showExplanation = false;

        // Variable to store user's file selection
        int fileChoice = -1;

        // Directory containing benchmark files
        String directoryName = "benchmarks";

        // Variable to store list of files available for selection
        List<String> filesList = FlowNetworkParser.getFiles(directoryName);

        // Looping to let the user again find the maximum flow of another graph if required
        while (runProgram) {
            // Display the files containing network flow graphs
            System.out.println("\nSelect a file to find the maximum flow:");
            for (int i = 0; i < filesList.size(); i++) {
                System.out.println((i + 1) + " - " + filesList.get(i));
            }

            // Prompt the user to select a file and handle invalid input
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

            // Form the file name and initialize the graph
            String filePath = "benchmarks/" + filesList.get(fileChoice - 1);
            Graph graph = null;

            // Prompt the user if detailed steps of the calculation are necessary
            System.out.println("\nDo you want to have additional information (detailed steps)?");
            if ((fileChoice >= 9 && fileChoice <= 19) || (fileChoice >= 28 && fileChoice <= 39)) {
                System.out.println("Note: For large files like this, this may flood the console, slow down the algorithm, and make outputs hard to read.");
            }
            System.out.print("Enter 'y' for Yes, 'n' for No: ");
            String additionalInfo = input.nextLine();
            if (additionalInfo.equalsIgnoreCase("y") || additionalInfo.equalsIgnoreCase("yes")) {
                showExplanation = true;
            }

            // Load the selected file and parse it into Graph
            try {
                graph = FlowNetworkParser.parseFile((filePath));
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error parsing the graph: " + e.getMessage());
            }

            // If graph exists, print the key information of the graph
            if (graph != null) {
                graph.printGraphInfo(filesList.get(fileChoice - 1));
            }

            // Start the timer to measure time taken to calculate maximum flow
            long start = System.currentTimeMillis();

            // Instantiate the DinicAlgorithm and calculate the maximum flow of the given graph
            try {
                DinicAlgorithm dinicAlgorithm = new DinicAlgorithm(graph, showExplanation);
                long maxFlow = dinicAlgorithm.maxFlow();
                System.out.println("Maximum flow: " + maxFlow);
            } catch (Exception e) {
                System.out.println("Error calculating the max flow: " + e.getMessage());
            }

            // End the time calculation and output the time taken
            long end = System.currentTimeMillis();
            double timeInSeconds = (end - start) / 1000.0;
            System.out.println("Runtime (s): " + String.format("%.3f", timeInSeconds));

            // Prompt the user if he/she wants to process another graph
            System.out.print("\nDo you want to find maximum flow of another graph? (y/n): ");
            String userChoice = input.nextLine();
            if (userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("yes")) {
                // Reset variables for next maximum flow calculation
                fileChoice = -1;
                showExplanation = false;
                continue;
            }

            // Terminate the program by exiting the while loop
            runProgram = false;
        }

        System.out.println("\n... Terminating Maximum Flow Finder ...");
    }
}