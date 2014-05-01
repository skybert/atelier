package no.studios.atelier.model;

import no.studios.atelier.model.*;

/**
 * Common OrderStatus code.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractOrderStatus extends AbstractAtelierEntity
    implements OrderStatus
{
  private String mName;

  public String getType()
  {
    return TYPE_ORDER_STATUS;
  }

  public String getName()
  {
    return mName;
  }

  public void setName(final String pName)
  {
    mName = pName;
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
    sb.append("mName=").append(mName);
    sb.append("]");

    return sb.toString();
  }
}
