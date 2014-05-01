package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.Date;

/**
 * Describe class ObjectFactoryTest here.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class UserFactoryTest extends TestCase
{

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public UserFactoryTest()
  {

  }

  public void testGetUser() throws Exception
  {
    int id = 1;
    User user = mObjectFactory.getUser(id);
    assertNotNull("user should not be null", user);
  }

  public void testCreateUser() throws Exception
  {
    User user = new DefaultUser();
    user.setUserName("user-" + Long.toString(System.currentTimeMillis()));
    Date date = new Date();
    user.setCreationDate(date);
    mObjectFactory.createUser(user);
  }

  public void testUpdateUser() throws Exception
  {
    int id = 1;
    User user = mObjectFactory.getUser(id);
    assertNotNull("should not be null", user);

    String userName = "rambo";
    user.setUserName(userName);
    mObjectFactory.updateUser(user);
    User updatedUser = mObjectFactory.getUser(id);
    assertEquals("username should have been updated", userName,
        updatedUser.getUserName());
  }
}
