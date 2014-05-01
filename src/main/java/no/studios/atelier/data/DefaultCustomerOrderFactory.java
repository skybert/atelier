package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

/**
 * CustomerOrder factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.11 $
 */
public class DefaultCustomerOrderFactory extends AbstractFactory implements
    CustomerOrderFactory
{
  public int getLastOrderId() throws DataOperationFailedException
  {
    Integer orderId = new Integer(-1);

    try
    {
      mSQLMapClient.startTransaction();
      orderId = (Integer) mSQLMapClient.queryForObject(GET_LAST_ORDER_ID, null);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get last order id";

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
        mLogger.error("could not end the transactoin", e);
      }
    }

    return orderId;
  }

  protected void addItemsToOrder(CustomerOrder pOrder) throws SQLException
  {
    List<OrderItem> orderItemList = (List<OrderItem>) mSQLMapClient
        .queryForList(GET_ORDER_ITEMS_BY_ORDER_ID, pOrder.getId());
    pOrder.setOrderItems(orderItemList);
  }

  protected CustomerOrder getCustomerOrderInTransaction(final int pOrderId)
    throws SQLException,
    NoSuchObjectException
  {
    CustomerOrder customerOrder = null;

    customerOrder = (CustomerOrder) mSQLMapClient.queryForObject(
        GET_CUSTOMER_ORDER, pOrderId);

    if (customerOrder == null)
    {
      throw new NoSuchObjectException("no such CustomerOrder with id="
          + pOrderId);
    }

    addItemsToOrder(customerOrder);

    return customerOrder;
  }

  public CustomerOrder getCustomerOrder(final int pCustomerOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    CustomerOrder customerOrder = null;

    try
    {
      mSQLMapClient.startTransaction();
      customerOrder = getCustomerOrderInTransaction(pCustomerOrderId);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customerOrder with id="
          + pCustomerOrderId;
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
        mLogger.error("could not end the transactoin", e);
      }
    }

    return customerOrder;
  }

  public List<CustomerOrder> getAllCustomerOrders(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<CustomerOrder> customerOrders = null;

    try
    {
      mSQLMapClient.startTransaction();
      customerOrders = (List<CustomerOrder>) mSQLMapClient.queryForList(
          GET_ALL_CUSTOMER_ORDERS, pCustomerId);
      if (customerOrders == null)
      {
        throw new NoSuchObjectException("no CustomerOrders for customer id="
            + pCustomerId);
      }

      for (CustomerOrder customerOrder : customerOrders)
      {
        addItemsToOrder(customerOrder);
      }

      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customerOrders for customer id="
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
        mLogger.error("could not end the transaction", e);
      }
    }

    return customerOrders;
  }

  public void createCustomerOrder(final CustomerOrder pCustomerOrder)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_CUSTOMER_ORDER, pCustomerOrder);

      List<OrderItem> orderItemList = pCustomerOrder.getOrderItems();
      if (orderItemList != null && orderItemList.size() > 0)
      {
        for (OrderItem orderItem : orderItemList)
        {
          orderItem.setOrderId(pCustomerOrder.getId());
          mSQLMapClient.insert(INSERT_ORDER_ITEM, orderItem);
        }
      }

      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pCustomerOrder=" + pCustomerOrder
            + " created successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating customerOrder=" + pCustomerOrder;
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
        mLogger.error("could not end the transactoin", e);
      }
    }
  }

  public void deleteCustomerOrder(final int pCustomerOrderId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete customer order id="
        + pCustomerOrderId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_ORDER_ITEM_BY_ORDER_ID,
          pCustomerOrderId) != 0;
      deleted = mSQLMapClient.delete(DELETE_CUSTOMER_ORDER, pCustomerOrderId) != 0;
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
      catch (SQLException e)
      {
        mLogger.error("could not end the transactoin", e);
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage
          + " iBatis returned false");
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("pCustomerOrderId=" + pCustomerOrderId
          + " deleted successfully");
    }
  }

  public void updateCustomerOrder(final CustomerOrder pCustomerOrder)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_CUSTOMER_ORDER, pCustomerOrder);

      List<OrderItem> orderItemList = pCustomerOrder.getOrderItems();
      if (orderItemList != null && orderItemList.size() > 0)
      {
        for (OrderItem orderItem : orderItemList)
        {
          orderItem.setOrderId(pCustomerOrder.getId());
          mSQLMapClient.insert(UPDATE_ORDER_ITEM, orderItem);
        }
      }

      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pCustomerOrder=" + pCustomerOrder
            + " updated successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating customerOrder=" + pCustomerOrder;
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
        mLogger.error("could not end the transactoin", e);
      }
    }
  }

  public List<CustomerOrder> getOrdersAfterDate(final Date pDate)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<CustomerOrder> customerOrders = null;

    try
    {
      mSQLMapClient.startTransaction();
      customerOrders = (List<CustomerOrder>) mSQLMapClient.queryForList(
          GET_ORDERS_AFTER_DATE, pDate);
      if (customerOrders == null)
      {
        throw new NoSuchObjectException("no CustomerOrders after date=" + pDate);
      }

      for (CustomerOrder customerOrder : customerOrders)
      {
        addItemsToOrder(customerOrder);
      }

      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customerOrders after date=" + pDate;
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
        mLogger.error("could not end the transactoin", e);
      }
    }

    return customerOrders;
  }
}
