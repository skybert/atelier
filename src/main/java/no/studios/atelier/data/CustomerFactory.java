package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * CustomerFactory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.9 $
 */
public interface CustomerFactory
{
  public final static String DELETE_CUSTOMER = "deleteCustomer";
  public final static String GET_ALL_CUSTOMERS = "getAllCustomers";
  public final static String GET_CUSTOMER = "getCustomerById";
  public final static String GET_CUSTOMERS_BY_ANY_OLD_ID = "getCustomersByAnyOldId";
  public final static String GET_CUSTOMERS_BY_ANY_NAME = "getCustomersByAnyName";
  public final static String INSERT_CUSTOMER = "insertCustomer";
  public final static String UPDATE_CUSTOMER = "updateCustomer";
  public final static String GET_LAST_CUSTOMER_ID = "get-last-customer-id";

  public Customer getCustomer(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public int getLastCustomerId() throws DataOperationFailedException;

  /**
   * Returns a list of customers with oldArchiveId or oldCustomerId. Normally,
   * this would be only one customer, hence the method name.
   * 
   * @param pOldId
   *          a <code>String</code> value, either oldCustomerId or oldArchiveId.
   * @return a <code>Customer</code> value
   * @exception NoSuchObjectException
   *              if an error occurs
   * @exception DataOperationFailedException
   *              if an error occurs
   */
  public List<Customer> getCustomersByAnyOldId(final String pOldId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public List<Customer> getCustomersByAnyName(final String pName)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createCustomer(final Customer pCustomer)
    throws DataOperationFailedException;

  public void deleteCustomer(final int pCustomerId)
    throws DataOperationFailedException;

  public void updateCustomer(final Customer pCustomer)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
