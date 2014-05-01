package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * ProductFactory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.8 $
 */
public interface ProductFactory
{
  public final static String DELETE_PRODUCT = "delete-product";
  public final static String GET_ALL_PRODUCTS = "get-all-products";
  public final static String GET_ALL_PRODUCTS_OF_TYPE = "get-all-products-of-type";
  public final static String GET_PRODUCT = "get-product-by-id";
  public final static String GET_LAST_PRODUCT_ID = "get-last-product-id";
  public final static String INSERT_PRODUCT = "insert-product";
  public final static String UPDATE_PRODUCT = "update-product";

  /**
   * Returns a list with all products.
   * 
   * @return a <code>List&lt;Product&gt;</code> value.
   * @exception DataOperationFailedException
   *              if an error occurs
   * @exception NoSuchObjectException
   *              if an error occurs
   */
  public List<Product> getAllProducts()
    throws NoSuchObjectException,
    DataOperationFailedException;

  public List<Product> getAllProductsOfType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public Product getProduct(final int pProductId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createProduct(final Product pProduct)
    throws DataOperationFailedException;

  public void deleteProduct(final int pProductId)
    throws DataOperationFailedException;

  public void updateProduct(final Product pProduct)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public int getLastProductId() throws DataOperationFailedException;
}
