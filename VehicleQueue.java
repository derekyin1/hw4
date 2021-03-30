/** Derek Yin 113251504 Recitation Section 1
*  This class defines a queue of vehicles using a circular array. All methods run in O(1). This class is cloneable.
*
*  @author Derek Yin
*/
public class VehicleQueue implements java.lang.Cloneable {
  public final int CAPACITY = 6;
  private Vehicle[] data;
  private int front;
  private int rear;
  private int size;
/**
*This is the constructor for the VehicleQueue.
*
*
*/
  public VehicleQueue(){
    front = -1;
    rear = -1;
    size = 0;
    data = new Vehicle[CAPACITY];
  }
/** This is a method that returns whether or not this VehicleQueue is empty.
* @return
* returns true if empty, false otherwise.
*
*/
  public boolean isEmpty(){
    return front == -1;
  }
/** This is a method that returns the size of this Queue.
* @return
* returns size of this VehicleQueue.
*
*/
  public int size(){
    return size;
  }
/** This is a method that enqueues a Vehicle to this VehicleQueue.
* @param vehicle
* Vehicle to be enqueued.
* @throws
* FullQueueException when queue is full.
*
*/
  public void enqueue(Vehicle vehicle) throws FullQueueException{
    if ((rear+1)%CAPACITY == front) throw new FullQueueException("");
    if (front == -1){
      front = 0;
      rear = 0;
      size++;
    }
    else rear = (rear+1)%CAPACITY;
    data[rear] = vehicle;
    size++;
  }
/** This is a method that dequeues from this VehicleQueue.
* @return
* Vehicle that was dequeued
* @throws
* EmptyQueueException when queue is empty.
*
*/
  public Vehicle dequeue() throws EmptyQueueException{
    Vehicle answer;
    if (front == -1) throw new EmptyQueueException("");
    answer = data[front];
    if (front == rear){
      front = -1;
      rear = -1;
      size--;
    }
    else front = (front+1)%CAPACITY;
    size--;
    return answer;
  }
/** This is a method that creates a clone of this VehicleQueue.
* @return
* Clone of this VehicleQueue
* @throws
* CloneNotSupportedException if this VehicleQueue cannot be cloned.
*/
  public Object clone() throws CloneNotSupportedException{
    VehicleQueue clone = (VehicleQueue)super.clone();
    return clone;
  }






}
