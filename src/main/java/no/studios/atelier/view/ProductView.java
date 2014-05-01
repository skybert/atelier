package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;
import no.studios.atelier.model.*;
import javax.servlet.http.HttpServletRequest;

/**
 * A nice product view, is always nice, i.e. it never throws exceptions, returns
 * emtpy lists and null if only one object is requested (and not a list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.6 $
 */
public final class ProductView extends AbstractView
{
  private ProductFactory mProductFactory;
  private ProductTypeFactory mProductTypeFactory;
  private ProductComparator mProductComparator;
  private ProductTypeComparator mProductTypeComparator;

  /**
   * Creates a new <code>ProductView</code> instance.
   * 
   * @deprecated use {@link ProductView(HttpServletRequest)} instead.
   */
  public ProductView()
  {
    mProductFactory = new DefaultProductFactory();
    mProductTypeFactory = new DefaultProductTypeFactory();
    mProductComparator = new ProductComparator();
    mProductTypeComparator = new ProductTypeComparator();
  }

  public ProductView(final HttpServletRequest pRequest)
  {
    super(pRequest);
    mProductFactory = new DefaultProductFactory();
    mProductTypeFactory = new DefaultProductTypeFactory();
    mProductComparator = new ProductComparator();
    mProductTypeComparator = new ProductTypeComparator();
  }

  public List<ProductType> getAllProductTypes()
  {
    List<ProductType> productTypeList = new ArrayList<ProductType>();

    try
    {
      productTypeList = mProductTypeFactory.getAllProductTypes();
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    Collections.sort(productTypeList, mProductTypeComparator);
    return productTypeList;
  }

  public String getProductId(final HttpServletRequest pRequest)
  {
    return pRequest.getParameter(Product.KEY_PRODUCT_ID);
  }

  /**
   * Uses {@link Product.KEY_PRODUCT_ID} inside the request object to find the
   * Product.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @return a <code>Product</code> value
   */
  public Product getProduct(final HttpServletRequest pRequest)
  {
    String productId = getProductId(pRequest);
    Product product = null;

    if (!StringUtil.isEmpty(productId))
    {
      product = getProduct(productId);
    }

    return product;
  }

  public Product getProduct(final String pId)
  {
    Product product = null;

    int id = -1;

    try
    {
      id = Integer.parseInt(pId);
    }
    catch (NumberFormatException nfe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("could not convert pId=" + pId + " to an int", nfe);
      }
      return product;
    }

    try
    {
      product = mProductFactory.getProduct(id);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }
    return product;
  }

  public List<Product> getAllProducts()
  {
    List<Product> productList = new ArrayList<Product>();

    try
    {
      productList = mProductFactory.getAllProducts();
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    Collections.sort(productList, mProductComparator);
    return productList;
  }

  public List<Product> getAllProductsOfType(final int pProductTypeId)
  {
    List<Product> productList = new ArrayList<Product>();
    return productList;
  }

}