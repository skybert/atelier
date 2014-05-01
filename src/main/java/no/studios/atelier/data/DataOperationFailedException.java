package no.studios.atelier.data;

/**
 * Exception <code>DataOperationFailedException</code>.
 * 
 * Created: Mon Nov 26 20:00:50 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class DataOperationFailedException extends Exception
{

  /**
   * Constructs a new <code>DataOperationFailedException</code> with
   * <code>null</code> as its detail message.
   */
  public DataOperationFailedException()
  {
  }

  /**
   * Constructs a new <code>DataOperationFailedException</code> with the
   * specified detail message.
   * 
   * @param message
   *          the detail message string.
   */
  public DataOperationFailedException(final String message)
  {
    super(message);
  }

  /**
   * Constructs a new <code>DataOperationFailedException</code> with the
   * specified cause and a detail message of
   * <code>(cause == null ? null : cause.toString())</code> (which typically
   * contains the class and detail message of cause).
   * 
   * @param cause
   *          the causing throwable. A null value is permitted and indicates
   *          that the cause is nonexistent or unknown.
   */
  public DataOperationFailedException(final Throwable cause)
  {
    super(cause == null ? (String) null : cause.toString());
    initCause(cause);
  }

  /**
   * Constructs a new <code>DataOperationFailedException</code> with the
   * specified cause and message.
   * 
   * @param message
   *          the detail message string.
   * @param cause
   *          the causing throwable. A null value is permitted and indicates
   *          that the cause is nonexistent or unknown.
   */
  public DataOperationFailedException(
      final String message,
      final Throwable cause)
  {
    super(message);
    initCause(cause);
  }
}
