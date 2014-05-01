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
public class CustomerFactoryTest extends TestCase
{

  /**
   * Creates a new <code>ObjectFactoryTest</code> instance.
   * 
   */
  public CustomerFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetLastCustomerId() throws Exception
  {
    int lastId = mObjectFactory.getLastCustomerId();
    assertTrue("should be something", lastId != -1);
  }

  public void testGetCustomer() throws Exception
  {
    int id = 1;
    Customer customer = mObjectFactory.getCustomer(id);
    assertNotNull("should not be null", customer);
  }

  public void testCreateCustomer() throws Exception
  {
    Customer customer = new DefaultCustomer();
    String firstName = "firstName-" + Long.toString(System.currentTimeMillis());
    String lastName = "lastName-" + Long.toString(System.currentTimeMillis());
    Date date = new Date();

    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setMobilePhone("+47 944 21 311");
    customer.setEmailAddress("me@online.no");
    customer.setBirthDate(date);
    customer.setCreationDate(date);
    customer.setPostCode("1700");

    String oldArchiveId = Long.toString(System.currentTimeMillis());
    customer.setOldArchiveId(oldArchiveId);

    mObjectFactory.createCustomer(customer);
  }

  public void testUpdateCustomer() throws Exception
  {
    int id = 1;
    Customer customer = mObjectFactory.getCustomer(id);
    assertNotNull("should not be null", customer);

    String firstName = "firstName-" + Long.toString(System.currentTimeMillis());
    String lastName = "lastName-" + Long.toString(System.currentTimeMillis());
    String address = "address-" + Long.toString(System.currentTimeMillis());
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setAddress(address);
    String oldArchiveId = Long.toString(System.currentTimeMillis());
    customer.setOldArchiveId(oldArchiveId);

    mObjectFactory.updateCustomer(customer);
    Customer updatedCustomer = mObjectFactory.getCustomer(id);
    assertEquals("firstName should have been updated", firstName,
        updatedCustomer.getFirstName());
    assertEquals("lastName should have been updated", lastName,
        updatedCustomer.getLastName());
    assertEquals("address should have been updated", address,
        updatedCustomer.getAddress());
    assertEquals("oldArchiveId should have been updated", oldArchiveId,
        updatedCustomer.getOldArchiveId());
  }
}
