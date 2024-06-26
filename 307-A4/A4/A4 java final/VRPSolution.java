import java.util.List;

public class VRPSolution {
    private List<List<Integer>> routes;
    double totalCost;

    public VRPSolution(List<List<Integer>> routes) {
        this.routes = routes;
    }

    public List<List<Integer>> getRoutes() {
        return routes;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
