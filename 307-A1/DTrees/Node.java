import java.util.ArrayList;
import java.util.List;


public class Node {
    // variables
    private Node left = null;
    private Node right = null;
    private Node parent = null;
    private double entropy = 0;
    private double IG = 0;
    private List<Double> valuesLeft = new ArrayList<Double>();
    private List<Double> valuesRight = new ArrayList<Double>();
    private int attribute = 0;
    private ArrayList<point> point = new ArrayList<point>();
    private boolean isLeaf = false;

    /**
     * Constructor for Node
     * @param parent parent node
     * @param left left child
     * @param right right child
     * @param value value of the node
     * @param IG information gain of the node
     */
    public Node(Node parent, Node left, Node right) {
        this.parent = null;
        this.left = null;
        this.right = null;
        this.entropy = 0;
        this.IG = 0;
    }

    /**
     * getter for left child
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Getter for right child
     */
    public Node getRight() {
        return right;
    }

    /**
     * Getter for parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Getter for entropy
     */
    public double getEntropy() {
        return entropy;
    }

    /**
     * Getter for IG
     */
    public double getIG() {
        return IG;
    }

    /**
     * Getter for valuesLeft
    */
    public List<Double> getValuesLeft() {
        return valuesLeft;
    }

    /**
     * Getter for valuesRight
     */
    public List<Double> getValuesRight() {
        return valuesRight;
    }

    /**
     * Getter for attribute
     */
    public int getAttribute() {
        return attribute;
    }

    /*
     * Getter for data
     */
    public ArrayList<point> getPoint() {
        return point;
    }

    /**
     * Getter for isLeaf
     * @return isLeaf
     */
    public boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * Setter for left child
     * @param left left child
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Setter for right child
     * @param right right child
     */
    public void setRight(Node right) {
        this.right = right;
    }

    /**
     * Setter for parent
     * @param parent parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Setter for value
     * @param value value of the node
     */
    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

    /**
     * Setter for IG
     * @param IG information gain of the node
     */
    public void setIG(double IG) {
        this.IG = IG;
    }

    /**
     * Setter for valuesLeft
     * @param valuesLeft values of the left child
     */
    public void setValuesLeft(List<Double> valuesLeft) {
        this.valuesLeft = valuesLeft;
    }

    /**
     * Setter for valuesRight
     * @param valuesRight values of the right child
     */
    public void setValuesRight(List<Double> valuesRight) {
        this.valuesRight = valuesRight;
    }

    /**
     * Setter for attribute
     * @param attribute attribute of the node
     */
    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    /**
     * Setter for data
     * @param point data of the node
     */
    public void setPoint(ArrayList<point> point) {
        this.point = point;
    }

    /**
     * Setter for isLeaf
     * @param isLeaf isLeaf
     */
    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}