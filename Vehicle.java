/** Derek Yin 113251504 Recitation Section 1
*  This class defines the Vehicle object, which will be enqueued and dequeued in this simulation.
*
*  @author Derek Yin
*/
public class Vehicle{
  private static int serialCounter = 0;
  private int serialId;
  private int timeArrived;
/**
*This is the constructor for a Vehicle object
*
* @param initTimeArrived
* Desired initial time arrived.
*
* @throws
* IllegalArgumentException when initTimeArrived is negative.
*/
  public Vehicle(int initTimeArrived){
    if (initTimeArrived > 0){
      serialCounter++;
      serialId = serialCounter;
      timeArrived = initTimeArrived;
    }
    else throw new IllegalArgumentException();
  }
/** This is a method that returns the SerialID of this Vehicle object.
* @return
* returns serialID number.
*
*/
  public int getSerialId(){
    return serialId;
  }


/** This is a method that returns the time of arrival for this Vehicle object.
* @return
* returns arrival Time.
*
*/
  public int getTimeArrived(){
    return timeArrived;
  }




}
