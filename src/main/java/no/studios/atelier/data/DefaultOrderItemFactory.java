package no.studios.atelier.data;

import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * OrderItem factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public final class DefaultOrderItemFactory extends AbstractFactory implements
    OrderItemFactory
{
  public OrderItem getOrderItem(final int pOrderItemId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    OrderItem orderItem = null;

    try
    {
      mSQLMapClient.startTransaction();
      orderItem = (OrderItem) mSQLMapClient.queryForObject(GET_ORDER_ITEM,
          pOrderItemId);
      mSQLMapClient.commitTransaction();

      if (orderItem == null)
      {
        throw new NoSuchObjectException("no such OrderItem with id="
            + pOrderItemId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get orderItem with id=" + pOrderItemId;
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

    return orderItem;
  }

  public List<OrderItem> getAllOrderItems(final int pOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<OrderItem> orderItems = null;

    try
    {
      mSQLMapClient.startTransaction();
      orderItems = (List<OrderItem>) mSQLMapClient.queryForList(
          GET_ORDER_ITEMS_BY_ORDER_ID, pOrderId);
      mSQLMapClient.commitTransaction();

      if (orderItems == null)
      {
        throw new NoSuchObjectException("no OrderItems for orderId=" + pOrderId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get orderItems for orderItem id="
          + pOrderId;
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

    return orderItems;
  }

  private void setDeliveryDate(final CustomerOrder pOrder) throws SQLException
  {
    // set delivery date
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(pOrder.getCreationDate());
    int longestProductionTime = 0;
    List<OrderItem> orderItemList = (List<OrderItem>) mSQLMapClient
        .queryForList(GET_ORDER_ITEMS_BY_ORDER_ID, pOrder.getId());

    for (OrderItem orderItem : orderItemList)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("product=" + orderItem.getProduct());
      }

      int productionTime = orderItem.getProduct().getProductionTime();
      if (productionTime > longestProductionTime)
      {
        longestProductionTime = productionTime;
      }
    }

    calendar.roll(Calendar.DAY_OF_YEAR, longestProductionTime);

    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("updating deliveryDate for order id=" + pOrder.getId()
          + " longestProductionTime=" + longestProductionTime + " old date="
          + pOrder.getDeliveryDate() + " new date=" + calendar.getTime());
    }

    pOrder.setDeliveryDate(calendar.getTime());
  }

  /**
   * Updates the order's updatedDate, deliveryDate and totalSum.
   * 
   * @param pOrderId
   *          an <code>int</code> value
   * @exception SQLException
   *              if an error occurs
   */
  private void updateOrder(final int pOrderId) throws SQLException
  {
    CustomerOrder customerOrder = (CustomerOrder) mSQLMapClient.queryForObject(
        GET_CUSTOMER_ORDER, pOrderId);
    customerOrder.setUpdatedDate(new Date());
    setDeliveryDate(customerOrder);
    customerOrder.setTotalAmount(PriceUtil.getPrice(customerOrder));
    mSQLMapClient.insert(UPDATE_CUSTOMER_ORDER, customerOrder);
  }

  public void createOrderItem(final OrderItem pOrderItem)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_ORDER_ITEM, pOrderItem);
      updateOrder(pOrderItem.getOrderId());
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pOrderItem=" + pOrderItem + " created successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating orderItem=" + pOrderItem;
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

  public void deleteOrderItem(final int pOrderItemId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete order item id=" + pOrderItemId;

    try
    {
      mSQLMapClient.startTransaction();
      int orderId = (Integer) mSQLMapClient.queryForObject(
          GET_ORDER_ID_BY_ITEM_ID, pOrderItemId);
      deleted = mSQLMapClient.delete(DELETE_ORDER_ITEM, pOrderItemId) != 0;
      updateOrder(orderId);
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
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("pOrderItemId=" + pOrderItemId + " deleted successfully");
    }
  }

  public void updateOrderItem(final OrderItem pOrderItem)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_ORDER_ITEM, pOrderItem);
      updateOrder(pOrderItem.getOrderId());
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pOrderItem=" + pOrderItem + " updated successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating orderItem=" + pOrderItem;
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
