package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Product factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public final class DefaultProductFactory extends AbstractFactory implements
    ProductFactory
{

  public int getLastProductId() throws DataOperationFailedException
  {
    Integer id = null;
    try
    {
      mSQLMapClient.startTransaction();
      id = (Integer) mSQLMapClient.queryForObject(GET_LAST_PRODUCT_ID, null);
      mSQLMapClient.commitTransaction();

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("last productId=" + id);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get max id";
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(errorMessage, sqEx);
      }

      throw new DataOperationFailedException(errorMessage, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // no need
      }
    }

    return id.intValue();
  }

  private final Object mProductListLock = new Object();
  private List<Product> mProductList = null;

  public List<Product> getAllProducts()
    throws DataOperationFailedException,
    NoSuchObjectException
  {
    synchronized (mProductListLock)
    {
      if (mProductList != null)
      {
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("mProductList is already read, # entries="
              + mProductList.size() + " returning existing list");
        }

        return mProductList;
      }
    }

    try
    {
      mSQLMapClient.startTransaction();
      synchronized (mProductListLock)
      {
        mProductList = (List<Product>) mSQLMapClient.queryForList(
            GET_ALL_PRODUCTS, null);
      }
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "could not get all products";
      mLogger.error(errorMessage);
      throw new DataOperationFailedException(errorMessage, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end the transaction", e);
      }
    }

    synchronized (mProductListLock)
    {
      if (mProductList == null || mProductList.size() == 0)
      {
        throw new NoSuchObjectException("no products found");
      }

      return mProductList;
    }
  }

  public Product getProduct(final int pProductId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    Product product = null;

    try
    {
      mSQLMapClient.startTransaction();
      product = (Product) mSQLMapClient.queryForObject(GET_PRODUCT, pProductId);
      mSQLMapClient.commitTransaction();

      if (product == null)
      {
        throw new NoSuchObjectException("no such Product with id=" + pProductId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get product with id=" + pProductId;
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(errorMessage, sqEx);
      }

      throw new DataOperationFailedException(errorMessage, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end the transaction", e);
      }
    }

    return product;
  }

  public void createProduct(final Product pProduct)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_PRODUCT, pProduct);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed creating product=" + pProduct;
      mLogger.error(errorMessage, e);
      throw new DataOperationFailedException(errorMessage, e);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end the transaction", e);
      }
    }
  }

  public void deleteProduct(final int pProductId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "failed to delete customer order id=" + pProductId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_PRODUCT, pProductId) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException se)
    {
      mLogger.error(errorMessage, se);
      throw new DataOperationFailedException(errorMessage, se);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end the transaction", e);
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage
          + " iBatis returned false");
    }
  }

  public void updateProduct(final Product pProduct)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(UPDATE_PRODUCT, pProduct);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException e)
    {
      String errorMessage = "failed updating product=" + pProduct;
      mLogger.error(errorMessage, e);
      throw new DataOperationFailedException(errorMessage, e);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        mLogger.error("could not end the transaction", e);
      }
    }
  }

  public List<Product> getAllProductsOfType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    List<Product> productList = null;
    try
    {
      productList = (List<Product>) mSQLMapClient.queryForList(
          GET_ALL_PRODUCTS_OF_TYPE, pProductTypeId);
    }
    catch (SQLException se)
    {
      mLogger.error(se);
    }

    return productList;
  }
}
