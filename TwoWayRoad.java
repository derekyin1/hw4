/** Derek Yin 113251504 Recitation Section 1
*  This class defines 3 forward facing and 3 backward facing lanes of a two way road, lanes being defined by VehicleQueues.
*
*  @author Derek Yin
*/
import java.util.ArrayList;
public class TwoWayRoad{
  public final int FORWARD_WAY = 0;
  public final int BACKWARD_WAY = 1;
  public static final int NUM_WAYS = 2;
  public final int LEFT_LANE = 0;
  public final int MIDDLE_LANE = 1;
  public final int RIGHT_LANE = 2;
  public static final int NUM_LANES = 3;

  private String name;
  private int greenTime;
  private int leftSignalGreenTime;
  private VehicleQueue[][] lanes;
  private LightValue lightValue;
/**
*This is the constructor for a TwoWayRoad
*
*@param initName
*desired name of the TwoWayRoad.
*@param message
*desired green Time of the TwoWayRoad.
*
*@throws
* IllegalArgumentException if initGreenTime is negative or initName is null.
*/
  public TwoWayRoad(String initName, int initGreenTime){
    if (initGreenTime > 0 && initName != null){
      name = initName;
      greenTime = initGreenTime;
      //lightValue = LightValue.GREEN;
      lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
      for (int r = 0; r < NUM_WAYS; r++){
        for (int c = 0; c < NUM_LANES; c++){
          lanes[r][c] = new VehicleQueue();
        }
      }
      leftSignalGreenTime =(int) Math.floor(1.0/NUM_LANES * initGreenTime);
    }
    else throw new IllegalArgumentException();
  }

/** This method executes the passage of time, changes light states, and dequeues vehicles according to the chart.
* @param timerVal
* indicates the current value of the countdownTimer, and determines the state of the light.
* @return
* returns an array of Vehicles that were dequeued during this step.
*
*/
@SuppressWarnings("unchecked")
  public Vehicle[] proceed(int timerVal){
    if (timerVal <= 0){
      throw new IllegalArgumentException();
    }
    ArrayList<Vehicle> dq = new ArrayList();
    if (timerVal > leftSignalGreenTime) lightValue = LightValue.GREEN;
    if (timerVal <= leftSignalGreenTime) lightValue = LightValue.LEFT_SIGNAL;




    boolean preempt = true;
    for (int ways = 0; ways < NUM_WAYS; ways++){
      if (!lanes[ways][RIGHT_LANE].isEmpty() || !lanes[ways][MIDDLE_LANE].isEmpty()){
        preempt = false;
      }
    }
    if (preempt) lightValue = LightValue.LEFT_SIGNAL;





//dequeuing vehicles

    for (int way = 0; way < NUM_WAYS; way++){
      for (int lane = 0; lane < NUM_LANES; lane++){
        if (!lanes[way][lane].isEmpty() && lightValue == LightValue.GREEN && lane != 0){
          try{
            dq.add(lanes[way][lane].dequeue());
          }
          catch (EmptyQueueException e){

          }
        }
        if (!lanes[way][lane].isEmpty() && lightValue == LightValue.LEFT_SIGNAL && lane != 1 && lane !=2){
          try{
            dq.add(lanes[way][lane].dequeue());
          }
          catch (EmptyQueueException e){

          }
        }

      }
    }

    return (Vehicle[]) dq.toArray(new Vehicle[dq.size()]);
  }
/** This method enqueues a Vehicle on a designated lane and way of the TwoWayRoad.
* @param wayIndex
* desired way (Forward or backward) to enqueue to.
* @param laneIndex
* desired lane (left, middle, right) to enqueue to.
* @param vehicle
* desired Vehicle object to enqueue.
*
* @throws
* IllegalArgumentException if any index is 0 or violates array lengths.
*/

  public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle){
    if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2 || vehicle == null){
      throw new IllegalArgumentException();
    }
    try{
      lanes[wayIndex][laneIndex].enqueue(vehicle);
    }
    catch (FullQueueException e){}

  }
/** This method enqueues a Vehicle on a designated lane and way of a specified TwoWayRoad.
* @param laneIndex
* desired lane to check if empty.
* @param wayIndex
* desired way (Forward or backward) to check if empty.
* @return
* true if desired lane is empty, false otherwise.
*
* @throws
* IllegalArgumentException if any index is 0 or violates array lengths.
*/
  public boolean isLaneEmpty(int wayIndex, int laneIndex){
    if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2){
      throw new IllegalArgumentException();
    }
    return lanes[wayIndex][laneIndex].isEmpty();
  }
/** This is a method that returns the greenTime of this TwoWayRoad.
* @return
* returns greenTime.
*
*/
  public int getGreenTime(){
    return greenTime;
  }
/** This is a method that returns the lightValue of this TwoWayRoad.
* @return
* returns light Value of this road.
*
*/
  public LightValue getLightValue(){
    return lightValue;
  }
/** This is a method that returns the name of this Road.
* @return
* returns name
*
*/
  public String getName(){
    return name;
  }
/** This is a helper method that helps the display() function in Intersection class.
* @param index
* desired index to access - 1 for forward/left lane, 2 for backward/right lane, 3 for forward/middle lane, 4 for backward/mid lane, 5 for forward/right lane, 6 for backward/left lane.
* @return
* returns desired VehicleQueue (lane).
*
*/
  public VehicleQueue dataAccess(int index){
    if (index == 1)
    return lanes[FORWARD_WAY][LEFT_LANE];
    else if (index == 2)
    return lanes[BACKWARD_WAY][RIGHT_LANE];
    else if (index == 3)
    return lanes[FORWARD_WAY][MIDDLE_LANE];
    else if (index == 4)
    return lanes[BACKWARD_WAY][MIDDLE_LANE];
    else if (index == 5)
    return lanes[FORWARD_WAY][RIGHT_LANE];
    else if (index == 6)
    return lanes[BACKWARD_WAY][LEFT_LANE];

    return null;

  }

}
