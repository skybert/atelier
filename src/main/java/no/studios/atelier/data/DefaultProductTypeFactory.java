package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.List;

/**
 * ProductType factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public final class DefaultProductTypeFactory extends AbstractFactory implements
    ProductTypeFactory
{
  private final static String GET_ALL_PRODUCT_TYPES = "getAllProductTypes";
  private final static String GET_PRODUCT_TYPE = "getProductTypeById";
  private final static String INSERT_PRODUCT_TYPE = "insertProductType";
  private final static String UPDATE_PRODUCT_TYPE = "updateProductType";
  private final static String DELETE_PRODUCT_TYPE = "deleteProductType";

  public List<ProductType> getAllProductTypes()
    throws DataOperationFailedException
  {
    List<ProductType> productTypeList = null;

    try
    {
      mSQLMapClient.startTransaction();
      productTypeList = (List<ProductType>) mSQLMapClient.queryForList(
          GET_ALL_PRODUCT_TYPES, null);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "could not get all produc types";
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
        // doesn't make any sense to deal with this exception.
      }
    }

    return productTypeList;
  }

  public ProductType getProductType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    ProductType productType = null;

    try
    {
      mSQLMapClient.startTransaction();
      productType = (ProductType) mSQLMapClient.queryForObject(
          GET_PRODUCT_TYPE, pProductTypeId);
      mSQLMapClient.commitTransaction();

      if (productType == null)
      {
        throw new NoSuchObjectException("no such ProductType with id="
            + pProductTypeId);
      }
    }
    catch (SQLException sqEx)
    {
      String errorMessage = "unable to get productType with id="
          + pProductTypeId;
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
        // doesn't make any sense to deal with this exception.
      }
    }

    return productType;
  }

  public void createProductType(final ProductType pProductType)
    throws DataOperationFailedException
  {
    try
    {
      mSQLMapClient.startTransaction();
      mSQLMapClient.insert(INSERT_PRODUCT_TYPE, pProductType);
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("unable to create pProductType="
          + pProductType, sqEx);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // doesn't make any sense to deal with this exception.
      }
    }

    // return created;
  }

  public void deleteProductType(final int pProductTypeId)
    throws DataOperationFailedException
  {
    boolean deleted = false;
    String errorMessage = "could not delete pProductTypeId=" + pProductTypeId;

    try
    {
      mSQLMapClient.startTransaction();
      deleted = mSQLMapClient.delete(DELETE_PRODUCT_TYPE, pProductTypeId) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
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
        // doesn't make any sense to deal with this exception.
      }
    }

    if (!deleted)
    {
      throw new DataOperationFailedException(errorMessage);
    }
  }

  public void updateProductType(final ProductType pProductType)
    throws NoSuchObjectException,
    DataOperationFailedException
  {
    boolean updated = false;

    try
    {
      mSQLMapClient.startTransaction();
      updated = mSQLMapClient.update(UPDATE_PRODUCT_TYPE, pProductType) != 0;
      mSQLMapClient.commitTransaction();
    }
    catch (SQLException sqEx)
    {
      throw new DataOperationFailedException("could not update productType="
          + pProductType);
    }
    finally
    {
      try
      {
        mSQLMapClient.endTransaction();
      }
      catch (SQLException e)
      {
        // doesn't make any sense to deal with this exception.
      }
    }

    // return updated;
  }
}
