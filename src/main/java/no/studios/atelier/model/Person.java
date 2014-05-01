package no.studios.atelier.model;

import java.util.Date;

/**
 * A person.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.5 $
 */
public interface Person extends AtelierEntity
{
  public static final String KEY_FIRST_NAME = "first-name";
  public static final String KEY_LAST_NAME = "last-name";
  public static final String KEY_ADDRESS = "address";
  public static final String KEY_POST_CODE = "post-code";
  public static final String KEY_HOME_PHONE = "home-phone";
  public static final String KEY_MOBILE_PHONE = "mobile-phone";
  public static final String KEY_WORK_PHONE = "work-phone";
  public static final String KEY_EMAIL = "email";
  public static final String KEY_BIRTH_DATE = "birth-date";

  public String getFirstName();

  public void setFirstName(final String pFirstName);

  public String getLastName();

  public void setLastName(final String pLastName);

  public String getHomePhone();

  public void setHomePhone(final String pHomePhone);

  public String getMobilePhone();

  public void setMobilePhone(final String pMobilePhone);

  public String getWorkPhone();

  public void setWorkPhone(final String pWorkPhone);

  public String getAddress();

  public void setAddress(final String pAddress);

  public String getEmailAddress();

  public void setEmailAddress(final String pEmailAddress);

  public void setPostCode(final String pPostCode);

  public String getPostCode();

  public void setPostPlace(final String pPostPlace);

  public String getPostPlace();

  public Date getBirthDate();

  public void setBirthDate(final Date pBirthDate);
}
