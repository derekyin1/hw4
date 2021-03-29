import java.util.Stack;
public class Intersection{
  public final int MAX_ROADS = 2;
  private TwoWayRoad[] roads;
  private int lightIndex;
  private int countdownTimer;

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

  public int getNumRoads(){
    return MAX_ROADS;
  }

  public int getLightIndex(){
    return lightIndex;
  }

  public int getCountdownTimer(){
    return countdownTimer;
  }


/*  public Vehicle[] timeStep(){
    Vehicle[] dequeued = new Vehicle[];

  } */

  public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle vehicle){
    if (roadIndex >= 0 && roadIndex < roads.length && wayIndex >= 0 && wayIndex < TwoWayRoad.NUM_WAYS && laneIndex >= 0 && laneIndex < TwoWayRoad.NUM_LANES && vehicle != null){
      roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
    }
    else throw new IllegalArgumentException();
  }


@SuppressWarnings("unchecked")
  public void display(){
    String sim = "################################################################################\n";
/** print time step, cars statistics, etc **/
    sim += "Time Step: " + "\n";
    //GREEN OR LEFT SIGNAL FOR WHICH ROUTE?
    sim+="\n";

    sim+="    ARRIVING CARS:\n";
    // print arriving CARS

    sim+="\n";

    sim+="    PASSING CARS:\n";

    sim+="\n";

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
          // add x' s for lights that are RED
          if (roads[i].getLightValue() == LightValue.LEFT_SIGNAL){
            sim += "     x ";
          }
          else if (roads[i].getLightValue() == LightValue.GREEN){
            sim += " x     ";
          }
          else if (roads[i].getLightValue() == LightValue.RED){
            sim += " x   x ";
          }
          else sim += "       ";
          sim += "[R] ";
        }
        else if (allLanes == 3 || allLanes == 4){
          sim += " [M]";
          if (roads[i].getLightValue() == LightValue.LEFT_SIGNAL){
            sim += " x   x ";
          }
          else if (roads[i].getLightValue() == LightValue.GREEN){
            sim += "       ";
          }
          else if (roads[i].getLightValue() == LightValue.RED){
            sim += " x   x ";
          }
          else sim += "       ";
          sim += "[M] ";
        }
        else if (allLanes == 5 || allLanes == 6){
          sim += " [R]";
          // add x' s for lights that are RED
          if (roads[i].getLightValue() == LightValue.LEFT_SIGNAL){
            sim += " x     ";
          }
          else if (roads[i].getLightValue() == LightValue.GREEN){
            sim += "     x ";
          }
          else if (roads[i].getLightValue() == LightValue.RED){
            sim += " x   x ";
          }
          else sim += "       ";
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

    sim+="\n\n    STATISTICS:\n";
    sim+="      Cars currently waiting:\n";
    sim+="      Total cars passed:\n";
    sim+="      Total wait time:\n";
    sim+="      Average wait time:\n";
    sim+="\n";

    sim += "################################################################################\n";
    System.out.println(sim);
  }


  public static void main(String[] args) {
    Vehicle car = new Vehicle(001);
    Vehicle truck = new Vehicle(002);
    VehicleQueue testQ = new VehicleQueue();
    try{
      testQ.enqueue(car);
    }
    catch (FullQueueException e){}

    TwoWayRoad testRoad1 = new TwoWayRoad("209 St", 10);
    TwoWayRoad testRoad2 = new TwoWayRoad("3235 St", 10);
    testRoad1.enqueueVehicle(0,0,car);
    testRoad2.enqueueVehicle(0,0,car);
    testRoad1.enqueueVehicle(1,2, car);
    testRoad1.enqueueVehicle(1,1, car);
    TwoWayRoad[] roads = new TwoWayRoad[2];
    roads[0] = testRoad1;
    roads[1] = testRoad2;
    Intersection test = new Intersection(roads);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, truck);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);
    test.enqueueVehicle(0,1,1, car);


    test.display();


  }


}
