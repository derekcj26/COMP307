import numpy as np


class Neural_Network:
    # Initialize the network
    def __init__(self, num_inputs, num_hidden, num_outputs, hidden_layer_weights, output_layer_weights, learning_rate,hidden_biases,outer_biases):
        self.num_inputs = num_inputs
        self.num_hidden = num_hidden
        self.num_outputs = num_outputs
        self.hidden_layer_weights = hidden_layer_weights
        self.output_layer_weights = output_layer_weights
        self.learning_rate = learning_rate
        self.hidden_biases =hidden_biases
        self.outer_biases=outer_biases


    # Calculate neuron activation for an input
    def sigmoid(self, input):
        output = 1/(1+np.exp(-input)) # TODO!
        return output

    # Feed forward pass input to a network output
    def forward_pass(self, inputs):
        hidden_layer_outputs = []
        for i in range(self.num_hidden):
            # TODO! Calculate the weighted sum, and then compute the final output.
            weighted_sum = sum(inputs*self.hidden_layer_weights[:,i]+self.hidden_biases[i])
            output =self.sigmoid(weighted_sum)
            hidden_layer_outputs.append(output)

        output_layer_outputs = []
        for i in range(self.num_outputs):
            # TODO! Calculate the weighted sum, and then compute the final output.
            weighted_sum = sum(hidden_layer_outputs*self.output_layer_weights[:,i]+self.outer_biases[i])
            output = self.sigmoid(weighted_sum)
            output_layer_outputs.append(output)

        hidden_layer_outputs = np.array(hidden_layer_outputs)
        output_layer_outputs = np.array(output_layer_outputs)
        return hidden_layer_outputs, output_layer_outputs

    # Backpropagate error and store in neurons
    def backward_propagate_error(self, inputs, hidden_layer_outputs, output_layer_outputs, desired_outputs):

        output_layer_betas = np.zeros(self.num_outputs)
        # TODO! Calculate output layer betas.
        output_layer_betas = desired_outputs-output_layer_outputs
        #print('OL betas: ', output_layer_betas)

        hidden_layer_betas = np.zeros(self.num_hidden)
        # TODO! Calculate hidden layer betas.
        for i in range(self.num_hidden):
            hidden_layer_betas[i] = np.sum(self.output_layer_weights[i] * output_layer_outputs*( 1 - output_layer_outputs ) * output_layer_betas)
        #print('HL betas: ', hidden_layer_betas)
        inputs = np.array(inputs).reshape(self.num_inputs)
        # This is a HxO array (H hidden nodes, O outputs)
        delta_output_layer_weights = np.zeros((self.num_hidden, self.num_outputs))
        # TODO! Calculate output layer weight changes.
        for i in range(self.num_hidden):
            delta_output_layer_weights[i] = self.learning_rate * hidden_layer_outputs[i] * output_layer_outputs * (1-output_layer_outputs) * output_layer_betas
    

        # This is a IxH array (I inputs, H hidden nodes)
        delta_hidden_layer_weights = np.zeros((self.num_inputs, self.num_hidden))
        # TODO! Calculate hidden layer weight changes.

        for i in range(self.num_inputs):
            delta_hidden_layer_weights[i] = self.learning_rate * inputs[i] * hidden_layer_outputs * (1 - hidden_layer_outputs) * hidden_layer_betas


        # Hidden Bias changes
        delta_hidden_biases = np.zeros(self.num_hidden)
        for i in range(self.num_hidden):
            delta_hidden_biases[i] =self.learning_rate*hidden_layer_outputs[i] * (1-hidden_layer_outputs[i])*hidden_layer_betas[i]

        # Outer Bias changes
        delta_outer_biases = np.zeros(self.num_outputs)
        for i in range(self.num_outputs):
            delta_outer_biases[i] =self.learning_rate*output_layer_outputs[i] * (1-output_layer_outputs[i])*output_layer_betas[i]

        # Return the weights we calculated, so they can be used to update all the weights.
        return delta_output_layer_weights, delta_hidden_layer_weights,delta_hidden_biases,delta_outer_biases

    def update_weights(self, delta_output_layer_weights, delta_hidden_layer_weights,delta_hidden_biases,delta_outer_biases):
        # TODO! Update the weights.
        self.output_layer_weights= self.output_layer_weights+delta_output_layer_weights
        self.hidden_layer_weights=self.hidden_layer_weights+delta_hidden_layer_weights
        self.hidden_biases = self.hidden_biases + delta_hidden_biases
        self.outer_biases = self.outer_biases+delta_outer_biases
        #print('Placeholder')

    def train(self, instances, desired_outputs, epochs):

        for epoch in range(epochs):
            print('epoch = ', epoch)
            predictions = []
            for i, instance in enumerate(instances):
                hidden_layer_outputs, output_layer_outputs = self.forward_pass(instance)
                delta_output_layer_weights, delta_hidden_layer_weights,delta_hidden_biases,delta_outer_biases = self.backward_propagate_error(
                    instance, hidden_layer_outputs, output_layer_outputs, desired_outputs[i])
                predicted_class = np.argmax(output_layer_outputs)  # TODO!
                predictions.append(predicted_class)

                # We use online learning, i.e. update the weights after every instance.
                self.update_weights(delta_output_layer_weights, delta_hidden_layer_weights,delta_hidden_biases,delta_outer_biases)

            # Print new weights
            #print('Hidden layer weights \n', self.hidden_layer_weights)
            #print('Output layer weights  \n', self.output_layer_weights)

            # TODO: Print accuracy achieved over this epoch
            acc = 0
            correct = 0
            for i in range(len(predictions)):
                if predictions[i] == np.argmax(desired_outputs[i]):
                    correct += 1
            
            acc = correct / len(predictions)
            print('Accuracy of epoch:\n',acc)
            
    def predict(self, instances):
        predictions = []
        for instance in instances:
            hidden_layer_outputs, output_layer_outputs = self.forward_pass(instance)
            #print(output_layer_outputs)
            predicted_class = np.argmax(output_layer_outputs)  # TODO! Should be 0, 1, or 2.
            predictions.append(predicted_class)
        return predictions
    