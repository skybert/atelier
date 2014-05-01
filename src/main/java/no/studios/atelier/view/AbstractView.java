package no.studios.atelier.view;

import java.util.*;
import org.apache.log4j.Logger;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import javax.servlet.http.HttpServletRequest;
import no.studios.atelier.data.*;
import no.studios.atelier.util.StringUtil;

/**
 * Convenience class for all views
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.10 $
 */
public abstract class AbstractView implements View
{
  protected final Logger mLogger = Logger.getLogger(getClass());
  protected ObjectFactory mObjectFactory = new DefaultObjectFactory();
  protected HttpServletRequest mRequest;

  /**
   * Creates a new <code>AbstractView</code> instance.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   */
  public AbstractView(final HttpServletRequest pRequest)
  {
    mRequest = pRequest;
  }

  /**
   * Creates a new <code>AbstractView</code> instance.
   * 
   * @deprecated use the other constructor.
   */
  public AbstractView()
  {
  }

  public List<Integer> getIntList(final String pKey)
  {
    String[] values = mRequest.getParameterValues(pKey);
    List<Integer> list = new ArrayList<Integer>();

    if (values == null)
    {
      return list;
    }

    for (String value : values)
    {
      try
      {
        list.add(Integer.parseInt(value));
      }
      catch (NumberFormatException nfe)
      {
        mLogger.error("failed getting int value list fo pKey=" + pKey, nfe);
      }
    }
    return list;
  }

  public Date getDate(final String pKey)
  {
    String value = mRequest.getParameter(pKey);
    if (value != null)
    {
      return DateUtil.parseSimpleNorwegian(value);
    }

    return null;
  }

  public String print(final String pS)
  {
    return StringUtil.print(pS);
  }

  public String print(final Object pO)
  {
    if (pO == null)
    {
      return "";
    }

    if (pO instanceof Integer)
    {
      return StringUtil.print((Integer) pO);
    }
    return pO.toString();
  }

  public String print(final Date pDate)
  {
    return StringUtil.print(pDate);
  }

  public String print(final double pDouble)
  {
    return StringUtil.print(pDouble);
  }

  public String print(final double pDouble, final boolean pWithoutDecimals)
  {
    return StringUtil.print(pDouble, pWithoutDecimals);
  }

  public String print(final int pInt)
  {
    return StringUtil.print(pInt);
  }

  public String print(final boolean pBool)
  {
    return pBool ? "Ja" : "Nei";
  }

  public String printName(final Customer pCustomer)
  {
    if (pCustomer.getFirstName() != null && pCustomer.getLastName() != null)
    {
      return print(pCustomer.getLastName()) + ", "
          + print(pCustomer.getFirstName());
    }
    else if (pCustomer.getLastName() != null)
    {
      return print(pCustomer.getLastName());
    }
    else if (pCustomer.getFirstName() != null)
    {
      return print(pCustomer.getFirstName());
    }

    return "";
  }

  public String getFormURI(
    final HttpServletRequest pRequest,
    final String pModule,
    final AtelierEntity pEntity)
  {
    return URIBuilder.getFormURI(pRequest, pModule, pEntity);
  }

  public String getURIFragment(final Customer pCustomer)
  {
    return URIBuilder.getURIFragment(pCustomer);
  }

  public String getURIFragment(final CustomerOrder pOrder)
  {
    return URIBuilder.getURIFragment(pOrder);
  }

  public String getURIFragment(final OrderItem pOrderItem)
  {
    return URIBuilder.getURIFragment(pOrderItem);
  }

  public String getURIFragment(final Invoice pInvoice)
  {
    return URIBuilder.getURIFragment(pInvoice);
  }

}
