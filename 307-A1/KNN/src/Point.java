
public record Point(double alc, double mal_acid, double ash, double alcOfAsh, double mag, double phenols, double flav, double nonFlav, double proanth, double colIntens, double hue, double OD, double proline, double cla) {
    
    /**
     * Distance method
     * @param other
     * @return
     */
    public double distance(Point other) {
        double dist = 0;
        dist = Math.sqrt(Math.pow(alc-other.alc, 2) + Math.pow(mal_acid-other.mal_acid, 2) + Math.pow(ash - other.ash, 2) +Math.pow(alcOfAsh - other.alcOfAsh, 2) + Math.pow(mag - other.mag, 2) + Math.pow(phenols - other.phenols, 2) + Math.pow(flav - other.flav, 2) + Math.pow(nonFlav - other.nonFlav, 2) + Math.pow(proanth - other.proanth, 2) + Math.pow(colIntens - other.colIntens, 2) + Math.pow(hue - other.hue, 2) + Math.pow(OD - other.OD, 2) + Math.pow(proline - other.proline, 2));
        return dist;
    }



}