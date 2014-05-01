package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * Invoice factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public interface InvoiceFactory
{
  public Invoice getInvoice(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  /**
   * Returns a list of customer orders for a given customer.
   * 
   * @param pCustomerId
   *          an <code>int</code> value
   * @return a <code>Invoice</code> value
   * @exception NoSuchObjectException
   *              if an error occurs
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  // public List<Invoice> getAllInvoices(final int pCustomerId)
  // throws NoSuchObjectException, DataOperationFailedException;

  public void createInvoice(final Invoice pInvoice)
    throws DataOperationFailedException;

  public void deleteInvoice(final int pInvoiceId)
    throws DataOperationFailedException;

  public void updateInvoice(final Invoice pInvoice)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public int getLastInvoiceId() throws DataOperationFailedException;

  /**
   * Returns a list of order IDs that correspond to the given invoice ID.
   * Normally, this only be one.
   * 
   * @param pInvoice
   *          an <code>int</code> value
   * @return a <code>List</code> value
   * @exception NoSuchObjectException
   *              if an error occurs
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public List<Integer> getOrderIdByInvoiceId(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  /**
   * Returns a list of invoice IDs that correspond to the given order ID.
   * Normally, this only be one.
   * 
   * @param pOrderId
   *          an <code>int</code> value
   * @return a <code>List</code> value, returns a null list if no invoices could
   *         be found. This is totally ok and hence no excepiton is thrown.
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public List<Integer> getInvoiceIdByOrderId(final int pOrderId)
    throws DataOperationFailedException;

  /**
   * Returns a list of invoices in date interval.
   * 
   * @param pFromDate
   *          a <code>Date</code> value
   * @param pToDate
   *          a <code>Date</code> value
   * @return a <code>List</code> value
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public List<Invoice> getInvoiceList(final Date pFromDate, final Date pToDate)
    throws DataOperationFailedException;

}
