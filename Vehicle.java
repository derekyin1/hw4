public class Vehicle{
  private static int serialCounter = 0;
  private int serialId;
  private int timeArrived;

  public Vehicle(int initTimeArrived){
    if (initTimeArrived > 0){
      serialCounter++;
      serialId = serialCounter;
      timeArrived = initTimeArrived;
    }
    else throw new IllegalArgumentException();
  }

  public int getSerialId(){
    return serialId;
  }



  public int getTimeArrived(){
    return timeArrived;
  }




}
