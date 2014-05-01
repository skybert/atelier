package no.studios.atelier.model;

import java.util.*;

/**
 * An order, if the customer orders multiple order items, these will all be
 * treated as their own order.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.11 $
 */
public interface CustomerOrder extends AtelierEntity
{
  public Customer getCustomer();

  public void setCustomer(final Customer pCustomer);

  public Date getDeliveryDate();

  public void setDeliveryDate(final Date pDeliveryDate);

  public Date getUpdatedDate();

  public void setUpdatedDate(final Date pUpdatedDate);

  public String getComment();

  public void setComment(final String pComment);

  public double getPaidAmount();

  public void setPaidAmount(final double pPaidAmount);

  public void setTotalAmount(final double pTotalAmount);

  public double getTotalAmount();

  public PaymentType getPaymentType();

  public void setPaymentType(final PaymentType pPaymentType);

  public OrderStatus getOrderStatus();

  public void setOrderStatus(final OrderStatus pOrderStatus);

  public List<OrderItem> getOrderItems();

  public void setOrderItems(final List<OrderItem> pOrderItemList);

  public void addOrderItem(final OrderItem pOrderItem);

  public void setAllowedUsedInNewspaper(final boolean pAllowed);

  public boolean getAllowedUsedInNewspaper();

  public void setAllowedUsedForMarketing(final boolean pAllowed);

  public boolean getAllowedUsedForMarketing();
}
