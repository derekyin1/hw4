/** Derek Yin 113251504 Recitation Section 1
*  This class runs a traffic simulation.
*
*  @author Derek Yin
*/
import java.util.Scanner;
import java.util.ArrayList;
public class IntersectionSimulator{

/** The main runs a terminal simulation of traffic and traffic lights at an Intersection and prints a summary.
*
*
*/
  public static void main(String[] args) {
    if (args.length > 1){
      int simTime = Integer.parseInt(args[0]);
      double prob = Double.parseDouble(args[1]);
      int numRoads = Integer.parseInt(args[2]);
      String[] names = new String[numRoads];
      int[] times = new int[numRoads];

      for (int i = 0; i < numRoads; i++){
        names[i] = args[3+i];
        times[i] = Integer.parseInt(args[3 + numRoads + i]);
      }
      simulate(simTime, prob, names, times);
    }
    else{
      Scanner in = new Scanner(System.in);
      boolean menu = true;
      boolean sim = false;
      while (menu){
        System.out.println("Welcome to IntersectionSimulator 2021");
        System.out.println("\nInput the simulation time:");
        if (in.hasNextInt()){
          int simTime = in.nextInt();
          if (simTime > 0){
            System.out.println("Input the arrival probability:");
            if (in.hasNextDouble()){
              double prob = in.nextDouble();
              if (prob > 0.0 && prob <= 1.0){
                System.out.println("Input number of streets:");
                if (in.hasNextInt()){
                  int numRoads = in.nextInt();
                  if (numRoads > 0 && numRoads < 3){
                    String dupe = "";
                    String[] names = new String[numRoads];
                    in.nextLine();
                    for (int i = 1; i <= numRoads; i++){
                      System.out.println("Input Street " + i + " name:");
                      String name = in.nextLine();
                      if (i == 1){
                        dupe = name;
                        if (name.equals("")){
                          System.out.println("Empty String detected.");
                          i--;
                       }
                        else names[i-1] = name;
                      }
                      if (i > 1){
                        if (name.equals(dupe) || name.equals("")){
                          System.out.println("Duplicate or Empty String detected.");
                          i--;
                        }
                        else names[i-1] = name;
                      }
                    }

                    int[] times = new int[numRoads];
                    for (int i = 0; i < numRoads; i++){
                      System.out.println("Input max green time for " + names[i] + ":");
                      if (in.hasNextInt()){
                        int time = in.nextInt();
                        if (time > 0){
                          times[i] = time;
                        }
                        else{
                          System.out.println("Time cannot be less than 0.");
                          i--;
                        }
                      }
                      else {
                        System.out.println("Input an integer.");
                        in.nextLine();
                        i--;
                      }
                    }
                    System.out.println("Starting Simulation...");
                    sim = true;
                    while (sim){
                      simulate(simTime, prob, names, times);
                      sim = false;
                      menu = false;

                    }
                  }
                  else {
                    System.out.println("Invalid Input.");
                    in.nextLine();
                    menu = false;
                    menu = true;
                  }
                }
                else {
                  System.out.println("Invalid Input.");
                  in.nextLine();
                  menu = false;
                  menu = true;
                }
              }
              else {
                System.out.println("Invalid Input.");
                in.nextLine();
                menu = false;
                menu = true;
              }
            }
            else {
              System.out.println("Invalid Input.");
              in.nextLine();
              menu = false;
              menu = true;
            }
          }
          else {
            System.out.println("Invalid Input.");
            in.nextLine();
            menu = false;
            menu = true;
          }
        }
        else {
          System.out.println("Invalid Input.");
          in.nextLine();
          menu = false;
          menu = true;
        }
      }
    }
  }


/** This method performs an iteration of the simulation based on the chart provided.
* @param simulationTime
* desired simulation time
* @param arrivalProbability
* desired probability that a Vehicle enters a lane.
* @param roadNames
* array of names of the roads to be simulated in the Intersection.
* @param maxGreenTimes
* array of maximum amount of steps the light is green for a specific road.
*/

@SuppressWarnings("unchecked")
  public static void simulate(int simulationTime, double arrivalProbability, String[] roadNames, int[] maxGreenTimes){
    int simTime = simulationTime;
    double prob = arrivalProbability;
    String[] names = roadNames;
    int[] greenTimes = maxGreenTimes;
    int timeStep = 1;
    int totalWaitTime = 0;
    int currentWait = 0;
    int totalPassed = 0;
    double avgWait = 0;
    int maxWait = 0;
    boolean isRunning = true;
    ArrayList<Integer> id = new ArrayList();
    ArrayList<String> roadName = new ArrayList();
    ArrayList<String> way = new ArrayList();
    ArrayList<String> lane = new ArrayList();
    TwoWayRoad[] roads = new TwoWayRoad[names.length];
    for (int i = 0; i < roads.length; i++){
      roads[i] = new TwoWayRoad(names[i], greenTimes[i]);
    }
    Intersection sim = new Intersection(roads);
    BooleanSource bool = new BooleanSource(prob);




    while (isRunning){
      int current = 0;
      if (timeStep>=simTime){
        boolean end = true;
        for (int i = 0; i < roads.length; i++){
          for (int ways = 0; ways < TwoWayRoad.NUM_WAYS; ways++){
            for (int lanes = 0; lanes < TwoWayRoad.NUM_LANES; lanes++){
              if (!roads[i].isLaneEmpty(ways, lanes)){
                end = false;
              }
            }
          }
        }
        if (end) isRunning = false;
      }
//occurs
      else{
      for (int i = 0; i < roads.length; i++){
        for (int ways = 0; ways < TwoWayRoad.NUM_WAYS; ways++){
          for (int lanes = 0; lanes < TwoWayRoad.NUM_LANES; lanes++){
            if (bool.occurs()){
              Vehicle car = new Vehicle(timeStep);
              sim.enqueueVehicle(i, ways, lanes, car);
              id.add(car.getSerialId());
              roadName.add(names[i]);
              if (ways == 0) way.add("FORWARD");
              if (ways == 1) way.add("BACKWARD");
              if (lanes == 0) lane.add("LEFT");
              if (lanes == 1) lane.add("MIDDLE");
              if (lanes == 2) lane.add("RIGHT");
              current++;
            }
          }
        }
      }
      }
      String desc = "################################################################################\n";
      Vehicle[] dq = sim.timeStep();
  /** print time step, cars statistics, etc **/
      desc += "Time Step: " + timeStep + "\n";
      desc+="\n";
      // or left signal
      if (roads[sim.getLightIndex()].getLightValue() == LightValue.GREEN){
        desc+="Green Light for " + names[sim.getLightIndex()] + ".\n";
      }
      if (roads[sim.getLightIndex()].getLightValue() == LightValue.LEFT_SIGNAL){
        desc+="Left arrow for " + names[sim.getLightIndex()] + ".\n";
      }
      desc+="Timer = " + (sim.getCountdownTimer() + 1) + "\n";
      desc+="\n";

      if (timeStep >= simTime){
        desc += "Cars no longer arriving.\n\n";
      }
      desc+="    ARRIVING CARS:\n";



      for (int i = 0; i < dq.length; i++){
        totalWaitTime += (timeStep - dq[i].getTimeArrived());
        totalPassed++;
      }
      //FIX THIS
      currentWait += current - dq.length;
      avgWait = (double) totalWaitTime / (double) totalPassed;

      //GREEN OR LEFT SIGNAL FOR WHICH ROUTE?

      //check for if time >= simtime
      for (int i = 0; i < id.size(); i++){
          desc+="       Car[" + String.format("%03d", id.get(i)) + "] entered " + roadName.get(i) + ", going " + way.get(i) + " in " + lane.get(i) + " lane.\n";

      }
      id.clear();
      roadName.clear();
      way.clear();
      lane.clear();


      desc+="\n";

      desc+="    PASSING CARS:\n";
      for (int i = 0; i < dq.length; i++){
        desc+="       Car[" + String.format("%03d", dq[i].getSerialId()) + "] passes through. Wait time of " + (timeStep - dq[i].getTimeArrived()) + "\n";
        if ((timeStep - dq[i].getTimeArrived() > maxWait)) maxWait = (timeStep - dq[i].getTimeArrived());
      }


      desc +="\n";
      System.out.println(desc);
      sim.display();


      String stats = "\n\n    STATISTICS:\n";
      stats+="      Cars currently waiting: " + currentWait + " cars\n";
      stats+="      Total cars passed: " + totalPassed + " cars\n";
      stats+="      Total wait time: " + totalWaitTime + " turns \n";
      stats+="      Average wait time: " + String.format("%.2f ", avgWait) + "turns \n";
      stats+="\n";

      System.out.println(stats);
      if (timeStep>=simTime){
        boolean end = true;
        for (int i = 0; i < roads.length; i++){
          for (int ways = 0; ways < TwoWayRoad.NUM_WAYS; ways++){
            for (int lanes = 0; lanes < TwoWayRoad.NUM_LANES; lanes++){
              if (!roads[i].isLaneEmpty(ways, lanes)){
                end = false;
              }
            }
          }
        }
        if (end){
          String summary = "################################################################################\n################################################################################\n################################################################################\n";
          summary += "\n";
          summary += "SIMULATION SUMMARY:\n";
          summary += "\n";
          summary += "    Total Time:         " + timeStep + " steps\n";
          summary += "    Total Vehicles:     " + totalPassed + " vehicles\n";
          summary += "    Longest wait time:  " + maxWait + " turns\n";
          summary += "    Total wait time:    " + totalWaitTime + " turns\n";
          summary += "    Average wait time:  " + String.format("%.2f ", avgWait) + " turns\n";
          summary += "\n";
          summary+= "End simulation.";
          System.out.println(summary);
          isRunning = false;
        }
      }
      timeStep++;


    }

  }


}
