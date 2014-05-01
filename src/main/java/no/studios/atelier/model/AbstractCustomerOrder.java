package no.studios.atelier.model;

import java.util.*;

/**
 * Describe interface Order here.
 * 
 * 
 * Created: Mon Nov 26 16:26:09 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.9 $
 */
public abstract class AbstractCustomerOrder extends AbstractAtelierEntity
    implements CustomerOrder
{
  private Customer mCustomer;
  private OrderStatus mOrderStatus;
  private Date mDeliveryDate;
  private Date mUpdatedDate;
  private String mComment;
  private double mPaidAmount;
  private double mTotalAmount;
  private PaymentType mPaymentType;
  private List<OrderItem> mOrderItemList = new ArrayList<OrderItem>();
  private boolean mAllowedUsedInNewspaper;
  private boolean mAllowedUsedForMarketing;

  public Customer getCustomer()
  {
    return mCustomer;
  }

  public void setCustomer(final Customer pCustomer)
  {
    mCustomer = pCustomer;
  }

  public Date getDeliveryDate()
  {
    return mDeliveryDate;
  }

  public void setDeliveryDate(final Date pDeliveryDate)
  {
    mDeliveryDate = pDeliveryDate;
  }

  public Date getUpdatedDate()
  {
    return mUpdatedDate;
  }

  public void setUpdatedDate(final Date pUpdatedDate)
  {
    mUpdatedDate = pUpdatedDate;
  }

  public String getComment()
  {
    return mComment;
  }

  public void setComment(final String pComment)
  {
    mComment = pComment;
  }

  public double getPaidAmount()
  {
    return mPaidAmount;
  }

  public void setPaidAmount(final double pPaidAmount)
  {
    mPaidAmount = pPaidAmount;
  }

  public double getTotalAmount()
  {
    return mTotalAmount;
  }

  public void setTotalAmount(final double pTotalAmount)
  {
    mTotalAmount = pTotalAmount;
  }

  public PaymentType getPaymentType()
  {
    return mPaymentType;
  }

  public void setPaymentType(final PaymentType pPaymentType)
  {
    mPaymentType = pPaymentType;
  }

  public OrderStatus getOrderStatus()
  {
    return mOrderStatus;
  }

  public void setOrderStatus(final OrderStatus pOrderStatus)
  {
    mOrderStatus = pOrderStatus;
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
    sb.append("customerId=").append(
        mCustomer != null ? mCustomer.getId() : "n/a");
    sb.append(variableSeparator);
    sb.append("mOrderStatus=").append(mOrderStatus);
    sb.append(variableSeparator);
    sb.append("mDeliveryDate=").append(mDeliveryDate);
    sb.append(variableSeparator);
    sb.append("mUpdatedDate=").append(mUpdatedDate);
    sb.append(variableSeparator);
    sb.append("mPaidAmount=").append(mPaidAmount);
    sb.append(variableSeparator);
    sb.append("mPaymentType=").append(mPaymentType);
    sb.append("]");
    return sb.toString();
  }

  public String getType()
  {
    return TYPE_ORDER;
  }

  public List<OrderItem> getOrderItems()
  {
    return mOrderItemList;
  }

  public void setOrderItems(final List<OrderItem> pOrderItemList)
  {
    mOrderItemList = pOrderItemList;
  }

  public void addOrderItem(final OrderItem pOrderItem)
  {
    // TODO syncronized
    mOrderItemList.add(pOrderItem);
  }

  public void setAllowedUsedInNewspaper(final boolean pAllowed)
  {
    mAllowedUsedInNewspaper = pAllowed;
  }

  public boolean getAllowedUsedInNewspaper()
  {
    return mAllowedUsedInNewspaper;
  }

  public void setAllowedUsedForMarketing(final boolean pAllowed)
  {
    mAllowedUsedForMarketing = pAllowed;
  }

  public boolean getAllowedUsedForMarketing()
  {
    return mAllowedUsedForMarketing;
  }

}
