package no.studios.atelier.data;

import no.studios.atelier.model.*;

/**
 * Describe interface CommentFactory here.
 * 
 * 
 * Created: Mon Nov 26 20:00:03 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface CommentFactory
{
  public final static String UPDATE_COMMENT = "updateComment";
  public final static String INSERT_COMMENT = "insertComment";
  public final static String GET_COMMENT = "getCommentById";
  public final static String GET_ALL_COMMENTS = "getAllCommentsByOrderId";

  public Comment getComment(final int pCommentId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createComment(final Comment pComment)
    throws DataOperationFailedException;

  public void deleteComment(final int pCommentId)
    throws DataOperationFailedException;

  public void updateComment(final Comment pComment)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
