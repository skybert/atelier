package no.studios.atelier.model;

import java.util.*;

/**
 * A comment, normally a customer order comment.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface Comment extends AtelierEntity
{
  public String getComment();

  public void setComment(final String pComment);

  /**
   * The user that made the comment.
   * 
   * @return an <code>int</code> value
   */
  public User getUser();

  /**
   * the user the made the comment.
   * 
   * @param pUser
   *          an <code>User</code> value
   */
  public void setUser(final User pUser);

  /**
   * Id to avoid endless recusion in DB layer.
   * 
   * @return an <code>int</code> value
   */
  public int getOrderId();

  /**
   * Id to avoid endless recusion in DB layer.
   * 
   * @param pOrderId
   *          an <code>int</code> value
   */
  public void setOrderId(final int pOrderId);
}
