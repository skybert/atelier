package no.studios.atelier.model;

/**
 * AbstractComment.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractComment extends AbstractAtelierEntity implements
    Comment
{
  private String mComment;
  private User mUser;
  private int mOrderId;

  public String getComment()
  {
    return mComment;
  }

  public void setComment(final String pComment)
  {
    mComment = pComment;
  }

  public User getUser()
  {
    return mUser;
  }

  public void setUser(final User pUser)
  {
    mUser = pUser;
  }

  public int getOrderId()
  {
    return mOrderId;
  }

  public void setOrderId(final int pOrderId)
  {
    mOrderId = pOrderId;
  }
}
