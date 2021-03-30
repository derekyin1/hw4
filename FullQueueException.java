/** Derek Yin 113251504 Recitation Section 1
*  This class is an exception called every time a full queue is enqueued.
*
*  @author Derek Yin
*/
public class FullQueueException extends Exception{
/**
*This is the constructor for the FullQueueException
*
*@param message
*the error message to be displayed
*
*/
  public FullQueueException(String message){
    super(message);
  }

}
