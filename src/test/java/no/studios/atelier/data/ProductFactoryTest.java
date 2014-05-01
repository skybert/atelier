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
public class ProductFactoryTest extends TestCase
{

  /**
   * Creates a new <code>ProductFactoryTest</code> instance.
   * 
   */
  public ProductFactoryTest()
  {

  }

  ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void testGetAllProducts() throws Exception
  {
    List<Product> productList = mObjectFactory.getAllProducts();
    assertNotNull("should not be null", productList);
    assertTrue("should be something there", productList.size() > 0);
  }

  public void testGetAllProductsOfType() throws Exception
  {
    List<Product> productList = mObjectFactory.getAllProductsOfType(1);
    assertNotNull("should not be null", productList);
    assertTrue("should be something there", productList.size() > 0);
  }

  public void testGetProduct() throws Exception
  {
    int id = 1;
    Product product = mObjectFactory.getProduct(id);
    assertNotNull("product should not be null", product);
  }

  public void testCreateProduct() throws Exception
  {
    Product product = new DefaultProduct();
    product.setName("product-" + Long.toString(System.currentTimeMillis()));
    ProductType productType = new DefaultProductType();
    productType.setId(1);
    product.setProductType(productType);
    Date date = new Date();
    product.setCreationDate(date);
    product.setPrice(100.50);
    mObjectFactory.createProduct(product);
  }

  public void testUpdateProduct() throws Exception
  {
    int id = 1;
    Product product = mObjectFactory.getProduct(id);
    assertNotNull("should not be null", product);

    String name = "product-" + System.currentTimeMillis();
    product.setName(name);
    mObjectFactory.updateProduct(product);
    Product updatedProduct = mObjectFactory.getProduct(id);
    assertEquals("productname should have been updated", name,
        updatedProduct.getName());
  }
}
