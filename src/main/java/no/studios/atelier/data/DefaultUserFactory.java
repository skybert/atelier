package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * User factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public final class DefaultUserFactory extends AbstractFactory implements
    UserFactory
{
  public User getUser(final int pUserId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    User user = null;

    try
    {
      mSQLMapClient.startTransaction();
      user = (User) mSQLMapClient.queryForObject(GET_USER, pUserId);
      mSQLMapClient.commitTransaction();

      if (user == null)
      {
        throw new NoSuchObjectException("no such User with id=" + pUserId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get user with id=" + pUserId;
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

    return user;
  }

  public List<User> getAllUsers(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<User> users = null;

    try
    {
      mSQLMapClient.startTransaction();
      users = (List<User>) mSQLMapClient.queryForList(GET_ALL_USERS,
          pCustomerId);
      mSQLMapClient.commitTransaction();

      if (users == null)
      {
        throw new NoSuchObjectException("no Users for customer id="
            + pCustomerId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get users for customer id="
          + pCustomerId;
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

    return users;
  }

  public void createUser(final User pUser) throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_USER, pUser);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating user=" + pUser;
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

  public void deleteUser(final int pUserId) throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete customer order id=" + pUserId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_USER, pUserId) != 0;
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

  public void updateUser(final User pUser)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_USER, pUser);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating user=" + pUser;
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
