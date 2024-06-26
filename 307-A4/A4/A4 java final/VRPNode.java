public class VRPNode {
    private int ID; // the ID of the node
    private double x; // the x-axis position
    private double y; // the y-axis position
    private double demand; // the demand of the node

    public VRPNode(int ID, double x, double y, double demand) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.demand = demand;
    }

    public VRPNode(int ID, double x, double y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.demand = 0;
    }

    public int getID() {
        return ID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDemand() {
        return demand;
    }

    public void setDemand(double demand) {
        this.demand = demand;
    }
}
