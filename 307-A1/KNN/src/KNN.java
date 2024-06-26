import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KNN{

    static ArrayList<Point>trainList = new ArrayList<Point>();
    static ArrayList<Point>testList = new ArrayList<Point>();
    static ArrayList<Double> neighbours = new ArrayList<Double>();
    static HashMap<Point,Double> hashPoint = new HashMap<Point,Double>();
    static ArrayList<String> csvList = new ArrayList<String>();
    static double accisEqual =0;
    static double totalEntries =0;
    private final static String COMMA_DELIMITER = ",";
    static StringBuilder sb1 = new StringBuilder("");


    /**
     * File reader for the train sets
     * @param file
     */
    public static void trainReader(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length >= 14) { // Ensure there are enough tokens to create a Point
                    Double[] values = new Double[14];
                    for (int i = 0; i < 14; i++) {
                        values[i] = Double.parseDouble(tokens[i]);
                    }
                    Point p = new Point(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12],values[13]);
                    trainList.add(p);
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
     * File reader for the test sets
     * @param file
     */
    public static void testReader(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length >= 14) { // Ensure there are enough tokens to create a Point
                    Double[] values = new Double[14];
                    for (int i = 0; i < 14; i++) {
                        values[i] = Double.parseDouble(tokens[i]);
                    }
                    Point p = new Point(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12], values[13]);
                    testList.add(p);
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
 * Knn Algorithm
 * @param training
 * @param test
 * @param k
 */
public static void knn (ArrayList<Point>training, ArrayList<Point> test, int k){
        for (Point testPoint : test) {
            for (Point trainPoint : training) {
                //generate the dist between two points and store them
                hashPoint.put(trainPoint,trainPoint.distance(testPoint));
                for (Map.Entry<Point,Double> set:hashPoint.entrySet()) {
                    neighbours.add(set.getValue());
                    }
            }
            List<Map.Entry<Point,Double>> sortedDistance = new ArrayList<>(hashPoint.entrySet());
            //sort each value through comparison
            sortedDistance.sort(Map.Entry.comparingByValue());
            //create a list of k points that are closest
            List<Map.Entry<Point,Double>>kShortestDistance = sortedDistance.subList(0, k);
            int maxCount = 0;
                double comCla = 0.0;
                for (Map.Entry<Point, Double> entry : kShortestDistance) {
                    sb1.append(entry.getValue().toString()+",");
                    int count = 0;
                    for (Map.Entry<Point, Double> entry2 : kShortestDistance) {
                        //comparing classes to aquire the accuracy
                        if (entry.getKey().cla() == entry2.getKey().cla()){
                            count++;
                        }
                        if (count>maxCount) {
                            maxCount = count;
                            comCla = entry.getKey().cla();
                        }
                    }
                }
                sb1.append(testPoint.cla()+",");
                sb1.append(comCla);
                csvList.add(sb1.toString());
                sb1.setLength(0);
                if(testPoint.cla()==comCla){
                accisEqual++;
                }
                totalEntries++;
        }
        System.out.println("Accuracy"+(accisEqual/totalEntries)*100+"%");
}

    /**
     * Csv writer
     * @param data
     * @param fileName
     * @param k
     */
    public static void writeToCsv(String data, String fileName,int k) {
        //System.out.println(data);
        data = data.substring(1, data.length()-1);
        String[] tokens = data.split(COMMA_DELIMITER);
        
        int count = 0;
        try (FileWriter writer = new FileWriter(fileName,true)) {
            // Write rows for every 5th item
            File file = new File(fileName);
            boolean isEmpty = file.length() == 0;

            // Write headers if the file is empty
            if (isEmpty) {
                for(int i =1; i <= k; i++){
                    writer.append("distance" + i +",");
                }
                    writer.append("class,");
                    writer.append("expected class");
                    writer.append('\n');
            }
            for (int i = 0; i < tokens.length; i++) {
                if (count == k+2) {
                    writer.append('\n');
                    count = 0;
                }
                writer.append(tokens[i]);
                writer.append(',');
                count++;
            }
            //writer.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
 * Main method
 * @param args
 */
public static void main(String[] args) {
    if (args.length != 4) {
        System.err.println("Usage: java KNN <train_file> <test_file> <output_file> <k>");
        System.exit(1);
    }

    String trainFile = args[0];
    String testFile = args[1];
    String outputFile = args[2];
    int k = Integer.parseInt(args[3]);
    trainReader(trainFile);
    testReader(testFile);
    knn(trainList,testList,k);
    writeToCsv(csvList.toString(),outputFile,k);
}

}