package no.studios.atelier.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public final class CustomerTest extends DBTestBase
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
    Customer customer = new Customer();
    // Integer id = 1;
    customer.creationDate(new Date());
    // customer.setId(id);
    customer.firstName("First name " + System.currentTimeMillis());
    customer.lastName("Last name " + System.currentTimeMillis());
    customer.birthDate(new Date());
    customer.address("Street @" + System.currentTimeMillis());

    PostPlace postPlace = em.find(PostPlace.class, 1);
    customer.postPlace(postPlace);
    customer.homePhone("12345678");
    customer.mobilePhone("23456789");
    customer.workPhone("09876543");
    customer.emailAddress("user" + System.currentTimeMillis() + "@example.com");
    em.persist(customer);
    assertTrue(em.contains(customer));

    Customer readAgain = em.find(Customer.class, customer.id());
    assertNotNull(readAgain);

    em.getTransaction().commit();

  }

}
