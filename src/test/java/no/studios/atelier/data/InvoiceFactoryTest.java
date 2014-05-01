package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * Tests both Invoice and OrderItems.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class InvoiceFactoryTest extends TestCase
{

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public InvoiceFactoryTest()
  {

  }

  public void testGetInvoice() throws Exception
  {
    int id = 1;
    Invoice invoice = mObjectFactory.getInvoice(id);
    assertNotNull("invoice should not be null", invoice);
  }

  public void testCreateInvoice() throws Exception
  {
    Invoice invoice = new DefaultInvoice();
    Date date = new Date();
    invoice.setCreationDate(date);

    Calendar calendar = Calendar.getInstance();
    calendar.roll(Calendar.DAY_OF_MONTH, 15);
    Date dueDate = calendar.getTime();
    invoice.setDueDate(dueDate);

    int orderId = 1;
    CustomerOrder order = mObjectFactory.getCustomerOrder(orderId);
    invoice.setOrder(order);

    mObjectFactory.createInvoice(invoice);
  }

  public void testUpdateInvoice() throws Exception
  {
    int id = 2;
    Invoice invoice = mObjectFactory.getInvoice(id);
    assertNotNull("should not be null", invoice);
    // System.out.println("original invoice=" + invoice);

    boolean newTaxIncluded = true;
    invoice.setTaxIncluded(newTaxIncluded);
    Date newDueDate = new Date();
    invoice.setDueDate(newDueDate);
    mObjectFactory.updateInvoice(invoice);

    Invoice updatedInvoice = mObjectFactory.getInvoice(id);
    // System.out.println("updated invoice=" + updatedInvoice);

    assertEquals("dueDate should have been updated", newDueDate.toString(),
        updatedInvoice.getDueDate().toString());
    assertEquals("taxIncluded should have been updated",
        updatedInvoice.getTaxIncluded(), newTaxIncluded);
  }
}
