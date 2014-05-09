package no.studios.atelier.data;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javax.persistence.PersistenceException;
import java.util.Date;

import no.studios.atelier.data.CustomerOrder;
import no.studios.atelier.data.PaymentType;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public final class CustomerOrderTest extends DBTestBase
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
    CustomerOrder order = new CustomerOrder();
    order.creationDate(new Date());

    PaymentType paymentType = em.find(PaymentType.class, 1);
    if (paymentType != null)
    {
      order.paymentType(paymentType);
    }

    em.persist(order);

    assertTrue(em.contains(order));
    CustomerOrder readAgainOrder = em.find(CustomerOrder.class, order.getId());
    assertNotNull(readAgainOrder);
    assertEquals(order.getId(), readAgainOrder.getId());

    em.getTransaction().commit();
  }

  @Test
  public void usingNonExistingPaymentTypeShouldFail()
  {
    em.getTransaction().begin();
    CustomerOrder order = new CustomerOrder();

    PaymentType paymentType = new PaymentType();
    paymentType.creationDate(new Date());
    paymentType.name("Kontant" + System.currentTimeMillis());
    paymentType.id(Integer.MAX_VALUE);
    order.paymentType(paymentType);

    try
    {
      em.persist(order);
      fail("Shouldn't be possible to create order with invalid payment type");
      em.getTransaction().commit();
    }
    catch (Exception e)
    {
      assertTrue("Should get a persistence exception here=" + e,
          e instanceof PersistenceException);
      em.getTransaction().rollback();
    }
  }

  @Test
  @Ignore("the @NotNull doesn't do the trick :(")
  public void usingNoPaymentTypeShouldFail()
  {
    em.getTransaction().begin();
    CustomerOrder order = new CustomerOrder();

    try
    {
      em.persist(order);
      fail("Shouldn't be possible to create order with no payment type");
      em.getTransaction().commit();
    }
    catch (Exception e)
    {
      // ok
    }

  }

}
