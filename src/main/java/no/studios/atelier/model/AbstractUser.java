package no.studios.atelier.model;

import no.studios.atelier.model.*;

/**
 * Describe interface User here.
 * 
 * 
 * Created: Mon Nov 26 16:20:00 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractUser extends AbstractPerson implements User
{
  private String mUserName;
  private byte[] mEncryptedPassword;

  public String getType()
  {
    return TYPE_USER;
  }

  public String getUserName()
  {
    return mUserName;
  }

  public void setUserName(final String pUserName)
  {
    mUserName = pUserName;
  }

  public byte[] getEncryptedPassword()
  {
    return mEncryptedPassword;
  }

  public void setEncryptedPassword(final byte[] pEncryptedPassowrd)
  {
    mEncryptedPassword = pEncryptedPassowrd;
  }

}
