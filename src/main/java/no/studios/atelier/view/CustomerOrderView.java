package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import javax.servlet.http.HttpServletRequest;
import no.studios.atelier.model.CustomerOrder;
import no.studios.atelier.util.*;

/**
 * A nice customer order view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.8 $
 */
public class CustomerOrderView extends AbstractView
{
  public CustomerOrderView(final HttpServletRequest pRequest)
  {
    super(pRequest);
  }

  public CustomerOrderView()
  {

  }

  public boolean hasNewOrderFailed(final HttpServletRequest pRequest)
  {
    String newOrder = pRequest.getParameter(CustomerOrder.KEY_NEW_ORDER);
    boolean newOrderAttemptFailed = false;

    if (!StringUtil.isEmpty(newOrder) && "failed".equals(newOrder))
    {
      newOrderAttemptFailed = true;
    }
    return newOrderAttemptFailed;
  }

  /**
   * Uses {@link CustomerOrder.KEY_ORDER_ID} inside the request object to find
   * the CustomerOrder.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @return a <code>CustomerOrder</code> value
   */
  public CustomerOrder getCustomerOrder(final HttpServletRequest pRequest)
  {
    String customerOrderId = pRequest.getParameter(CustomerOrder.KEY_ORDER_ID);
    CustomerOrder customerOrder = null;

    if (!StringUtil.isEmpty(customerOrderId))
    {
      customerOrder = getCustomerOrder(customerOrderId);
    }

    return customerOrder;
  }

  public CustomerOrder getCustomerOrder(final int pId)
  {
    CustomerOrder customerOrder = null;

    try
    {
      customerOrder = mObjectFactory.getCustomerOrder(pId);
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
    return customerOrder;

  }

  public CustomerOrder getCustomerOrder(final String pId)
  {
    CustomerOrder customerOrder = null;

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
      return customerOrder;
    }

    return getCustomerOrder(id);
  }

  public double getOrderBalance(final CustomerOrder pOrder)
  {
    return getOrderPrice(pOrder) - pOrder.getPaidAmount();
  }

  public static double getOrderPrice(final CustomerOrder pOrder)
  {
    return PriceUtil.getPrice(pOrder);
  }

  /**
   * Will return the invoice ID for the order. If no invoice exists for this
   * order, <code>-1</code> is returned.
   * 
   * @param pOrder
   *          a <code>CustomerOrder</code> value
   * @return a <code>int</code> value
   */
  public int getInvoiceId(final CustomerOrder pOrder)
  {
    int invoiceId = -1;
    try
    {
      List<Integer> invoiceIdList = mObjectFactory.getInvoiceIdByOrderId(pOrder
          .getId());
      if (invoiceIdList.size() == 1)
      {
        return invoiceIdList.get(0);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return invoiceId;
  }

}