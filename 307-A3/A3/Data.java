public class Data {

    private String cla;
    private String [] values;
    
    public Data (String cla, String[] values ){
        this.cla = cla;
        this.values = values;
    }
    // Returns the class of the data
    public String getCla(){
        return cla;
    }
    // Returs the values in the data
    public String[] getValues(){
        return values;
    }


}
