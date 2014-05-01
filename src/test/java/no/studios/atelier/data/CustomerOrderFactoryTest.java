package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * Tests both CustomerOrder and OrderItems.
 * 
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.5 $
 */
public class CustomerOrderFactoryTest extends TestCase
{

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public CustomerOrderFactoryTest()
  {

  }

  OrderItem mOrderItem;
  OrderItem mOrderItem2;

  public void setUp()
  {
    mOrderItem = new DefaultOrderItem();
    mOrderItem2 = new DefaultOrderItem();

    Date now = new Date();
    mOrderItem.setCreationDate(now);
    mOrderItem2.setCreationDate(now);

    Product product = new DefaultProduct();
    product.setId(1);
    mOrderItem.setProduct(product);

    product = new DefaultProduct();
    product.setId(2);
    mOrderItem2.setProduct(product);

    String comment = "a comment @" + Long.toString(System.currentTimeMillis());
    mOrderItem.setComment(comment);

    comment = "a comment @" + Long.toString(System.currentTimeMillis());
    mOrderItem2.setComment(comment);

    mOrderItem.setDiscount(10.50);
    mOrderItem2.setDiscount(2.0);

    // overriding the total amount.
    mOrderItem.setTotalAmount(500.0);
    mOrderItem2.setTotalAmount(1500.0);

    mOrderItem.setNumberOfItems(10);
    mOrderItem2.setNumberOfItems(5);

  }

  public void testGetCustomerOrder() throws Exception
  {
    int id = 1;
    CustomerOrder customerOrder = mObjectFactory.getCustomerOrder(id);
    assertNotNull("should not be null", customerOrder);
  }

  public void testGetAllCustomerOrders() throws Exception
  {
    int customerId = 1;
    List<CustomerOrder> customerOrderList = mObjectFactory
        .getAllCustomerOrders(customerId);
    assertNotNull("should not be null", customerOrderList);

    for (CustomerOrder customerOrder : customerOrderList)
    {
      assertNotNull("should not be null", customerOrder);
    }
  }

  public void testCreateCustomerOrder() throws Exception
  {
    CustomerOrder customerOrder = new DefaultCustomerOrder();

    Customer customer = new DefaultCustomer();
    customer.setId(1);
    customerOrder.setCustomer(customer);

    PaymentType paymentType = new DefaultPaymentType();
    paymentType.setId(1);
    customerOrder.setPaymentType(paymentType);

    OrderStatus orderStatus = new DefaultOrderStatus();
    orderStatus.setId(1);
    customerOrder.setOrderStatus(orderStatus);

    Date date = new Date();
    customerOrder.setCreationDate(date);
    customerOrder.setUpdatedDate(date);

    customerOrder.addOrderItem(mOrderItem);
    customerOrder.addOrderItem(mOrderItem2);

    mObjectFactory.createCustomerOrder(customerOrder);
  }

  public void testUpdateCustomerOrder() throws Exception
  {
    int id = 1;
    CustomerOrder customerOrder = mObjectFactory.getCustomerOrder(id);
    assertNotNull("should not be null", customerOrder);

    Customer customer = new DefaultCustomer();
    customer.setId(1);
    customerOrder.setCustomer(customer);

    PaymentType paymentType = new DefaultPaymentType();
    paymentType.setId(1);
    customerOrder.setPaymentType(paymentType);

    OrderStatus orderStatus = new DefaultOrderStatus();
    orderStatus.setId(1);
    customerOrder.setOrderStatus(orderStatus);

    Date date = new Date();
    customerOrder.setUpdatedDate(date);
    customerOrder.setDeliveryDate(date);

    mObjectFactory.updateCustomerOrder(customerOrder);

    CustomerOrder updatedCustomerOrder = mObjectFactory.getCustomerOrder(id);
    assertNotNull("should not be null", updatedCustomerOrder.getCustomer());
    assertTrue("should be have been updated",
        customer.getId() == updatedCustomerOrder.getCustomer().getId());

    assertNotNull("should not be null", updatedCustomerOrder.getOrderStatus());
    assertTrue("should be have been updated",
        orderStatus.getId() == updatedCustomerOrder.getOrderStatus().getId());

    assertNotNull("should not be null", updatedCustomerOrder.getPaymentType());
    assertTrue("should be have been updated",
        paymentType.getId() == updatedCustomerOrder.getPaymentType().getId());

    assertNotNull("should not be null", updatedCustomerOrder.getOrderStatus());
    assertTrue("should be have been updated",
        orderStatus.getId() == updatedCustomerOrder.getOrderStatus().getId());

    assertNotNull("should not be null", updatedCustomerOrder.getDeliveryDate());
    assertEquals("delivery date should be have been updated", date.toString(),
        updatedCustomerOrder.getDeliveryDate().toString());

    assertNotNull("should not be null", updatedCustomerOrder.getUpdatedDate());
    assertEquals("updated date should be have been updated", date.toString(),
        updatedCustomerOrder.getUpdatedDate().toString());

  }
}
