package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * A nice customer view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public final class CustomerView extends AbstractView
{
  private CustomerFactory mCustomerFactory;
  private CustomerOrderFactory mCustomerOrderFactory;

  public CustomerView()
  {
    mCustomerFactory = new DefaultCustomerFactory();
    mCustomerOrderFactory = new DefaultCustomerOrderFactory();
  }

  public String getCustomerId(final HttpServletRequest pRequest)
  {
    return pRequest.getParameter(CustomerOrder.KEY_CUSTOMER_ID);
  }

  /**
   * Uses {@link Customer.KEY_CUSTOMER_ID} inside the request object to find the
   * Customer.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @return a <code>Customer</code> value
   */
  public Customer getCustomer(final HttpServletRequest pRequest)
  {
    String customerId = getCustomerId(pRequest);
    Customer customer = null;

    if (!StringUtil.isEmpty(customerId))
    {
      customer = getCustomer(customerId);
    }

    return customer;
  }

  public List<Customer> getCustomersByAnyOldId(final String pId)
  {
    List<Customer> customerList = new ArrayList<Customer>();
    try
    {
      customerList = mCustomerFactory.getCustomersByAnyOldId(pId);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return customerList;
  }

  public List<Customer> getCustomersByAnyName(final String pName)
  {
    List<Customer> customerList = new ArrayList<Customer>();
    try
    {
      customerList = mCustomerFactory.getCustomersByAnyName(pName);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return customerList;
  }

  public Customer getCustomer(final String pId)
  {
    Customer customer = null;

    int id = -1;

    try
    {
      id = Integer.parseInt(pId);
    }
    catch (NumberFormatException nfe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("could not convert pId=" + pId + " to an int", nfe);
      }
      return customer;
    }

    try
    {
      customer = mCustomerFactory.getCustomer(id);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }
    return customer;
  }

  public List<CustomerOrder> getAllCustomerOrders(final int pCustomerId)
  {
    List<CustomerOrder> customerOrderList = new ArrayList<CustomerOrder>();

    try
    {
      customerOrderList = mCustomerOrderFactory
          .getAllCustomerOrders(pCustomerId);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return customerOrderList;
  }

}