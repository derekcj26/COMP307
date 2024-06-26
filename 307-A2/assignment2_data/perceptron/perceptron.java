import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class perceptron{
//Initialising variables
static ArrayList<Data>trainData = new ArrayList<Data>();
static ArrayList<Data>testData = new ArrayList<Data>();
static boolean prediction = false;
static double learningRate =1;
static double[] weights = new double[35];
static int totalIterations = 0;
static int classedWrongly = 0;

    /**
 * Main method
 * @param args
 */
public static void main(String[] args) {
    //error message
    if (args.length != 1 && args.length != 2) {
        System.err.println("Usage: java perceptron <data_file>, or java perceptron <train_file> <test_file>");
        System.exit(1);
    }
    String dataFile = args[0];
    trainReader(dataFile);
    initialiseWeights();
    train(trainData);
    //Prints out the final weights used
    System.out.println("Weights used: ");
    for(int i=0; i<weights.length; i++){
        System.out.println(weights[i]);
    }
    System.out.println("Total Iterations: " + totalIterations);
    System.out.println("Final classification accuracy on train set: " + accuracy(trainData)*100);
    //Runs the test data whne args is 2
    if(args.length == 2){
        String testFile = args[1];
        testReader(testFile);
        System.out.println("Final classification accuracy on test set: " + accuracy(testData)*100);
    }

}

    /**
     * File reader for the radars.
     * @param file
     */
    public static void trainReader(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                //seperates data to be tokenised
                String[] tokens = line.trim().split(" ");
                //iterates to add the data to the values array
                if (tokens.length >= 35) {
                    double[] values = new double[tokens.length];
                    for (int i = 1; i < tokens.length -1; i++) {
                        values[i+1] = Double.parseDouble(tokens[i]);
                    }
                    String cla = tokens[34];
                    double dummy =1.0;
                    values[0] = dummy;
                    //populates the data
                    Data d = new Data(values, cla);
                    trainData.add(d);
                } else {
                    // Handle case where there are not enough tokens in the line
                    System.err.println("Invalid line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle or log the exception as appropriate
        }
    }

    /**
     * @param file
     * Reader for the test data
     */
    public static void testReader(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                if (tokens.length >= 35) {
                    double[] values = new double[tokens.length];
                    for (int i = 0; i < tokens.length -1; i++) {
                        values[i] = Double.parseDouble(tokens[i]);
                    }
                    String cla = tokens[34];
                    Data d = new Data(values, cla);
                    testData.add(d);
                } else {
                    // Handle case where there are not enough tokens in the line
                    System.err.println("Invalid line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle or log the exception as appropriate
        }
    }


    /**
     * Intitialise method for the weights.
     * Randomises the value of each weight
     */
    public static void initialiseWeights(){
        for(int i = 0; i < weights.length; i ++){
            weights[i] = Math.random();
        }
    }

    /**
     * @param d
     * @param value
     * @param learningRate
     * Update weight function
     */
    public static void updateWeights(Data d ,double value, double learningRate){
        //for each weight * the appropriate value and learning rate
        for(int i = 0; i < weights.length; i++){
            weights[i] = weights[i] + d.features()[i] * value * learningRate;
        }
    }

    /**
     * @param list
     * Method to train the data. 
     */
    public static void train(ArrayList<Data> list){
        //initialising variables
        double iterations =0;
        boolean converged = false;
        int errors = 0;
        int minErrors = Integer.MAX_VALUE;
        //Sets limitations on the amount of iterations and if it has converged.
        while(!converged && iterations != 100){
            converged = true;
            errors = 0;
            //for each data predict if the class will be the wanted output
            for(Data d :list){
                String prediction = predict(d.addingWeights(weights));
                //when predict is wrong
                if(!prediction.equals(d.cla())){
                    errors++;
                    converged=false;
                    //when the prediction is +
                    if(prediction.equals("b")){
                        updateWeights(d, 1,learningRate);
                    }else{
                    //when the prediction is -
                        updateWeights(d, -1,learningRate);
                    }
                }
            }
            totalIterations++;
            if(errors<minErrors){
                minErrors = errors;
                iterations = 0;
            } else{
                iterations++;
            }
        }
    }

    /**
     * @param sum
     * @return
     * predict method which returns the character reprsening a + or - class
     */
    public static String predict(double sum){
        String prediction = sum > 0 ? "g" : "b";
        return prediction;
    }

    /**
     * @param trainedList
     * @return
     * Method to discover accuracy on the given data set.
     */
    public static double accuracy(ArrayList<Data> trainedList) {
        int correctPredictions = 0;
        for(int i =0; i<trainedList.size(); i++){
                if(predict(trainedList.get(i).addingWeights(weights)).equals((trainedList.get(i).cla()))){
                    correctPredictions++;
            }
        }
        System.out.println("Instances Classed Wrongly: " + (trainedList.size() - correctPredictions));
        return(double) correctPredictions/trainedList.size();
    }

}
