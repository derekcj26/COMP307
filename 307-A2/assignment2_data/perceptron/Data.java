/**public record Data(double dummy, double f1,double f2,double f3,double f4,double f5,double f6,double f7, double f8, double f9, double f10, double f11, double f12, double f13, double f14, double f15, double f16, double f17, double f18, double f19, double f20, double f21, double f22, double f23, double f24, double f25, double f26, double f27, double f28, double f29, double f30, double f31, double f32, double f33, double f34,String cla) {
    public String returnCla(){
        return cla;
    }
    public double addingWeights(double w){
        return (f1*w+f2*w+f3*w+f4*w+f5*w+f6*w+f7*w+f8*w+f9*w+f10*w+f11*w+f12*w+f13*w+f14*w+f15*w+f16*w+f17*w+f18*w+f19*w+f20*w+f21*w+f22*w+f23*w+f24*w+f25*w+f26*w+f27*w+f28*w+f29*w+f30*w+f31*w+f32*w+f33*w+f34);
    }
    public double getSumofFeatures(){
        return (f1+f2+f3+f4+f5+f6+f7+f8+f9+f10+f11+f12+f13+f14+f15+f16+f17+f18+f19+f20+f21+f22+f23+f24+f25+f26+f27+f28+f29+f30+f31+f32+f33+f34);

    }

} */

public record Data(double[] features, String cla){
    public String returnCla(){
        return cla;
    }
    public double addingWeights(double[] w){
        double addedWeights = 0.0;
        for(int i = 0; i < features.length; i++){
            addedWeights = addedWeights + (features[i] * w[i]);
        }
    return addedWeights;
    } 
}