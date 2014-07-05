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
 * Describe class <code>CustomerOrderServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.7 $
 */
public class CustomerOrderServlet extends AbstractAtelierServlet
{
  private ProductFactory mProductFactory;
  private CustomerOrderFactory mCustomerOrderFactory;
  private PaymentTypeFactory mPaymentTypeFactory;

  // TODO proper
  final static long serialVersionUID = 0L;

  public CustomerOrderServlet()
  {
    mProductFactory = new DefaultProductFactory();
    mCustomerOrderFactory = new DefaultCustomerOrderFactory();
    mPaymentTypeFactory = new DefaultPaymentTypeFactory();
  }

  public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
    throws ServletException,
    IOException
  {
    super.configure(pRequest, pResponse);
    int command = getCommand(pRequest.getRequestURI(), CustomerOrder.URI_ORDER);

    debug(pRequest);

    switch (command)
    {
      case COMMAND_NEW:
        createOrder(pRequest, pResponse);
        break;
      case COMMAND_EDIT:
        editOrder(pRequest, pResponse);
        break;
      case COMMAND_DELETE:
        deleteOrder(pRequest, pResponse);
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

    mLogger.debug("finished with doGet()");
  }

  private boolean validOrder(final Map pOrderMap)
  {
    return pOrderMap.containsKey(CustomerOrder.KEY_CUSTOMER_ID)
        && pOrderMap.containsKey(CustomerOrder.KEY_PAYMENT_TYPE_ID);
  }

  private CustomerOrder getCustomerOrder(final HttpServletRequest pRequest)
    throws IOException
  {
    CustomerOrder customerOrder = new DefaultCustomerOrder();

    // will only be there when editing an existing order
    int customerOrderId = getInt(pRequest, CustomerOrder.KEY_ORDER_ID);
    if (customerOrderId != -1)
    {
      customerOrder.setId(customerOrderId);
    }

    try
    {
      int customerId = getInt(pRequest, CustomerOrder.KEY_CUSTOMER_ID);
      if (customerId != -1)
      {
        Customer customer = new DefaultCustomer();
        customer.setId(customerId);
        customerOrder.setCustomer(customer);
      }

      int numberOfItems = getInt(pRequest, CustomerOrder.KEY_NUMBER_OF_ITEMS);
      if (numberOfItems == -1)
      {
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("couldn't find=" + CustomerOrder.KEY_NUMBER_OF_ITEMS
              + " in request");
        }
        // default is one item :-)
        numberOfItems = 1;
      }

      // changing the product changes many things ...
      int productId = getInt(pRequest, CustomerOrder.KEY_PRODUCT_ID);
      if (productId != -1)
      {
        Product product = mProductFactory.getProduct(productId);
        Date deliveryDate = DateUtil.getDateInDays(product.getProductionTime());
        customerOrder.setDeliveryDate(deliveryDate);
        double totalPrice = product.getPrice() * numberOfItems;
      }
      else if (mLogger.isDebugEnabled())
      {
        mLogger.debug("could not find=" + CustomerOrder.KEY_PRODUCT_ID);
      }

      String discount = (String) pRequest
          .getParameter(CustomerOrder.KEY_DISCOUNT);
      if (!StringUtil.isEmpty(discount))
      {
        // customerOrder.setDiscount(Double.parseDouble(discount));
      }

      String paidAmount = (String) pRequest
          .getParameter(CustomerOrder.KEY_PAID_AMOUNT);
      if (!StringUtil.isEmpty(paidAmount))
      {
        customerOrder.setPaidAmount(Double.parseDouble(paidAmount));
      }

      PaymentType paymentType = new DefaultPaymentType();
      int paymentTypeId = getInt(pRequest, CustomerOrder.KEY_PAYMENT_TYPE_ID);
      if (paymentTypeId != -1)
      {
        paymentType = mPaymentTypeFactory.getPaymentType(paymentTypeId);
      }
      else
      {
        // first payment type is the default, see constants.sql
        paymentType.setId(1);
      }
      customerOrder.setPaymentType(paymentType);

      // setting the order status
      OrderStatus orderStatus = new DefaultOrderStatus();
      int statusId = getInt(pRequest, OrderStatus.KEY_STATUS_ID);
      if (statusId != -1)
      {
        orderStatus.setId(statusId);
      }
      else
      {
        // first status type is the default, see constants.sql
        orderStatus.setId(1);
      }
      customerOrder.setOrderStatus(orderStatus);

      boolean allowedInNewspaper = getBoolean(pRequest,
          CustomerOrder.KEY_ALLOWED_IN_NEWSPAPER);
      boolean allowedForMarketing = getBoolean(pRequest,
          CustomerOrder.KEY_ALLOWED_FOR_MARKETING);
      customerOrder.setAllowedUsedInNewspaper(allowedInNewspaper);
      customerOrder.setAllowedUsedForMarketing(allowedForMarketing);

      return customerOrder;
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

  private void editOrder(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validOrder(pRequest.getParameterMap()))
    {
      CustomerOrder updateOrder = getCustomerOrder(pRequest);
      int orderId = getInt(pRequest, CustomerOrder.KEY_ORDER_ID);

      try
      {
        CustomerOrder originalOrder = mCustomerOrderFactory
            .getCustomerOrder(orderId);

        // updated what's different and changable, for instance,
        // customerId is not changable.
        originalOrder.setPaidAmount(updateOrder.getPaidAmount());
        originalOrder.setPaymentType(updateOrder.getPaymentType());
        originalOrder.setUpdatedDate(new Date());
        originalOrder.setOrderStatus(updateOrder.getOrderStatus());
        originalOrder.setComment(updateOrder.getComment());
        originalOrder.setAllowedUsedInNewspaper(updateOrder
            .getAllowedUsedInNewspaper());
        originalOrder.setAllowedUsedForMarketing(updateOrder
            .getAllowedUsedForMarketing());

        mCustomerOrderFactory.updateCustomerOrder(originalOrder);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewOrder.jsp?" + Customer.KEY_CUSTOMER_ID + "="
            + originalOrder.getCustomer().getId() + "&" + Customer.KEY_ORDER_ID
            + "=" + orderId + "&" + CustomerOrder.URI_EDIT + "="
            + CustomerOrder.URI_SUCCESS);
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

      // TODO proper
      sendErrorMessage(-1, pRequest, pResponse);
    }
    else
    {
      // TODO proper
      sendErrorMessage(-1, pRequest, pResponse);
    }
  }

  private void createOrder(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validOrder(pRequest.getParameterMap()))
    {
      CustomerOrder customerOrder = getCustomerOrder(pRequest);

      if (customerOrder != null)
      {
        customerOrder.setCreationDate(new Date());
        int customerId = customerOrder.getCustomer().getId();

        try
        {
          mCustomerOrderFactory.createCustomerOrder(customerOrder);

          int newOrderId = mCustomerOrderFactory.getLastOrderId();
          String redirectTo = getBaseURL(pRequest) + pRequest.getContextPath()
              + "/newOrderItem.jsp?" + Customer.KEY_ORDER_ID + "=" + newOrderId;

          mLogger.debug("redirecting to=" + redirectTo);
          pResponse.sendRedirect(redirectTo);
        }
        catch (DataOperationFailedException dofe)
        {
          mLogger.error(dofe);
          sendErrorMessage(customerId, pRequest, pResponse);
        }
      }
    }
    else
    {
      mLogger.error("non valid order=" + pRequest.getParameterMap());
      sendErrorMessage(-1, pRequest, pResponse);
    }
  }

  private void sendErrorMessage(
    final int pCustomerId,
    final HttpServletRequest pRequest,
    final HttpServletResponse pResponse) throws IOException
  {
    pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
        + "/newOrder.jsp?" + CustomerOrder.KEY_CUSTOMER_ID + "=" + pCustomerId
        + "&new-order=failed" + "&" + pRequest.getQueryString());
  }

  private void deleteOrder(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    try
    {
      int customerOrderId = getInt(pRequest, CustomerOrder.KEY_ORDER_ID);
      int customerId = getInt(pRequest, Customer.KEY_CUSTOMER_ID);

      if (customerOrderId != -1 && customerId != -1)
      {
        mCustomerOrderFactory.deleteCustomerOrder(customerOrderId);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewCustomer.jsp?" + Customer.KEY_CUSTOMER_ID + "="
            + customerId + "&" + Customer.URI_DELETE + "="
            + Customer.URI_SUCCESS);
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
