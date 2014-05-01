package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.*;

/**
 * PaymentType factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public final class DefaultPaymentTypeFactory extends AbstractFactory implements
    PaymentTypeFactory
{
  private final static String GET_PAYMENT_TYPE = "get-payment-type-by-id";
  private final static String GET_ALL_PAYMENT_TYPES = "get-all-payment-types";
  private final static String INSERT_PAYMENT_TYPE = "insert-payment-type";
  private final static String UPDATE_PAYMENT_TYPE = "update-payment-type";
  private final static String DELETE_PAYMENT_TYPE = "delete-payment-type";

  private final Object mPaymentTypeListLock = new Object();
  private List<PaymentType> mPaymentTypeList = null;

  public List<PaymentType> getAllPaymentTypes()
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    synchronized (mPaymentTypeListLock)
    {
      if (mPaymentTypeList != null)
      {
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("mPaymentTypeList is already read, # entries="
              + mPaymentTypeList.size() + " returning existing list");
        }

        return mPaymentTypeList;
      }
    }

    try
    {
      mSQLMapClient.startTransaction();
      synchronized (mPaymentTypeListLock)
      {
        mPaymentTypeList = (List<PaymentType>) mSQLMapClient.queryForList(
            GET_ALL_PAYMENT_TYPES, null);
      }
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "could not get all paymentTypes";
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

    synchronized (mPaymentTypeListLock)
    {
      if (mPaymentTypeList == null || mPaymentTypeList.size() == 0)
      {
        throw new NoSuchObjectException("no paymentTypes found");
      }

      return mPaymentTypeList;
    }
  }

  public PaymentType getPaymentType(final int pPaymentTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    PaymentType paymentType = null;

    try
    {
      mSQLMapClient.startTransaction();
      paymentType = (PaymentType) mSQLMapClient.queryForObject(
          GET_PAYMENT_TYPE, pPaymentTypeId);
      mSQLMapClient.commitTransaction();

      if (paymentType == null)
      {
        throw new NoSuchObjectException("no such PaymentType with id="
            + pPaymentTypeId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get paymentType with id="
          + pPaymentTypeId;
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

    return paymentType;
  }

  public void createPaymentType(final PaymentType pPaymentType)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_PAYMENT_TYPE, pPaymentType);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("unable to create pPaymentType="
          + pPaymentType, sqEx);
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

  public void deletePaymentType(final int pPaymentTypeId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "could not delete pPaymentTypeId=" + pPaymentTypeId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_PAYMENT_TYPE, pPaymentTypeId) != 0;
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

  public void updatePaymentType(final PaymentType pPaymentType)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    boolean updated = false;

    try
    {
      mSQLMapClient.startTransaction();
      updated = mSQLMapClient.update(UPDATE_PAYMENT_TYPE, pPaymentType) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("could not update paymentType="
          + pPaymentType);
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
