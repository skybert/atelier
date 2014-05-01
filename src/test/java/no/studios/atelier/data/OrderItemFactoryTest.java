package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * Tests both OrderItem and OrderItems.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class OrderItemFactoryTest extends TestCase
{

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public OrderItemFactoryTest()
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

    mOrderItem.setOrderId(1);
    mOrderItem2.setOrderId(1);
  }

  public void testCreateOrderItem() throws Exception
  {
    mObjectFactory.createOrderItem(mOrderItem);
  }

  public void testGetOrderItem() throws Exception
  {
    int id = 1;
    OrderItem orderItem = mObjectFactory.getOrderItem(id);
    assertNotNull("should not be null", orderItem);
  }

  public void testUpdateOrderItem() throws Exception
  {
    int id = 1;
    OrderItem orderItem = mObjectFactory.getOrderItem(id);

    Product newProduct = new DefaultProduct();
    newProduct.setId(10);
    orderItem.setProduct(newProduct);

    double newDiscount = 11.1;
    orderItem.setDiscount(newDiscount);

    int newNumberOfItems = 18;
    orderItem.setNumberOfItems(newNumberOfItems);

    String newComment = "new comment @ " + System.currentTimeMillis();
    orderItem.setComment(newComment);

    double newTotalAmount = 2321.55;
    orderItem.setTotalAmount(newTotalAmount);

    mObjectFactory.updateOrderItem(orderItem);

    OrderItem updatedOrderItem = mObjectFactory.getOrderItem(id);
    assertNotNull("should not be null", updatedOrderItem);
    assertNotNull("should not be null", updatedOrderItem.getProduct());
    assertTrue("product should have been updated",
        newProduct.getId() == updatedOrderItem.getProduct().getId());
    assertTrue("discount should be updated",
        newDiscount == updatedOrderItem.getDiscount());
    assertTrue("number of items should be updated",
        newNumberOfItems == updatedOrderItem.getNumberOfItems());
    assertEquals("comment should have been updatd", newComment,
        updatedOrderItem.getComment());
    assertTrue("total should have been updated",
        newTotalAmount == updatedOrderItem.getTotalAmount());
  }

}
