import java.util.ArrayList;

public class point{
    /*
     *Inititialisers
     */
    private ArrayList<Double> attributes = new ArrayList<Double>();
    private double cla;


    /**
     * Point constructor
     * @param attributesList
     * @param cla
     */
    public point(ArrayList<Double>attributesList, double cla){
        this.attributes = attributesList;
        this.cla = cla;
    }


    /**
     * returns the list of attributes
     * @return attributes
     */
    public ArrayList<Double> getAttributes(){
        return attributes;
    }

    /**
     * returns a specific attribute
     * @param i
     * @return
     */
    public double getAttribute(int i){
        return attributes.get(i);
    }
    

    /**
     * retunns the associated class value
     * @return
     */
    public double getCla(){
        return cla;
    }
}




