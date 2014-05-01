package no.studios.atelier.data;

/*
 *           File: $Header: /home/torstein/cvs/atelier/src/main/java/no/studios/atelier/data/DefaultLock.java,v 1.1 2007/12/26 16:07:56 torstein Exp $
 * Last edited by: $Author: torstein $ $Date: 2007/12/26 16:07:56 $
 *   Version     : $Revision: 1.1 $
 */

import java.util.Date;
import no.studios.atelier.model.AtelierEntity;
import no.studios.atelier.model.User;
import no.studios.atelier.model.AbstractLock;

/**
 * An EngineLock of a {@link AtelierEntity}.
 * 
 * @author <a href="mailto:torstein@skybert.nu">Torstein Krause Johansen</a>
 * @version $Revision: 1.1 $
 */
public class DefaultLock extends AbstractLock
{
  /**
   * Creates a new <code>DefaultLock</code> instance.
   * 
   * @param pEntity
   *          an <code>AtelierEntity</code> value
   * @param pUser
   *          an <code>User</code> value
   * @param pDate
   *          a <code>Date</code> value
   */
  public DefaultLock(AtelierEntity pEntity, User pUser, Date pDate)
  {
    mResourceId = pEntity.getId();
    mResourceType = pEntity.getType();
    mUser = pUser;
    mDate = pDate;
  }

  /**
   * Creates a new <code>DefaultLock</code> instance.
   * 
   * @param pResourceId
   *          an <code>int</code> value
   * @param pResourceType
   *          a <code>String</code> value
   * @param pUser
   *          an <code>User</code> value
   * @param pDate
   *          a <code>Date</code> value
   */
  public DefaultLock(
      int pResourceId,
      String pResourceType,
      User pUser,
      Date pDate)
  {
    mResourceId = pResourceId;
    mResourceType = pResourceType;
    mUser = pUser;
    mDate = pDate;
  }
}
