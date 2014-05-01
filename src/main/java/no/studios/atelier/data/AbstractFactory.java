package no.studios.atelier.data;

import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.Reader;
import com.ibatis.common.resources.Resources;
import java.sql.SQLException;

/**
 * Common code for all factory implementations.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class AbstractFactory
{
  protected final Logger mLogger = Logger.getLogger(getClass());
  protected SqlMapClient mSQLMapClient;
  private final String IBATIS_CONFIG = "no/studios/atelier/data/SQLMapConfig.xml";

  public final String DELETE_CUSTOMER_ORDER = "deleteCustomerOrder";
  public final String DELETE_INVOICE = "delete-invoice";
  public final String DELETE_ORDER_ITEM = "deleteOrderItem";
  public final String DELETE_ORDER_ITEM_BY_ORDER_ID = "delete-order-item-by-order-id";
  public final String GET_ALL_CUSTOMER_ORDERS = "getAllCustomerOrders";
  public final String GET_CUSTOMER_ORDER = "getCustomerOrderById";
  public final String GET_INVOICE = "get-invoice-by-id";
  public final String GET_INVOICE_ID_BY_ORDER_ID = "get-invoice-id-by-order-id";
  public final String GET_INVOICE_LIST_IN_DATE_RANGE = "get-invoice-list-in-date-range";
  public final String GET_LAST_INVOICE_ID = "get-last-invoice-id";
  public final String GET_LAST_ORDER_ID = "get-last-order-id";
  public final String GET_ORDERS_AFTER_DATE = "get-orders-after-date";
  public final String GET_ORDER_ID_BY_INVOICE_ID = "get-order-id-by-invoice-id";
  public final String GET_ORDER_ID_BY_ITEM_ID = "get-order-id-by-item-id";
  public final String GET_ORDER_ITEM = "getOrderItemById";
  public final String GET_ORDER_ITEMS_BY_ORDER_ID = "get-order-items-by-order-id";
  public final String INSERT_CUSTOMER_ORDER = "insertCustomerOrder";
  public final String INSERT_INVOICE = "insert-invoice";
  public final String INSERT_ORDER_ITEM = "insertOrderItem";
  public final String UPDATE_CUSTOMER_ORDER = "updateCustomerOrder";
  public final String UPDATE_INVOICE = "update-invoice";
  public final String UPDATE_ORDER_ITEM = "updateOrderItem";

  public final String FROM_DATE = "fromDate";
  public final String TO_DATE = "toDate";

  /**
   * Creates a new <code>AbstractFactory</code> instance.
   */
  protected AbstractFactory()
  {
    try
    {
      Reader reader = Resources.getResourceAsReader(IBATIS_CONFIG);
      mSQLMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
      reader.close();
    }
    catch (IOException ioEx)
    {
      String errorMessage = "Something bad happened while building the SqlMapClient.";
      throw new RuntimeException(errorMessage, ioEx);
    }
  }

  protected void endTransaction()
  {
    try
    {
      mSQLMapClient.endTransaction();
    }
    catch (SQLException se)
    {
      mLogger.error("could not end the transaction", se);
    }
  }

}
