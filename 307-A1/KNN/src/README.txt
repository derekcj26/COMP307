This Java program implements the K-Nearest Neighbors (KNN) algorithm for classification. KNN is a straightforward algorithm used for classification tasks.

Usage
To run the program:
java KNN <train_file> <test_file> <output_file> <k>
<train_file>: Path to training data (CSV).
<test_file>: Path to testing data (CSV).
<output_file>: Path to save predictions (CSV).
<k>: Number of neighbors to consider (integer).




Key Features
Data Handling: Reads data from CSV files, ensuring proper format.
KNN Algorithm: Computes K nearest neighbors for each test point.
Prediction: Predicts class labels based on majority vote among neighbors.
Output: Writes predictions to a CSV file.
Note
Input CSV files should have 14 attributes + class label per line.
Assumes classification task with discrete class labels.
Refer to the source code for detailed implementation and usage instructions.