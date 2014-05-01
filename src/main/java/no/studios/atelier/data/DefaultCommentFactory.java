package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Comment factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public final class DefaultCommentFactory extends AbstractFactory implements
    CommentFactory
{
  public Comment getComment(final int pCommentId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    Comment comment = null;

    try
    {
      mSQLMapClient.startTransaction();
      comment = (Comment) mSQLMapClient.queryForObject(GET_COMMENT, pCommentId);
      mSQLMapClient.commitTransaction();

      if (comment == null)
      {
        throw new NoSuchObjectException("no such Comment with id=" + pCommentId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get comment with id=" + pCommentId;
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(errorMessage, sqEx);
      }

      throw new DataOperationFailedException(errorMessage, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // no need
      }
    }

    return comment;
  }

  public List<Comment> getAllComments(final int pOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<Comment> comments = null;

    try
    {
      mSQLMapClient.startTransaction();
      comments = (List<Comment>) mSQLMapClient.queryForList(GET_ALL_COMMENTS,
          pOrderId);
      mSQLMapClient.commitTransaction();

      if (comments == null)
      {
        throw new NoSuchObjectException("no Comments for customer id="
            + pOrderId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get comments for orderId=" + pOrderId;
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(errorMessage, sqEx);
      }

      throw new DataOperationFailedException(errorMessage, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // no need
      }
    }

    return comments;
  }

  public void createComment(final Comment pComment)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_COMMENT, pComment);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating comment=" + pComment;
      mLogger.error(errorMessage, e);
      throw new DataOperationFailedException(errorMessage, e);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // no need
      }
    }
  }

  public void deleteComment(final int pCommentId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete customer order id=" + pCommentId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete("deleteComment", pCommentId) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException se)
    {
      mLogger.error(errorMessage, se);
      throw new DataOperationFailedException(errorMessage, se);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException se)
      {
        // no need
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage
          + " iBatis returned false");
    }
  }

  public void updateComment(final Comment pComment)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_COMMENT, pComment);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating comment=" + pComment;
      mLogger.error(errorMessage, e);
      throw new DataOperationFailedException(errorMessage, e);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // no need
      }
    }
  }
}
