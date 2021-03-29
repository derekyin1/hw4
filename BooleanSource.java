public class BooleanSource{
  private double probability;

  public BooleanSource(double initProbability){
    if (initProbability > 0 && initProbability <= 1){
      probability = initProbability;
    }
    else throw new IllegalArgumentException();
  }

  public double getProb(){
    return probability;
  }

  public void setProb(double newProb){
    if (newProb > 0 && newProb <= 1){
      probability = newProb;
    }
    else throw new IllegalArgumentException();
  }

  public boolean occurs(){
    return (Math.random() < probability);
  }



}
