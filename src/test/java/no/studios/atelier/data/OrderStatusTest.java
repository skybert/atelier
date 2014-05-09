package no.studios.atelier.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public final class OrderStatusTest extends DBTestBase
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

    OrderStatus orderStatus = new OrderStatus();
    orderStatus.creationDate(new Date());
    orderStatus.name("Status-" + System.currentTimeMillis());
    orderStatus.description("Description of " + System.currentTimeMillis());

    em.persist(orderStatus);
    assertTrue(em.contains(orderStatus));
    em.getTransaction().commit();
  }

}
