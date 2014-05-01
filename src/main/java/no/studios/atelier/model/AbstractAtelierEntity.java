package no.studios.atelier.model;

import no.studios.atelier.model.*;
import java.util.Date;

public abstract class AbstractAtelierEntity implements AtelierEntity
{
  private int mId;
  private Date mCreationDate;
  private String mType;

  public int getId()
  {
    return mId;
  }

  public void setId(final int pId)
  {
    mId = pId;
  }

  public Date getCreationDate()
  {
    return mCreationDate;
  }

  public void setCreationDate(final Date pCreationDate)
  {
    mCreationDate = pCreationDate;
  }

  public String getType()
  {
    return TYPE_UNDEFINED;
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
    sb.append("mId=").append(mId);
    sb.append(variableSeparator);
    sb.append("mType=").append(mType);
    sb.append(variableSeparator);
    sb.append("mCreationDate=").append(mCreationDate);
    sb.append("]");

    return sb.toString();
  }

}