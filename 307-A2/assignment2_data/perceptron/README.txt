Perceptron Algorithm Implementation
Overview
This Java program implements the Perceptron algorithm, a simple machine learning algorithm used for binary classification tasks. The program reads training and testing data from files, trains the perceptron model using the training data, and evaluates its performance on both training and testing datasets.

Usage: 
To execute the program, use the following command-line syntax:
	java perceptron <data_file>
Or, for separate training and testing files:

	java perceptron <train_file> <test_file>


Output: 

Will show accuracy, how many classes were classifed wrong and the weights used. There will be two accuracys and classification outputs if you are using the train and test data.


Methods:
main(String[] args): Entry point of the program. Parses command-line arguments, reads training data, initializes weights, trains the model, and evaluates its performance.

trainReader(String file): Reads training data from a file, tokenizes it, and populates the trainData array list.

testReader(String file): Reads testing data from a file, tokenizes it, and populates the testData array list.

initialiseWeights(): Initializes weights with random values.

updateWeights(Data d, double value, double learningRate): Updates weights based on a data point, its corresponding class, and the learning rate.

train(ArrayList<Data> list): Trains the perceptron model using the provided training data.

predict(double sum): Predicts the class of a data point based on the sum of weighted inputs.

accuracy(ArrayList<Data> trainedList): Computes the accuracy of the model on a given dataset.


Variables:
trainData: ArrayList containing training data points.

testData: ArrayList containing testing data points.

learningRate: Learning rate parameter for updating weights.

weights: Array storing weights for each feature.

totalIterations: Total number of iterations during training.

classedWrongly: Number of instances misclassified during training.

Data Representation

The program expects input data files where each line represents a data point. Features are space-separated, and the last token represents the class label.

