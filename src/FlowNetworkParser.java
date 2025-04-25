import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlowNetworkParser {

    public static FlowNetwork parseFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int nodeNum = Integer.parseInt(reader.readLine().trim());
        FlowNetwork graph = new FlowNetwork(nodeNum);

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
}
