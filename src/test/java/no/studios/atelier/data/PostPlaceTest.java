package no.studios.atelier.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public final class PostPlaceTest extends DBTestBase
{

  @Before
  public void setUp() throws Exception
  {
    super.setUp();
  }

  @Test
  public void testPersistence()
  {
    em.getTransaction().begin();
    String postCode = "1700" + System.currentTimeMillis();
    String place = "Sarpsborg";
    PostPlace postPlace = new PostPlace(postCode, place);
    // postPlace.setCreationDate(new Date());
    em.persist(postPlace);
    assertTrue(em.contains(postPlace));

    PostPlace readAgain = em.find(PostPlace.class, postPlace.getId());
    assertNotNull(readAgain);
    assertEquals(postCode, readAgain.getPostCode());
    assertEquals(place, readAgain.getPostPlace());

    em.getTransaction().commit();

  }

}
