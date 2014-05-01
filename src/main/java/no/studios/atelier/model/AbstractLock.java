package no.studios.atelier.model;

/*
 *           File: $Header: /home/torstein/cvs/atelier/src/main/java/no/studios/atelier/model/AbstractLock.java,v 1.1 2007/12/26 16:07:56 torstein Exp $
 * Last edited by: $Author: torstein $ $Date: 2007/12/26 16:07:56 $
 *   Version     : $Revision: 1.1 $
 */

import java.util.Date;

/**
 * A default implementation of a {@link Lock}.
 * 
 * @author <a href="mailto:torstein@skybert.nu">Torstein Krause Johansen</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractLock implements Lock
{
  protected Date mDate;
  protected User mUser;
  protected String mUserHost;
  protected int mResourceId;
  protected String mResourceType;

  public final Date getDate()
  {
    return mDate;
  }

  public final User getUser()
  {
    return mUser;
  }

  public String getUserHost()
  {
    return mUserHost;
  }

  public void setUserHost(String pUserHost)
  {
    mUserHost = pUserHost;
  }

  public final void setUser(final User pUser)
  {
    mUser = pUser;
  }

  public final int getResourceId()
  {
    return mResourceId;
  }

  public final void setResourceId(final int pResourceId)
  {
    mResourceId = pResourceId;
  }

  public final String getResourceType()
  {
    return mResourceType;
  }

  public final void setResourceType(final String pResourceType)
  {
    mResourceType = pResourceType;
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
    sb.append("mDate=").append(mDate);
    sb.append(variableSeparator);
    sb.append("mUser=").append(mUser);
    sb.append(variableSeparator);
    sb.append("mUserHost=").append(mUserHost);
    sb.append(variableSeparator);
    sb.append("mResourceId=").append(mResourceId);
    sb.append(variableSeparator);
    sb.append("mResourceType=").append(mResourceType);
    sb.append("]");

    return sb.toString();
  }

}
