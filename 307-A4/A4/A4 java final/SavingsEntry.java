public class SavingsEntry implements Comparable<SavingsEntry> {
    private VRPNode i;
    private VRPNode j;
    private double savings;

    public SavingsEntry(VRPNode i, VRPNode j, double savings) {
        this.i = i;
        this.j = j;
        this.savings = savings;
    }
    public VRPNode getI() {
        return i;
    }
    
    public VRPNode getJ() {
        return j;
    }

    public double getSavings() {
        return savings;
    }

    @Override
    public int compareTo(SavingsEntry other) {
        return Double.compare(other.savings, this.savings); // retuns in descending order
    }
}