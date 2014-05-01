package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * Describe interface OrderStatusFactory here.
 * 
 * 
 * Created: Mon Nov 26 20:00:03 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface OrderStatusFactory
{
  public List<OrderStatus> getAllOrderStatus()
    throws NoSuchObjectException,
    DataOperationFailedException;

  public OrderStatus getOrderStatus(final int pOrderStatusId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createOrderStatus(final OrderStatus pOrderStatus)
    throws DataOperationFailedException;

  public void deleteOrderStatus(final int pOrderStatusId)
    throws DataOperationFailedException;

  public void updateOrderStatus(final OrderStatus pOrderStatus)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
