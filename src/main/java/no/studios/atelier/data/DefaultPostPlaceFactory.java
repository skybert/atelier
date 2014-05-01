package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * PostPlace factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.6 $
 */
public final class DefaultPostPlaceFactory extends AbstractFactory implements
    PostPlaceFactory
{
  private final static String GET_ALL_POST_PLACES = "getAllPostPlaces";
  private final static String GET_POST_PLACE_BY_ID = "getPostPlaceById";
  private final static String GET_POST_PLACE_BY_CODE = "getPostPlaceByCode";
  private final static String INSERT_POST_PLACE = "insertPostPlace";
  private final static String UPDATE_POST_PLACE = "updatePostPlace";
  private final static String DELETE_POST_PLACE = "deletePostPlace";

  private final Object mPostPlaceListLock = new Object();
  private List<PostPlace> mPostPlaceList = null;

  public List<PostPlace> getAllPostPlaces()
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    synchronized (mPostPlaceListLock)
    {
      if (mPostPlaceList != null)
      {
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("mPostPlaceList is already read, # entries="
              + mPostPlaceList.size() + " returning existing list");
        }

        return mPostPlaceList;
      }
    }

    try
    {
      mSQLMapClient.startTransaction();
      synchronized (mPostPlaceListLock)
      {
        mPostPlaceList = (List<PostPlace>) mSQLMapClient.queryForList(
            GET_ALL_POST_PLACES, null);
      }
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "could not get all post places";
      mLogger.error(errorMessage);
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
        // doesn't make any sense to deal with this exception.
      }
    }

    synchronized (mPostPlaceListLock)
    {
      if (mPostPlaceList == null || mPostPlaceList.size() == 0)
      {
        throw new NoSuchObjectException("no post places found");
      }

      return mPostPlaceList;
    }
  }

  public PostPlace getPostPlace(final String pPostCode)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    PostPlace postPlace = null;

    try
    {
      mSQLMapClient.startTransaction();
      postPlace = (PostPlace) mSQLMapClient.queryForObject(
          GET_POST_PLACE_BY_CODE, pPostCode);
      mSQLMapClient.commitTransaction();

      if (postPlace == null)
      {
        throw new NoSuchObjectException("no such PostPlace with id="
            + pPostCode);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get postPlace with id=" + pPostCode;
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
        mLogger.error("could not end transaction", e);
      }
    }

    return postPlace;
  }

  public PostPlace getPostPlace(final int pPostPlaceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    PostPlace postPlace = null;

    try
    {
      mSQLMapClient.startTransaction();
      postPlace = (PostPlace) mSQLMapClient.queryForObject(
          GET_POST_PLACE_BY_ID, pPostPlaceId);
      mSQLMapClient.commitTransaction();

      if (postPlace == null)
      {
        throw new NoSuchObjectException("no such PostPlace with id="
            + pPostPlaceId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get postPlace with id=" + pPostPlaceId;

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
        mLogger.error("could not end transaction", e);
      }
    }

    return postPlace;
  }

  public void createPostPlace(final PostPlace pPostPlace)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_POST_PLACE, pPostPlace);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("unable to create pPostPlace="
          + pPostPlace, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end transaction", e);
      }
    }

    // return created;
  }

  public void deletePostPlace(final int pPostPlaceId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "could not delete pPostCode=" + pPostPlaceId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_POST_PLACE, pPostPlaceId) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
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
        mLogger.error("could not end transaction", e);
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage);
    }
  }

  public void updatePostPlace(final PostPlace pPostPlace)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    boolean updated = false;

    String errorMessage = "could not update postPlace=" + pPostPlace;

    try
    {
      mSQLMapClient.startTransaction();
      updated = mSQLMapClient.update(UPDATE_POST_PLACE, pPostPlace) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
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
        mLogger.error("could not end transaction", e);
      }
    }

    if (!updated)
    {
      throw new DataOperationFailedException(errorMessage);
    }

  }
}
