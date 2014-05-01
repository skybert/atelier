package no.studios.atelier.util;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import no.studios.atelier.data.*;
import java.util.*;

/**
 * PaymentTypeComparatorTest.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class PaymentTypeComparatorTest extends TestCase
{
  public void testSortPaymentType() throws Exception
  {
    List<PaymentType> list = new ArrayList<PaymentType>();
    PaymentType status1 = new DefaultPaymentType();
    status1.setId(1);
    status1.setName("Kontant");
    PaymentType status2 = new DefaultPaymentType();
    status2.setId(2);
    status2.setName("Faktura");
    PaymentType status3 = new DefaultPaymentType();
    status3.setId(3);
    status3.setName("Noe annet");
    list.add(status1);
    list.add(status2);
    list.add(status3);

    Collections.sort(list, new PaymentTypeComparator());

    // the first should always be "Kontant"
    PaymentType paymentType = list.get(0);
    assertEquals("should be 'Kontant'", "Kontant", paymentType.getName());
  }

  /**
   * Creates a new <code>PaymentTypeComparatorTest</code> instance.
   * 
   */
  public PaymentTypeComparatorTest()
  {

  }

}
