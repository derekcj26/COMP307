import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VRPIO {

    public static VRPInstance loadInstance(File file) {
        String line;
        String[] segments;

        Map<Integer, VRPNode> nodes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // line 1: "NAME : xxx"
            reader.readLine(); // line 2: "COMMENT : xxx"
            reader.readLine(); // line 3: "TYPE : CVRP"

            line = reader.readLine(); // line 4: "DIMENSION : xxx"
            segments = line.split(" : ");
            int numNodes = Integer.valueOf(segments[1]);

            reader.readLine(); // line 5: "EDGE_WEIGHT_TYPE : EUC_2D"

            line = reader.readLine(); // line 6: "CAPACITY : xxx"
            segments = line.split(" : ");
            double capacity = Double.valueOf(segments[1]);

            reader.readLine(); // line 7: "NODE_COORD_SECTION"
            for (int i = 0; i < numNodes; i++) {
                // Read the coordinates of each node
                line = reader.readLine();
                segments = line.split("\\s+");

                int id = Integer.valueOf(segments[1]);
                double x = Double.valueOf(segments[2]);
                double y = Double.valueOf(segments[3]);

                VRPNode node = new VRPNode(id, x, y);
                nodes.put(id, node);
            }

            reader.readLine(); // "DEMAND_SECTION"
            for (int i = 0; i < numNodes; i++) {
                // Read the demand of each node
                line = reader.readLine();
                segments = line.split("\\s+");

                int id = Integer.valueOf(segments[0]);
                double demand = Double.valueOf(segments[1]);
                nodes.get(id).setDemand(demand);
            }

            reader.readLine(); // "DEPOT_SECTION"
            // Read the depot
            line = reader.readLine();
            segments = line.split("\\s+");
            int depotID = Integer.valueOf(segments[1]);

            return new VRPInstance(nodes, capacity, nodes.get(depotID));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static VRPSolution loadSolution(File file){
        String line;
        String[] segments;

        List<List<Integer>> routes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (true) {
                line = reader.readLine();
                if (!line.startsWith("Route"))
                    break;

                segments = line.split(": ");
                segments = segments[1].split("\\s+");

                List<Integer> route = new ArrayList<>();
                for (int i = 0; i < segments.length; i++) {
                    route.add(Integer.valueOf(segments[i]));
                }
                routes.add(route);
            }

            return new VRPSolution(routes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeSolution(VRPSolution solution, String outFileName) {
        File outFile = new File(outFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            for (int i = 0; i < solution.getRoutes().size(); i++) {
                writer.write("Route #");
                writer.write(String.valueOf(i+1));
                writer.write(": ");
                for (int j = 0; j < solution.getRoutes().get(i).size(); j++) {
                    writer.write(solution.getRoutes().get(i).get(j) + " ");
                }
                writer.newLine();
            }

            writer.write("Cost " + solution.getTotalCost());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
