package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.*;

/**
 * Testing Report factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class ReportFactoryTest extends TestCase
{

  ReportFactory mReportFactory = new DefaultReportFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public ReportFactoryTest()
  {

  }

  public void testGetOrderedCertainProducts() throws Exception
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2001, 1, 1);
    Date creationDate = calendar.getTime();
    List<Integer> productIdList = new ArrayList<Integer>()
    {
      {
        add(1);
        add(2);
        add(4);
        add(8);
      }
    };

    List<Map> list = mReportFactory.getOrderedCertainProducts(creationDate,
        productIdList);
    assertNotNull("should not be null", list);
    assertTrue("should return many rows", list.size() > 0);

    String[] fields = new String[] { "creation_date", "first_name",
        "post_code", "address", "last_name", "paid_amount" };

    for (Map map : list)
    {
      for (String field : fields)
      {
        assertTrue("field should be there in the result",
            map.containsKey(field));
      }
    }
  }
}
