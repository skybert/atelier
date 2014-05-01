package no.studios.atelier.util;

import javax.servlet.http.HttpServletRequest;
import no.studios.atelier.model.AtelierEntity;
import no.studios.atelier.model.Customer;
import no.studios.atelier.model.CustomerOrder;
import no.studios.atelier.model.Invoice;
import no.studios.atelier.model.OrderItem;

/**
 * Builds URIs for Atelier.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $ $Date: 2008/04/02 20:45:23 $
 */
public final class URIBuilder
{

  /**
   * Creates a new <code>URIBuilder</code> instance.
   * 
   */
  private URIBuilder()
  {

  }

  public static String getFormURI(
    final HttpServletRequest pRequest,
    final String pModule,
    final AtelierEntity pEntity)
  {
    return pRequest.getContextPath() + "/" + pModule + "/"
        + (pEntity != null ? AtelierEntity.URI_EDIT : AtelierEntity.URI_NEW);
  }

  public static String getURIFragment(final Customer pCustomer)
  {
    return AtelierEntity.KEY_CUSTOMER_ID + "=" + pCustomer.getId();
  }

  public static String getURIFragment(final CustomerOrder pCustomerOrder)
  {
    return AtelierEntity.KEY_ORDER_ID + "=" + pCustomerOrder.getId();
  }

  public static String getURIFragment(final OrderItem pOrderItem)
  {
    return AtelierEntity.KEY_ORDER_ITEM_ID + "=" + pOrderItem.getId();
  }

  public static String getURIFragment(final Invoice pInvoice)
  {
    return AtelierEntity.KEY_INVOICE_ID + "=" + pInvoice.getId();
  }

  public static String getInvoiceURIFragment(final int pId)
  {
    return AtelierEntity.KEY_INVOICE_ID + "=" + pId;
  }

  public static String getInvoiceURI(
    final CustomerOrder pOrder,
    final int pInvoiceId)
  {
    if (pInvoiceId == -1)
    {
      return "newInvoice.jsp?" + getURIFragment(pOrder);
    }
    else
    {
      return "viewInvoice.jsp?" + getInvoiceURIFragment(pInvoiceId);
    }
  }

  public static String getDeleteURI(final Invoice pInvoice)
  {
    return Invoice.DELETE_INVOICE_JSP + "?" + getURIFragment(pInvoice);
  }

  public static String getPrintURI(final Invoice pInvoice)
  {
    return Invoice.PRINT_INVOICE_JSP + "?" + getURIFragment(pInvoice);
  }

  public static String getViewURI(final Invoice pInvoice)
  {
    return Invoice.VIEW_INVOICE_JSP + "?" + getURIFragment(pInvoice);
  }

  public static String getNewURI(final Invoice pInvoice)
  {
    return Invoice.NEW_INVOICE_JSP + "?" + getURIFragment(pInvoice);
  }

  public static String getDeleteURI(final CustomerOrder pCustomerOrder)
  {
    return CustomerOrder.DELETE_ORDER_JSP + "?"
        + getURIFragment(pCustomerOrder);
  }

  public static String getPrintURI(final CustomerOrder pCustomerOrder)
  {
    return CustomerOrder.PRINT_ORDER_JSP + "?" + getURIFragment(pCustomerOrder);
  }

  public static String getViewURI(final CustomerOrder pCustomerOrder)
  {
    return CustomerOrder.VIEW_ORDER_JSP + "?" + getURIFragment(pCustomerOrder);
  }

  public static String getNewURI(final CustomerOrder pCustomerOrder)
  {
    return CustomerOrder.NEW_ORDER_JSP + "?" + getURIFragment(pCustomerOrder);
  }
}
