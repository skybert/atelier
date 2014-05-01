package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import javax.servlet.http.HttpServletRequest;
import no.studios.atelier.data.NoSuchObjectException;
import no.studios.atelier.data.InvoiceFactory;
import no.studios.atelier.data.DataOperationFailedException;
import no.studios.atelier.data.DefaultInvoiceFactory;
import no.studios.atelier.model.Invoice;
import no.studios.atelier.util.StringUtil;

/**
 * A nice invoice view, is always nice, i.e. it never throws exceptions, returns
 * emtpy lists and null if only one object is requested (and not a list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.7 $ $Date: 2008/10/11 19:17:48 $
 */
public final class InvoiceView extends CustomerOrderView
{
  public static final double TAX = 25.0;

  private InvoiceFactory mInvoiceFactory;

  public InvoiceView(final HttpServletRequest pRequest)
  {
    super(pRequest);
    mInvoiceFactory = new DefaultInvoiceFactory();
  }

  public InvoiceView()
  {
    mInvoiceFactory = new DefaultInvoiceFactory();
  }

  public boolean hasNewInvoiceFailed(final HttpServletRequest pRequest)
  {
    String newInvoice = pRequest.getParameter(Invoice.KEY_NEW_INVOICE);
    boolean newInvoiceAttemptFailed = false;

    if (!StringUtil.isEmpty(newInvoice) && "failed".equals(newInvoice))
    {
      newInvoiceAttemptFailed = true;
    }
    return newInvoiceAttemptFailed;
  }

  /**
   * Uses {@link Invoice.KEY_INVOICE_ID} inside the request object to find the
   * Invoice.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @deprected use {@link getInvoice()} instead.
   * @return a <code>Invoice</code> value
   */
  public Invoice getInvoice(final HttpServletRequest pRequest)
  {
    String invoiceId = pRequest.getParameter(Invoice.KEY_INVOICE_ID);
    Invoice invoice = null;

    if (!StringUtil.isEmpty(invoiceId))
    {
      invoice = getInvoice(invoiceId);
    }

    return invoice;
  }

  public Invoice getInvoice()
  {
    String invoiceId = mRequest.getParameter(Invoice.KEY_INVOICE_ID);
    Invoice invoice = null;

    if (!StringUtil.isEmpty(invoiceId))
    {
      invoice = getInvoice(invoiceId);
    }

    return invoice;
  }

  public Invoice getInvoice(final String pId)
  {
    Invoice invoice = null;

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
      return invoice;
    }

    try
    {
      invoice = mInvoiceFactory.getInvoice(id);
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
    return invoice;
  }

  /**
   * If pInvoice is null, a default due date of <code>15</code> from today is
   * returned.
   * 
   * @param pInvoice
   *          an <code>Invoice</code> value
   * @return a <code>Date</code> value
   */
  public Date getDueDate(final Invoice pInvoice)
  {
    if (pInvoice != null)
    {
      return pInvoice.getDueDate();
    }

    Calendar calendar = Calendar.getInstance();
    calendar.roll(Calendar.DAY_OF_MONTH, 15);

    return calendar.getTime();
  }

  /**
   * Returns the total price of the invoice, including tax. {@link
   * Invoice.getTaxIncluded()} is used to determine whether or not the prices
   * include tax or it should be added.
   * 
   * @param pInvoice
   *          an <code>Invoice</code> value
   * @return a <code>double</code> value
   */
  public double getInvoicePrice(final Invoice pInvoice)
  {
    double invoicePrice = getOrderPrice(pInvoice.getOrder());
    if (!pInvoice.getTaxIncluded())
    {
      invoicePrice += (invoicePrice * TAX / 100);
    }
    return invoicePrice;
  }

  public double getInvoicePrice(final List<Invoice> pInvoiceList)
  {
    double price = 0.0;
    for (Invoice invoice : pInvoiceList)
    {
      price += getInvoicePrice(invoice);
    }
    return price;
  }

  public double getTax(final Invoice pInvoice)
  {
    if (pInvoice.getTaxIncluded())
    {
      // Mama says:
      // getOrderPrice(pInvoice.getOrder()) / 5
      return (getOrderPrice(pInvoice.getOrder()) / (1.0 + TAX / 100.0))
          * (TAX / 100);
    }

    return TAX * getOrderPrice(pInvoice.getOrder()) / 100;
  }

  public List<Invoice> getInvoiceList(final Date pFromDate, final Date pToDate)
  {
    List<Invoice> invoiceList = new ArrayList<Invoice>();
    try
    {
      invoiceList = mInvoiceFactory.getInvoiceList(pFromDate, pToDate);
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }
    return invoiceList;
  }
}