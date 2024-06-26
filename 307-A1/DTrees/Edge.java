
public class Edge {
    // variables
    private Node parent;
    private Node child;
    private int value;
    private boolean checked = false;
    
    /**
     * Constructor for Edge
     * @param parent
     * @param child
     * @param value
     */
    public Edge(Node parent, Node child, int value) {
        this.parent = parent;
        this.child = child;
        this.value = value;
    }
    
    /**
     * Getter for parent
     * @return parent
     */
    public Node getParent() {
        return parent;
    }
    
    /**
     * Getter for child
     * @return child
     */
    public Node getChild() {
        return child;
    }
    
    /**
     * Getter for value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for Checked
     * @return checked
     */
    public boolean getChecked() {
        return checked;
    }

    /**
     * Setter for Checked
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
