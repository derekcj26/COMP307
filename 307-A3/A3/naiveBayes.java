
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class naiveBayes{

// Iniitalising Variables
static String[] headers;
static ArrayList<Data> dataList = new ArrayList<Data>();
static ArrayList<Data> testList = new ArrayList<Data>();
static ArrayList<Count> countList = new ArrayList<Count>();
static ArrayList<Count> classCountList = new ArrayList<Count>();
static Map<String, Double> probabilities = new HashMap<>();
static double classTotal =0;
static double featureTotal =0;
static double conditionalProbability=0;

/**
 * Main Class
 * @param args
 */
public static void main(String[] args) {
    //System arguments
    if (args.length != 2) {
        System.err.println("Usage: java naiveBayes <train_file> <test_file>");
        System.exit(1);
    }
    String trainFile = args[0];
    String testFile = args[1];
    readDataset(trainFile);
    readTestset(testFile);
    innit_count();

    //Printing the possibility table
    for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
        System.out.println(entry);
    }
    System.out.println();
    String classLabel = "no-recurrence-events";
    int correctPredictions = 0;
    int totalInstances = testList.size();
    //Gets the values for the specific instance
    for (Data testData : testList) {
        System.out.print("For Instance X with values: ");
        String[] values = testData.getValues();
        for (int i = 0; i < values.length; i++) {
            System.out.print("  " + values[i]);
        }
        System.out.println();
        //Calls the class label to get the predicted class
        String predictedClassLabel = predictClassLabel(testData);
        String actualClassLabel = testData.getCla();
        System.out.println("Predicted class: " + predictedClassLabel + ", Actual class: " + actualClassLabel);
        if (predictedClassLabel.equals(actualClassLabel)) {
            correctPredictions++;
        }
        classLabel = "no-recurrence-events";
        System.out.println("Score for test instance when class = "+classLabel+" is: "+ calculateScore(testData, classLabel));
        classLabel = "recurrence-events";
        System.out.println("Score for test instance when class = "+classLabel+" is: "+ calculateScore(testData, classLabel));
        System.out.println();
    }
    double accuracy = (double) correctPredictions / totalInstances * 100;
    System.out.println("Accuracy: " + accuracy + "%");
    
}

/**
 * @param filePath
 */
public static void readDataset(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        String headerLine = br.readLine();
        headers = headerLine.split(",");
        while ((line = br.readLine()) != null) {
            // Splits at commas
            String[] features = line.split(",");
            String cla = features[1];
            String[] values = new String[features.length - 2];
            // Assign values correctly
            //Starts reading from when the values begin in the data
            for (int i = 2; i < features.length; i++) {
                values[i - 2] = features[i];
            }
            Data dpoint = new Data(cla, values);
            dataList.add(dpoint);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * @param filePath
 */
public static void readTestset(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        String headerLine = br.readLine();
        headers = headerLine.split(",");
        while ((line = br.readLine()) != null) {
            // Splits at commas
            String[] features = line.split(",");
            String cla = features[1];
            String[] values = new String[features.length - 2];
            // Assign values correctly
            //Starts reading from when the values begin in the data
            for (int i = 2; i < features.length; i++) {
                values[i - 2] = features[i];
            }
            Data dpoint = new Data(cla, values);
            testList.add(dpoint);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


/**
 * @return
 */
public static Map<String,Double> innit_count() {
    for (Data d : dataList) {
        if (d.getCla().equals("no-recurrence-events") || d.getCla().equals("recurrence-events")) {
            // Check if there's already a Count object for the class
            boolean found = false;
            for (Count cl : classCountList) {
                if (cl.getCla() != null && cl.getCla().equals(d.getCla())) {
                    // Increment count if class already exists
                    cl.incrementCount();
                    classTotal++;
                    found = true;
                }
            }
            // If no existing Count object, create a new one
            if (!found) {
                Count c = new Count(d.getCla(), 1);
                classCountList.add(c);
                classTotal++;
            }
        }

        for (int i = 0; i < d.getValues().length; i++) { // Adjusted loop to include all features
            String feature = headers[i+2];
            String value = d.getValues()[i];
            //System.out.println(value.length());
            String cla = d.getCla();
            // Check if there's already a Count object for the feature, value, and class
            boolean found = false;
            for (Count cl : countList) {
                if(countList.isEmpty()){
                    Count c = new Count(feature, value, cla,1);
                    countList.add(c);
                    featureTotal++;
                    found =true;
                }
                else if (cl.getFeature().equals(feature) && cl.getValue().equals(value) && cl.getCla().equals(cla)) {
                    // Increment count if Count object already exists
                    cl.incrementCount();
                    found = true;
                    //featureTotal++;
                    break;
                }
            }
            // If no existing Count object, create a new one
            if (!found) {
                Count c = new Count(feature, value, cla,1);
                countList.add(c);
                featureTotal++;
            }
            
        }
        
    }
    for (Count c : classCountList) {
        double classProbability = c.getCount() / classTotal;
        probabilities.put("prob(y=" + c.getCla() + ")", classProbability);

        for (Count f : countList) {
            if (f.getCla().equals(c.getCla())) {
                double conditionalProbability = f.getCount() / c.getCount();
                probabilities.put("prob(X_" + f.getFeature() + "=" + f.getValue() +
                    "|y=" + c.getCla() + ")", conditionalProbability);
            }
        }
    }

    return probabilities;

}

/**
 * @param testData
 * @param classLabel
 * @return
 */
public static double calculateScore(Data testData, String classLabel) {
    double totalScore = 0;
        // Step 1: Initialize score with the probability of the class label y
        double score = 0;
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            if(entry.getKey().equals("prob(y=" + classLabel + ")"))
            score = entry.getValue();
        }

        // Step 2: Multiply the score by the conditional probability of each feature value given the class label y
        String[] values = testData.getValues();
        for (int i = 0; i < values.length; i++) {
            String feature = headers[i + 2]; // Adjusted index to account for class label and zero-based indexing
            String value = values[i];
            for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
                if(entry.getKey().equals("prob(X_" + feature + "=" + value +
                "|y=" + classLabel + ")"))
                conditionalProbability = entry.getValue();
            }
            score *= conditionalProbability;
        }

        // Accumulate the score for each test instance
        totalScore += score;
    

    // Step 3: Return the total score
    return (double)totalScore;
}

public static String predictClassLabel(Data testData) {
    String bestClassLabel = "";
    double maxScore = Double.MIN_VALUE;
    for (String classLabel : probabilities.keySet()) {
        //System.out.println(classLabel);
        if(classLabel.contains("no-recurrence-events")){
            classLabel="no-recurrence-events";
        }else{
            classLabel="recurrence-events";
        }
        double score = calculateScore(testData, classLabel);
        if (score > maxScore) {
            maxScore = score;
            bestClassLabel = classLabel;
        }
    }

    return bestClassLabel;
}

}