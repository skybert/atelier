package no.studios.atelier.data;

import org.apache.log4j.Logger;
import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * Default implementaiton of the ObjectFactory interface. Provides an access
 * point to all factories.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.21 $
 */
public final class DefaultObjectFactory extends AbstractObjectFactory
{
  private CommentFactory mCommentFactory;
  private UserFactory mUserFactory;
  private CustomerFactory mCustomerFactory;
  private PostPlaceFactory mPostPlaceFactory;
  private ProductFactory mProductFactory;
  private ProductTypeFactory mProductTypeFactory;
  private PaymentTypeFactory mPaymentTypeFactory;
  private OrderStatusFactory mOrderStatusFactory;
  private OrderItemFactory mOrderItemFactory;
  private CustomerOrderFactory mCustomerOrderFactory;
  private InvoiceFactory mInvoiceFactory;

  public DefaultObjectFactory()
  {
    mCustomerFactory = new DefaultCustomerFactory();
    mUserFactory = new DefaultUserFactory();
    mCommentFactory = new DefaultCommentFactory();
    mProductTypeFactory = new DefaultProductTypeFactory();
    mPaymentTypeFactory = new DefaultPaymentTypeFactory();
    mOrderStatusFactory = new DefaultOrderStatusFactory();
    mOrderItemFactory = new DefaultOrderItemFactory();
    mCustomerOrderFactory = new DefaultCustomerOrderFactory();
    mPostPlaceFactory = new DefaultPostPlaceFactory();
    mProductFactory = new DefaultProductFactory();
    mInvoiceFactory = new DefaultInvoiceFactory();
  }

  public void setCustomerFactory(final CustomerFactory pCustomerFactory)
  {
    mCustomerFactory = pCustomerFactory;
  }

  public void setInvoiceFactory(final InvoiceFactory pInvoiceFactory)
  {
    mInvoiceFactory = pInvoiceFactory;
  }

  public void setProductFactory(final ProductFactory pProductFactory)
  {
    mProductFactory = pProductFactory;
  }

  public void setUserFactory(final UserFactory pUserFactory)
  {
    mUserFactory = pUserFactory;
  }

  public void setCommentFactory(final CommentFactory pCommentFactory)
  {
    mCommentFactory = pCommentFactory;
  }

  public int getLastCustomerId() throws DataOperationFailedException
  {
    return mCustomerFactory.getLastCustomerId();
  }

  public Customer getCustomer(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerFactory.getCustomer(pCustomerId);
  }

  public List<Customer> getCustomersByAnyOldId(final String pOldId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerFactory.getCustomersByAnyOldId(pOldId);
  }

  public List<Customer> getCustomersByAnyName(final String pName)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerFactory.getCustomersByAnyName(pName);
  }

  public void createCustomer(final Customer pCustomer)
    throws DataOperationFailedException
  {
    mCustomerFactory.createCustomer(pCustomer);
  }

  public void deleteCustomer(final int pCustomerId)
    throws DataOperationFailedException
  {
    mCustomerFactory.deleteCustomer(pCustomerId);
  }

  public void updateCustomer(final Customer pCustomer)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mCustomerFactory.updateCustomer(pCustomer);
  }

  public ProductType getProductType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mProductTypeFactory.getProductType(pProductTypeId);
  }

  public List<ProductType> getAllProductTypes()
    throws DataOperationFailedException
  {
    return mProductTypeFactory.getAllProductTypes();
  }

  public void createProductType(final ProductType pProductType)
    throws DataOperationFailedException
  {
    mProductTypeFactory.createProductType(pProductType);
  }

  public void deleteProductType(final int pProductTypeId)
    throws DataOperationFailedException
  {
    mProductTypeFactory.deleteProductType(pProductTypeId);
  }

  public void updateProductType(final ProductType pProductType)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mProductTypeFactory.updateProductType(pProductType);
  }

  public List<PaymentType> getAllPaymentTypes()
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mPaymentTypeFactory.getAllPaymentTypes();
  }

  public PaymentType getPaymentType(final int pPaymentTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mPaymentTypeFactory.getPaymentType(pPaymentTypeId);
  }

  public void createPaymentType(final PaymentType pPaymentType)
    throws DataOperationFailedException
  {
    mPaymentTypeFactory.createPaymentType(pPaymentType);
  }

  public void deletePaymentType(final int pPaymentTypeId)
    throws DataOperationFailedException
  {
    mPaymentTypeFactory.deletePaymentType(pPaymentTypeId);
  }

  public void updatePaymentType(final PaymentType pPaymentType)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mPaymentTypeFactory.updatePaymentType(pPaymentType);
  }

  public List<OrderStatus> getAllOrderStatus()
    throws NoSuchObjectException,
    DataOperationFailedException

  {
    return mOrderStatusFactory.getAllOrderStatus();
  }

  public OrderStatus getOrderStatus(final int pOrderStatusId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mOrderStatusFactory.getOrderStatus(pOrderStatusId);
  }

  public void createOrderStatus(final OrderStatus pOrderStatus)
    throws DataOperationFailedException
  {
    mOrderStatusFactory.createOrderStatus(pOrderStatus);
  }

  public void deleteOrderStatus(final int pOrderStatusId)
    throws DataOperationFailedException
  {
    mOrderStatusFactory.deleteOrderStatus(pOrderStatusId);
  }

  public void updateOrderStatus(final OrderStatus pOrderStatus)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mOrderStatusFactory.updateOrderStatus(pOrderStatus);
  }

  public User getUser(final int pUserId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mUserFactory.getUser(pUserId);
  }

  public void createUser(final User pUser) throws DataOperationFailedException
  {
    mUserFactory.createUser(pUser);
  }

  public void deleteUser(final int pUserId) throws DataOperationFailedException
  {
    mUserFactory.deleteUser(pUserId);
  }

  public void updateUser(final User pUser)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mUserFactory.updateUser(pUser);
  }

  public CustomerOrder getCustomerOrder(final int pCustomerOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerOrderFactory.getCustomerOrder(pCustomerOrderId);
  }

  public List<CustomerOrder> getAllCustomerOrders(final int pCustomerId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerOrderFactory.getAllCustomerOrders(pCustomerId);
  }

  public void createCustomerOrder(final CustomerOrder pCustomerOrder)
    throws DataOperationFailedException
  {
    mCustomerOrderFactory.createCustomerOrder(pCustomerOrder);
  }

  public int getLastOrderId() throws DataOperationFailedException
  {
    return mCustomerOrderFactory.getLastOrderId();
  }

  public List<CustomerOrder> getOrdersAfterDate(final Date pDate)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCustomerOrderFactory.getOrdersAfterDate(pDate);
  }

  public void deleteCustomerOrder(final int pCustomerOrderId)
    throws DataOperationFailedException
  {
    mCustomerOrderFactory.deleteCustomerOrder(pCustomerOrderId);
  }

  public void updateCustomerOrder(final CustomerOrder pCustomerOrder)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mCustomerOrderFactory.updateCustomerOrder(pCustomerOrder);
  }

  public PostPlace getPostPlace(final int pPostPlaceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mPostPlaceFactory.getPostPlace(pPostPlaceId);
  }

  public PostPlace getPostPlace(final String pPostCode)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mPostPlaceFactory.getPostPlace(pPostCode);
  }

  public List<PostPlace> getAllPostPlaces()
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mPostPlaceFactory.getAllPostPlaces();
  }

  public void createPostPlace(final PostPlace pPostPlace)
    throws DataOperationFailedException
  {
    mPostPlaceFactory.createPostPlace(pPostPlace);
  }

  public void deletePostPlace(final int pPostPlaceId)
    throws DataOperationFailedException
  {
    mPostPlaceFactory.deletePostPlace(pPostPlaceId);
  }

  public void updatePostPlace(final PostPlace pPostPlace)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mPostPlaceFactory.updatePostPlace(pPostPlace);
  }

  public List<Product> getAllProducts()
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mProductFactory.getAllProducts();
  }

  public List<Product> getAllProductsOfType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mProductFactory.getAllProductsOfType(pProductTypeId);
  }

  public int getLastProductId() throws DataOperationFailedException
  {
    return mProductFactory.getLastProductId();
  }

  public Product getProduct(final int pProductId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mProductFactory.getProduct(pProductId);
  }

  public void createProduct(final Product pProduct)
    throws DataOperationFailedException
  {
    mProductFactory.createProduct(pProduct);
  }

  public void deleteProduct(final int pProductId)
    throws DataOperationFailedException
  {
    mProductFactory.deleteProduct(pProductId);
  }

  public void updateProduct(final Product pProduct)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mProductFactory.updateProduct(pProduct);
  }

  public Comment getComment(final int pCommentId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mCommentFactory.getComment(pCommentId);
  }

  public void createComment(final Comment pComment)
    throws DataOperationFailedException
  {
    mCommentFactory.createComment(pComment);
  }

  public void deleteComment(final int pCommentId)
    throws DataOperationFailedException
  {
    mCommentFactory.deleteComment(pCommentId);
  }

  public void updateComment(final Comment pComment)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mCommentFactory.updateComment(pComment);
  }

  public List<OrderItem> getAllOrderItems(final int pOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mOrderItemFactory.getAllOrderItems(pOrderId);
  }

  public OrderItem getOrderItem(final int pOrderItemId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mOrderItemFactory.getOrderItem(pOrderItemId);
  }

  public void createOrderItem(final OrderItem pOrderItem)
    throws DataOperationFailedException
  {
    mOrderItemFactory.createOrderItem(pOrderItem);
  }

  public void deleteOrderItem(final int pOrderItemId)
    throws DataOperationFailedException
  {
    mOrderItemFactory.deleteOrderItem(pOrderItemId);
  }

  public void updateOrderItem(final OrderItem pOrderItem)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mOrderItemFactory.updateOrderItem(pOrderItem);
  }

  public Invoice getInvoice(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mInvoiceFactory.getInvoice(pInvoiceId);
  }

  public List<Invoice> getInvoiceList(final Date pFromDate, final Date pToDate)
    throws DataOperationFailedException
  {
    return mInvoiceFactory.getInvoiceList(pFromDate, pToDate);
  }

  public void createInvoice(final Invoice pInvoice)
    throws DataOperationFailedException
  {
    mInvoiceFactory.createInvoice(pInvoice);
  }

  public void deleteInvoice(final int pInvoiceId)
    throws DataOperationFailedException
  {
    mInvoiceFactory.deleteInvoice(pInvoiceId);
  }

  public void updateInvoice(final Invoice pInvoice)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    mInvoiceFactory.updateInvoice(pInvoice);
  }

  public int getLastInvoiceId() throws DataOperationFailedException
  {
    return mInvoiceFactory.getLastInvoiceId();
  }

  public List<Integer> getOrderIdByInvoiceId(final int pInvoiceId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    return mInvoiceFactory.getOrderIdByInvoiceId(pInvoiceId);
  }

  public List<Integer> getInvoiceIdByOrderId(final int pOrderId)
    throws DataOperationFailedException
  {
    return mInvoiceFactory.getInvoiceIdByOrderId(pOrderId);
  }

}
