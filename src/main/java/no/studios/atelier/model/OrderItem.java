package no.studios.atelier.model;

import java.util.*;

/**
 * An order, if the customer orders multiple order items, these will all be
 * treated as their own order.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface OrderItem extends AtelierEntity
{
  public static final String KEY_DISCOUNT = "discount";
  public static final String KEY_NUMBER_OF_ITEMS = "number-of-items";

  public Product getProduct();

  public void setProduct(final Product pProduct);

  public String getComment();

  public void setComment(final String pComment);

  public double getDiscount();

  public void setDiscount(final double pDiscount);

  public int getOrderId();

  public void setOrderId(final int pOrderId);

  /**
   * Returns the total amount of the product at the time of purchase. This field
   * is here also because it should be possible for the uesr to override the
   * total sum.
   * 
   * @return a <code>double</code> value
   */
  public double getTotalAmount();

  /**
   * Sets the total amount of the product at the time of purchase. This field is
   * here also because it should be possible for the uesr to override the total
   * sum.
   * 
   * @param pTotalAmount
   *          a <code>double</code> value
   */
  public void setTotalAmount(final double pTotalAmount);

  public void setNumberOfItems(final int pItems);

  public int getNumberOfItems();

}
