package no.studios.atelier.model;

/**
 * A user of the Atelier.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface User extends Person
{
  public String getUserName();

  public void setUserName(final String pUserName);

  public byte[] getEncryptedPassword();

  public void setEncryptedPassword(final byte[] pEncryptedPassowrd);

}
