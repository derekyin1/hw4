/** Derek Yin 113251504 Recitation Section 1
*  This class is an exception that is called when an empty queue is dequeued.
*  @author Derek Yin
*/
public class EmptyQueueException extends Exception{
/**
*This is the constructor for the EmptyStackException.
*
*@param message
*the error message to be displayed
*
*/
  public EmptyQueueException(String message){
    super(message);
  }

}
