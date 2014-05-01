package no.studios.atelier.model;

import no.studios.atelier.model.*;

/**
 * Describe interface Product here.
 * 
 * 
 * Created: Mon Nov 26 16:22:07 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractProductType extends AbstractAtelierEntity
    implements ProductType
{
  private String mName;

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
