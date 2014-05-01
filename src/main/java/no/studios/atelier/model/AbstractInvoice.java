package no.studios.atelier.model;

import java.util.*;

/**
 * Invoice.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public abstract class AbstractInvoice extends AbstractAtelierEntity implements
    Invoice
{
  private Date mDueDate;
  private boolean mTaxIncluded;
  private CustomerOrder mOrder;

  public CustomerOrder getOrder()
  {
    return mOrder;
  }

  public void setOrder(final CustomerOrder pOrder)
  {
    mOrder = pOrder;
  }

  public Date getDueDate()
  {
    return mDueDate;
  }

  public void setDueDate(final Date pDueDate)
  {
    mDueDate = pDueDate;
  }

  public String getType()
  {
    return TYPE_INVOICE;
  }

  public void setTaxIncluded(final boolean pTaxIncluded)
  {
    mTaxIncluded = pTaxIncluded;
  }

  public boolean getTaxIncluded()
  {
    return mTaxIncluded;
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
    sb.append("mId=").append(getId());
    sb.append(variableSeparator);
    sb.append("mDueDate=").append(mDueDate);
    sb.append(variableSeparator);
    sb.append("mTaxIncluded=").append(mTaxIncluded);
    sb.append(variableSeparator);
    sb.append("mOrder=").append(mOrder);
    sb.append("]");

    return sb.toString();
  }

}
