public class VehicleQueue implements java.lang.Cloneable {
  public final int CAPACITY = 6;
  private Vehicle[] data;
  private int front;
  private int rear;
  private int size;

  public VehicleQueue(){
    front = -1;
    rear = -1;
    size = 0;
    data = new Vehicle[CAPACITY];
  }

  public boolean isEmpty(){
    return front == -1;
  }

  public int size(){
    return size;
  }

  public void enqueue(Vehicle vehicle) throws FullQueueException{
    if ((rear+1)%CAPACITY == front) throw new FullQueueException("");
    if (front == -1){
      front = 0;
      rear = 0;
    }
    else rear = (rear+1)%CAPACITY;
    data[rear] = vehicle;
    size++;
  }

  public Vehicle dequeue() throws EmptyQueueException{
    Vehicle answer;
    if (front == -1) throw new EmptyQueueException("");
    answer = data[front];
    if (front == rear){
      front = -1;
      rear = -1;
    }
    else front = (front+1)%CAPACITY;
    size--;
    return answer;
  }

  public Object clone() throws CloneNotSupportedException{
    VehicleQueue clone = (VehicleQueue)super.clone();
    return clone;
  }






}
