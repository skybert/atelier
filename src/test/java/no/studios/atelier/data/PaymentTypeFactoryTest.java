package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;

/**
 * Describe class PaymentTypeFactoryTest here.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class PaymentTypeFactoryTest extends TestCase
{

  /**
   * Creates a new <code>PaymentTypeFactoryTest</code> instance.
   * 
   */
  public PaymentTypeFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetPaymentType() throws Exception
  {
    int id = 1;
    PaymentType paymentType = mObjectFactory.getPaymentType(id);
    assertNotNull("should not be null", paymentType);
  }

  public void testCreatePaymentType() throws Exception
  {
    PaymentType paymentType = new DefaultPaymentType();
    paymentType.setName("Test payment type-" + System.currentTimeMillis());
    mObjectFactory.createPaymentType(paymentType);
  }

  public void testUpdatePaymentType() throws Exception
  {
    int id = 1;
    PaymentType paymentType = mObjectFactory.getPaymentType(id);
    String originalName = paymentType.getName();
    paymentType.setName(originalName + "-changed");
    mObjectFactory.updatePaymentType(paymentType);

    PaymentType updatedPaymentType = mObjectFactory.getPaymentType(id);
    assertEquals("name should have been changed", originalName + "-changed",
        updatedPaymentType.getName());

    // changing it back
    updatedPaymentType.setName(originalName);
    mObjectFactory.updatePaymentType(updatedPaymentType);
  }

}
