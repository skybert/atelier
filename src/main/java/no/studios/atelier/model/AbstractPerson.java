package no.studios.atelier.model;

import no.studios.atelier.model.*;
import java.util.Date;

/**
 * Describe class Person here.
 * 
 * 
 * Created: Mon Nov 26 16:12:59 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractPerson extends AbstractAtelierEntity implements
    Person
{
  private Date mBirthDate;
  private String mFirstName;
  private String mLastName;
  private String mAddress;
  private String mPostCode;
  private String mPostPlace;
  private String mEmailAddress;
  private String mHomePhone;
  private String mWorkPhone;
  private String mMobilePhone;

  public String getFirstName()
  {
    return mFirstName;
  }

  public void setFirstName(final String pFirstName)
  {
    mFirstName = pFirstName;
  }

  public String getLastName()
  {
    return mLastName;
  }

  public void setLastName(final String pLastName)
  {
    mLastName = pLastName;
  }

  public String getHomePhone()
  {
    return mHomePhone;
  }

  public void setHomePhone(final String pHomePhone)
  {
    mHomePhone = pHomePhone;
  }

  public String getMobilePhone()
  {
    return mMobilePhone;
  }

  public String getWorkPhone()
  {
    return mWorkPhone;
  }

  public void setWorkPhone(final String pWorkPhone)
  {
    mWorkPhone = pWorkPhone;
  }

  public void setMobilePhone(final String pMobilePhone)
  {
    mMobilePhone = pMobilePhone;
  }

  public String getAddress()
  {
    return mAddress;
  }

  public void setAddress(final String pAddress)
  {
    mAddress = pAddress;
  }

  public String getEmailAddress()
  {
    return mEmailAddress;
  }

  public void setEmailAddress(final String pEmailAddress)
  {
    mEmailAddress = pEmailAddress;
  }

  public void setPostCode(final String pPostCode)
  {
    mPostCode = pPostCode;
  }

  public String getPostCode()
  {
    return mPostCode;
  }

  public void setPostPlace(final String pPostPlace)
  {
    mPostPlace = pPostPlace;
  }

  public String getPostPlace()
  {
    return mPostPlace;
  }

  public Date getBirthDate()
  {
    return mBirthDate;
  }

  public void setBirthDate(final Date pBirthDate)
  {
    mBirthDate = pBirthDate;
  }

  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    final int sbSize = 1000;
    final String variableSeparator = "  ";
    final StringBuffer sb = new StringBuffer(sbSize);

    sb.append("mBirthDate=").append(mBirthDate);
    sb.append(variableSeparator);
    sb.append("mFirstName=").append(mFirstName);
    sb.append(variableSeparator);
    sb.append("mLastName=").append(mLastName);
    sb.append(variableSeparator);
    sb.append("mAddress=").append(mAddress);
    sb.append(variableSeparator);
    sb.append("mPostCode=").append(mPostCode);
    sb.append(variableSeparator);
    sb.append("mEmailAddress=").append(mEmailAddress);
    sb.append(variableSeparator);
    sb.append("mHomePhone=").append(mHomePhone);
    sb.append(variableSeparator);
    sb.append("mWorkPhone=").append(mWorkPhone);
    sb.append(variableSeparator);
    sb.append("mMobilePhone=").append(mMobilePhone);

    return sb.toString();
  }

}
