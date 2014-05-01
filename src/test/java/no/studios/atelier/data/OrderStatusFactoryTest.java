package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;

/**
 * Describe class ObjectFactoryTest here.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class OrderStatusFactoryTest extends TestCase
{

  /**
   * Creates a new <code>OrderStatusFactoryTest</code> instance.
   * 
   */
  public OrderStatusFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetOrderStatus() throws Exception
  {
    int id = 1;
    OrderStatus orderStatus = mObjectFactory.getOrderStatus(id);
    assertNotNull("should not be null", orderStatus);
  }

  public void testGetAllOrderStatus() throws Exception
  {
    List<OrderStatus> orderStatusList = mObjectFactory.getAllOrderStatus();
    assertNotNull("should not be null", orderStatusList);
    assertTrue("should be greater than null", orderStatusList.size() > 0);
  }

  public void testCreateOrderStatus() throws Exception
  {
    OrderStatus orderStatus = new DefaultOrderStatus();
    orderStatus.setName("orderStatus-" + System.currentTimeMillis());
    mObjectFactory.createOrderStatus(orderStatus);
  }

  public void testUpdateOrderStatus() throws Exception
  {
    int id = 1;
    OrderStatus orderStatus = mObjectFactory.getOrderStatus(id);
    String originalName = orderStatus.getName();
    orderStatus.setName(originalName + "-changed");
    mObjectFactory.updateOrderStatus(orderStatus);

    OrderStatus updatedOrderStatus = mObjectFactory.getOrderStatus(id);
    assertEquals("name should have been changed", originalName + "-changed",
        updatedOrderStatus.getName());

    // changing it back
    updatedOrderStatus.setName(originalName);
    mObjectFactory.updateOrderStatus(updatedOrderStatus);
  }

}
