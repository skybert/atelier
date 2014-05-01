package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import javax.servlet.http.HttpServletRequest;
import no.studios.atelier.data.NoSuchObjectException;
import no.studios.atelier.data.DataOperationFailedException;
import no.studios.atelier.model.AtelierEntity;
import no.studios.atelier.util.StringUtil;

/**
 * A nice order item view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public final class OrderItemView extends AbstractView
{
  private OrderItemFactory mOrderItemFactory;

  public OrderItemView()
  {
    mOrderItemFactory = new DefaultOrderItemFactory();
  }

  public double getTotalAmount(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? pOrderItem.getNumberOfItems()
        * pOrderItem.getProduct().getPrice() : 0.0;
  }

  public double getOldTotalAmount(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? pOrderItem.getTotalAmount() : 0.0;
  }

  public String getNumberOfItems(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? Integer.toString(pOrderItem.getNumberOfItems())
        : Integer.toString(1);
  }

  public String getDiscount(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? Double.toString(pOrderItem.getDiscount())
        : Double.toString(0.0);
  }

  public String getComment(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? pOrderItem.getComment() : "";
  }

  public String getSubmitLabel(final OrderItem pOrderItem)
  {
    return pOrderItem != null ? "Endre" : "Registrer";
  }

  /**
   * Uses {@link OrderItem.KEY_ORDER_ITEM_ID} inside the request object to find
   * the OrderItem.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @return a <code>OrderItem</code> value
   */
  public OrderItem getOrderItem(final HttpServletRequest pRequest)
  {
    String orderItemId = pRequest.getParameter(AtelierEntity.KEY_ORDER_ITEM_ID);
    OrderItem orderItem = null;

    if (!StringUtil.isEmpty(orderItemId))
    {
      orderItem = getOrderItem(orderItemId);
    }

    return orderItem;
  }

  /**
   * Reads the orderItem frm the request.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @param pFromScope
   *          a <code>boolean</code> value. If true, the method will try to ead
   *          the order item from the request scope using the
   *          <code>OrderItem.class.getName()</code> key.
   * @return an <code>OrderItem</code> value
   */
  public OrderItem getOrderItem(
    final HttpServletRequest pRequest,
    final boolean pFromScope)
  {
    OrderItem orderItem = null;

    if (pFromScope)
    {
      try
      {
        orderItem = (OrderItem) pRequest
            .getAttribute(OrderItem.class.getName());
      }
      catch (ClassCastException cce)
      {
        mLogger.error(cce);
      }
    }
    else
    {
      orderItem = getOrderItem(pRequest);
    }

    return orderItem;
  }

  public OrderItem getOrderItem(final String pId)
  {
    OrderItem orderItem = null;

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
      return orderItem;
    }

    try
    {
      orderItem = mOrderItemFactory.getOrderItem(id);
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
    return orderItem;
  }

}