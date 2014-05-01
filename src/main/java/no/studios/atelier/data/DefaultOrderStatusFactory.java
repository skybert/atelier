package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * OrderStatus factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public final class DefaultOrderStatusFactory extends AbstractFactory implements
    OrderStatusFactory
{
  private final static String GET_ORDER_STATUS = "getOrderStatusById";
  private final static String GET_ALL_ORDER_STATUS = "getAllOrderStatus";
  private final static String INSERT_ORDER_STATUS = "insertOrderStatus";
  private final static String UPDATE_ORDER_STATUS = "updateOrderStatus";
  private final static String DELETE_ORDER_STATUS = "deleteOrderStatus";

  public List<OrderStatus> getAllOrderStatus()
    throws DataOperationFailedException,
    NoSuchObjectException
  {
    List<OrderStatus> orderStatusList = null;

    try
    {
      mSQLMapClient.startTransaction();
      orderStatusList = (List<OrderStatus>) mSQLMapClient.queryForList(
          GET_ALL_ORDER_STATUS, null);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "could not get all orderStatus";
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

    if (orderStatusList == null || orderStatusList.size() == 0)
    {
      throw new NoSuchObjectException("no orderStatus found");
    }

    return orderStatusList;
  }

  public OrderStatus getOrderStatus(final int pOrderStatusId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    OrderStatus orderStatus = null;

    try
    {
      mSQLMapClient.startTransaction();
      orderStatus = (OrderStatus) mSQLMapClient.queryForObject(
          GET_ORDER_STATUS, pOrderStatusId);
      mSQLMapClient.commitTransaction();

      if (orderStatus == null)
      {
        throw new NoSuchObjectException("no such OrderStatus with id="
            + pOrderStatusId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get orderStatus with id="
          + pOrderStatusId;
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
        // doesn't make any sense to deal with this exception.
      }
    }

    return orderStatus;
  }

  public void createOrderStatus(final OrderStatus pOrderStatus)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_ORDER_STATUS, pOrderStatus);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("unable to create pOrderStatus="
          + pOrderStatus, sqEx);
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

    // return created;
  }

  public void deleteOrderStatus(final int pOrderStatusId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "could not delete pOrderStatusId=" + pOrderStatusId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_ORDER_STATUS, pOrderStatusId) != 0;
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
        // doesn't make any sense to deal with this exception.
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage);
    }
  }

  public void updateOrderStatus(final OrderStatus pOrderStatus)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    boolean updated = false;

    try
    {
      mSQLMapClient.startTransaction();
      updated = mSQLMapClient.update(UPDATE_ORDER_STATUS, pOrderStatus) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("could not update orderStatus="
          + pOrderStatus);
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

    // return updated;
  }
}
