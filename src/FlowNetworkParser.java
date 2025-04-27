import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlowNetworkParser {

    public static Graph parseFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int nodeNum = Integer.parseInt(reader.readLine().trim());
        Graph graph = new Graph(nodeNum);

        String line;
        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.trim().split("\\s+");
            if (data.length != 3) {
                continue;
            }

            int from = Integer.parseInt(data[0]);
            int to = Integer.parseInt(data[1]);
            int capacity = Integer.parseInt(data[2]);

            graph.addEdge(from, to, capacity);
        }

        reader.close();
        return graph;
    }

    public static List<String> getFiles(String directoryName) {
        File dir = new File(directoryName);
        List<String> fileNames = new ArrayList<>();

        if (dir.exists()) {
            File[] filesList = dir.listFiles((dir1, name) -> name.endsWith(".txt"));

            if (filesList != null) {

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

    private static String extractPrefix(String fileName) {
        int underscoreIndex = fileName.indexOf("_");
        if (underscoreIndex != 0) {
            return fileName.substring(0, underscoreIndex);
        }
        return fileName;
    }

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
