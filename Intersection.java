/** Derek Yin 113251504 Recitation Section 1
*  This class represents a crossing of 2 or more roads at a stop light in our simulation.
*
*  @author Derek Yin
*/
import java.util.Stack;
public class Intersection{
  public final int MAX_ROADS = 2;
  private TwoWayRoad[] roads;
  private int lightIndex;
  private int countdownTimer;
/**
*This is the constructor for an Intersection
*
*@param initRoads
*initial array of TwoWayRoads that define the Intersection
*
*@throws
* throws IllegalArgumentException when initRoads is null or greater than MAX_ROADS
*/
  public Intersection(TwoWayRoad[] initRoads){
    if (initRoads == null || initRoads.length > MAX_ROADS){
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < MAX_ROADS; i++){
      if (initRoads[i] == null){
        throw new IllegalArgumentException();
      }
    }
    roads = new TwoWayRoad[MAX_ROADS];
    roads = initRoads;
    lightIndex = 0;
    countdownTimer = roads[lightIndex].getGreenTime();
  }
/** This method returns max number of roads allowed in this Intersection.
* @return
* returns max number of roads.
*
*/
  public int getNumRoads(){
    return MAX_ROADS;
  }
/** This method returns the index of the TwoWayRoad in this Intersection that has the Green/Left Signal.
* @return
* returns index of TwoWayRoad that has green light.
*
*/
  public int getLightIndex(){
    return lightIndex;
  }
/** This method returns the timer value that determines how long a TwoWayRoad has the green light for.
* @return
* returns the countdown Timer value.
*
*/
  public int getCountdownTimer(){
    return countdownTimer;
  }
/** This method performs an iteration of the simulation based on the chart provided.
* @return
* returns an array of Vehicles that were dequeued during this step.
*
*/
  public Vehicle[] timeStep(){
    if (countdownTimer==0){
      lightIndex = (lightIndex+1)%MAX_ROADS;
      countdownTimer = roads[lightIndex].getGreenTime();
    }
    if (roads[lightIndex].getLightValue() == LightValue.LEFT_SIGNAL){
      boolean preempt = true;
      for (int i = 0; i < TwoWayRoad.NUM_WAYS; i++){
        if (!roads[lightIndex].isLaneEmpty(i,0)){
          preempt = false;
        }
      }
      if (preempt){
        lightIndex = (lightIndex+1)%MAX_ROADS;
        countdownTimer = roads[lightIndex].getGreenTime();
      }
    }

    Vehicle[] dq = roads[lightIndex].proceed(countdownTimer);
    countdownTimer--;
    return dq;
  }
/** This method enqueues a Vehicle on a designated lane and way of a specified TwoWayRoad.
* @param roadIndex
* desired TwoWayRoad to enqueue to.
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
  public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle vehicle){
    if (roadIndex >= 0 && roadIndex < roads.length && wayIndex >= 0 && wayIndex < TwoWayRoad.NUM_WAYS && laneIndex >= 0 && laneIndex < TwoWayRoad.NUM_LANES && vehicle != null){
      roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
    }
    else throw new IllegalArgumentException();
  }

/** This method prints the current state of the Intersection in a formatted fashion.
*
*
*
*/
@SuppressWarnings("unchecked")
  public void display(){
    String sim = "";
    int row = 0;
    for (int i = 0; i < MAX_ROADS; i++){
      int allLanes = 1;
      sim += "    " + roads[i].getName() + ":\n";
      sim += "                           FORWARD               BACKWARD                       \n";
      sim += "    ==============================               ===============================\n";
      while (allLanes <= 6){
        if ((roads[i]).dataAccess(allLanes).isEmpty()){
          sim += "                                  ";
          // add x's for lights that are red
          allLanes++;
        }
        else{
          java.util.Stack newStack = new Stack();
          try{
            VehicleQueue q =(VehicleQueue)(roads[i].dataAccess(allLanes)).clone();
            while (!q.isEmpty()){
              try{
                newStack.push(q.dequeue());
              }
              catch (EmptyQueueException e){}

            }
            int numCars = newStack.size();
            sim+="    ";
            for (int spaces = 1; spaces <= 30 - (numCars*5); spaces++){
              sim += " ";
            }
            while (!newStack.isEmpty()){
              sim += "[" + String.format("%03d", ((Vehicle) newStack.pop()).getSerialId()) + "]";
              numCars++;
            }

          }
          catch (CloneNotSupportedException e){}

          allLanes++;
        }

        if (allLanes == 1 || allLanes == 2){
          sim += " [L]";
          if (i == lightIndex){
            if (roads[lightIndex].getLightValue() == LightValue.LEFT_SIGNAL){
              sim += "     x ";
            }
            else if (roads[lightIndex].getLightValue() == LightValue.GREEN){
              sim += " x     ";
            }
          }
          else sim += " x   x ";
          sim += "[R] ";
        }
        else if (allLanes == 3 || allLanes == 4){
          sim += " [M]";
          if (i == lightIndex){
            if (roads[lightIndex].getLightValue() == LightValue.LEFT_SIGNAL){
              sim += " x   x ";
            }
            else if (roads[lightIndex].getLightValue() == LightValue.GREEN){
              sim += "       ";
            }
          }
          else sim += " x   x ";

          sim += "[M] ";
        }
        else if (allLanes == 5 || allLanes == 6){
          sim += " [R]";
          if (i == lightIndex){
            if (roads[lightIndex].getLightValue() == LightValue.LEFT_SIGNAL){
              sim += " x     ";
            }
            else if (roads[lightIndex].getLightValue() == LightValue.GREEN){
              sim += "     x ";
            }
          }
          else sim += " x   x ";

          sim += "[L] ";
        }



        if (roads[i].dataAccess(allLanes).isEmpty()){
          sim += "\n";
          allLanes++;
        }
        else{
          try{
            VehicleQueue q2 = (VehicleQueue)(roads[i].dataAccess(allLanes)).clone();


            while(!q2.isEmpty()){
              try{
                sim += "[" + String.format("%03d", q2.dequeue().getSerialId()) + "]";
              }
              catch (EmptyQueueException e){}
            }

          }
          catch (CloneNotSupportedException e){}
          allLanes++;
          sim+="\n";
        }
        if (allLanes > 6){
          sim +=   "    ==============================               ===============================\n\n";
        }
        else sim += "    ------------------------------               -------------------------------\n";
      }

    }


    System.out.println(sim);
  }
/** This is a method thatdetermines whether or not this Intersection is empty.
* @return
* true if Intersection is empty, false otherwise.

*/
  public boolean isEmpty(){
    for (int i = 0; i < roads.length; i++){
      for (int way = 0; way < TwoWayRoad.NUM_WAYS; way++){
        for (int lane = 0; lane <TwoWayRoad.NUM_LANES; lane++){
          if (roads[i].isLaneEmpty(way, lane)){
            return false;
          }
        }
      }
    }
      return true;
  }




}
