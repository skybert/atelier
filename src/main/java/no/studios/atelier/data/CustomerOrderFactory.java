package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * CustomerOrder factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.5 $
 */
public interface CustomerOrderFactory
{
  public CustomerOrder getCustomerOrder(final int pCustomerOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  /**
   * Returns a list of customer orders for a given customer.
   * 
   * @param pCustomerId
   *          an <code>int</code> value
   * @return a <code>CustomerOrder</code> value
   * @exception NoSuchObjectException
   *              if an error occurs
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public List<CustomerOrder> getAllCustomerOrders(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createCustomerOrder(final CustomerOrder pCustomerOrder)
    throws DataOperationFailedException;

  public void deleteCustomerOrder(final int pCustomerOrderId)
    throws DataOperationFailedException;

  public void updateCustomerOrder(final CustomerOrder pCustomerOrder)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public int getLastOrderId() throws DataOperationFailedException;

  public List<CustomerOrder> getOrdersAfterDate(final Date pDate)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
