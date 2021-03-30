/** Derek Yin 113251504 Recitation Section 1
*  This class abstracts a random occurrence generator.
*
*  @author Derek Yin
*/
public class BooleanSource{
  private double probability;
/** This is a constructor that creates a random occurrence generator with specified probability.
* @param initProbability
*
* @throws
* IllegalArgumentException if initProbability is not within the range (0,1]
*/
  public BooleanSource(double initProbability){
    if (initProbability > 0 && initProbability <= 1){
      probability = initProbability;
    }
    else throw new IllegalArgumentException();
  }
/** This is a method that returns the probability variable of the BooleanSource object.
* @return
* returns probability.
*
*/
  public double getProb(){
    return probability;
  }
/** This method sets probability of BooleanSource to new probability.
* @param newProb
* desired probability.
*
*@throws
*throws IllegalArgumentException if newProb is not in the range (0, 1]
*/
  public void setProb(double newProb){
    if (newProb > 0 && newProb <= 1){
      probability = newProb;
    }
    else throw new IllegalArgumentException();
  }
/** This method determines if an action occurs based on given probability of BooleanSource object.
* @return
* returns true if occurs, false if does not occur.
*/
  public boolean occurs(){
    return (Math.random() < probability);
  }



}
