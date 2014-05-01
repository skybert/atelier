package no.studios.atelier.data;

import no.studios.atelier.model.*;

/**
 * Describe interface UserFactory here.
 * 
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.4 $ $Date: 2008/11/08 20:51:56 $
 */
public interface UserFactory
{
  public final static String DELETE_USER = "deleteUser";
  public final static String GET_ALL_USERS = "getAllUsers";
  public final static String GET_USER = "getUserById";
  public final static String INSERT_USER = "insertUser";
  public final static String UPDATE_USER = "updateUser";

  public User getUser(final int pUserId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createUser(final User pUser) throws DataOperationFailedException;

  public void deleteUser(final int pUserId) throws DataOperationFailedException;

  public void updateUser(final User pUser)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
