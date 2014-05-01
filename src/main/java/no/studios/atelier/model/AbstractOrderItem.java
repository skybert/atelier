package no.studios.atelier.model;

import java.util.*;

/**
 * an OrderItem.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public abstract class AbstractOrderItem extends AbstractAtelierEntity implements
    OrderItem
{
  private Product mProduct;
  private String mComment;
  private double mDiscount;
  private double mTotalAmount;
  protected int mNumberOfItems;
  private int mOrderId;

  public int getOrderId()
  {
    return mOrderId;
  }

  public void setOrderId(final int pOrderId)
  {
    mOrderId = pOrderId;
  }

  public Product getProduct()
  {
    return mProduct;
  }

  public void setProduct(final Product pProduct)
  {
    mProduct = pProduct;
  }

  public String getComment()
  {
    return mComment;
  }

  public void setComment(final String pComment)
  {
    mComment = pComment;
  }

  public double getDiscount()
  {
    return mDiscount;
  }

  public void setDiscount(final double pDiscount)
  {
    mDiscount = pDiscount;
  }

  public double getTotalAmount()
  {
    return mTotalAmount;
  }

  public void setTotalAmount(final double pTotalAmount)
  {
    mTotalAmount = pTotalAmount;
  }

  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    final int sbSize = 1000;
    final String variableSeparator = "  ";
    final StringBuffer sb = new StringBuffer(sbSize);

    sb.append(getClass().getName() + "[");
    sb.append("productId=").append(mProduct != null ? mProduct.getId() : "n/a");
    sb.append(variableSeparator);
    sb.append("mNumberOfItems=").append(mNumberOfItems);
    sb.append(variableSeparator);
    sb.append("mDiscount=").append(mDiscount);
    sb.append(variableSeparator);
    sb.append("mTotalAmount=").append(mTotalAmount);
    sb.append("]");
    return sb.toString();
  }

  public String getType()
  {
    return TYPE_ORDER_ITEM;
  }

  public void setNumberOfItems(final int pNumberOfItems)
  {
    mNumberOfItems = pNumberOfItems;
  }

  public int getNumberOfItems()
  {
    return mNumberOfItems;
  }
}
