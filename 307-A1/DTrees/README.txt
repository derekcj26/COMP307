# Decision Tree Algorithm Implementation

## Overview
This project contains Java classes implementing a decision tree algorithm for classification tasks. The decision tree algorithm builds a tree-like structure where each internal node represents an attribute test, each branch represents the outcome of the test, and each leaf node represents a class label. The decision tree is constructed based on the information gain criterion to recursively split the dataset into subsets.

## Classes
The project consists of the following Java classes:

1. `decisionTree`: This class contains the main logic for building the decision tree, reading the dataset, calculating information gain, checking accuracy, and printing the tree.

2. `Node`: Represents a node in the decision tree. Each node contains references to its parent, left child, and right child, along with other attributes such as entropy, information gain, attribute value, and whether it is a leaf node.

3. `point`: Represents a data point in the dataset. Each data point consists of attributes and a class associated with it.

4. `Edge`: Represents an edge connecting nodes in the decision tree. It specifies the parent node, child node, and the value associated with the edge.

## Usage
To use the decision tree algorithm, follow these steps:

Compile the Java files using a Java compiler.
    ```
    javac decisionTree.java point.java Edge.java Node.java
    ```
3. Run the compiled `decisionTree` class with the following command-line arguments:
    ```
    java decisionTree <train_file> <output_tree>
    ```
    - `<train_file>`: Path to the training dataset file.
    - `<output_tree>`: Path to the output file where the decision tree will be saved.
    