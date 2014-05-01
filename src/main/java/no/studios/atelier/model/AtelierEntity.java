package no.studios.atelier.model;

import java.util.Date;

/**
 * Mother class for Atelier entities.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.14 $
 */
public interface AtelierEntity
{
  public final static String TYPE_UNDEFINED = "type-undefined";
  public final static String TYPE_USER = "type-user";
  public final static String TYPE_ORDER = "type-order";
  public final static String TYPE_INVOICE = "type-invoice";
  public final static String TYPE_ORDER_ITEM = "type-order-item";
  public final static String TYPE_PRODUCT = "type-product";
  public final static String TYPE_POST_PLACE = "type-post-place";
  public final static String TYPE_ORDER_STATUS = "type-order-status";
  public final static String TYPE_CUSTOMER = "type-customer";

  public final static String URI_NEW = "new";
  public final static String URI_EDIT = "edit";
  public final static String URI_DELETE = "delete";
  public final static String URI_SUCCESS = "success";
  public final static String URI_FAILED = "failed";
  public final static String URI_ORDER = "order";
  public final static String URI_INVOICE = "invoice";
  public final static String URI_PRODUCT = "product";
  public final static String URI_ORDER_ITEM = "order-item";
  public final static String URI_TOOLBAR_TYPE = "toolbar-type";

  public final static String DELETE_INVOICE_JSP = "deleteInvoice.jsp";
  public final static String NEW_INVOICE_JSP = "newInvoice.jsp";
  public final static String VIEW_INVOICE_JSP = "viewInvoice.jsp";
  public final static String PRINT_INVOICE_JSP = "printInvoice.jsp";

  public final static String DELETE_ORDER_JSP = "deleteOrder.jsp";
  public final static String NEW_ORDER_JSP = "newOrder.jsp";
  public final static String VIEW_ORDER_JSP = "viewOrder.jsp";
  public final static String PRINT_ORDER_JSP = "printOrder.jsp";

  public final static String TOOLBAR_TYPE_CANCEL = "toolbar-cancel";
  public final static String TOOLBAR_TYPE_INVOICE = "toolbar-invoice";
  public final static String TOOLBAR_TYPE_INVOICE_LIST = "toolbar-invoice-list";
  public final static String TOOLBAR_TYPE_ORDER = "toolbar-order";

  public static final String KEY_ALLOWED_IN_NEWSPAPER = "allowed-for-newspaper";
  public static final String KEY_ALLOWED_FOR_MARKETING = "allowed-for-marketing";
  public static final String KEY_CREATION_DATE = "creation-date";
  public static final String KEY_COMMENT = "comment";
  public static final String KEY_CUSTOMER_ID = "customer-id";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_DISCOUNT = "discount";
  public static final String KEY_DUE_DATE = "due-date";
  public static final String KEY_FROM_DATE = "from-date";
  public static final String KEY_INVOICE_ID = "invoice-id";
  public static final String KEY_NAME = "name";
  public static final String KEY_NEW_INVOICE = "new-invoice";
  public static final String KEY_NEW_ORDER = "new-order";
  public static final String KEY_NUMBER_OF_ITEMS = "number-of-items";
  public static final String KEY_ORDER_ID = "order-id";
  public static final String KEY_ORDER_ITEM_ID = "order-item-id";
  public static final String KEY_PAID_AMOUNT = "paid-amount";
  public static final String KEY_PAYMENT_TYPE_ID = "payment-type-id";
  public static final String KEY_PRICE = "price";
  public static final String KEY_PRODUCTION_TIME = "production-time";
  public static final String KEY_PRODUCT_ID = "product-id";
  public static final String KEY_PRODUCT_ID_LIST = "product-id-list";
  public static final String KEY_PRODUCT_TYPE_ID = "product-type-id";
  public static final String KEY_TAX_INCLUDED = "tax-included";
  public static final String KEY_TO_DATE = "to-date";
  public static final String KEY_TOTAL_AMOUNT = "total-amount";

  public int getId();

  public void setId(final int pId);

  public Date getCreationDate();

  public void setCreationDate(final Date pCreationDate);

  /**
   * Returns the type of the resource.
   * 
   * @return a <code>String</code> value
   */
  public String getType();
}
