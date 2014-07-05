package no.studios.atelier.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;
import no.studios.atelier.model.*;
import java.util.*;

/**
 * Describe class <code>ProductServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $ $Date: 2008/01/31 17:40:57 $
 */
public class ProductServlet extends AbstractAtelierServlet
{
  private ProductFactory mProductFactory;
  private ProductTypeFactory mProductTypeFactory;

  public ProductServlet()
  {
    mProductFactory = new DefaultProductFactory();
    mProductTypeFactory = new DefaultProductTypeFactory();
  }

  /**
   * We're not too picky at the moment :-)
   * 
   * @param pOrderMap
   *          a <code>Map</code> value
   * @return a <code>boolean</code> value
   */
  private boolean validProduct(final Map pOrderMap)
  {
    return pOrderMap.containsKey(Product.KEY_NAME);
  }

  private Product getProduct(final HttpServletRequest pRequest)
  {
    Product product = new DefaultProduct();

    // will only be there when editing an existing product
    int productId = getInt(pRequest, Product.KEY_PRODUCT_ID);
    if (productId != -1)
    {
      product.setId(productId);
    }

    String name = pRequest.getParameter(Product.KEY_NAME);
    if (!StringUtil.isEmpty(name))
    {
      product.setName(name);
    }

    String description = pRequest.getParameter(Product.KEY_DESCRIPTION);
    if (!StringUtil.isEmpty(description))
    {
      product.setDescription(description);
    }

    String price = pRequest.getParameter(Product.KEY_PRICE);
    if (!StringUtil.isEmpty(price))
    {
      double dPrice = 0.0;
      try
      {
        dPrice = Double.parseDouble(price);
      }
      catch (NumberFormatException nfe)
      {
        mLogger.error("invalid price=" + price, nfe);
      }

      product.setPrice(dPrice);
    }

    int productionTime = getInt(pRequest, Product.KEY_PRODUCTION_TIME);
    if (productionTime != -1)
    {
      product.setProductionTime(productionTime);
    }

    int productTypeId = getInt(pRequest, Product.KEY_PRODUCT_TYPE_ID);
    if (productTypeId != -1)
    {
      try
      {
        ProductType productType = mProductTypeFactory
            .getProductType(productTypeId);
        product.setProductType(productType);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }

    return product;
  }

  public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
    throws ServletException,
    IOException
  {
    super.configure(pRequest, pResponse);
    int command = getCommand(pRequest.getRequestURI(), Product.URI_PRODUCT);

    debug(pRequest);

    switch (command)
    {
      case COMMAND_NEW:
        createProduct(pRequest, pResponse);
        break;
      case COMMAND_EDIT:
        editProduct(pRequest, pResponse);
        break;
      case COMMAND_DELETE:
        deleteProduct(pRequest, pResponse);
        break;
      default:
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("no recognisable command, URI="
              + pRequest.getRequestURI());
        }

        pResponse
            .sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath());
        break;
    }
  }

  private void createProduct(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validProduct(pRequest.getParameterMap()))
    {
      try
      {
        Product product = getProduct(pRequest);
        product.setCreationDate(new Date());
        mProductFactory.createProduct(product);

        // show the new user
        int productId = mProductFactory.getLastProductId();
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewProduct.jsp?" + Product.KEY_PRODUCT_ID + "=" + productId
            + "&" + Product.URI_NEW + "=" + Product.URI_SUCCESS);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid product=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Product.URI_NEW + "=" + Product.URI_FAILED, pRequest,
        pResponse);
  }

  private void editProduct(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validProduct(pRequest.getParameterMap()))
    {
      try
      {
        Product product = getProduct(pRequest);
        mProductFactory.updateProduct(product);
        pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
            + "/viewProduct.jsp?" + Product.KEY_PRODUCT_ID + "="
            + product.getId() + "&" + Product.URI_EDIT + "="
            + Product.URI_SUCCESS);
        // pResponse.setStatus(HttpServletResponse.SC_OK);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid product=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Product.URI_EDIT + "=" + Product.URI_FAILED, pRequest,
        pResponse);
  }

  private void deleteProduct(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    try
    {
      Product product = getProduct(pRequest);
      mProductFactory.deleteProduct(product.getId());

      pResponse.sendRedirect(getBaseURL(pRequest) + pRequest.getContextPath()
          + "/viewAllProducts.jsp?" + Product.URI_DELETE + "="
          + Product.URI_SUCCESS);
      return;
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    sendErrorMessage(Product.URI_DELETE + "=" + Product.URI_FAILED, pRequest,
        pResponse);
  }

}
