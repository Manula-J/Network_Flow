// Name - Manula Imantha Jayabodhi
// IIT ID - 20221047
// UOW ID - w2052695

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlowNetworkParser {

    /*
    *  Parses the flow network in the file to create the graph object
    *
    *  @param    filename - The name of the file to be parsed
    *  @return   A graph object representing the flow network
    */
    public static Graph parseFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read the number of nodes and initialize the graph
        int nodeNum = Integer.parseInt(reader.readLine().trim());
        Graph graph = new Graph(nodeNum);

        String line;
        while ((line = reader.readLine()) != null) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }

            // Parse each edge line (source, destination, capacity)
            String[] data = line.trim().split("\\s+");
            if (data.length != 3) {
                continue;
            }

            int from = Integer.parseInt(data[0]);
            int to = Integer.parseInt(data[1]);
            int capacity = Integer.parseInt(data[2]);

            // Add the parsed edge to the graph
            graph.addEdge(from, to, capacity);
        }

        reader.close();
        return graph;
    }

    /*
    *  Get the list of all the text filenames in the given directory
    *
    *  @param   directoryName - The name of the directory containing the files
    *  @return  List of the text filenames
    */
    public static List<String> getFiles(String directoryName) {
        File dir = new File(directoryName);
        List<String> fileNames = new ArrayList<>();

        // Checking if directory exists
        if (dir.exists()) {
            // Get the list of files in the directory
            File[] filesList = dir.listFiles((dir1, name) -> name.endsWith(".txt"));

            if (filesList != null) {
                // Sort the files by filename according to string and numeric parts
                Arrays.sort(filesList, (a, b) -> {
                    String prefixA = extractPrefix(a.getName());
                    String prefixB = extractPrefix(b.getName());
                    int numberA = extractNumber(a.getName());
                    int numberB = extractNumber(b.getName());

                    int cmp = prefixA.compareTo(prefixB);
                    if (cmp != 0) {
                        return cmp;
                    } else {
                        return Integer.compare(numberA, numberB);
                    }
                });

                // Add the sorted filenames to the list
                for (File file : filesList) {
                    fileNames.add(file.getName());
                }

            } else {
                System.out.println("No text files files found in " + directoryName);
            }
        } else {
            System.out.println("Directory " + directoryName + " does not exist.");
        }
        return fileNames;
    }

    /*
    *  Extracts the string part of the filename for sorting
    *
    *  @param   fileName - The name of the text file
    *  @return  The string part of the text filename
    */
    private static String extractPrefix(String fileName) {
        int underscoreIndex = fileName.indexOf("_");
        if (underscoreIndex != 0) {
            return fileName.substring(0, underscoreIndex);
        }
        return fileName;
    }

    /*
     *  Extracts the numeric part of the filename for sorting
     *
     *  @param   fileName - The name of the text file
     *  @return  The extracted number of the text filename
     */
    private static int extractNumber(String fileName) {
        try {
            int start = fileName.indexOf("_") + 1;
            int end = fileName.indexOf(".txt");
            String number = fileName.substring(start, end);
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }
}
