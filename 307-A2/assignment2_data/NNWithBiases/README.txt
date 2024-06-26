Penguin Species Classification with Neural Network
This Python script demonstrates the classification of penguin species using a neural network. The dataset used for training and testing contains measurements of various penguin species.

Place the penguins307-train.csv and penguins307-test.csv files containing the training and testing datasets respectively in the same directory as the script.


Usage

Run the script using Python3:

	run the a2Part1 file.

Output:
There will be the accuracy outputs through each epoch and the final accuracy on the test with the biases and the initial and final weights used. 

The script will perform the following tasks:
Load the training data from penguins307-train.csv.
Preprocess the data by scaling features and encoding labels.
Initialize a neural network with specified parameters.
Perform a single backpropagation pass using the first instance only.
Train the neural network for 100 epochs on all instances.
Load the testing data from penguins307-test.csv.
Compute and print the test accuracy of the trained model.
