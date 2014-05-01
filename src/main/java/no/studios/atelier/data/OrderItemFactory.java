package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * Describe interface OrderItemFactory here.
 * 
 * 
 * Created: Mon Nov 26 20:00:03 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface OrderItemFactory
{
  public List<OrderItem> getAllOrderItems(final int pOrderId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public OrderItem getOrderItem(final int pOrderItemId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createOrderItem(final OrderItem pOrderItem)
    throws DataOperationFailedException;

  public void deleteOrderItem(final int pOrderItemId)
    throws DataOperationFailedException;

  public void updateOrderItem(final OrderItem pOrderItem)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
