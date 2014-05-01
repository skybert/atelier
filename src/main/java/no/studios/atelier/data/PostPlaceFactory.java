package no.studios.atelier.data;

import java.util.List;
import no.studios.atelier.model.*;

/**
 * PostPlaceFactory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public interface PostPlaceFactory
{
  public List<PostPlace> getAllPostPlaces()
    throws NoSuchObjectException,
    DataOperationFailedException;

  public PostPlace getPostPlace(final String pPostCode)
    throws NoSuchObjectException,
    DataOperationFailedException;

  /**
   * Get the post place by its database ID.
   * 
   * @param pPostPlaceId
   *          an <code>int</code> value
   * @return a <code>PostPlace</code> value
   * @exception NoSuchObjectException
   *              if an error occurs
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public PostPlace getPostPlace(final int pPostPlaceId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createPostPlace(final PostPlace pPostPlace)
    throws DataOperationFailedException;

  public void deletePostPlace(final int pPostPlaceId)
    throws DataOperationFailedException;

  public void updatePostPlace(final PostPlace pPostPlace)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
