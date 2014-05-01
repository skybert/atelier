package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;

/**
 * A nice order status view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public final class OrderStatusView extends AbstractView
{
  private OrderStatusFactory mOrderStatusFactory;

  public OrderStatusView()
  {
    mOrderStatusFactory = new DefaultOrderStatusFactory();
  }

  public OrderStatus getOrderStatus(final String pId)
  {
    OrderStatus orderStatus = null;

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
      return orderStatus;
    }

    try
    {
      orderStatus = mOrderStatusFactory.getOrderStatus(id);
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
    return orderStatus;
  }

  public List<OrderStatus> getAllOrderStatus()
  {
    List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();

    try
    {
      orderStatusList = mOrderStatusFactory.getAllOrderStatus();
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

    Collections.sort(orderStatusList, new OrderStatusComparator());
    return orderStatusList;
  }

}