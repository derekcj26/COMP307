
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class decisionTree{
public static ArrayList<point> dataset = new ArrayList<point>();
public static String highestIGAttribute="";
public static double parent =0;
static Node root;
static List<Edge> edges = new ArrayList<Edge>();
static StringBuilder tree = new StringBuilder();
static String treeOutput = "";
static String accuracyString="";

/**
 * Main Class
 * @param args
 */
public static void main(String[] args) {
    //creates args
    if (args.length != 2) {
        System.err.println("Usage: java decisionTree <train_file> <output_tree>");
        System.exit(1);
    }
    String trainfile =args[0];
    treeOutput = args[1];
    readDataset(trainfile);
    //creates root and begins the tree
    root = new Node(null, null, null);
    root = createTree(dataset, root);
    // Add edges to the tree
    addEdges(root);
    // Check Accuracy
    double accuracy = checkAccuracy(dataset, root);
    accuracyString = ("Accuracy: "+ accuracy);
    printTree();
    //System.out.println("Accuracy: "+ accuracy);
}


/**
 * Data reader
 * @param filePath
 */
public static void readDataset(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        // Skip header
        br.readLine();
        while ((line = br.readLine()) != null) {
            //splits at commas
            String[] parts = line.split(",");
            int numAttributes = parts.length-1;
            ArrayList<Double> attributes = new  ArrayList<>();
            //discovers the ammount of attributes that are present in the data
            for(int i =0; i<numAttributes; i++){
                attributes.add(Double.parseDouble(parts[i]));
            }
            double cla = Double.parseDouble(parts[numAttributes]);
            dataset.add(new point(attributes, cla));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * Begins finding the highestIG and is the starting method
 * that calls other methods to complete entropy and class probability
 * @param pList
 * @param attributeAccessor
 * @return
 */
public static double findHighestIG(ArrayList<point> pList, Function<point, Double> attributeAccessor){
    double highestIG=0;
    ArrayList<point>equals0 = new ArrayList<point>();
    ArrayList<point>equals1 = new ArrayList<point>();
    //finding the relevant attribute
    for(point p: pList){
        double attributeValue = attributeAccessor.apply(p);
        if(attributeValue==0){
            equals0.add(p);
        }
        else{
            equals1.add(p);
        }
    }
    //values for the weighted child formula
    double totalInstances = pList.size();
    double child0Weight = equals0.size() / totalInstances;
    double child1Weight = equals1.size() / totalInstances;
    if (equals0.isEmpty() || equals1.isEmpty()) {
        // One of the child sets is empty, IG is 0
        return 0;
    }
    //calls the class dist method
    parent = findClassDistribution(pList);
    //calculates the avg weighted child
    double averageChild =  (findClassDistribution(equals0)*child0Weight)+(findClassDistribution(equals1)*child1Weight);
    highestIG = findIG(averageChild, parent);
    return highestIG;
}


/**
 * function that finds the associated class with each point
 * @param p
 * @return
 */
public static double findClassDistribution(ArrayList<point> p){
    double classDis =0;
    double classZero =0;
    double classOne =0;
    double totalAmount=0;
    ArrayList<Double> probList = new ArrayList<Double>();
    for(point listPoint : p){
        if(listPoint.getCla()==0){
            classZero++;
        }else{
            classOne++;
        }
        totalAmount++;
    }
    classZero =classZero/totalAmount;
    classOne = classOne/totalAmount;
    probList.add(classZero);
    probList.add(classOne);
    //calls the entropy class
    classDis=findEntropy(probList);
    //returns the entropy of the given lis
    return classDis;
}


/**
 * Entropy Calculation
 * @param list
 * @return
 */
public static double findEntropy(ArrayList<Double> list){
    double entropy = 0;
        for (double probability : list) {
            if (probability != 0) { // Avoid log(0)
            entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }
        return entropy;
}


/**
 * Information Gain Calculation
 * @param averageEntropy
 * @param parentEntropy
 * @return
 */
public static double findIG(double averageEntropy, double parentEntropy){
    double IG =0;
    IG=parentEntropy-averageEntropy;
    return IG;
}



/**
 * Method for the best att to split on
 * @param point
 * @param usedAttributes
 * @return
 */
public static int bestAttribute(ArrayList<point> point, List<Integer> usedAttributes) {
        double maxGain = 0;
        int bestAttribute = -1; // Initialize with a default value
        for (int i = 0; i < point.get(0).getAttributes().size(); i++) {
            final int index =i;
            //finds the highest gain through iteration
            double gain = findHighestIG(point, p -> p.getAttributes().get(index));
            if (gain > maxGain && !usedAttributes.contains(i)){
                maxGain = gain;
                bestAttribute = i;
            }
        }
        return bestAttribute;
    }


/**
 *Tree Creator
 * @param point
 * @param node
 * @return
 */
public static Node createTree(ArrayList<point> point, Node node) {
        if (point.isEmpty()) {
            return null;
        }
        // Start creating the tree
        List<Integer> usedAttributes = new ArrayList<Integer>();
        int attribute = bestAttribute(point, usedAttributes);
        usedAttributes.add(attribute);
        node = addNode(point, node, attribute, "", tree, usedAttributes);
        
        return node;
    }

    /**
     * add a node to the tree
     * recursive call to create the tree
     * @param point
     * @param parent
     * @param attribute
     * @param feature
     * @param tab
     * @param tree
     * @return node
     */
    public static Node addNode(ArrayList<point> point, Node parent, int attribute, String tab, StringBuilder tree, List<Integer> usedAttributes) {
        Node node = new Node(parent, null, null);
        List<Integer> usedAbove = usedAttributes; // copy current list of atributes used in parent nodes
        // if all the data points have the same classification make it a leaf node otherwise split
        int i =attribute;
        node.setPoint(point);
        if(attribute == -1){
            // set the values in the leaf node
            node.setIsLeaf(true);
            node.setAttribute(parent.getAttribute());
            // The attribute value needs to be set to the parent attribute as that was the values it split on
            for(point p : point) {
                if(p.getAttribute(node.getAttribute()) == 0){
                    node.getValuesLeft().add(p.getAttribute(node.getAttribute()));
                } else {
                    node.getValuesRight().add(p.getAttribute(node.getAttribute()));
                }
            }

            // create this section of the tree
            if(parent != null){
                if(parent.getLeft() == node){
                    buildTree(point, node, true, tab, "left");
                } else {
                    buildTree(point, node, true, tab, "right");
                }
            }
            return node;
        } else if(findHighestIG(point, p -> p.getAttributes().get(i)) < 0.00001){
            // set the values in the leaf node
            node.setIsLeaf(true);
            node.setAttribute(parent.getAttribute());
            // The attribute value needs to be set to the parent attribute as that was the values it split on
            for(point p : point) {
                if(p.getAttribute(node.getAttribute()) == 0){
                    node.getValuesLeft().add(p.getAttribute(node.getAttribute()));
                } else {
                    node.getValuesRight().add(p.getAttribute(node.getAttribute()));
                }
            }

            // create this section of the tree
            if(parent != null){
                if(parent.getLeft() == node){
                    buildTree(point, node, true, tab, "left");
                } else {
                    buildTree(point, node, true, tab, "right");
                }
            }
        } else {
            node.setAttribute(attribute);
            tree.append(tab + "att: " + attribute + "(IG: " + findHighestIG(point, p -> p.getAttributes().get(i)) + ", Entropy: " + findClassDistribution(point) + ")\n");
            // put the attributes in the node
            ArrayList<point> left = new ArrayList<point>();
            ArrayList<point> right = new ArrayList<point>();
            for (point p : point) {
                if (p.getAttribute(attribute) == 0) {
                    left.add(p);
                } else if (p.getAttribute(attribute) == 1) {
                    right.add(p);
                }
            }

            // recursively call the function to create the tree
            // for left side
            buildTree(point, node, false, tab, "left"); // create part of the tree
            attribute = bestAttribute(left, usedAbove);
            usedAbove.add(attribute);
            node.setLeft(addNode(left, node, attribute, tab + "\t", tree, usedAbove));
            usedAbove.remove(usedAbove.size() - 1);

            // for right side
            buildTree(point, node, false, tab, "right"); // create part of the tree
            attribute = bestAttribute(right, usedAbove);
            usedAbove.add(attribute);
            node.setRight(addNode(right, node, attribute, tab + "\t", tree, usedAbove));
            usedAbove.remove(usedAbove.size() - 1);
        }
        return node;
    }
    /**
     * Print the tree
     */
    public static void printTree() {
        System.out.println(tree.toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(treeOutput))) {
        writer.write(tree.toString());
        writer.write(accuracyString);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    /**
     * Check the accuracy of the tree
     * @param point
     * @param node
     * @return accuracy
     */
    public static double checkAccuracy(ArrayList<point> point, Node node) {
        int correct = getCorrect(point, node);
        double accuracy = (double) correct / point.size() * 100;
        return accuracy;
    }

    /**
     * Get the amount of correct data points
     * @param point
     */
    public static int getCorrect(ArrayList<point> point, Node node) {
        int correct = 0;
        Node current = node;
        // check if node has children or is a leaf
        if(current.getLeft() != null && current.getRight() != null){
            //System.out.println("Parent: " + current.getParent() + " Child: " + current + " Value: " + current.getAttribute());
            correct += getCorrect(current.getLeft().getPoint(), current.getLeft());
            correct += getCorrect(current.getRight().getPoint(), current.getRight());
        }
        // if it is leaf add up correct points
        if(current.getIsLeaf()){
            //System.out.println("Leaf: " + current.getAttribute() + " " + current.getValuesLeft().size() + " " + current.getValuesRight().size());
            // check the value of the data point to that of the edge and add to correct if correct
            for(Edge e : edges){
                if(e.getChild() == current && !e.getChecked()){
                    e.setChecked(true);
                    for(point p : point){
                        //System.out.println("Data: " + d.getAttribute(current.getAttribute()) + " " + d.getClassification());
                        if(p.getAttribute(current.getAttribute()) == e.getValue() && p.getCla() == 0){
                            correct++;
                        } else if(p.getAttribute(current.getAttribute()) == e.getValue() && p.getCla() == 1){
                            correct++;
                        }
                    }
                }
            }
        }
        return correct;
    }

    /**
     * Build the tree
     * @param data
     * @param node
     * @param leaf
     * @param tab
     * @param branch
     */
    public static void buildTree(ArrayList<point> p, Node node, boolean leaf, String tab, String branch) {
        // Check if the node is a leaf
        if(leaf){
            // add parts to the string builder
            tree.append(tab + "Leaf{0:" + node.getValuesLeft().size() + ", 1: " + node.getValuesRight().size() +"}\n");
        } else {
            // add parts to string builder
            if(branch.equals("left")){
                tree.append(tab + "-- att " + node.getAttribute() + " == 0 --\n");
            }else if (branch.equals("right")){
                tree.append(tab + "-- att " + node.getAttribute() + " == 1 --\n");
            }
            tab += "\t";
        }
    }

        /**
     * Add edge to the tree
     * @param node
     */
    public static void addEdges(Node node) {
        
        if(node.getLeft() != null && node.getRight() != null){
            // add left edges
            Edge e = new Edge(node, node.getLeft(), 0);
            edges.add(e);
            addEdges(node.getLeft());
        }

        
        if(node.getRight() != null){
            // add right edges
            Edge e = new Edge(node, node.getRight(), 1);
            edges.add(e);
            addEdges(node.getRight());
        }
    }

/**
     * Print the edges
     */
    public static void printEdges() {
        for(Edge e : edges){
            System.out.println("Parent: " + e.getParent() + " Child: " + e.getChild() + " Value: " + e.getValue() + " " + e.getParent().getIsLeaf());
            System.out.println("Parent: " + e.getParent().getAttribute());
        }
    }

}





