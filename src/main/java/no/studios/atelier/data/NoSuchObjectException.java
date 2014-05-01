package no.studios.atelier.data;

/**
 * Exception <code>NoSuchObjectException</code>.
 * 
 * Created: Mon Nov 26 20:06:58 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class NoSuchObjectException extends Exception
{

  /**
   * Constructs a new <code>NoSuchObjectException</code> with <code>null</code>
   * as its detail message.
   */
  public NoSuchObjectException()
  {
  }

  /**
   * Constructs a new <code>NoSuchObjectException</code> with the specified
   * detail message.
   * 
   * @param message
   *          the detail message string.
   */
  public NoSuchObjectException(final String message)
  {
    super(message);
  }

  /**
   * Constructs a new <code>NoSuchObjectException</code> with the specified
   * cause and a detail message of
   * <code>(cause == null ? null : cause.toString())</code> (which typically
   * contains the class and detail message of cause).
   * 
   * @param cause
   *          the causing throwable. A null value is permitted and indicates
   *          that the cause is nonexistent or unknown.
   */
  public NoSuchObjectException(final Throwable cause)
  {
    super(cause == null ? (String) null : cause.toString());
    initCause(cause);
  }

  /**
   * Constructs a new <code>NoSuchObjectException</code> with the specified
   * cause and message.
   * 
   * @param message
   *          the detail message string.
   * @param cause
   *          the causing throwable. A null value is permitted and indicates
   *          that the cause is nonexistent or unknown.
   */
  public NoSuchObjectException(final String message, final Throwable cause)
  {
    super(message);
    initCause(cause);
  }
}
