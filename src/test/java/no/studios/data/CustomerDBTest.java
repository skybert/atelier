package no.studios.data;

import static org.junit.Assert.assertTrue;

import java.sql.DriverManager;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.studios.atelier.model.Customer;

import org.junit.Before;
import org.junit.Test;

public final class CustomerDBTest
{

  private EntityManagerFactory emFactory;

  private EntityManager em;

  @Before
  public void setUp() throws Exception
  {
    System.out.println("Starting in-memory database for unit tests");
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    DriverManager.getConnection(
        "jdbc:derby:memory:unit-testing-jpa;create=true").close();

    emFactory = Persistence.createEntityManagerFactory("test-pu");
    em = emFactory.createEntityManager();
  }

  @Test
  public void testPersistence()
  {
    em.getTransaction().begin();
    Customer customer = new Customer();
    // Integer id = 1;
    customer.setCreationDate(new Date());
    // customer.setId(id);
    customer.setFirstName("Firs name " + System.currentTimeMillis());
    customer.setLastName("Last name " + System.currentTimeMillis());
    customer.setBirthDate(new Date());
    customer.setAddress("Street @" + System.currentTimeMillis());
    customer.setPostCode("0172");
    customer.setHomePhone("12345678");
    customer.setMobilePhone("23456789");
    customer.setWorkPhone("09876543");

    em.persist(customer);
    assertTrue(em.contains(customer));

    for (Integer id = 1; id < 10; id++)
    {
      Customer readCustomer = em.find(Customer.class, id);
      System.out.println("found customer=" + readCustomer);
    }

    em.getTransaction().commit();

  }

}
