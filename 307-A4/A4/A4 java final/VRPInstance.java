import java.util.List;
import java.util.Map;

public class VRPInstance {
    private Map<Integer, VRPNode> nodes; // The hash map of the nodes in the instance, the key is the node ID
    private double capacity; // the vehicle capacity
    private VRPNode depot; // the depot node

    public VRPInstance(Map<Integer, VRPNode> nodes, double capacity, VRPNode depot) {
        this.nodes = nodes;
        this.capacity = capacity;
        this.depot = depot;
    }

    public Map<Integer, VRPNode> getNodes() {
        return nodes;
    }

    public double getCapacity() {
        return capacity;
    }

    public VRPNode getDepot() {
        return depot;
    }
}
