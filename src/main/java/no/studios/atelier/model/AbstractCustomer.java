package no.studios.atelier.model;

import java.util.*;

/**
 * Describe interface Customer here.
 * 
 * 
 * Created: Mon Nov 26 16:26:09 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractCustomer extends AbstractPerson implements
    Customer
{
  private String mOldCustomerId;
  private String mOldArchiveId;

  public String getOldArchiveId()
  {
    return mOldArchiveId;
  }

  public void setOldArchiveId(final String pArchiveId)
  {
    mOldArchiveId = pArchiveId;
  }

  public String getOldCustomerId()
  {
    return mOldCustomerId;
  }

  public void setOldCustomerId(final String pCustomerId)
  {
    mOldCustomerId = pCustomerId;
  }

}
