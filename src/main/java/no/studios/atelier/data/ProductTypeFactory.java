package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * ProductTypeFactory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.4 $
 */
public interface ProductTypeFactory
{
  public List<ProductType> getAllProductTypes()
    throws DataOperationFailedException;

  public ProductType getProductType(final int pProductTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createProductType(final ProductType pProductType)
    throws DataOperationFailedException;

  public void deleteProductType(final int pProductTypeId)
    throws DataOperationFailedException;

  public void updateProductType(final ProductType pProductType)
    throws NoSuchObjectException,
    DataOperationFailedException;
}
