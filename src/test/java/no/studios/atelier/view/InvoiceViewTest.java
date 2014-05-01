package no.studios.atelier.view;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import no.studios.atelier.data.*;

import java.util.*;

/**
 * InvoiceViewTest.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $ $Date: 2008/04/04 21:09:21 $
 */
public class InvoiceViewTest extends TestCase
{
  CustomerOrder mOrder;
  InvoiceView mView = new InvoiceView();

  public void setUp()
  {
    mOrder = new DefaultCustomerOrder();

    OrderItem item = new DefaultOrderItem();
    item.setTotalAmount(100.0);

    OrderItem anotherItem = new DefaultOrderItem();
    anotherItem.setTotalAmount(200.0);

    mOrder.addOrderItem(item);
    mOrder.addOrderItem(anotherItem);
  }

  public void testGetTaxTaxNotIncluded() throws Exception
  {
    Invoice invoice = new DefaultInvoice();
    invoice.setTaxIncluded(false);
    invoice.setOrder(mOrder);

    assertEquals("tax should have been added", (300.0 * 0.25),
        mView.getTax(invoice));

  }

  public void testGetTaxTaxIncluded() throws Exception
  {
    Invoice invoice = new DefaultInvoice();
    invoice.setTaxIncluded(true);
    invoice.setOrder(mOrder);

    assertEquals("tax should not have been added", ((300.0 / 1.25) * 0.25),
        mView.getTax(invoice));

  }
}
