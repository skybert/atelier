package no.studios.atelier.data;

import junit.framework.TestCase;
import no.studios.atelier.model.*;
import java.util.List;

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
public class ProductTypeFactoryTest extends TestCase
{

  /**
   * Creates a new <code>ProductTypeFactoryTest</code> instance.
   * 
   */
  public ProductTypeFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetProductType() throws Exception
  {
    int id = 1;
    ProductType productType = mObjectFactory.getProductType(id);
    assertNotNull("should not be null", productType);
  }

  public void testGetAllProductTypes() throws Exception
  {
    List<ProductType> productTypeList = mObjectFactory.getAllProductTypes();
    assertNotNull("should not be null", productTypeList);
    assertTrue("should be something there", productTypeList.size() > 0);
  }

  public void testCreateProductType() throws Exception
  {
    ProductType productType = new DefaultProductType();
    productType.setName("Test product type-" + System.currentTimeMillis());
    mObjectFactory.createProductType(productType);
  }

  public void testUpdateProductType() throws Exception
  {
    int id = 1;
    ProductType productType = mObjectFactory.getProductType(id);
    String originalName = productType.getName();
    productType.setName(originalName + "-changed");
    mObjectFactory.updateProductType(productType);

    ProductType updatedProductType = mObjectFactory.getProductType(id);
    assertEquals("name should have been changed", originalName + "-changed",
        updatedProductType.getName());

    // changing it back
    updatedProductType.setName(originalName);
    mObjectFactory.updateProductType(updatedProductType);
  }

  public void testGetPaymentType() throws Exception
  {
    int id = 1;
    PaymentType paymentType = mObjectFactory.getPaymentType(id);
    assertNotNull("should not be null", paymentType);
  }
}
