package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Customer factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.9 $
 */
public final class DefaultCustomerFactory extends AbstractFactory implements
    CustomerFactory
{
  public int getLastCustomerId() throws DataOperationFailedException
  {
    Integer id = null;
    try
    {
      mSQLMapClient.startTransaction();
      id = (Integer) mSQLMapClient.queryForObject(GET_LAST_CUSTOMER_ID, null);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("last customerId=" + id);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get max id";
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

    return id.intValue();
  }

  public Customer getCustomer(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    Customer customer = null;

    try
    {
      mSQLMapClient.startTransaction();
      customer = (Customer) mSQLMapClient.queryForObject("getCustomerById",
          pCustomerId);
      mSQLMapClient.commitTransaction();

      if (customer == null)
      {
        throw new NoSuchObjectException("no such Customer with id="
            + pCustomerId);
      }

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("returning customer=" + customer);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customer with id=" + pCustomerId;
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
        // no need to do anything with this exception
        // we are ending the transaction anyway so catching
        // another one will have no meaning
      }
    }

    return customer;
  }

  public List<Customer> getCustomersByAnyOldId(final String pOldId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<Customer> customerList = null;

    try
    {
      mSQLMapClient.startTransaction();
      customerList = (List<Customer>) mSQLMapClient.queryForList(
          GET_CUSTOMERS_BY_ANY_OLD_ID, pOldId);
      mSQLMapClient.commitTransaction();

      if (customerList == null)
      {
        throw new NoSuchObjectException("no such Customer with pOldId="
            + pOldId);
      }

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("returning " + customerList.size()
            + " customers for anyOldId=" + pOldId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customer with pOldId=" + pOldId;
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
        // no need to do anything with this exception
        // we are ending the transaction anyway so catching
        // another one will have no meaning
      }
    }

    return customerList;
  }

  public List<Customer> getCustomersByAnyName(final String pName)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<Customer> customerList = null;

    try
    {
      customerList = (List<Customer>) mSQLMapClient.queryForList(
          GET_CUSTOMERS_BY_ANY_NAME, "%" + pName + "%");
      if (customerList == null)
      {
        throw new NoSuchObjectException("no such Customer with pOldId=" + pName);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get customer with anyName=" + pName;
      throw new DataOperationFailedException(errorMessage, sqEx);
    }

    return customerList;
  }

  public void createCustomer(final Customer pCustomer)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_CUSTOMER, pCustomer);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("created customer=" + pCustomer + " successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating customer=" + pCustomer;
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

  public void deleteCustomer(final int pCustomerId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete customer id=" + pCustomerId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_CUSTOMER, pCustomerId) != 0;
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
      mLogger.debug("deleted customerId=" + pCustomerId + " successfully");
    }

  }

  public void updateCustomer(final Customer pCustomer)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_CUSTOMER, pCustomer);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("updated customer=" + pCustomer + " successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating customer=" + pCustomer;
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
