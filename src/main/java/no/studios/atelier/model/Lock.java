package no.studios.atelier.model;

/*
 *           File: $Header: /home/torstein/cvs/atelier/src/main/java/no/studios/atelier/model/Lock.java,v 1.1 2007/12/26 16:07:56 torstein Exp $
 * Last edited by: $Author: torstein $ $Date: 2007/12/26 16:07:56 $
 *   Version     : $Revision: 1.1 $
 */

import java.util.Date;

/**
 * A lock of a {@link AtelierEntity}.
 * 
 * @author <a href="mailto:torstein@skybert.nu">Torstein Krause Johansen</a>
 * @version $Revision: 1.1 $
 */
public interface Lock
{

  /**
   * Gets the date when the lock was created.
   * 
   * @return the value of date
   */
  public Date getDate();

  /**
   * Gets the value of user
   * 
   * @return the value of user
   */
  public User getUser();

  /**
   * Gets the user host.
   * 
   * @return a <code>String</code> value
   */
  public String getUserHost();

  /**
   * Sets the user host.
   * 
   * @param pUserHost
   *          a <code>String</code> value
   */
  public void setUserHost(String pUserHost);

  /**
   * Sets the value of user
   * 
   * @param pUser
   *          Value to assign to this.user
   */
  public void setUser(User pUser);

  /**
   * Gets the value of resourceId
   * 
   * @return the value of resourceId
   */
  public int getResourceId();

  /**
   * Sets the value of resourceId
   * 
   * @param pResourceId
   *          Value to assign to this.resourceId
   */
  public void setResourceId(int pResourceId);

  /**
   * Gets the value of resourceType
   * 
   * @return the value of resourceType
   */
  public String getResourceType();

  /**
   * Sets the value of resourceType
   * 
   * @param pResourceType
   *          Value to assign to this.resourceType
   */
  public void setResourceType(String pResourceType);

}
