package no.studios.atelier.util;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import no.studios.atelier.data.*;
import java.util.*;

/**
 * OrderStatusComparatorTest.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class OrderStatusComparatorTest extends TestCase
{
  public void testSortOrderStatus() throws Exception
  {
    List<OrderStatus> list = new ArrayList<OrderStatus>();
    OrderStatus status1 = new DefaultOrderStatus();
    status1.setId(1);
    status1.setName("Ny");
    OrderStatus status2 = new DefaultOrderStatus();
    status2.setId(2);
    status2.setName("Ferdig");
    OrderStatus status3 = new DefaultOrderStatus();
    status3.setId(3);
    status3.setName("Noe annet");
    list.add(status1);
    list.add(status2);
    list.add(status3);

    Collections.sort(list, new OrderStatusComparator());

    // the first should always be "Ny"
    OrderStatus status = list.get(0);
    assertEquals("should be 'Ny'", "Ny", status.getName());
  }

  /**
   * Creates a new <code>OrderStatusComparatorTest</code> instance.
   * 
   */
  public OrderStatusComparatorTest()
  {

  }

}
