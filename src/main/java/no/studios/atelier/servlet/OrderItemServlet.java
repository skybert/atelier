package no.studios.atelier.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import no.studios.atelier.model.*;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;

/**
 * Describe class <code>OrderItemServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $ $Date$
 */
public class OrderItemServlet extends AbstractAtelierServlet
{
  private ProductFactory mProductFactory;
  private OrderItemFactory mOrderItemFactory;
  private PaymentTypeFactory mPaymentTypeFactory;

  // TODO proper
  final static long serialVersionUID = 0L;

  public OrderItemServlet()
  {
    mProductFactory = new DefaultProductFactory();
    mOrderItemFactory = new DefaultOrderItemFactory();
    mPaymentTypeFactory = new DefaultPaymentTypeFactory();
  }

  public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
    throws ServletException,
    IOException
  {
    super.configure(pRequest, pResponse);
    int command = getCommand(pRequest.getRequestURI(),
        AtelierEntity.URI_ORDER_ITEM);

    debug(pRequest);

    switch (command)
    {
      case COMMAND_NEW:
        createOrderItem(pRequest, pResponse);
        break;
      case COMMAND_EDIT:
        editOrderItem(pRequest, pResponse);
        break;
      case COMMAND_DELETE:
        deleteOrderItem(pRequest, pResponse);
        break;
      default:
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("no recognisable command, URI="
              + pRequest.getRequestURI());
        }

        pResponse
            .sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath());
        break;
    }
  }

  private boolean validOrderItem(final Map pOrderMap)
  {
    return pOrderMap.containsKey(OrderItem.KEY_ORDER_ID)
        && pOrderMap.containsKey(OrderItem.KEY_PRODUCT_ID);
  }

  private OrderItem getOrderItem(final HttpServletRequest pRequest)
    throws IOException
  {
    OrderItem orderItem = new DefaultOrderItem();

    // will only be there when editing an existing order item
    int orderItemId = getInt(pRequest, AtelierEntity.KEY_ORDER_ITEM_ID);
    if (orderItemId != -1)
    {
      orderItem.setId(orderItemId);
    }

    // we also get the order id, so we know to which order we want to
    // "hook on to".
    int customerOrderId = getInt(pRequest, OrderItem.KEY_ORDER_ID);
    if (customerOrderId != -1)
    {
      orderItem.setOrderId(customerOrderId);
    }

    try
    {
      int numberOfItems = getInt(pRequest, OrderItem.KEY_NUMBER_OF_ITEMS);
      if (numberOfItems == -1)
      {
        // default is one item :-)
        numberOfItems = 1;
      }
      orderItem.setNumberOfItems(numberOfItems);

      // changing the product changes many things ...
      int productId = getInt(pRequest, OrderItem.KEY_PRODUCT_ID);
      if (productId != -1)
      {
        Product product = mProductFactory.getProduct(productId);
        orderItem.setProduct(product);
        // don't set it here
        // double totalPrice = product.getPrice() * numberOfItems;
        // orderItem.setTotalAmount(totalPrice);
      }
      else if (mLogger.isDebugEnabled())
      {
        mLogger.debug("could not find=" + OrderItem.KEY_PRODUCT_ID);
      }

      String discount = (String) pRequest.getParameter(OrderItem.KEY_DISCOUNT);
      if (!StringUtil.isEmpty(discount))
      {
        orderItem.setDiscount(Double.parseDouble(discount));
      }

      String totalAmount = (String) pRequest
          .getParameter(OrderItem.KEY_TOTAL_AMOUNT);
      if (!StringUtil.isEmpty(totalAmount))
      {
        orderItem.setTotalAmount(Double.parseDouble(totalAmount));
      }

      String comment = (String) pRequest.getParameter(OrderItem.KEY_COMMENT);
      if (!StringUtil.isEmpty(comment))
      {
        orderItem.setComment(comment);
      }

      return orderItem;
    }
    catch (NumberFormatException nfe)
    {
      mLogger.error(nfe);
    }
    catch (NoSuchObjectException nsoe)
    {
      mLogger.error(nsoe);
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return null;
  }

  private void editOrderItem(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validOrderItem(pRequest.getParameterMap()))
    {
      OrderItem updateOrderItem = getOrderItem(pRequest);
      int orderItemId = getInt(pRequest, OrderItem.KEY_ORDER_ITEM_ID);

      try
      {
        OrderItem originalOrderItem = mOrderItemFactory
            .getOrderItem(orderItemId);

        // update the price if either product or number of items have
        // changed.
        if (updateOrderItem.getProduct().getId() != originalOrderItem
            .getProduct().getId()
            || updateOrderItem.getNumberOfItems() != originalOrderItem
                .getNumberOfItems()
            || updateOrderItem.getDiscount() != originalOrderItem.getDiscount())
        {
          Product product = updateOrderItem.getProduct();
          originalOrderItem.setProduct(product);

          double totalPrice = ((updateOrderItem.getNumberOfItems() * product
              .getPrice()) - (updateOrderItem.getNumberOfItems()
              * product.getPrice() * updateOrderItem.getDiscount() / 100));

          if (mLogger.isDebugEnabled())
          {
            mLogger.debug("recalculating order sum, old="
                + originalOrderItem.getTotalAmount() + " new=" + totalPrice);
          }

          originalOrderItem.setTotalAmount(totalPrice);
        }
        else
        {
          originalOrderItem.setTotalAmount(updateOrderItem.getTotalAmount());
        }

        originalOrderItem.setDiscount(updateOrderItem.getDiscount());
        originalOrderItem.setNumberOfItems(updateOrderItem.getNumberOfItems());
        originalOrderItem.setComment(updateOrderItem.getComment());

        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("originalOrderItem=" + originalOrderItem
              + " updateOrderItem=" + updateOrderItem);
        }

        mOrderItemFactory.updateOrderItem(originalOrderItem);

        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewOrderItem.jsp?" + Customer.KEY_ORDER_ID + "="
            + originalOrderItem.getOrderId() + "&"
            + AtelierEntity.KEY_ORDER_ITEM_ID + "=" + originalOrderItem.getId()
            + "&" + OrderItem.URI_EDIT + "=" + OrderItem.URI_SUCCESS);
        return;
      }
      catch (NumberFormatException nfe)
      {
        mLogger.error(nfe);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }

      sendErrorMessage(updateOrderItem.getOrderId(), AtelierEntity.URI_EDIT,
          pRequest, pResponse);
    }
  }

  private void createOrderItem(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validOrderItem(pRequest.getParameterMap()))
    {
      OrderItem orderItem = getOrderItem(pRequest);

      if (orderItem != null)
      {
        orderItem.setCreationDate(new Date());

        if (!(orderItem.getTotalAmount() > 0.0))
        {
          double totalPrice = ((orderItem.getNumberOfItems() * orderItem
              .getProduct().getPrice()) - (orderItem.getNumberOfItems()
              * orderItem.getProduct().getPrice() * orderItem.getDiscount() / 100));
          orderItem.setTotalAmount(totalPrice);
        }

        try
        {
          mOrderItemFactory.createOrderItem(orderItem);
          pResponse.sendRedirect(getBaseURL(pRequest)
              + pRequest.getContextPath() + "/viewOrder.jsp?"
              + AtelierEntity.KEY_ORDER_ID + "=" + orderItem.getOrderId() + "&"
              + AtelierEntity.URI_NEW + "=" + AtelierEntity.URI_SUCCESS);
        }
        catch (DataOperationFailedException dofe)
        {
          mLogger.error(dofe);
          sendErrorMessage(orderItem.getOrderId(), AtelierEntity.URI_NEW,
              pRequest, pResponse);
        }
      }
    }
  }

  private void sendErrorMessage(
    final int pOrderId,
    final String pOperation,
    final HttpServletRequest pRequest,
    final HttpServletResponse pResponse) throws IOException
  {
    pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
        + "/viewOrder.jsp?" + OrderItem.KEY_ORDER_ID + "=" + pOrderId + "&"
        + pOperation + "=" + AtelierEntity.URI_FAILED);
  }

  private void deleteOrderItem(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    try
    {
      int orderItemId = getInt(pRequest, OrderItem.KEY_ORDER_ITEM_ID);
      int customerOrderId = getInt(pRequest, Customer.KEY_ORDER_ID);

      if (orderItemId != -1 && customerOrderId != -1)
      {
        mOrderItemFactory.deleteOrderItem(orderItemId);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewOrder.jsp?" + Customer.KEY_ORDER_ID + "=" + customerOrderId
            + "&" + Customer.URI_DELETE + "=" + Customer.URI_SUCCESS);
        return;
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    sendErrorMessage(Customer.URI_DELETE + "=" + Customer.URI_FAILED, pRequest,
        pResponse);
  }

}
