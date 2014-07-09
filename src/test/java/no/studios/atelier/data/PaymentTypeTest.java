package no.studios.atelier.data;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public final class PaymentTypeTest extends DBTestBase
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
    PaymentType paymentType = new PaymentType();
    paymentType.creationDate(new Date());
    paymentType.name("Kontant-" + System.currentTimeMillis());
    em.persist(paymentType);
    assertTrue(em.contains(paymentType));
    em.getTransaction().commit();
  }

}
