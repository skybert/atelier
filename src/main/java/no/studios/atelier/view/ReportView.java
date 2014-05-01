package no.studios.atelier.view;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;

/**
 * A nice report view, is always nice, i.e. it never throws exceptions, returns
 * emtpy lists and null if only one object is requested (and not a list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $ $Date: 2008/11/08 20:51:56 $
 */
public final class ReportView extends AbstractView
{
  private CustomerOrderFactory mCustomerOrderFactory;
  private ReportFactory mReportFactory;

  public ReportView(final HttpServletRequest pRequest)
  {
    super(pRequest);
    mCustomerOrderFactory = new DefaultCustomerOrderFactory();
    mReportFactory = new DefaultReportFactory();
  }

  public List<Map> getOrderedCertainProducts(
    final Date pOrderFromDate,
    final List<Integer> pProductIdList)
  {
    List<Map> result = new ArrayList<Map>();

    try
    {
      result = mReportFactory.getOrderedCertainProducts(pOrderFromDate,
          pProductIdList);
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return result;
  }

  public List<CustomerOrder> getOrdersAfterDate(final String pDate)
  {
    List<CustomerOrder> customerOrderList = new ArrayList<CustomerOrder>();
    try
    {
      Date date = DateUtil.parseSimpleNorwegian(pDate);
      if (date == null)
      {
        return customerOrderList;
      }

      customerOrderList = mCustomerOrderFactory.getOrdersAfterDate(date);
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

    return customerOrderList;
  }

}