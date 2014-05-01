package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;
import java.util.Date;

/**
 * Describe class ObjectFactoryTest here.
 * 
 * 
 * Created: Tue Nov 27 20:37:54 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public class CommentFactoryTest extends TestCase
{

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public CommentFactoryTest()
  {

  }

  public void testGetComment() throws Exception
  {
    int id = 1;
    Comment comment = mObjectFactory.getComment(id);
    assertNotNull("should not be null", comment);
  }

  public void testCreateComment() throws Exception
  {
    Comment comment = new DefaultComment();

    User user = new DefaultUser();
    user.setId(2);
    comment.setUser(user);
    comment.setOrderId(1);
    comment.setComment("comment=" + System.currentTimeMillis());

    Date date = new Date();
    comment.setCreationDate(date);

    mObjectFactory.createComment(comment);
  }

  public void testUpdateComment() throws Exception
  {
    int id = 1;
    Comment comment = mObjectFactory.getComment(id);
    assertNotNull("should not be null", comment);

    User user = new DefaultUser();
    user.setId(1);
    comment.setUser(user);

    Date date = new Date();

    mObjectFactory.updateComment(comment);

    Comment updatedComment = mObjectFactory.getComment(id);
  }
}
