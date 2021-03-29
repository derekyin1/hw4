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
@SuppressWarnings("unchecked")
  public Vehicle[] proceed(int timerVal){
    if (timerVal <= 0){
      throw new IllegalArgumentException();
    }
    ArrayList<Vehicle> dq = new ArrayList();
//dq first or after checking lights?

    if (timerVal > leftSignalGreenTime){
      lightValue = LightValue.GREEN;
    }
    if (timerVal <= leftSignalGreenTime){
      lightValue = LightValue.LEFT_SIGNAL;
    }
    if (timerVal < 1 || isLaneEmpty(FORWARD_WAY,RIGHT_LANE) && isLaneEmpty(FORWARD_WAY, MIDDLE_LANE) && isLaneEmpty(FORWARD_WAY, LEFT_LANE) && isLaneEmpty(BACKWARD_WAY,RIGHT_LANE) && isLaneEmpty(BACKWARD_WAY, MIDDLE_LANE) && isLaneEmpty(BACKWARD_WAY, LEFT_LANE) ){
      lightValue = LightValue.RED;
    }

    if (lightValue == LightValue.GREEN){
      try{
        dq.add(lanes[FORWARD_WAY][MIDDLE_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
      try{
        dq.add(lanes[FORWARD_WAY][RIGHT_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
      try{
        dq.add(lanes[BACKWARD_WAY][MIDDLE_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
      try{
        dq.add(lanes[BACKWARD_WAY][RIGHT_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
    }
    if (lightValue == LightValue.LEFT_SIGNAL){
      try{
        dq.add(lanes[FORWARD_WAY][LEFT_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
      try{
        dq.add(lanes[BACKWARD_WAY][LEFT_LANE].dequeue());
      }
      catch (EmptyQueueException e){}
    }


    return (Vehicle[]) dq.toArray();
  }


  public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle){
    if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2 || vehicle == null){
      throw new IllegalArgumentException();
    }
    try{
      lanes[wayIndex][laneIndex].enqueue(vehicle);
    }
    catch (FullQueueException e){}

  }

  public boolean isLaneEmpty(int wayIndex, int laneIndex){
    if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2){
      throw new IllegalArgumentException();
    }
    return lanes[wayIndex][laneIndex].isEmpty();
  }

  public int getGreenTime(){
    return greenTime;
  }

  public LightValue getLightValue(){
    return lightValue;
  }

  public String getName(){
    return name;
  }

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
