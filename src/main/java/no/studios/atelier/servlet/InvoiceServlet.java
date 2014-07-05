package no.studios.atelier.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;
import no.studios.atelier.model.*;
import java.util.*;

/**
 * Describe class <code>InvoiceServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $ $Date: 2008/04/02 20:45:23 $
 */
public class InvoiceServlet extends AbstractAtelierServlet
{
  private ObjectFactory mInvoiceFactory;

  public InvoiceServlet()
  {
    mObjectFactory = new DefaultObjectFactory();
  }

  /**
   * We're not too picky at the moment :-)
   * 
   * @param pOrderMap
   *          a <code>Map</code> value
   * @return a <code>boolean</code> value
   */
  private boolean validInvoice(final Map pOrderMap)
  {
    return pOrderMap.containsKey(Invoice.KEY_DUE_DATE)
        && pOrderMap.containsKey(Invoice.KEY_ORDER_ID);
  }

  private Invoice getInvoice(final HttpServletRequest pRequest)
  {
    Invoice invoice = new DefaultInvoice();

    // will only be there when editing an existing invoice
    int invoiceId = getInt(pRequest, Invoice.KEY_INVOICE_ID);
    if (invoiceId != -1)
    {
      invoice.setId(invoiceId);
    }

    // will be (at least) passed when creating a new invoice.
    int orderId = getInt(pRequest, Invoice.KEY_ORDER_ID);
    if (orderId != -1)
    {
      try
      {
        CustomerOrder order = mObjectFactory.getCustomerOrder(orderId);
        invoice.setOrder(order);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }
    // if this is an existing invoice, we should be able to find the
    // order by looking up the invoice ID.
    else if (invoiceId != -1)
    {
      List<Integer> orderIdList = null;
      try
      {
        orderIdList = mObjectFactory.getOrderIdByInvoiceId(invoiceId);
        if (orderIdList != null && orderIdList.size() == 1)
        {
          CustomerOrder order = mObjectFactory.getCustomerOrder(orderIdList
              .get(0));
          invoice.setOrder(order);
        }
        else if (orderIdList.size() > 1)
        {
          mLogger.error("there should only be one order for " + " invoiceId="
              + invoiceId + ", but I found + " + orderIdList.size());
        }
      }
      catch (NoSuchObjectException nsoe)
      {
        String errorMessage = "corresponding order could not be found for "
            + "invoiceId=" + invoiceId + " orderIdList=" + orderIdList;
        mLogger.error(errorMessage, nsoe);
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }

    Date dueDate = getDate(pRequest, Invoice.KEY_DUE_DATE);
    boolean taxIncluded = getBoolean(pRequest, Invoice.KEY_TAX_INCLUDED);
    invoice.setDueDate(dueDate);
    invoice.setTaxIncluded(taxIncluded);

    return invoice;
  }

  public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
    throws ServletException,
    IOException
  {
    super.configure(pRequest, pResponse);
    int command = getCommand(pRequest.getRequestURI(), Invoice.URI_INVOICE);

    debug(pRequest);

    switch (command)
    {
      case COMMAND_NEW:
        createInvoice(pRequest, pResponse);
        break;
      case COMMAND_EDIT:
        editInvoice(pRequest, pResponse);
        break;
      case COMMAND_DELETE:
        deleteInvoice(pRequest, pResponse);
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

  private void createInvoice(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validInvoice(pRequest.getParameterMap()))
    {
      try
      {
        Invoice invoice = getInvoice(pRequest);
        invoice.setCreationDate(new Date());
        mObjectFactory.createInvoice(invoice);

        // show the new user
        int invoiceId = mObjectFactory.getLastInvoiceId();
        invoice.setId(invoiceId);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewInvoice.jsp?" + URIBuilder.getURIFragment(invoice) + "&"
            + Invoice.URI_NEW + "=" + Invoice.URI_SUCCESS);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid invoice=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Invoice.URI_NEW + "=" + Invoice.URI_FAILED, pRequest,
        pResponse);
  }

  private void editInvoice(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validInvoice(pRequest.getParameterMap()))
    {
      try
      {
        Invoice invoice = getInvoice(pRequest);
        mObjectFactory.updateInvoice(invoice);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewInvoice.jsp?" + URIBuilder.getURIFragment(invoice) + "&"
            + Invoice.URI_EDIT + "=" + Invoice.URI_SUCCESS);
        // pResponse.setStatus(HttpServletResponse.SC_OK);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid invoice=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Invoice.URI_EDIT + "=" + Invoice.URI_FAILED, pRequest,
        pResponse);
  }

  private void deleteInvoice(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    try
    {
      Invoice invoice = getInvoice(pRequest);
      mObjectFactory.deleteInvoice(invoice.getId());

      CustomerOrder order = invoice.getOrder();
      if (order != null)
      {
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewOrder.jsp?" + URIBuilder.getURIFragment(order) + "&"
            + Invoice.URI_DELETE + "=" + Invoice.URI_SUCCESS);
      }
      else
      {
        pResponse
            .sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath());
      }

      return;
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    sendErrorMessage(Invoice.URI_DELETE + "=" + Invoice.URI_FAILED, pRequest,
        pResponse);
  }

}
