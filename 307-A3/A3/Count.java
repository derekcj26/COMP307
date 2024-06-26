public class Count {
    private String cla;
    private double count;
    private String feature;
    private String value;

    // Count object for the class probabilities
    public Count(String cla, double count){
        this.cla =cla;
        this.count =count;
    }

    // Count object for the possibility of teh attributes and their values
    public Count(String feature, String value, String cla,double count){
        this.feature=feature;
        this.value=value;
        this.cla=cla;
        this.count=count;

    }
    // Gets the count value
    public double getCount(){
        return count;
    }
    // Returns the class of the count
    public String getCla(){
        return cla;
    }
    // Returns the feature of the count
    public String getFeature(){
        return feature;
    }
    // Returns the value associatd with the count
    public String getValue(){
        return value;
    }

    public double incrementCount(){
        return count++;
    }





    
    
    
}
