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
public class PostPlaceFactoryTest extends TestCase
{

  /**
   * Creates a new <code>PostPlaceFactoryTest</code> instance.
   * 
   */
  public PostPlaceFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetAllPostPlaces() throws Exception
  {
    List<PostPlace> postPlaceList = mObjectFactory.getAllPostPlaces();
    assertNotNull("should not be null", postPlaceList);
    assertTrue("should be something there", postPlaceList.size() > 0);
  }

  public void testGetPostPlace() throws Exception
  {
    String postCode = "1700";
    PostPlace postPlace = mObjectFactory.getPostPlace(postCode);
    assertNotNull("postPlace should not be null", postPlace);
  }

  public void testCreatePostPlace() throws Exception
  {
    PostPlace postPlace = new DefaultPostPlace();
    String postCode = "postCode-" + Long.toString(System.currentTimeMillis());
    String place = "postPlace-" + Long.toString(System.currentTimeMillis());
    postPlace.setPostCode(postCode);
    postPlace.setPostPlace(place);

    Date date = new Date();
    postPlace.setCreationDate(date);
    mObjectFactory.createPostPlace(postPlace);
  }

  public void testUpdatePostPlace() throws Exception
  {
    int id = 1;
    PostPlace postPlace = mObjectFactory.getPostPlace(id);
    assertNotNull("should not be null", postPlace);

    String postCode = "postCode-" + Long.toString(System.currentTimeMillis());
    String place = "postPlace-" + Long.toString(System.currentTimeMillis());
    postPlace.setPostCode(postCode);
    postPlace.setPostPlace(place);

    mObjectFactory.updatePostPlace(postPlace);

    PostPlace updatedPostPlace = mObjectFactory.getPostPlace(postCode);
    assertEquals("post code should have been updated", postCode,
        updatedPostPlace.getPostCode());
    assertEquals("post place should have been updated", place,
        updatedPostPlace.getPostPlace());
  }
}
