package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Invoice factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.7 $
 */
public final class DefaultInvoiceFactory extends DefaultCustomerOrderFactory
    implements InvoiceFactory
{
  private void addFullOrderToInvoiceList(final List<Invoice> pInvoiceList)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    for (Invoice invoice : pInvoiceList)
    {
      if (invoice.getOrder() != null)
      {
        CustomerOrder order = getCustomerOrder(invoice.getOrder().getId());
        invoice.setOrder(order);
      }
      else
      {
        mLogger.error("order is null for invoice=" + invoice);
      }
    }
  }

  public List<Invoice> getInvoiceList(final Date pFromDate, final Date pToDate)
    throws DataOperationFailedException
  {
    List<Invoice> invoiceList = null;
    Map<String, Date> dateMap = new HashMap<String, Date>();
    dateMap.put(FROM_DATE, pFromDate);
    dateMap.put(TO_DATE, pToDate);

    String errorMessage = "failed getting invoiceList " + "fromDate="
        + pFromDate + " toDate=" + pToDate;
    try
    {
      invoiceList = (List<Invoice>) mSQLMapClient.queryForList(
          GET_INVOICE_LIST_IN_DATE_RANGE, dateMap);
      addFullOrderToInvoiceList(invoiceList);
    }
    catch (NoSuchObjectException nsoe)
    {
      throw new DataOperationFailedException(errorMessage, nsoe);
    }
    catch (SQLException se)
    {
      throw new DataOperationFailedException(errorMessage, se);
    }

    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("getInvoiceList fromDate=" + pFromDate + " toDate="
          + pToDate + " returned " + invoiceList.size() + " invoices");
    }

    return invoiceList;
  }

  public int getLastInvoiceId() throws DataOperationFailedException
  {
    Integer invoiceId = new Integer(-1);

    try
    {
      invoiceId = (Integer) mSQLMapClient.queryForObject(GET_LAST_INVOICE_ID,
          null);
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get last invoice id";
      throw new DataOperationFailedException(errorMessage, sqEx);
    }

    return invoiceId;
  }

  public Invoice getInvoice(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    Invoice invoice = null;

    try
    {
      invoice = (Invoice) mSQLMapClient.queryForObject(GET_INVOICE, pInvoiceId);

      if (invoice == null)
      {
        throw new NoSuchObjectException("no invoice with id=" + pInvoiceId);
      }

      addItemsToOrder(invoice.getOrder());
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get invoice with id=" + pInvoiceId;
      throw new DataOperationFailedException(errorMessage, sqEx);
    }

    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("read from DB invoice=" + invoice);
    }

    return invoice;
  }

  public void updateInvoice(final Invoice pInvoice)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_INVOICE, pInvoice);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pInvoice=" + pInvoice + " updated successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating invoice=" + pInvoice;
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

  public void deleteInvoice(final int pInvoiceId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete invoice id=" + pInvoiceId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_INVOICE, pInvoiceId) != 0;
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
      mLogger.debug("pInvoiceId=" + pInvoiceId + " deleted successfully");
    }
  }

  public void createInvoice(final Invoice pInvoice)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_INVOICE, pInvoice);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("pInvoice=" + pInvoice + " created successfully");
      }
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating customerOrder=" + pInvoice;
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

  public List<Integer> getOrderIdByInvoiceId(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<Integer> orderIdList = null;
    try
    {
      orderIdList = (List<Integer>) mSQLMapClient.queryForList(
          GET_ORDER_ID_BY_INVOICE_ID, pInvoiceId);
    }
    catch (SQLException se)
    {
      String errorMessage = "was not able to get order ID(s)"
          + " for pInvoiceId=" + pInvoiceId;
      throw new DataOperationFailedException(errorMessage, se);
    }

    if (orderIdList == null)
    {
      throw new NoSuchObjectException("couldn't find order IDs for "
          + "pInvoiceId=" + pInvoiceId);
    }

    return orderIdList;
  }

  public List<Integer> getInvoiceIdByOrderId(final int pOrderId)
    throws DataOperationFailedException
  {
    List<Integer> invoiceIdList = null;
    try
    {
      invoiceIdList = (List<Integer>) mSQLMapClient.queryForList(
          GET_INVOICE_ID_BY_ORDER_ID, pOrderId);
    }
    catch (SQLException se)
    {
      String errorMessage = "was not able to get invoice ID(s)"
          + " for pOrderId=" + pOrderId;
      throw new DataOperationFailedException(errorMessage, se);
    }

    return invoiceIdList;
  }
}
